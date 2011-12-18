package com.soebes.multithreading.cp;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * @author Karl Heinz Marbaise
 */
public class Index {
    private static final Logger LOGGER = Logger.getLogger(Index.class);

    private File indexBaseFolder;

    private String name;

    public Index(String name, File indexBaseFolder) {
        super();
        this.indexBaseFolder = indexBaseFolder;
        this.name = name;
    }

    public void createIndex() {
        //Create the index as a sibling to the indexBaseFolder.
        File indexFolder = new File(indexBaseFolder.getParentFile(), getName());
        try {
            indexFolder.mkdirs();
        } catch (Exception e) {
            LOGGER.error("Failure during creation of index folders. ", e);
        }
    }

    public final String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getIndexBaseFolder() {
        return indexBaseFolder;
    }

    public void setIndexBaseFolder(File indexBaseFolder) {
        this.indexBaseFolder = indexBaseFolder;
    }

}
