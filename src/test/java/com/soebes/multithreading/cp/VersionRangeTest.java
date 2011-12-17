package com.soebes.multithreading.cp;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

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
        // 100 elements will be divided into 5 blocks
        // which means to have each block 20 elements.
        // Block number 2 means (0,1,2) starting with
        // element (0..19, 20..39, 40..59) 40...
        VersionRange vrResult = vr.getBlockRange(5, 2);
        assertTrue(vrResult.size() == 20);
        assertEquals(vrResult.getFirstVersion().getVersion(), "40");
        assertEquals(vrResult.getLastVersion().getVersion(), "59");
    }

    @Test
    public void getRangesBySize35() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        // 35 Entries

        // 0..34
        // 35..69
        // 70..104 => only 70..99
        List<VersionRange> vrResults = vr.getRangesBySize(35);
        assertTrue(vrResults.size() == 3);
        assertEquals(vrResults.get(0).getFirstVersion().getVersion(), "0");
        assertEquals(vrResults.get(0).getLastVersion().getVersion(), "34");

        assertEquals(vrResults.get(1).getFirstVersion().getVersion(), "35");
        assertEquals(vrResults.get(1).getLastVersion().getVersion(), "69");

        assertEquals(vrResults.get(2).getFirstVersion().getVersion(), "70");
        assertEquals(vrResults.get(2).getLastVersion().getVersion(), "99");
        
    }

    @Test
    public void getRangesBySize40() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        // 40 Entries

        // 0..39
        // 40..79
        // 80..119 => only 80..99
        List<VersionRange> vrResults = vr.getRangesBySize(40);
        assertTrue(vrResults.size() == 3);
        assertEquals(vrResults.get(0).getFirstVersion().getVersion(), "0");
        assertEquals(vrResults.get(0).getLastVersion().getVersion(), "39");

        assertEquals(vrResults.get(1).getFirstVersion().getVersion(), "40");
        assertEquals(vrResults.get(1).getLastVersion().getVersion(), "79");

        assertEquals(vrResults.get(2).getFirstVersion().getVersion(), "80");
        assertEquals(vrResults.get(2).getLastVersion().getVersion(), "99");
        
    }

    @Test
    public void getRangesBySize80() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        // 80 Entries

        // 0..79
        // 80..159 => only 80..99
        List<VersionRange> vrResults = vr.getRangesBySize(80);
        assertTrue(vrResults.size() == 2);
        assertEquals(vrResults.get(0).getFirstVersion().getVersion(), "0");
        assertEquals(vrResults.get(0).getLastVersion().getVersion(), "79");

        assertEquals(vrResults.get(1).getFirstVersion().getVersion(), "80");
        assertEquals(vrResults.get(1).getLastVersion().getVersion(), "99");
    }

    @Test
    public void getRangesBySize99() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        // 99 Entries

        // 0..98
        // 99
        List<VersionRange> vrResults = vr.getRangesBySize(99);
        assertTrue(vrResults.size() == 2);
        assertEquals(vrResults.get(0).getFirstVersion().getVersion(), "0");
        assertEquals(vrResults.get(0).getLastVersion().getVersion(), "98");

        assertEquals(vrResults.get(1).getFirstVersion().getVersion(), "99");
        assertEquals(vrResults.get(1).getLastVersion().getVersion(), "99");
    }

    
    @Test
    public void getRangesBySize100() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        // 100 Entries

        // 0..99
        List<VersionRange> vrResults = vr.getRangesBySize(100);
        assertTrue(vrResults.size() == 1);
        assertEquals(vrResults.get(0).getFirstVersion().getVersion(), "0");
        assertEquals(vrResults.get(0).getLastVersion().getVersion(), "99");
    }

    @Test
    public void getRangesBySize200() {
        VersionRange vr = new VersionRange();
        for (int i = 0; i < 100; i++) {
            Version v = new Version(Integer.toString(i));
            vr.add(v);
        }

        // 200 Entries

        // 0..99
        List<VersionRange> vrResults = vr.getRangesBySize(200);
        assertTrue(vrResults.size() == 1);
        assertEquals(vrResults.get(0).getFirstVersion().getVersion(), "0");
        assertEquals(vrResults.get(0).getLastVersion().getVersion(), "99");
    }

}
