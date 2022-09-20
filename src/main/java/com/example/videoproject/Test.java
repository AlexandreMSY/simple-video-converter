package com.example.videoproject;

import ws.schild.jave.EncoderException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Test {
    public static void main(String[] args) throws EncoderException {
        System.out.println(System.getProperty("user.home") + "\\Videos");
    }
}
