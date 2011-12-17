package com.soebes.multithreading.cp.supose.scan;

import java.util.ArrayList;

import org.tmatesoft.svn.core.SVNLogEntry;

import com.soebes.multithreading.cp.Version;
import com.soebes.multithreading.cp.VersionRange;

public class ScanRepository implements IScanBehaviour {

    @Override
    public void scanRepository(RepositoryScanParameter parameter) {
        Repository repository = new Repository(parameter.getUri().toString(), parameter.getAuthenticationManager());
        ReadLogEntries readLogs = new ReadLogEntries(repository);
        ArrayList<SVNLogEntry> logs = readLogs.getLogEntries();
        VersionRange vr = new VersionRange();
        for (SVNLogEntry svnLogEntry : logs) {
//            Version version = new Version(svnLogEntry);
//            vr.add(version);
        }
        
        
    }

}
