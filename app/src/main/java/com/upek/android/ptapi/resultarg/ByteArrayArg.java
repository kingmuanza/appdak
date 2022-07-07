package com.upek.android.ptapi.resultarg;

public class ByteArrayArg implements ByteArrayArgI {
    private static final long serialVersionUID = 207421847833034972L;
    public byte[] value;

    public void setValue(byte[] value2) {
        this.value = value2;
    }

    public byte[] getValue() {
        return this.value;
    }
}
