package com.upek.android.ptapi;

import com.upek.android.ptapi.callback.PtGuiStateCallback;
import com.upek.android.ptapi.callback.PtGuiStreamingCallback;
import com.upek.android.ptapi.callback.PtIdleCallback;
import com.upek.android.ptapi.callback.PtNavigationCallback;
import com.upek.android.ptapi.resultarg.ByteArrayArgI;
import com.upek.android.ptapi.resultarg.IntegerArgI;
import com.upek.android.ptapi.resultarg.PtBirArgI;
import com.upek.android.ptapi.resultarg.PtSwipeInfoArgI;
import com.upek.android.ptapi.struct.PtBir;
import com.upek.android.ptapi.struct.PtExtendedInfo;
import com.upek.android.ptapi.struct.PtFingerListItem;
import com.upek.android.ptapi.struct.PtImgFormat;
import com.upek.android.ptapi.struct.PtInfo;
import com.upek.android.ptapi.struct.PtInputBir;
import com.upek.android.ptapi.struct.PtMatchOnHostParams;

public interface PtConnectionI {
    void authentify(int i, byte[] bArr) throws PtException;

    void authentifyAdv(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3, int i4, int i5) throws PtException;

    void authentifyEx(int i, byte[] bArr) throws PtException;

    void calibrate(int i) throws PtException;

    void cancel(int i) throws PtException;

    void capture(byte b, PtBirArgI ptBirArgI, int i, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    void close() throws PtException;

    void closeDevice() throws PtException;

    void closeSession() throws PtException;

    PtBir convertTemplate(PtInputBir ptInputBir, int i, boolean z) throws PtException;

    byte[] convertTemplateEx(int i, int i2, byte[] bArr, int i3, int i4, byte[] bArr2, int i5) throws PtException;

    void deleteAllFingers() throws PtException;

    void deleteFinger(int i) throws PtException;

    void detectFingerEx(int i, int i2) throws PtException;

    void enroll(byte b, PtInputBir ptInputBir, PtBirArgI ptBirArgI, IntegerArgI integerArgI, byte[] bArr, int i, PtBirArgI ptBirArgI2, byte[] bArr2, ByteArrayArgI byteArrayArgI) throws PtException;

    PtExtendedInfo extendedInfo() throws PtException;

    void formatInternalNVM(int i, byte[] bArr, byte[] bArr2) throws PtException;

    byte[] getAccessRights(int i) throws PtException;

    byte[] getAppData(int i) throws PtException;

    int getAvailableMemory(int i) throws PtException;

    byte[] getFingerData(int i) throws PtException;

    PtImgFormat getImageFormat(int i) throws PtException;

    void getLED(IntegerArgI integerArgI, IntegerArgI integerArgI2, IntegerArgI integerArgI3) throws PtException;

    Object getSessionCfgEx(short s) throws PtException;

    void getSwipeInfo(PtSwipeInfoArgI ptSwipeInfoArgI, ByteArrayArgI byteArrayArgI, ByteArrayArgI byteArrayArgI2) throws PtException;

    byte[] grab(byte b, int i, boolean z, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    byte[] grabWindow(byte b, int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    short identifyMatchOnHost(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtInputBir[] ptInputBirArr, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, PtMatchOnHostParams ptMatchOnHostParams) throws PtException;

    PtInfo info() throws PtException;

    boolean isStrongEncryptionEnabled() throws PtException;

    PtFingerListItem[] listAllFingers() throws PtException;

    PtBir loadFinger(int i, boolean z) throws PtException;

    void navigate(int i, PtNavigationCallback ptNavigationCallback, byte[] bArr) throws PtException;

    void navigateWithSleep(int i, int i2, PtNavigationCallback ptNavigationCallback, byte[] bArr) throws PtException;

    void openSession(String str) throws PtException;

    int scanQuality() throws PtException;

    void secureChannel(int i, int i2, int i3) throws PtException;

    void secureChannelEx(int i, int i2, int i3, int i4) throws PtException;

    void setAppData(int i, byte[] bArr) throws PtException;

    void setFingerData(int i, byte[] bArr) throws PtException;

    void setFingerPayload(int i, byte[] bArr) throws PtException;

    void setGUICallbacks(PtGuiStreamingCallback ptGuiStreamingCallback, PtGuiStateCallback ptGuiStateCallback) throws PtException;

    void setImageFormat(int i, PtImgFormat ptImgFormat, int i2) throws PtException;

    void setLED(int i, int i2, int i3) throws PtException;

    void setSessionCfgEx(short s, Object obj) throws PtException;

    int sleep(int i, PtIdleCallback ptIdleCallback) throws PtException;

    void sleepThenCapture(PtIdleCallback ptIdleCallback, byte b, int i, IntegerArgI integerArgI, IntegerArgI integerArgI2, PtBirArgI ptBirArgI, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    void sleepThenGrab(PtIdleCallback ptIdleCallback, byte b, int i, boolean z, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    int storeFinger(PtInputBir ptInputBir) throws PtException;

    byte[] transact(byte[] bArr) throws PtException;

    void updateFingerPayload(int i, int i2, byte[] bArr) throws PtException;

    boolean verify(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, int i, boolean z, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    int verifyAll(Integer num, Integer num2, Boolean bool, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, int i, boolean z, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    short verifyEx(Integer num, Integer num2, Boolean bool, PtInputBir[] ptInputBirArr, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, int i, boolean z, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    boolean verifyMatch(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtInputBir ptInputBir2, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI) throws PtException;

    boolean verifyMatchOnHost(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtInputBir ptInputBir2, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, PtMatchOnHostParams ptMatchOnHostParams) throws PtException;
}
