package com.soebes.multithreading.cp.supose.scan;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNLogEntry;

import com.soebes.multithreading.cp.Index;
import com.soebes.multithreading.cp.Version;
import com.soebes.multithreading.cp.VersionRange;

public class ScanVersionRange implements Callable<Index>{
    private static final Logger LOGGER = Logger.getLogger(ScanVersionRange.class);

    private Repository repository;
    private VersionRange versionRange;

    public ScanVersionRange(Repository repository, VersionRange versionRange) {
        super();
        this.repository = repository;
        this.versionRange = versionRange;
    }

    @Override
    public Index call() throws Exception {
        for (Version version : versionRange.getVersionRange()) {
            SVNLogEntry svnLogEntry = version.getLogEntry();
            LOGGER.info("Indexing revision:" + svnLogEntry.getRevision());
        }
        return null;
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
