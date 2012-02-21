package com.soebes.multithreading.cp;

import org.tmatesoft.svn.core.SVNLogEntry;

public class Version {

    public final static Version UNDEFINED = new Version();
    public final static Version LAST = new Version("LAST");
    public final static Version FIRST = new Version("FIRST");

    private String version;
    
    //FIXME: This is wrong, cause it's coupled to Subversion but must be independant!
    private SVNLogEntry logEntry;
    
    private Version() {
        this.version = null;
    }

    public Version(String version) {
        this.version = version;
    }

    public Version(Integer version) {
        this.version = version.toString();
    }

    public Version(SVNLogEntry logEntry) {
        super();
        this.logEntry = logEntry;
        this.version = Long.toString(logEntry.getRevision());
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isUNDEFINED() {
        if (UNDEFINED.equals(this)) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isFIRST() {
	if (FIRST.equals(this)) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean isLAST() {
	if (LAST.equals(this)) {
	    return true;
	} else {
	    return false;
	}
    }

    public SVNLogEntry getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(SVNLogEntry logEntry) {
        this.logEntry = logEntry;
    }
    
    @Override
    public String toString() {
        return this.version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        Version other = (Version) obj;
        if (version == null) {
            if (other.version != null) {
                return false;
            }
        } else if (!version.equals(other.version)) {
            return false;
        }
        return true;
    }

}
