package com.soebes.multithreading.cp.supose.scan;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc.admin.SVNAdminClient;

import com.soebes.multithreading.cp.RevisionRange;
import com.soebes.multithreading.cp.TestBase;

public class ScanRepositoryIT extends TestBase {
    private static final Logger LOGGER = Logger.getLogger(ScanRepositoryIT.class);

    @BeforeSuite
    public void createRepository() throws SVNException, FileNotFoundException {
        LOGGER.info("Using the following directory: " + getRepositoryDirectory());
        SVNURL repositoryURL = SVNRepositoryFactory.createLocalRepository(new File(getRepositoryDirectory()), false, true);
        LOGGER.info("Integration Test Repository created.");
        LOGGER.info("The URL:" + repositoryURL.toString());

        LOGGER.info("Start loading the dump file into the repository.");
        //Create the path to the repos.dump file which is located
        //in the arc/test/resources directory.
        SVNAdminClient admin = new SVNAdminClient((ISVNAuthenticationManager)null, null);

//        SVNClientManager client = SVNClientManager.newInstance();
        

//        client.
    }

    
    @Test
    public void firstTest() throws MalformedURLException {
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager();

//        URI repositoryURI = URI.create("file:///home/kama/test-supose/bass/");
        
//        URI repositoryURI = URI.create("file:///home/hadoop/test-repos/bass/");
        URI repositoryURI = URI.create("file:///Users/kama/test-supose/repos/");
        
        File indexDirectory = new File("/Users/kama/ws-git/multithreading/target/index");

//        RevisionRange range = new RevisionRange(1, 50);
        RevisionRange range = RevisionRange.ALL;
        RepositoryScanParameter parameter = new RepositoryScanParameter(authManager, repositoryURI, indexDirectory, range);
        
        IScanBehaviour scan = new ScanRepositoryStrategy();
        scan.scanRepository(parameter);
    }
    
}
