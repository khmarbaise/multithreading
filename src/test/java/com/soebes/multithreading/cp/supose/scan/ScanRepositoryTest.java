package com.soebes.multithreading.cp.supose.scan;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.testng.annotations.Test;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class ScanRepositoryTest {

    @Test
    public void firstTest() throws MalformedURLException {
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager();

        URI repositoryURI = URI.create("file:///home/kama/test-supose/bass/");
        File indexDirectory = new File("/home/kama/test-supose/test-index-bass/test");
        RepositoryScanParameter parameter = new RepositoryScanParameter(authManager, repositoryURI, indexDirectory);
        
        IScanBehaviour scan = new ScanRepositoryStrategy();
        scan.scanRepository(parameter);
    }
    
}
