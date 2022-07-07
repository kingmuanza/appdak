package com.upek.android.ptapi.resultarg;

import com.upek.android.ptapi.struct.PtBir;

public class PtBirArg implements PtBirArgI {
    private static final long serialVersionUID = 2600200723705932620L;
    public PtBir value;

    public void setValue(PtBir value2) {
        this.value = value2;
    }

    public PtBir getValue() {
        return this.value;
    }
}
