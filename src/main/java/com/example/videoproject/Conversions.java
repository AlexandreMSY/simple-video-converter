package com.example.videoproject;

import java.text.DecimalFormat;

public class Conversions {
    private static String sizeFormat(double number){
        return new DecimalFormat("#.##").format(number);
    }

    public static String byteConversion(float size){
        double kilobyte = Math.pow(2, 10);
        double megabyte = Math.pow(2, 20);
        double gigabyte = Math.pow(2, 30);
        double terabyte = Math.pow(2, 40);
        double result;

        if (size < kilobyte){
            return size + "Byte";
        } else if (size < megabyte) {
            result = size / kilobyte;
            return sizeFormat(result) + " KB";
        } else if (size < gigabyte) {
            result = size / megabyte;
            return sizeFormat(result) + " MB";
        } else if (size < terabyte){
            result = size / gigabyte;
            return sizeFormat(result) + " GB";
        } else {
            result = size / terabyte;
            return sizeFormat(result) + " TB";
        }
    }
}
