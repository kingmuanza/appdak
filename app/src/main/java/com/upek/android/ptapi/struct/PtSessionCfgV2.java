package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtSessionCfgV2 implements Serializable, Cloneable {
    private static final long serialVersionUID = 5787917790207269696L;
    public int antispoofingLevel;
    public int callbackLevel;
    public boolean latentDetect;
    public boolean multipleEnroll;
    public int navClickAllowedMovement;
    public short navClickTimeMax;
    public short navClickTimeMin;
    public short navFingerDownDelay;
    public short navFingerUpDelay;
    public short navMovementDelay;
    public boolean navUseHwDetection;
    public short recFingerDownDelay;
    public short recFingerUpDelay;
    public boolean recRemoveBottomup;
    public boolean recRemoveTopdown;
    public boolean recUseHwDetection;
    public int securitySetting;
    public boolean sensorDisabling;
    public boolean subSample;
    public byte subWindowArea;
    public boolean wakeUpByFinger;
    public short wffFingerDownDelay;
    public short wffFingerUpDelay;
    public boolean wffUseHwDetection;

    public PtSessionCfgV2 clone() throws CloneNotSupportedException {
        return (PtSessionCfgV2) super.clone();
    }
}
