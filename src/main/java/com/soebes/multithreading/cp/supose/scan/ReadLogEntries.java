package com.soebes.multithreading.cp.supose.scan;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.wc.SVNRevision;

import com.soebes.multithreading.cp.Version;
import com.soebes.multithreading.cp.VersionRange;

public class ReadLogEntries {
    private static Logger LOGGER = Logger.getLogger(ReadLogEntries.class);

    private VersionRange versionRange = null;

    private Repository repository;
    /**
     * This defines the revision from where we start to scan the given
     * repository.
     */
    private long startRevision;
    /**
     * This defines the revision to which we will scan the given repository.
     */
    private long endRevision;

    
    public ReadLogEntries(Repository repository, long startRevision, long endRevision) {
        super();
        this.repository = repository;
        this.startRevision = startRevision;
        this.endRevision = endRevision;
        this.versionRange = new VersionRange();
    }

    public ReadLogEntries(Repository repository) {
        this(repository, 1, SVNRevision.HEAD.getNumber());
    }

    public void readRevisions() {
        try {
            readLogEntries();
        } catch (SVNAuthenticationException e) {
            LOGGER.error("Failed through authentication failure.", e);
        } catch (SVNException e) {
            LOGGER.error("Failed through SVNException.", e);
        }
    }

    private void readLogEntries() throws SVNAuthenticationException, SVNException {
        try {
            getRepository().getRepository().log(new String[] { "" }, startRevision, endRevision, true, true,
                    new ISVNLogEntryHandler() {
                        public void handleLogEntry(SVNLogEntry logEntry) {
                            if (getVersionRange().size() % 100 == 0) {
                                LOGGER.info("Scanned 100 revisions.");
                            }
                            getVersionRange().add(new Version(logEntry));
//                            logEntries.add(logEntry);
                        }
                    });
        } catch (SVNAuthenticationException svnae) {
            LOGGER.error("Authentication has failed. '" + getRepository().getUrl() + "'", svnae);
            throw svnae;
        } catch (SVNException svne) {
            LOGGER.error("error while collecting log information for '" + getRepository().getUrl() + "'", svne);
            throw svne;
        } finally {
//            LogEntryStop();
        }
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public VersionRange getVersionRange() {
        return versionRange;
    }

    public void setVersionRange(VersionRange versionRange) {
        this.versionRange = versionRange;
    }

}
