package com.authentec.java.ptapi.Technocrat.basicsample;

import java.io.ByteArrayOutputStream;

public class Base64 {
    static final char[] charTab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String encode(byte[] data) {
        return encode(data, 0, data.length, (StringBuffer) null).toString();
    }

    public static StringBuffer encode(byte[] data, int start, int len, StringBuffer buf) {
        if (buf == null) {
            buf = new StringBuffer((data.length * 3) / 2);
        }
        int end = len - 3;
        int i = start;
        int n = 0;
        while (i <= end) {
            int d = ((data[i] & 255) << 16) | ((data[i + 1] & 255) << 8) | (data[i + 2] & 255);
            buf.append(charTab[(d >> 18) & 63]);
            buf.append(charTab[(d >> 12) & 63]);
            buf.append(charTab[(d >> 6) & 63]);
            buf.append(charTab[d & 63]);
            i += 3;
            int n2 = n + 1;
            if (n >= 14) {
                n2 = 0;
                buf.append("\r\n");
            }
            n = n2;
        }
        if (i == (start + len) - 2) {
            int d2 = ((data[i] & 255) << 16) | ((data[i + 1] & 255) << 8);
            buf.append(charTab[(d2 >> 18) & 63]);
            buf.append(charTab[(d2 >> 12) & 63]);
            buf.append(charTab[(d2 >> 6) & 63]);
            buf.append("=");
        } else if (i == (start + len) - 1) {
            int d3 = (data[i] & 255) << 16;
            buf.append(charTab[(d3 >> 18) & 63]);
            buf.append(charTab[(d3 >> 12) & 63]);
            buf.append("==");
        }
        return buf;
    }

    static int decode(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 'A';
        }
        if (c >= 'a' && c <= 'z') {
            return (c - 'a') + 26;
        }
        if (c >= '0' && c <= '9') {
            return (c - '0') + 26 + 26;
        }
        switch (c) {
            case '+':
                return 62;
            case '/':
                return 63;
            case 61 /*61*/:
                return 0;
            default:
                throw new RuntimeException(new StringBuffer("unexpected code: ").append(c).toString());
        }
    }

    public static byte[] decode(String s) {
        int i = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = s.length();
        while (true) {
            if (i >= len || s.charAt(i) > ' ') {
                if (i != len) {
                    int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6) + decode(s.charAt(i + 3));
                    bos.write((tri >> 16) & 255);
                    if (s.charAt(i + 2) == '=') {
                        break;
                    }
                    bos.write((tri >> 8) & 255);
                    if (s.charAt(i + 3) == '=') {
                        break;
                    }
                    bos.write(tri & 255);
                    i += 4;
                } else {
                    break;
                }
            } else {
                i++;
            }
        }
        return bos.toByteArray();
    }
}
