package com.soebes.multithreading.cp.supose.scan;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.wc.SVNRevision;

public class ReadLogEntries {
    private static Logger LOGGER = Logger.getLogger(ReadLogEntries.class);

    private ArrayList<SVNLogEntry> logEntries = null;

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
        this.logEntries = new ArrayList<SVNLogEntry>();
    }

    public ReadLogEntries(Repository repository) {
        super();
        this.repository = repository;
        this.startRevision = 1;
        this.endRevision = SVNRevision.HEAD.getNumber();
        this.logEntries = new ArrayList<SVNLogEntry>();
    }

    public void readRevisions() throws SVNAuthenticationException, SVNException {
        readLogEntries();
    }

    private void readLogEntries() throws SVNAuthenticationException, SVNException {
        try {
            getRepository().getRepository().log(new String[] { "" }, startRevision, endRevision, true, true,
                    new ISVNLogEntryHandler() {
                        public void handleLogEntry(SVNLogEntry logEntry) {
                            logEntries.add(logEntry);
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

    public ArrayList<SVNLogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(ArrayList<SVNLogEntry> logEntries) {
        this.logEntries = logEntries;
    }


}
