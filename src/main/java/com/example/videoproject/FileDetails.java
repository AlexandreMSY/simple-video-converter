package com.example.videoproject;

public class FileDetails {
    String fileName;
    String outputFormat;
    String fileSize;
    String status;
    int qualityPreset;

    public FileDetails(String fileName, String outputFormat, String fileSize,String status, int qualityPreset) {
        this.fileName = fileName;
        this.outputFormat = outputFormat;
        this.fileSize = fileSize;
        this.qualityPreset = qualityPreset;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getQualityPreset() {
        return qualityPreset;
    }

    public void setQualityPreset(int qualityPreset) {
        this.qualityPreset = qualityPreset;
    }
}
