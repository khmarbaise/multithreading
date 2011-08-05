package com.soebes.multithreading.cp;

import java.util.List;

/**
 * Will handle a revision range
 * like 1..1000 or 
 * abc....ccccc (git like).
 * 
 * @author Karl Heinz Marbaise
 */
public class VersionRange {

    private final List<String> versionRange;

    public VersionRange(List<String> versionRange) {
        this.versionRange = versionRange;
    }

    public final List<String> getVersionRange() {
        return versionRange;
    }

}
