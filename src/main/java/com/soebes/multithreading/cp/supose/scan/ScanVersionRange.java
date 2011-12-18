package com.soebes.multithreading.cp.supose.scan;

import java.io.File;
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
    private RepositoryScanParameter repositoryScanParameter;

    public ScanVersionRange(RepositoryScanParameter repositoryScanParamter, Repository repository, VersionRange versionRange) {
        super();
        this.repository = repository;
        this.versionRange = versionRange;
        this.repositoryScanParameter = repositoryScanParamter;
    }

    /**
     * Will create a folder name out of the information about the VersionRange we
     * have.
     * @return The name of index which will created by this Task.
     */
    private String getIndexFolderName() {
        return "IDX-" + versionRange.getFirstVersion().getVersion() + "-" + versionRange.getLastVersion().getVersion();
    }

    @Override
    public Index call() throws Exception {
        File indexDirectory = getRepositoryScanParameter().getIndexDirectory();
        //Sub folder of the indexDirectory.
        File taskIndexDirectory = new File(indexDirectory, getIndexFolderName());
        Index idx = new Index();
        idx.setName(getIndexFolderName());
        
        for (Version version : versionRange.getVersionRange()) {
            SVNLogEntry svnLogEntry = version.getLogEntry();
            LOGGER.info("Indexing revision:" + svnLogEntry.getRevision());
        }
        return idx;
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

    public RepositoryScanParameter getRepositoryScanParameter() {
        return repositoryScanParameter;
    }

    public void setRepositoryScanParameter(RepositoryScanParameter repositoryScanParameter) {
        this.repositoryScanParameter = repositoryScanParameter;
    }

}
