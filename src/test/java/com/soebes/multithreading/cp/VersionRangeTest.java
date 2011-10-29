package com.soebes.multithreading.cp;

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

}
