package com.example.videoproject;

public class FileDetails {
    private String fileName;
    private String outputFormat;
    private String fileSize;
    private String status;

    //those attributes are used as indexes for the comboBoxes
    private int qualityPreset;
    private int videoBitRateOption;
    private int videoFrameRateOption;

    private int audioBitRateOption;

    private int audioSampleRateOption;

    private int audioChannelOption;

    public FileDetails(String fileName, String outputFormat, String fileSize,String status, int qualityPreset) {
        this.fileName = fileName;
        this.outputFormat = outputFormat;
        this.fileSize = fileSize;
        this.qualityPreset = qualityPreset;
        this.status = status;
    }

    public int getAudioBitRateOption() {
        return audioBitRateOption;
    }

    public void setAudioBitRateOption(int audioBitRateOption) {
        this.audioBitRateOption = audioBitRateOption;
    }

    public int getAudioSampleRateOption() {
        return audioSampleRateOption;
    }

    public void setAudioSampleRateOption(int audioSampleRateOption) {
        this.audioSampleRateOption = audioSampleRateOption;
    }

    public int getAudioChannelOption() {
        return audioChannelOption;
    }

    public void setAudioChannelOption(int audioChannelOption) {
        this.audioChannelOption = audioChannelOption;
    }

    public int getVideoBitRateOption() {
        return videoBitRateOption;
    }

    public void setVideoBitRateOption(int videoBitRateOption) {
        this.videoBitRateOption = videoBitRateOption;
    }

    public int getVideoFrameRateOption() {
        return videoFrameRateOption;
    }

    public void setVideoFrameRateOption(int videoFrameRateOption) {
        this.videoFrameRateOption = videoFrameRateOption;
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
