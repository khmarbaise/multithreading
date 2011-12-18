package com.soebes.multithreading.cp.supose.scan;

import java.io.File;
import java.net.URI;

import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;

public class RepositoryScanParameter {

    private ISVNAuthenticationManager authenticationManager;
//    private Filtering filtering;
    
    private URI uri;
    
    private File indexDirectory;

    /**
     * @param authenticationManager The authentication manager which is used to authenticate.
     * @param uri
     *            The access URI to the repository either <code>file:///</code>, <code>http://</code>,
     *            <code>https://</code> or <code>svn://</code>.
     * 
     * @param indexDirectory
     *            The directory of the index which will be created.
     */
    public RepositoryScanParameter(ISVNAuthenticationManager authenticationManager, URI uri, File indexDirectory) {
        super();
        this.authenticationManager = authenticationManager;
        this.uri = uri;
        this.indexDirectory = indexDirectory;
    }

    public ISVNAuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(ISVNAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public File getIndexDirectory() {
        return indexDirectory;
    }

    public void setIndexDirectory(File indexDirectory) {
        this.indexDirectory = indexDirectory;
    }
}
