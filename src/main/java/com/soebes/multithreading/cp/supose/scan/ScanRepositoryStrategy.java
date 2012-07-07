package com.soebes.multithreading.cp.supose.scan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.tmatesoft.svn.core.SVNException;

import com.soebes.multithreading.cp.Index;
import com.soebes.multithreading.cp.RevisionRange;

public class ScanRepositoryStrategy implements IScanBehaviour {
    private static final Logger LOGGER = Logger.getLogger(ScanRepositoryStrategy.class);

    private int calculateNumberOfThreads(double utilization, double waittime, double computetime) {
        int numberOfCpus = Runtime.getRuntime().availableProcessors();

        double u_cpu = utilization; // (0..1) target CPU utilization
        double W = waittime; // wait time.
        double C = computetime; // compute time
        int threads = (int) (numberOfCpus * u_cpu * (1.0 + (W / C)));
        LOGGER.info("Number of CPU's: " + numberOfCpus
                + " utilization:" + utilization
                + " waittime:" + waittime
                + " computetime:" + computetime +
                " threads:" + threads);
        return threads;
    }


    /* (non-Javadoc)
     * @see com.soebes.multithreading.cp.supose.scan.IScanBehaviour#scanRepository(com.soebes.multithreading.cp.supose.scan.RepositoryScanParameter)
     */
    @Override
    public void scanRepository(RepositoryScanParameter parameter) {

        int numberOfThreads = 3 * Runtime.getRuntime ( ).availableProcessors ( );

        ExecutorService exec =  new ThreadPoolExecutor(numberOfThreads, 
        	numberOfThreads, 
        	2000L, 
        	TimeUnit.MILLISECONDS, 
        	//FIXME: Check if this is the right approach?
        	new ArrayBlockingQueue<Runnable>(600)
        );

        ExecutorCompletionService<Index> execCompletion = new ExecutorCompletionService<Index>(exec);

        Repository repository = new Repository(parameter.getUri(), parameter.getAuthenticationManager());

        long latestRevision;
        long firstRevision = parameter.getRevisionRange().getFrom();

        if (parameter.getRevisionRange().equals(RevisionRange.ALL)) {
            try {
        	latestRevision = repository.getRepository().getLatestRevision();
            } catch (SVNException e1) {
        	LOGGER.error("Problem during getting the latest revision.", e1);
        	return;
            }
        } else {
            latestRevision = parameter.getRevisionRange().getTo();
        }

        LOGGER.info("We need to read a repository from "+ firstRevision + ".." + latestRevision);

        //FIXME: The following will only work if we have an SVN repository which has long as revision numbers.
        RevisionRange rRange = new RevisionRange(firstRevision, latestRevision);
        
        //FIXME: 300 is only a test value ? (should be made configurable...(property file or command line parameter!)
        List<RevisionRange> revisionRanges = rRange.getRevisionRangeBySize(200);

        for (RevisionRange revisionRange : revisionRanges) {

            ScanVersionRange task = new ScanVersionRange(parameter, revisionRange);

            LOGGER.info("exec.submit(task)");

            execCompletion.submit(task);
        }

        //Wait till all indexers are ended...

        ArrayList<Index> resultList = new ArrayList<Index>();
        
        while (resultList.size() < revisionRanges.size()) {
            Future<Index> result = execCompletion.poll();
            if (result == null) {
                // LOGGER.info("No task has stopped.");
                // Nothing stopped yet.
        	try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    //Intentially left blank.
		}
                continue;
            }

            try {
                Index resultIndex = result.get();
                LOGGER.info("Index " + resultIndex.getName() + " done.");
                resultList.add(resultIndex);
            } catch (InterruptedException e) {
                LOGGER.error("InterruptedException:", e);
            } catch (ExecutionException e) {
                LOGGER.error("ExectionException::", e);
            }
        }

        LOGGER.info("All indexers have been ended.");

        LOGGER.info("Merging all indexes together.");
        try {
	    IndexHelper.mergeIndex(parameter.getIndexDirectory(), resultList);
	} catch (CorruptIndexException e1) {
	    //What to do?
	} catch (IOException e1) {
	    //What to do
	}

        //Merge generated indexes into a single one (new) or an existing index.
        LOGGER.info("Merging all indexes together finished.");

        //Delete the merged indexes...
        for (Index item : resultList) {
            // item.getIndexFolder()
            try {
                LOGGER.info("Deleting " + item.getName ( ));
		FileUtils.deleteDirectory(item.getIndexFolder());
	    } catch (IOException e) {
		LOGGER.error("IOException during deletion of " + item.getIndexFolder(), e);
	    }
        }

    }

}
