package com.authentec.java.ptapi.Technocrat.basicsample;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OpNavigateSettings {
    private static final int LONG_TOUCH_ANDROID_INTERVAL = 1600;
    private static final int NAV_PROP_SERIALIZED_LENGTH = 84;
    private static final long serialVersionUID = -4217723181578697347L;
    public boolean clickEnabled;
    public boolean hwNavigationEnabled;
    public float inputMultiplierX;
    public float inputMultiplierY;
    public int longTouchMaxMovement;
    public int longTouchMode;
    public int longTouchTime;
    public Serializable modeParams;
    public boolean pressureClickProcessingEnabled;
    public boolean sensorButtonEnabled;
    public boolean tappingClickProcessingEnabled;

    public class LongTouchMode {
        public static final int DISABLED = 0;
        public static final int ONCE = 1;
        public static final int STICKY = 2;

        public LongTouchMode() {
        }
    }

    public static class MouseModeParams implements Serializable {
        private static final long serialVersionUID = -1878985172574647855L;
        public float cursorAccelMultiplier = 1.0f;
        public int cursorAccelThreshold = 10;
        public int cursorMaxSpeed = 5;
        public int gridDivisorX = 1;
        public int gridDivisorY = 1;
        public float inertialFriction = 0.0f;
        public float inertialMaxSpeed = 0.0f;
        public float noFingerMaxSpeed = 0.0f;
        public int repeatDelay = 50;
        public float repeatedSpeedMultiplier = 1.0f;
        public int straighteningFactor = 0;

        public MouseModeParams() {
        }

        public MouseModeParams(MouseModeParams orig) {
            this.cursorAccelThreshold = orig.cursorAccelThreshold;
            this.cursorAccelMultiplier = orig.cursorAccelMultiplier;
            this.cursorMaxSpeed = orig.cursorMaxSpeed;
            this.gridDivisorX = orig.gridDivisorX;
            this.gridDivisorY = orig.gridDivisorY;
            this.straighteningFactor = orig.straighteningFactor;
            this.inertialMaxSpeed = orig.inertialMaxSpeed;
            this.inertialFriction = orig.inertialFriction;
            this.repeatedSpeedMultiplier = orig.repeatedSpeedMultiplier;
            this.repeatDelay = orig.repeatDelay;
            this.noFingerMaxSpeed = orig.noFingerMaxSpeed;
        }

        public MouseModeParams(float inputMultiplierX, float inputMultiplierY, int cursorAccelThreshold2, float cursorAccelMultiplier2, int cursorMaxSpeed2, int gridDivisorX2, int gridDivisorY2, int straighteningFactor2, float maxInertialSpeed, float inertialFriction2, float repeatedSpeedMultiplier2, int repeatDelay2, float noFingerMaxSpeed2) {
            this.cursorAccelThreshold = cursorAccelThreshold2;
            this.cursorAccelMultiplier = cursorAccelMultiplier2;
            this.cursorMaxSpeed = cursorMaxSpeed2;
            this.gridDivisorX = gridDivisorX2;
            this.gridDivisorY = gridDivisorY2;
            this.straighteningFactor = straighteningFactor2;
            this.inertialMaxSpeed = maxInertialSpeed;
            this.inertialFriction = inertialFriction2;
            this.repeatedSpeedMultiplier = repeatedSpeedMultiplier2;
            this.repeatDelay = repeatDelay2;
            this.noFingerMaxSpeed = noFingerMaxSpeed2;
        }
    }

    public static class ArrowModeParams implements Serializable {
        private static final long serialVersionUID = -7946894592543665156L;
        public boolean accelEnabled = false;
        public int accelPeriodLength = 0;
        public int fastKeyRepeatRate = 100;
        public int keyRepeatDelay = 300;
        public int keyRepeatRate = 200;
        public int keyThreshold = 20;
        public int preAccelPeriodLength = 2000;

        public ArrowModeParams() {
        }

        public ArrowModeParams(ArrowModeParams orig) {
            this.keyThreshold = orig.keyThreshold;
            this.keyRepeatDelay = orig.keyRepeatDelay;
            this.keyRepeatRate = orig.keyRepeatRate;
            this.accelEnabled = orig.accelEnabled;
            this.preAccelPeriodLength = orig.preAccelPeriodLength;
            this.accelPeriodLength = orig.accelPeriodLength;
            this.fastKeyRepeatRate = orig.fastKeyRepeatRate;
        }

        public ArrowModeParams(int keyThreshold2, int keyRepeatDelay2, int keyRepeatRate2, boolean accelEnabled2, int preAccelPeriodLength2, int accelPeriodLength2, int fastKeyRepeatRate2) {
            this.keyThreshold = keyThreshold2;
            this.keyRepeatDelay = keyRepeatDelay2;
            this.keyRepeatRate = keyRepeatRate2;
            this.accelEnabled = accelEnabled2;
            this.preAccelPeriodLength = preAccelPeriodLength2;
            this.accelPeriodLength = accelPeriodLength2;
            this.fastKeyRepeatRate = fastKeyRepeatRate2;
        }
    }

    public OpNavigateSettings() {
        this.clickEnabled = true;
        this.tappingClickProcessingEnabled = true;
        this.pressureClickProcessingEnabled = false;
        this.sensorButtonEnabled = false;
        this.hwNavigationEnabled = false;
        this.modeParams = null;
        this.inputMultiplierX = 1.0f;
        this.inputMultiplierY = 1.0f;
        this.longTouchMode = 2;
        this.longTouchTime = 3000;
        this.longTouchMaxMovement = 0;
        this.modeParams = new ArrowModeParams();
    }

    public OpNavigateSettings(OpNavigateSettings orig) {
        this.clickEnabled = true;
        this.tappingClickProcessingEnabled = true;
        this.pressureClickProcessingEnabled = false;
        this.sensorButtonEnabled = false;
        this.hwNavigationEnabled = false;
        this.modeParams = null;
        this.inputMultiplierX = 1.0f;
        this.inputMultiplierY = 1.0f;
        this.longTouchMode = 2;
        this.longTouchTime = 3000;
        this.longTouchMaxMovement = 0;
        this.inputMultiplierX = orig.inputMultiplierX;
        this.inputMultiplierY = orig.inputMultiplierY;
        this.longTouchTime = orig.longTouchTime;
        this.longTouchMaxMovement = orig.longTouchMaxMovement;
        this.longTouchMode = orig.longTouchMode;
        this.clickEnabled = orig.clickEnabled;
        this.hwNavigationEnabled = orig.hwNavigationEnabled;
        this.pressureClickProcessingEnabled = orig.pressureClickProcessingEnabled;
        this.sensorButtonEnabled = orig.sensorButtonEnabled;
        this.tappingClickProcessingEnabled = orig.tappingClickProcessingEnabled;
        if (orig.modeParams instanceof MouseModeParams) {
            this.modeParams = new MouseModeParams((MouseModeParams) orig.modeParams);
        } else {
            this.modeParams = new ArrowModeParams((ArrowModeParams) orig.modeParams);
        }
    }

    public static OpNavigateSettings createDefaultMousePostprocessingParams() {
        OpNavigateSettings prop = new OpNavigateSettings();
        prop.getClass();
        prop.modeParams = new MouseModeParams();
        return prop;
    }

    public static OpNavigateSettings createDefaultDiscretePostprocessingParams() {
        OpNavigateSettings prop = new OpNavigateSettings();
        prop.getClass();
        prop.modeParams = new ArrowModeParams();
        return prop;
    }

    public byte[] serialize(boolean systemNavigation, int orientation) throws Exception {
        byte b;
        int clickFilteringType;
        int i;
        byte b2 = 1;
        byte[] retVal = new byte[84];
        ByteBuffer buffer = ByteBuffer.wrap(retVal);
        int longTouchT = this.longTouchTime;
        if (systemNavigation) {
            longTouchT -= 1600;
            if (this.longTouchTime < 0) {
                longTouchT = 0;
            }
        }
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        if (this.clickEnabled) {
            b = 1;
        } else {
            b = 0;
        }
        buffer.put(b);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        if (this.tappingClickProcessingEnabled) {
            clickFilteringType = 1;
        } else {
            clickFilteringType = 0;
        }
        if (this.pressureClickProcessingEnabled) {
            clickFilteringType |= 2;
        }
        buffer.putInt(clickFilteringType);
        if (this.modeParams instanceof MouseModeParams) {
            buffer.putInt(0);
            buffer.putInt(this.longTouchMode);
            buffer.putInt(longTouchT);
            buffer.putInt(this.longTouchMaxMovement);
            buffer.putFloat(this.inputMultiplierX);
            buffer.putFloat(this.inputMultiplierY);
            buffer.putInt(orientation);
            if (!this.sensorButtonEnabled) {
                b2 = 0;
            }
            buffer.putInt(b2);
            MouseModeParams params = (MouseModeParams) this.modeParams;
            buffer.putInt(params.cursorAccelThreshold);
            buffer.putFloat(params.cursorAccelMultiplier);
            buffer.putInt(params.cursorMaxSpeed);
            buffer.putInt(params.gridDivisorX);
            buffer.putInt(params.gridDivisorY);
            buffer.putInt(params.straighteningFactor);
            buffer.putFloat(params.inertialMaxSpeed);
            buffer.putFloat(params.inertialFriction);
            buffer.putFloat(params.repeatedSpeedMultiplier);
            buffer.putInt(params.repeatDelay);
            buffer.putFloat(params.noFingerMaxSpeed);
        } else if (this.modeParams instanceof ArrowModeParams) {
            buffer.putInt(1);
            buffer.putInt(this.longTouchMode);
            buffer.putInt(longTouchT);
            buffer.putInt(this.longTouchMaxMovement);
            buffer.putFloat(this.inputMultiplierX);
            buffer.putFloat(this.inputMultiplierY);
            buffer.putInt(orientation);
            if (this.sensorButtonEnabled) {
                i = 1;
            } else {
                i = 0;
            }
            buffer.putInt(i);
            ArrowModeParams params2 = (ArrowModeParams) this.modeParams;
            buffer.putInt(params2.keyThreshold);
            buffer.putInt(params2.keyRepeatDelay);
            buffer.putInt(params2.keyRepeatRate);
            if (!params2.accelEnabled) {
                b2 = 0;
            }
            buffer.put(b2);
            buffer.put((byte) 0);
            buffer.put((byte) 0);
            buffer.put((byte) 0);
            buffer.putInt(params2.preAccelPeriodLength);
            buffer.putInt(params2.accelPeriodLength);
            buffer.putInt(params2.fastKeyRepeatRate);
        } else {
            throw new Exception("modeParams aren't instance of neither MouseModeParams, nor ArrowModeParams " + this.modeParams);
        }
        return retVal;
    }
}
