package com.authentec.java.ptapi.Technocrat.basicsample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class Utils {
    public static byte[] getBytesFromFile(File file) throws IOException {
        int numRead;
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[((int) file.length())];
        int offset = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean containsDigit(String s) {
        if (s == null) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateEmail(String email) {
        if (Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b").matcher(email).find()) {
            return true;
        }
        return false;
    }
}
