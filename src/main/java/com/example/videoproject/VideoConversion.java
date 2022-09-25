package com.example.videoproject;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class VideoConversion {
    private String outputFormat;
    private String outputName;
    private String sourcePath;
    private String targetPath;

    private String audioCodec;
    private Integer audioBitRate;
    private Integer audioChannels;
    private Integer audioSamplingRate;

    private String videoCodec;
    Integer videoBitRate;
    private Integer videoFrameRate;

    public VideoConversion(String sourcePath, String targetPath, String outputName) {
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.outputName = outputName;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public void setOutputFormat(String outputFormat){
        this.outputFormat = outputFormat;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public String getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    public Integer getAudioBitRate() {
        return audioBitRate;
    }

    public void setAudioBitRate(Integer audioBitRate) {
        this.audioBitRate = audioBitRate;
    }

    public Integer getAudioChannels() {
        return audioChannels;
    }

    public void setAudioChannels(Integer audioChannels) {
        this.audioChannels = audioChannels;
    }

    public Integer getAudioSamplingRate() {
        return audioSamplingRate;
    }

    public void setAudioSamplingRate(Integer audioSamplingRate) {
        this.audioSamplingRate = audioSamplingRate;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public Integer getVideoBitRate() {
        return videoBitRate;
    }

    public void setVideoBitRate(Integer videoBitRate) {
        this.videoBitRate = videoBitRate;
    }

    public Integer getVideoFrameRate() {
        return videoFrameRate;
    }

    public void setVideoFrameRate(Integer videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public boolean encode() throws EncoderException {
        File source = new File(this.sourcePath);
        File target = new File(this.targetPath + "\\" + this.outputName + "." + (this.outputFormat.equals("matroska") ?  "mkv" : this.outputFormat));

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec(this.audioCodec);
        audio.setBitRate(this.audioBitRate);
        audio.setChannels(this.audioChannels);
        audio.setSamplingRate(this.audioSamplingRate);

        VideoAttributes video = new VideoAttributes();
        video.setCodec(this.videoCodec);
        video.setBitRate(this.videoBitRate);
        video.setFrameRate(this.videoFrameRate);

        EncodingAttributes attributes = new EncodingAttributes();
        attributes.setOutputFormat(this.outputFormat);
        attributes.setVideoAttributes(video);
        attributes.setAudioAttributes(audio);

        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(source), target, attributes);

        return true;
    }

    public String showInfo(){
        String info = "File source: " + this.sourcePath + "\n" +
                "File target: " + this.targetPath + "\n" +
                "Audio codec: " + this.audioCodec + "\n" +
                "Audio bit rate: " + this.audioBitRate + "\n" +
                "Audio channels: " + this.audioChannels + "\n" +
                "Audio sampling rate: " + this.audioSamplingRate + "\n" +
                "Video codec: " + this.videoCodec + "\n" +
                "Video bit rate: " + this.videoBitRate + "\n" +
                "Video frame rate: " + this.videoFrameRate + "\n" +
                "Output Format: " + this.outputFormat;

        return info;
    }
}
