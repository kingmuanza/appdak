package com.upek.android.ptapi.callback;

import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.struct.PtGuiSampleImage;
import java.io.Serializable;

public class PtStdGuiStateCallback implements PtGuiStateCallback, Serializable {
    private static final long serialVersionUID = 1037818033190338173L;

    public byte guiStateCallbackInvoke(int guiState, int message, byte progress, PtGuiSampleImage sampleBuffer, byte[] data) throws PtException {
        throw new PtException(PtException.PT_STATUS_GENERAL_ERROR);
    }
}
