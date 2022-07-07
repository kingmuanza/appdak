package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtImgFormat implements Serializable {
    private static final long serialVersionUID = -292890296360812451L;
    public short compressMode;
    public short flags;
    public byte inBpp;
    public short inXDpi;
    public short inYDpi;
    public byte outBpp;
    public short outXDpi;
    public short outYDpi;
    public short powerMode;
}
