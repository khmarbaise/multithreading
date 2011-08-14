package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.soebes.multithreading.cp.VersionRange;

public class VersionRangeTest {
    @Test
    public void f() {
	List<Version> versionRange = new ArrayList<Version>();

	for (int i = 0; i < 1000000; i++) {
	    Version v = new Version(Integer.toString(i));
	    versionRange.add(v);
	}

	VersionRange vr = new VersionRange(versionRange);

    }
}
