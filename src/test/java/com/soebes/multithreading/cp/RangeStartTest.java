package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class RangeStartTest {
    private static final Logger LOGGER = Logger.getLogger(RangeStartTest.class);

    public VersionRange createVersionRange(long rangeStart, long rangeEnd) {
        List<Version> vrList = new ArrayList<Version>();
        for (long i = rangeStart; i < rangeEnd; i++) {
            Version v = new Version(Long.toString(i));
            vrList.add(v);
        }
        VersionRange vr = new VersionRange(vrList);
        return vr;
    }

    /**
     * @param lastVersion Read from configuration file of the last time we read something.
     * @param versionRange The range we currently got from the repository...
     */
    public void scanRepository(VersionRange versionRange) {
        if (versionRange.size() < 1) {
            //Nothing to do
            LOGGER.info("We don't need to read version, cause there are no entries.");
            return;
        }
        //We can read the range...
    }

    @Test
    public void xtest() {
        //Express the following with ranges to be independent from
        //real version numbers. This is the idea to get that
        //working with revisions like git-revisions (sha1)
        //

        // 1. 
        //    Start:     1 Ende: 10000 => 1000 revisions per thread
        VersionRange vr = createVersionRange(1, 10000);

        //Simulate if we read the first time ever...So no revision read before...
        scanRepository(vr);

        // 2. 
        //    Start: 10001 Ende: 15399 => 1000 revisions per thread
//        VersionRange vr = createVersionRange(10001, 15399);

        // 3. 
        //    Start: 15399 Ende: 15399 => Nothing will be started.
        vr = new VersionRange(new ArrayList<Version>());
        scanRepository(vr);

        // 4. 
        //    Start: 15399 Ende: 15520 => A single thread will be started.


    }

    // 2. 
    //    Start: 1 Ende: 1000000
    

}
