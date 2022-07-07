package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtSwipeInfo implements Serializable {
    private static final long serialVersionUID = -7710443288196089058L;
    public short backgroundColor;
    public byte invertedSwipe;
    public byte noiseRobustnessFlags;
    public short qualityMessage;
    public byte qualityScore;
    public short realHeight;
    public short recErrorStates;
    public byte recScore;
    public short roiLength;
    public short roiPos;
}
