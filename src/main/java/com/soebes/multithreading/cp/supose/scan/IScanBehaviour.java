package com.soebes.multithreading.cp.supose.scan;

/**
 * This is the definition of how the repository will be scanned.
 * 
 * This is done, cause at the moment it's not clear how the best 
 * strategy is to scan the repository in meaning of multithreading.
 * This give me the opportunity to change the strategy of using different
 * multi-threading approaches.
 * 
 * @author Karl Heinz Marbaise
 *
 */
public interface IScanBehaviour {

    /**
     * Scans the whole repository with a particular strategy.
     * @param parameter The instance of the parameter which will be used
     * to scan the repository.
     */
    void scanRepository (RepositoryScanParameter parameter);
}
