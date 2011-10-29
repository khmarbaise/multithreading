package com.soebes.multithreading.cp;

public class Version {

    public final static Version UNDEFINED = new Version();

    private String version;
    
    private Version() {
        this.version = null;
    }

    public Version(String version) {
        this.version = version;
    }

    public Version(Integer version) {
        this.version = version.toString();
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