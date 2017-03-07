package org.finra.interview.hua.api.file.entity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class File {

    private BasicFileAttributes metadata;
    private InputStream content;

    public File(Path file) throws IOException {
        this.metadata = Files.readAttributes(file, BasicFileAttributes.class);
    }

    public BasicFileAttributes getBasicFileAttributes() {
        return metadata;
    }

    public void setBasicFileAttributes(BasicFileAttributes metadata) {
        this.metadata = metadata;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

}
