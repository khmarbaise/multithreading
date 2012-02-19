package com.soebes.multithreading.cp;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author Karl Heinz Marbaise
 */
public class Index {
    private static final Logger LOGGER = Logger.getLogger(Index.class);

    private File indexBaseFolder;
    
    private File indexFolder;

    private String name;
    
    private Analyzer analyzer;
    
    private IndexWriter indexWriter;
    
    private boolean create;

    public Index(String name, File indexBaseFolder, Analyzer analyzer) {
        super();
        this.indexBaseFolder = indexBaseFolder;
        this.indexFolder = null;
        this.name = name;
        this.analyzer = analyzer;
        this.create = true;
    }

    public Index(String name, File indexBaseFolder, Analyzer analyzer, boolean create) {
	this(name, indexBaseFolder, analyzer);
	this.create = create;
    }

    public String toString() {
	StringBuilder result = new StringBuilder();
	result.append("Name:" + getName() + " index-folder:" + getIndexFolder() + " index-base-folder:" + getIndexBaseFolder());
	return result.toString();
    }
    /**
     * FIXME: Should be in the ctor ? Better?
     * @throws IOException
     */
    public void createIndex() throws IOException {
        createIndexFolder ( );
//        Directory d = Directory.
        IndexWriterConfig conf = new IndexWriterConfig ( Version.LUCENE_35, getAnalyzer() );
        if (create) {
            conf.setOpenMode(OpenMode.CREATE);
        } else {
            conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
        }
        FSDirectory fs = FSDirectory.open ( this.indexFolder );

        indexWriter = new IndexWriter(fs, conf);
    }

    private void createIndexFolder() {
        //Create the index as a sibling to the indexBaseFolder.
        indexFolder = new File(indexBaseFolder.getParentFile(), getName());
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

    public Analyzer getAnalyzer ()
    {
        return analyzer;
    }

    public void setAnalyzer ( Analyzer analyzer )
    {
        this.analyzer = analyzer;
    }

    public IndexWriter getIndexWriter ()
    {
        return indexWriter;
    }

    public void setIndexWriter ( IndexWriter indexWriter )
    {
        this.indexWriter = indexWriter;
    }

    public File getIndexFolder() {
        return indexFolder;
    }

    public void setIndexFolder(File indexFolder) {
        this.indexFolder = indexFolder;
    }

}
