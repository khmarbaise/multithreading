package com.soebes.multithreading.cp.supose.scan;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.tmatesoft.svn.core.SVNLogEntry;

import com.soebes.multithreading.cp.Index;
import com.soebes.multithreading.cp.RevisionRange;
import com.soebes.multithreading.cp.Version;
import com.soebes.multithreading.cp.VersionRange;

public class ScanVersionRange implements Callable<Index>{
    private static final Logger LOGGER = Logger.getLogger(ScanVersionRange.class);

    private Repository repository;
    private RevisionRange revisionRange;
    private RepositoryScanParameter repositoryScanParameter;

    public ScanVersionRange(RepositoryScanParameter repositoryScanParamter, RevisionRange revisionRange) {
        super();
        this.repository = new Repository(repositoryScanParamter.getUri(), repositoryScanParamter.getAuthenticationManager());
        this.revisionRange = revisionRange;
        this.repositoryScanParameter = repositoryScanParamter;
        LOGGER.info("ScanVersionRange (" + getRevisionRange().getFrom() + ", " + getRevisionRange().getTo() + ")");
    }

    /**
     * Will create a folder name out of the information about the VersionRange we
     * have.
     * @return The name of index which will created by this Task.
     */
    private String getIndexFolderName() {
        return "IDX-" + Long.toString(revisionRange.getFrom()) + "-" + Long.toString(revisionRange.getTo());
    }

    @Override
    public Index call() throws Exception {

        LOGGER.info("ScanVersionRange reading log entries.");

        ReadLogEntries readLogs = new ReadLogEntries(getRepository(), getRevisionRange().getFrom(), getRevisionRange().getTo());

        readLogs.readRevisions();
        
        LOGGER.info("ScanVersionRange reading log entries done.");

        VersionRange versionRange = readLogs.getVersionRange();

        Analyzer analyzer = AnalyzerFactory.createInstance();

        Index index = new Index(getIndexFolderName(), getRepositoryScanParameter().getIndexDirectory(), analyzer);
        index.createIndex ( );

        long indexedRevisions = 1;
        
        for (Version version : versionRange.getVersionRange()) {
            SVNLogEntry svnLogEntry = version.getLogEntry();
            if (svnLogEntry.getChangedPaths().size() > 0) {

                try {
                    if (indexedRevisions % 100 == 0) {
                        LOGGER.info("Indexing revision:" + svnLogEntry.getRevision());
                    }
                    indexedRevisions ++;
                    scanChangeSet(index, svnLogEntry);
                } catch (Exception e) {
                    LOGGER.error("Error during workOnChangeSet() ", e);
                } finally {
//                    count++;
                }

            } else {
                LOGGER.warn("Empty ChangeSet found in revision: " + svnLogEntry.getRevision());
            }

        }
        
        index.getIndexWriter().close();
        return index;
    }

    private void scanChangeSet(Index index, SVNLogEntry svnLogEntry)
	    throws CorruptIndexException, IOException {
	RevisionDocument rd = new RevisionDocument();
	rd.addTokenizedField(FieldNames.REVISION, Long.toString(svnLogEntry.getRevision()));
	rd.addTokenizedField(FieldNames.MESSAGE, svnLogEntry.getMessage());
	rd.addTokenizedField(FieldNames.AUTHOR, svnLogEntry.getAuthor());
	index.getIndexWriter().addDocument(rd.getDoc());
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public RevisionRange getRevisionRange() {
        return revisionRange;
    }

    public void setRevisionRange(RevisionRange revisionRange) {
        this.revisionRange = revisionRange;
    }

    public RepositoryScanParameter getRepositoryScanParameter() {
        return repositoryScanParameter;
    }

    public void setRepositoryScanParameter(RepositoryScanParameter repositoryScanParameter) {
        this.repositoryScanParameter = repositoryScanParameter;
    }

}
