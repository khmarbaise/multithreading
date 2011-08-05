package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.soebes.multithreading.cp.VersionRange;

public class VersionRangeTest {
    @Test
    public void f() {
	List<String> versionRange = new ArrayList<String>();

	for (int i = 0; i < 1000000; i++) {
	    versionRange.add(Integer.toString(i));
	}

	VersionRange vr = new VersionRange(versionRange);

    }
}
