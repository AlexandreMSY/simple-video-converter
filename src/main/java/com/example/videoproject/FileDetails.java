package com.example.videoproject;

public class FileDetails {
    String fileName;
    String outputFormat;
    String fileSize;

    public FileDetails(String fileName, String outputFormat, String fileSize) {
        this.fileName = fileName;
        this.outputFormat = outputFormat;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
