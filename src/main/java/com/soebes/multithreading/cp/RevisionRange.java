package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.wc.SVNRevision;

public class RevisionRange {

    /**
     * Constant for the whole repository.
     */
    public static final RevisionRange ALL = new RevisionRange(1, SVNRevision.HEAD.getNumber());

    private long from;
    private long to;


    public RevisionRange(long from, long to) {
        super();
        this.from = from;
        this.to = to;
    }

    public List<RevisionRange> getRevisionRangeBySize(int blockSize) {
        ArrayList<RevisionRange> result = new ArrayList<RevisionRange>();
        for(long start = getFrom(); start<getTo(); start += blockSize) {
            long from = start;
            long to = start + blockSize - 1;
            if (to >= getTo()) {
                to = getTo();
            }
            RevisionRange vr = new RevisionRange(from, to);
            result.add(vr);
        }
        return result;
        
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }
    

}
