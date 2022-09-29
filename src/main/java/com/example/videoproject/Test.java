package com.example.videoproject;

import ws.schild.jave.EncoderException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Test {
    public static void main(String[] args) throws EncoderException {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Video Files",
                "mp4", "flv", "avi", "mkv");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(filter);

        JFileChooser saveChooser = new JFileChooser();
        saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int sourceValue = fileChooser.showOpenDialog(null);
        int targetValue = saveChooser.showSaveDialog(null);

        if (sourceValue == JFileChooser.APPROVE_OPTION && targetValue == JFileChooser.APPROVE_OPTION){
            File source = fileChooser.getSelectedFile();
            File target = saveChooser.getSelectedFile();

            String sourcePath = source.getAbsolutePath();
            String newFileName = JOptionPane.showInputDialog(null,"Set the file name");
            String targetPath = target.getAbsolutePath();

            System.out.println(sourcePath);
            System.out.println(targetPath);

            VideoConversion test = new VideoConversion(sourcePath, targetPath, newFileName);
            test.setOutputFormat("mp4");
            test.setVideoCodec("theora");
            test.encode();
            System.out.println(test.getVideoTag());


        }
    }
}