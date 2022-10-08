package utilitaries;

import java.text.DecimalFormat;

public class Conversions {
    private static String decimalFormat(double number){
        return new DecimalFormat("#.##").format(number);
    }

    public static String byteConversion(float size){
        double kilobyte = Math.pow(2, 10);
        double megabyte = Math.pow(2, 20);
        double gigabyte = Math.pow(2, 30);
        double terabyte = Math.pow(2, 40);
        double result;

        if (size < kilobyte){
            return size + " Byte";
        } else if (size < megabyte) {
            result = size / kilobyte;
            return decimalFormat(result) + " KB";
        } else if (size < gigabyte) {
            result = size / megabyte;
            return decimalFormat(result) + " MB";
        } else if (size < terabyte){
            result = size / gigabyte;
            return decimalFormat(result) + " GB";
        } else {
            result = size / terabyte;
            return decimalFormat(result) + " TB";
        }
    }

    public static String removeFileExtension(String fileName) {                       //check https://www.quickprogrammingtips.com/java/how-to-remove-extension-from-filename-in-java.html for more details
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }

    public static int kbpsToBps(int kilobitsPerSecond){
        return kilobitsPerSecond * 1000;
    }
}
