package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;

/**
 * Will handle a revision range
 * like 1..1000 or 
 * abc....ccccc (git like).
 * 
 * @author Karl Heinz Marbaise
 */
public class VersionRange {

    private final List<Version> versionRange;

    public VersionRange() {
        this.versionRange = new ArrayList<Version>();
    }

    public VersionRange(List<Version> versionRange) {
        this.versionRange = versionRange;
    }

    public final List<Version> getVersionRange() {
        return versionRange;
    }

    public boolean hasVersions() {
        if (versionRange.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return versionRange.size();
    }
    
    public void add(Version version) {
        if (!versionRange.contains(version)) {
            versionRange.add(version);
        }
    }

    public Version getFirstVersion() {
        if (hasVersions()) {
            return versionRange.get(0);
        } else {
            return Version.UNDEFINED;
        }
    }

    public Version getLastVersion() {
        if (hasVersions()) {
            return versionRange.get(versionRange.size() - 1);
        } else {
            return Version.UNDEFINED;
        }
    }
}
