package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtInfo implements Serializable {
    private static final long serialVersionUID = 7995127951996870854L;
    public int authentifyId;
    public int companionVendorCode;
    public int fwConfig;
    public int fwFunctionality;
    public int fwMinNextVersion;
    public int fwVariant;
    public int fwVersion;
    public int id;
    public short imageHeight;
    public short imageWidth;
    public int maxGrabWindow;
    public int sensorType;
    public int usage;
}
