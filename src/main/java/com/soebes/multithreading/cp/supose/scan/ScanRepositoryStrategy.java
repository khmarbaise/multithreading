package com.soebes.multithreading.cp.supose.scan;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.testng.internal.thread.CountDownAdapter;

import com.soebes.multithreading.cp.Index;
import com.soebes.multithreading.cp.VersionRange;

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

        int numberOfThreads = calculateNumberOfThreads(0.9, 1, 1);

        ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);

        ExecutorCompletionService<Index> execCompletion = new ExecutorCompletionService<Index>(exec);

        Repository repository = new Repository(parameter.getUri().toString(), parameter.getAuthenticationManager());

        ReadLogEntries readLogs = new ReadLogEntries(repository);
        readLogs.readRevisions();
        
        VersionRange vr = readLogs.getVersionRange();

        //FIXME: 300 is only a test value ? (should be made configurable...(property file or command line parameter!)
        List<VersionRange> versionRanges = vr.getRangesBySize(300);

        final CountDownLatch countDown = new CountDownLatch(versionRanges.size());
        
        for (VersionRange versionRange : versionRanges) {

            LOGGER.info("scanRepository:" + versionRange.size());

            ScanVersionRange task = new ScanVersionRange(parameter, versionRange);

            LOGGER.info("exec.submit(task)");

            execCompletion.submit(task);
        }

        try {
            countDown.await();
        } catch (InterruptedException e) {
            LOGGER.error("Failure:", e);
        }

        //Merge generated indexes into a single one (new) or an existing index.
        
    }

}
