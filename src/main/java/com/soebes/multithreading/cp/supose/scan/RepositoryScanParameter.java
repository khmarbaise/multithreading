package com.soebes.multithreading.cp.supose.scan;

import java.io.File;
import java.net.URI;

import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;

import com.soebes.multithreading.cp.RevisionRange;

public class RepositoryScanParameter {

    private ISVNAuthenticationManager authenticationManager;
    
    private URI uri;
    
    private File indexDirectory;
    
    private RevisionRange revisionRange;


    /**
     * @param authenticationManager The authentication manager which is used to authenticate.
     * @param uri
     *            The access URI to the repository either <code>file:///</code>, <code>http://</code>,
     *            <code>https://</code> or <code>svn://</code>.
     * 
     * @param indexDirectory
     *            The directory of the index which will be created.
     */
    public RepositoryScanParameter(ISVNAuthenticationManager authenticationManager, URI uri, File indexDirectory, RevisionRange revisionRange) {
        super();
        this.authenticationManager = authenticationManager;
        this.uri = uri;
        this.indexDirectory = indexDirectory;
        this.revisionRange = revisionRange;
    }

    public RepositoryScanParameter(ISVNAuthenticationManager authenticationManager, URI uri, File indexDirectory) {
	this(authenticationManager, uri, indexDirectory,RevisionRange.ALL);
    }

    public ISVNAuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(ISVNAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public File getIndexDirectory() {
        return indexDirectory;
    }

    public void setIndexDirectory(File indexDirectory) {
        this.indexDirectory = indexDirectory;
    }

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public RevisionRange getRevisionRange() {
	    return revisionRange;
	}

	public void setRevisionRange(RevisionRange revisionRange) {
	    this.revisionRange = revisionRange;
	}
}
