package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;

public class RevisionRange {

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
