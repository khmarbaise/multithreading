package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle a range of versions.
 * 
 * This is implemented to handle version not only based on 
 * integer number. This will work also for version which look
 * like version coming from git.
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

    /**
     * 
     * @param numberOfBlocks
     *            The number of blocks in which the versionRange will
     *            be divided.
     * @param blockNumber
     *            counting the blocks from 0..numberOfBlocks
     * @return The versionRange block.
     */
    public VersionRange getBlockRange(int numberOfBlocks, int blockNumber) {
        int blockSize = versionRange.size() / numberOfBlocks;
        
        int from = blockSize * blockNumber;
        int to = blockSize * (blockNumber + 1) - 1;

        if (to >= versionRange.size()) {
            to = versionRange.size() - 1;
        }
        return getRange(from, to);
    }

    /**
     * Get a new instance of VersionRange with all elements starting
     * <code>from</code> till the end.
     * @param from The start position.
     * @return instance of VersionRange which contains the excerpt of the versions.
     */
    public VersionRange getRange(int from) {
        return getRange(from, versionRange.size() - 1);
    }

    public VersionRange getRange(int from, int to) {
        if (from < 0) {
            throw new IllegalArgumentException("from must be greater or equal 0");
        }
        if (from > to) {
            throw new IllegalArgumentException("from must be less or equal to");
        }
        
        if (from >= versionRange.size()) {
            throw new IllegalArgumentException("from can't be greater than the current size.");
        }
        if (to >= versionRange.size()) {
            throw new IllegalArgumentException("to can't be greater than the current size.");
        }

        ArrayList<Version> result = new ArrayList<Version>();
        for (int position = from; position <= to; position++) {
            result.add(versionRange.get(position));
        }
        return new VersionRange(result);
    }
}
