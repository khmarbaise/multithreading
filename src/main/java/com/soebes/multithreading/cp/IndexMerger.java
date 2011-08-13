package com.soebes.multithreading.cp;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

/**
 * This will represent the repository scanning for a number of given revisions
 * which is usually a block like 1000 or more.
 * 
 * @author Karl Heinz Marbaise
 */
public class IndexMerger implements Callable<Index> {
    private static final Logger LOGGER = Logger.getLogger(IndexMerger.class);

    private Index destinationIndex;
    private List<Index> sourceIndexes;
    
    public IndexMerger(Index destinationIndex, List<Index> sourceIndexes) {
        this.destinationIndex = destinationIndex;
        this.sourceIndexes = sourceIndexes;
    }

    public void waitMilliSeconds(long milliSeconds) {
        long start = System.currentTimeMillis();

        while ((System.currentTimeMillis() - start) < milliSeconds) {
            double s = Math.sin(System.currentTimeMillis());
            // The following line is intended to prevent any compiler from
            // optimizing this code away!
            if (String.valueOf(s).hashCode() == (int) s) {
                System.out.print(" ");
            }
            
            if (((System.currentTimeMillis() - start) % 500) == 0) {
                LOGGER.info("Indexer running.");
            }
        }

    }

    /** {@inheritDoc} */
    public Index call() throws Exception {
        LOGGER.info("IndexMerger Started " + destinationIndex.getName());
        for (Index index : sourceIndexes) {
            Random r = new Random(new Date().getTime());
            int value = r.nextInt(100); // 0..99
            LOGGER.info("Merge index " + index.getName() + " into " + destinationIndex.getName());
            waitMilliSeconds(2000 + 2000 * value);
        }
        Index result = new Index();
        result.setName(destinationIndex.getName());
        return result;
    }

}
