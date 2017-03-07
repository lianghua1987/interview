package org.finra.interview.hua.api.file.entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class Metadata {

    private Date creationTime;
    private Date lastAccessTime;
    private Date lastModifiedTime;
    private Boolean isDirectory;
    private Integer size;

    public Metadata() {
    }

    public Metadata(Path file) throws IOException { 
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Boolean getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
