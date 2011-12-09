package com.soebes.multithreading.cp;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class VersionRangeTest {

    @Test
    public void checkIfHasVersionIsFalseIfNoVersionHasBeenAdded() {
        VersionRange vr = new VersionRange();

        assertFalse(vr.hasVersions());
    }

    @Test
    public void checkIfAddWorks() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 10; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        assertTrue(vr.hasVersions());
        assertTrue(vr.size() == 10);

    }

    @Test
    public void checkIfLastVersionDeliversTheLastAddedVersion() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 10; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        assertTrue(vr.hasVersions());
        assertTrue(vr.size() == 10);
        assertTrue(vr.getLastVersion().equals(new Version(9)));

    }

    @Test
    public void checkIfFirstVersionDeliversTheFirstAddedVersion() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 10; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        assertTrue(vr.hasVersions());
        assertTrue(vr.size() == 10);
        assertTrue(vr.getFirstVersion().equals(new Version(0)));
    }

    @Test
    public void checkIfAddDoesNotAddAlreadyAddedVerson() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 10; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        assertTrue(vr.hasVersions());
        assertTrue(vr.size() == 10);
        vr.add(new Version(7));
        assertTrue(vr.size() == 10);

    }

    @Test
    public void getVersionRangeFromInstanceOfVersionRange() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        VersionRange vrNew = vr.getRange(0, 19);
        assertTrue(vrNew.size() == 20);
        
        VersionRange vrNew1 = vr.getRange(90);
        assertTrue(vrNew1.size() == 10);

    }

    @Test
    public void getVersionBlockRange() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }
        //100 elements will be divided into 5 blocks
        //which means to have each block 20 elements.
        //Block number 2 means (0,1,2) starting with
        //element (0..19, 20..39, 40..59) 40...
        VersionRange vrResult = vr.getBlockRange(5, 2);
        assertTrue(vrResult.size() == 20);
        assertEquals(vrResult.getFirstVersion().getVersion(), "40");
        assertEquals(vrResult.getLastVersion().getVersion(), "59");
    }
}
