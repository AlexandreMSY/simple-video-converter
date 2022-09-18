package com.example.videoproject;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class VideoConversion {
    String sourcePath;
    String targetPath;

    String audioCodec;
    Integer audioBitRate;
    Integer audioChannels;
    Integer audioSamplingRate;

    String videoCodec;
    Integer videoBitRate;
    Integer videoFrameRate;

    public VideoConversion(String sourcePath, String targetPath) {
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

    public void setAudioAttributes(String codec, Integer bitRate, Integer channels, Integer SamplingRate){
        this.audioCodec = codec;
        this.audioBitRate = bitRate;
        this.audioChannels = channels;
        this.audioSamplingRate = SamplingRate;
    }

    public void setVideoAttributes(String codec, Integer bitRate, Integer frameRate){
        this.videoCodec = codec;
        this.videoBitRate = bitRate;
        this.videoFrameRate = frameRate;
    }

    public void encode(String outputFormat) throws EncoderException {
        File source = new File(this.sourcePath);
        File target = new File(this.targetPath);

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
        attributes.setOutputFormat(outputFormat);
        attributes.setVideoAttributes(video);
        attributes.setAudioAttributes(audio);

        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(source), target, attributes);
    }


}
