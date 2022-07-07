package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtSessionCfgV3 implements Serializable, Cloneable {
    private static final long serialVersionUID = 1618954488401640175L;
    public int antispoofingLevel;
    public short bioEnrollInputType;
    public short bioVerifyInputType;
    public int callbackLevel;
    public byte consolidationType;
    public short enableScanQualityQuery;
    public boolean latentDetect;
    public boolean multipleEnroll;
    public int navClickAllowedMovement;
    public short navClickTimeMax;
    public short navClickTimeMin;
    public short navFingerDownDelay;
    public short navFingerUpDelay;
    public short navMovementDelay;
    public short navNavigationType;
    public boolean navUseHwDetection;
    public byte recDigitalGain;
    public short recFingerDownDelay;
    public short recFingerUpDelay;
    public boolean recRemoveBottomup;
    public boolean recRemoveTopdown;
    public byte recRetuning;
    public short recTerminationPolicy;
    public boolean recUseHwDetection;
    public int securitySetting;
    public boolean sensorDisabling;
    public boolean subSample;
    public byte subWindowArea;
    public boolean wakeUpByFinger;
    public int wakeUpByFingerTimeout;
    public short wffFingerDownDelay;
    public short wffFingerUpDelay;
    public boolean wffUseHwDetection;

    public PtSessionCfgV3 clone() throws CloneNotSupportedException {
        return (PtSessionCfgV3) super.clone();
    }
}
