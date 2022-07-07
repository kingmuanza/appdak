package com.authentec.java.ptapi.Technocrat.basicsample;

public class PtHelper {
    public static final String GetGuiStateCallbackMessage(int guiState, int message, byte progress) {
        String s = null;
        if ((guiState & 1) != 1) {
            return null;
        }
        switch (message) {
            case 2:
                s = "Too Light";
                break;
            case 3:
                s = "Too Dry";
                break;
            case 4:
                s = "Too Dark";
                break;
            case 5:
                s = "Too High";
                break;
            case 6:
                s = "Too Low";
                break;
            case 7:
                s = "Too Left";
                break;
            case 8:
                s = "Too Right";
                break;
            case 9:
                s = "Too Small";
                break;
            case 10:
            case 11:
                s = "Bad Quality";
                break;
            case 12:
            case 13:
            case 14:
            case 38:
            case 39:
                s = "Put Finger";
                break;
            case 15:
                s = "Lift Finger";
                break;
            case 19:
                s = "Keep Finger";
                break;
            case 28:
                s = "Too Fast";
                break;
            case 29:
                s = "Too Skewed";
                break;
            case 30:
                s = "Too Short";
                break;
            case 32:
                s = "Processing Image";
                break;
            case 34:
                s = "Backward movement";
                break;
            case 35:
                s = "Joint Detectected";
                break;
            case 36:
                s = "Press Center and Harder";
                break;
            case 45:
                s = "Template extracted from last swipe doesn't match previous one";
                break;
            case 46:
                s = "Enrollment progress";
                if ((guiState & 2) == 2) {
                    s = String.valueOf(s) + ": " + progress + '%';
                    break;
                }
                break;
        }
        return s;
    }
}
