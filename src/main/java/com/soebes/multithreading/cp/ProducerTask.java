package com.soebes.multithreading.cp;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;


/**
 * The idea is to have a producer which says we have to index 
 * revision 1..100 and the next 101..200 etc. This will work 
 * with git as well, cause the only change to have a list of 
 * sha1 instead of revision numbers...
 * 
 * @author Karl Heinz Marbaise
 * 
 */
public class ProducerTask implements Computable<VersionRange, Index> {
    private static final Logger LOGGER = Logger.getLogger(ProducerTask.class);

    /** {@inheritDoc} */
    public Index compute(VersionRange arg) throws InterruptedException {
	LOGGER.info("Started computation:");
	Index i = new Index();
	i.setName("Test");
	Random r = new Random(new Date().getTime());
	int value = r.nextInt(100); //0..99 
	Thread.sleep(value * 50 + 200); // 200 ms 
	LOGGER.info("Ended computation:");
	return i;
    }

}
