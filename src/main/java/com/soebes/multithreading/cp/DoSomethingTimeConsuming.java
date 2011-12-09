package com.soebes.multithreading.cp;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

/**
 * This will represent the repository scanning for a number of given revisions
 * which is usually a block like 1000 or more.
 * 
 * @author Karl Heinz Marbaise
 */
public class DoSomethingTimeConsuming implements Callable<Index> {
    private static final Logger LOGGER = Logger.getLogger(DoSomethingTimeConsuming.class);

    private static final int MIN_WAITING_IN_MILLI_SECONDS = 200;
    private VersionRange versionRange;
    private Long number;

    public DoSomethingTimeConsuming(Long number, VersionRange versionRange) {
        this.number = number;
        this.versionRange = versionRange;
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
        }

    }

    /** {@inheritDoc} */
    public Index call() throws Exception {
        Index i = new Index();
        Random r = new Random(new Date().getTime());
        int value = r.nextInt(100); // 0..99
        Version first = versionRange.getFirstVersion();
        Version last = versionRange.getLastVersion();
        i.setName(
            "number: " + number
            + " first:" + first.getVersion()
            + " last:" + last.getVersion()
            + " random:" + Integer.toString(value));
        LOGGER.info("Started computation[" + number + "]: first:" + first
                + " last:" + last + " random: " + Integer.toString(value));

        // Really produce some kind of work for the CPU
        waitMilliSeconds(value * 50 + MIN_WAITING_IN_MILLI_SECONDS); // 200 ms
        // Thread.sleep(value * 50 + MIN_WAITING_IN_MILLI_SECONDS); // 200 ms

        i.setName("S-" + first.getVersion() + "-" + last.getVersion());
        LOGGER.info("Ended computation[" + number + "]: first:" + first.getVersion()
                + " last:" + last.getVersion() + " random: " + Integer.toString(value));

        return i;
    }

}
