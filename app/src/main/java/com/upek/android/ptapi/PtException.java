package com.upek.android.ptapi;

public class PtException extends Exception {
    public static final int PT_STATUS_ACCESS_DENIED = -1046;
    public static final int PT_STATUS_ALREADY_OPENED = -1039;
    public static final int PT_STATUS_ANTISPOOFING_EXPORT = -1044;
    public static final int PT_STATUS_ANTISPOOFING_IMPORT = -1045;
    public static final int PT_STATUS_ANTISPOOFING_NOT_CAPTURED = -1065;
    public static final int PT_STATUS_API_ALREADY_INITIALIZED = -1003;
    public static final int PT_STATUS_API_NOT_INIT = -1002;
    public static final int PT_STATUS_AS_BG_OUT_OF_RANGE = -1132;
    public static final int PT_STATUS_AS_OFFSET_OUT_OF_RANGE = -1131;
    public static final int PT_STATUS_AUTHENTIFICATION_FAILED = -1053;
    public static final int PT_STATUS_BAD_BIO_TEMPLATE = -1042;
    public static final int PT_STATUS_BIOMETRIC_TIMEOUT = -1050;
    public static final int PT_STATUS_BIO_OPERATION_CANCELED = -1052;
    public static final int PT_STATUS_CALIBRATION_FAILED = -1064;
    public static final int PT_STATUS_CANCEL_IGNORED = -1116;
    public static final int PT_STATUS_CANNOT_CONNECT = -1040;
    public static final int PT_STATUS_COMM_ERROR = -1057;
    public static final int PT_STATUS_CONSOLIDATION_FAILED = -1051;
    public static final int PT_STATUS_CRYPTO_ERROR = -1090;
    public static final int PT_STATUS_CRYPTO_KEY_TOO_LONG = -1093;
    public static final int PT_STATUS_CRYPTO_MECHANISM_NOT_SUPPORTED = -1091;
    public static final int PT_STATUS_CRYPTO_PADDING_ERROR = -1092;
    public static final int PT_STATUS_CRYPTO_RSA_BAD_PUBLIC_KEY = -1101;
    public static final int PT_STATUS_CRYPTO_RSA_KEY_CHECK = -1100;
    public static final int PT_STATUS_CRYPTO_SYM_BAD_KEY = -1094;
    public static final int PT_STATUS_DATA_TOO_LARGE = -1008;
    public static final int PT_STATUS_DEVICE_NOT_FOUND = -1077;
    public static final int PT_STATUS_DEVICE_NOT_GRANTED = -1106;
    public static final int PT_STATUS_DEVICE_SICK = -1078;
    public static final int PT_STATUS_DIAGNOSTICS_FAILED = -1067;
    public static final int PT_STATUS_EMULATED_NVM_INVALID_FORMAT = -1118;
    public static final int PT_STATUS_ENDIAN_ERROR = -1107;
    public static final int PT_STATUS_EX_ACCESS_VIOLATION = -1096;
    public static final int PT_STATUS_FUNCTION_FAILED = -1033;
    public static final int PT_STATUS_GAIN_OFFSET = -1073;
    public static final int PT_STATUS_GENERAL_ERROR = -1001;
    public static final int PT_STATUS_GOING_TO_SLEEP = -1055;
    public static final int PT_STATUS_HW_RNG_INIT_ERROR = -1095;
    public static final int PT_STATUS_I2C_EEPROM_ERROR = -1060;
    public static final int PT_STATUS_INVALID_HANDLE = -1005;
    public static final int PT_STATUS_INVALID_INPUT_BIR_FORM = -1036;
    public static final int PT_STATUS_INVALID_OTP_SEED = -1110;
    public static final int PT_STATUS_INVALID_PARAMETER = -1004;
    public static final int PT_STATUS_INVALID_PURPOSE = -1061;
    public static final int PT_STATUS_JAVA_CB_ERROR = -1105;
    public static final int PT_STATUS_JAVA_CB_REMOTE = -1104;
    public static final int PT_STATUS_KEYEX_KEY_NOT_SET = -1108;
    public static final int PT_STATUS_LATCHUP_DETECTED = -1066;
    public static final int PT_STATUS_MALLOC_FAILED = -1007;
    public static final int PT_STATUS_MORE_DATA = -1010;
    public static final int PT_STATUS_NOT_ENOUGH_MEMORY = -1006;
    public static final int PT_STATUS_NOT_ENOUGH_PERMANENT_MEMORY = -1009;
    public static final int PT_STATUS_NOT_ENOUGH_TFM_MEMORY = -1038;
    public static final int PT_STATUS_NOT_IMPLEMENTED = -1056;
    public static final int PT_STATUS_NOT_SUPPORTED = -1063;
    public static final int PT_STATUS_NO_DATA = -1099;
    public static final int PT_STATUS_NO_LAST_MATCH_DATA = -1098;
    public static final int PT_STATUS_NO_SENSOR = -1069;
    public static final int PT_STATUS_NO_TEMPLATE = -1049;
    public static final int PT_STATUS_NVM_CANNOT_READ = -1088;
    public static final int PT_STATUS_NVM_CANNOT_WRITE = -1087;
    public static final int PT_STATUS_NVM_ERROR = -1086;
    public static final int PT_STATUS_NVM_INVALID_FILE_ID = -1089;
    public static final int PT_STATUS_NVM_INVALID_FORMAT = -1102;
    public static final int PT_STATUS_NVM_UNSUPPORTED_VERSION = -1103;
    public static final int PT_STATUS_OK = 0;
    public static final int PT_STATUS_OLD_VERSION = -1075;
    public static final int PT_STATUS_OPERATION_CANCELED = -1111;
    public static final int PT_STATUS_OS_ACCESS_DENIED = -1130;
    public static final int PT_STATUS_OTP_SEQUENCE_NUMBER_OVERFLOW = -1085;
    public static final int PT_STATUS_POWER_SHUTOFF = -1074;
    public static final int PT_STATUS_SAFE_MODE = -1081;
    public static final int PT_STATUS_SAME_VERSION = -1068;
    public static final int PT_STATUS_SC_BAD_PARAM = -3103;
    public static final int PT_STATUS_SC_COMM_FAIL = -3102;
    public static final int PT_STATUS_SC_ERROR = -3100;
    public static final int PT_STATUS_SC_NOT_SUPPORTED = -3101;
    public static final int PT_STATUS_SC_NO_CARD = -3104;
    public static final int PT_STATUS_SECCHAN_KEY_NOT_SET = -1114;
    public static final int PT_STATUS_SECURE_CHANNEL_ALREADY_ESTABLISHED = -1084;
    public static final int PT_STATUS_SENSOR_BUS_CONGESTION = -1117;
    public static final int PT_STATUS_SENSOR_HW_ERROR = -1082;
    public static final int PT_STATUS_SENSOR_NOT_CALIBRATED = -1080;
    public static final int PT_STATUS_SENSOR_NOT_REPAIRABLE = -1072;
    public static final int PT_STATUS_SENSOR_OUT_OF_LIMITS = -1070;
    public static final int PT_STATUS_SESSION_NOT_AUTHENTICATED = -1083;
    public static final int PT_STATUS_SESSION_TERMINATED = -1058;
    public static final int PT_STATUS_SIGN_KEY_NOT_SET = -1109;
    public static final int PT_STATUS_SLOT_NOT_FOUND = -1043;
    public static final int PT_STATUS_SONLY_ALREADY_OPENED = -1113;
    public static final int PT_STATUS_SUSPEND = -1076;
    public static final int PT_STATUS_SWIPE_TOO_BAD = -1062;
    public static final int PT_STATUS_TCS_OVERCURRENT = -1115;
    public static final int PT_STATUS_TIMEOUT = -1041;
    public static final int PT_STATUS_TOO_MANY_BAD_LINES = -1071;
    public static final int PT_STATUS_TOUCH_CHIP_ERROR = -1059;
    public static final int PT_STATUS_UNKNOWN_COMMAND = -1054;
    public static final int PT_STATUS_UNSUPPORTED_SPEED = -1079;
    public static final int PT_STATUS_VCH_ALREADY_REGISTERED = -1121;
    public static final int PT_STATUS_VCH_CHANNEL_NOT_AVAILABLE = -1129;
    public static final int PT_STATUS_VCH_COMM_ERROR = -1124;
    public static final int PT_STATUS_VCH_ERROR = -1119;
    public static final int PT_STATUS_VCH_INVALID_HANDLE = -1126;
    public static final int PT_STATUS_VCH_INVALID_IMPLEMENTATION = -1120;
    public static final int PT_STATUS_VCH_INVALID_PROTOCOL = -1128;
    public static final int PT_STATUS_VCH_JOB_ABORTED = -1127;
    public static final int PT_STATUS_VCH_LOST = -1112;
    public static final int PT_STATUS_VCH_MISSING_DEPENDENCIES = -1123;
    public static final int PT_STATUS_VCH_NOT_REGISTERED = -1122;
    public static final int PT_STATUS_VCH_TIMEOUT = -1125;
    public static final int PT_STATUS_WRONG_FINGER_DATA_ACCESS_RIGHTS = -1097;
    public static final int PT_STATUS_WRONG_RESPONSE = -1037;
    private static final long serialVersionUID = -3495268522431934546L;
    int code;

    public PtException(int code2) {
        this.code = code2;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        String result = "PtApi error(" + this.code + "): ";
        switch (this.code) {
            case PT_STATUS_SC_NO_CARD /*-3104*/:
                return String.valueOf(result) + "The card is not present in the reader";
            case PT_STATUS_SC_BAD_PARAM /*-3103*/:
                return String.valueOf(result) + "Incorrect parameter detected";
            case PT_STATUS_SC_COMM_FAIL /*-3102*/:
                return String.valueOf(result) + "Failure during communication with the card";
            case PT_STATUS_SC_NOT_SUPPORTED /*-3101*/:
                return String.valueOf(result) + "Communication with this card is not supported";
            case PT_STATUS_SC_ERROR /*-3100*/:
                return String.valueOf(result) + "General smart-card error";
            case PT_STATUS_SECCHAN_KEY_NOT_SET /*-1114*/:
                return String.valueOf(result) + "Private SECCHAN key was not set";
            case PT_STATUS_SONLY_ALREADY_OPENED /*-1113*/:
                return String.valueOf(result) + "SONLY session is already opened";
            case PT_STATUS_VCH_LOST /*-1112*/:
                return String.valueOf(result) + "Virtual channel connection has been lost";
            case PT_STATUS_OPERATION_CANCELED /*-1111*/:
                return String.valueOf(result) + "Operation was canceled";
            case PT_STATUS_INVALID_OTP_SEED /*-1110*/:
                return String.valueOf(result) + "Invalid OTP seed";
            case PT_STATUS_SIGN_KEY_NOT_SET /*-1109*/:
                return String.valueOf(result) + "Private SIGN key was not set";
            case PT_STATUS_KEYEX_KEY_NOT_SET /*-1108*/:
                return String.valueOf(result) + "Private KEYEX key was not set";
            case PT_STATUS_ENDIAN_ERROR /*-1107*/:
                return String.valueOf(result) + "Can not convert some parameter to proper endian. This can happenonly if the paramater is pointer to memory whose meaning is contextdependent and if the host uses other then little endian.";
            case PT_STATUS_DEVICE_NOT_GRANTED /*-1106*/:
                return String.valueOf(result) + "Access to device not granted";
            case PT_STATUS_JAVA_CB_ERROR /*-1105*/:
                return String.valueOf(result) + "Java callback throws java.lang.Error";
            case PT_STATUS_JAVA_CB_REMOTE /*-1104*/:
                return String.valueOf(result) + "Java callback throws java.rmi.RemoteException";
            case PT_STATUS_NVM_UNSUPPORTED_VERSION /*-1103*/:
                return String.valueOf(result) + "NVM has unsupported version";
            case PT_STATUS_NVM_INVALID_FORMAT /*-1102*/:
                return String.valueOf(result) + "NVM has invalid format or is not formatted at all";
            case PT_STATUS_CRYPTO_RSA_BAD_PUBLIC_KEY /*-1101*/:
                return String.valueOf(result) + "Bad RSA public key supplied";
            case PT_STATUS_CRYPTO_RSA_KEY_CHECK /*-1100*/:
                return String.valueOf(result) + "RSA key is not consistent";
            case PT_STATUS_NO_DATA /*-1099*/:
                return String.valueOf(result) + "No data found";
            case PT_STATUS_NO_LAST_MATCH_DATA /*-1098*/:
                return String.valueOf(result) + "Last match data don't exist";
            case PT_STATUS_WRONG_FINGER_DATA_ACCESS_RIGHTS /*-1097*/:
                return String.valueOf(result) + "Used wrong finger data access rights";
            case PT_STATUS_EX_ACCESS_VIOLATION /*-1096*/:
                return String.valueOf(result) + "Device is already opened for exclusive access by somebody else";
            case PT_STATUS_HW_RNG_INIT_ERROR /*-1095*/:
                return String.valueOf(result) + "HW random number generator initialization failed";
            case PT_STATUS_CRYPTO_SYM_BAD_KEY /*-1094*/:
                return String.valueOf(result) + "Bad symmetric key used";
            case PT_STATUS_CRYPTO_KEY_TOO_LONG /*-1093*/:
                return String.valueOf(result) + "Key too long probably due to the export regulations;";
            case PT_STATUS_CRYPTO_PADDING_ERROR /*-1092*/:
                return String.valueOf(result) + "Padding error detected during crypto operation";
            case PT_STATUS_CRYPTO_MECHANISM_NOT_SUPPORTED /*-1091*/:
                return String.valueOf(result) + "Requested cryptographic mechanism is not supported";
            case PT_STATUS_CRYPTO_ERROR /*-1090*/:
                return String.valueOf(result) + "General crypto error";
            case PT_STATUS_NVM_INVALID_FILE_ID /*-1089*/:
                return String.valueOf(result) + "Attempt to access non-existing internal NVM file";
            case PT_STATUS_NVM_CANNOT_READ /*-1088*/:
                return String.valueOf(result) + "NVM read operation failed";
            case PT_STATUS_NVM_CANNOT_WRITE /*-1087*/:
                return String.valueOf(result) + "NVM write operation failed";
            case PT_STATUS_NVM_ERROR /*-1086*/:
                return String.valueOf(result) + "General NVM error";
            case PT_STATUS_OTP_SEQUENCE_NUMBER_OVERFLOW /*-1085*/:
                return String.valueOf(result) + "Overflow of One Time Password sequence number";
            case PT_STATUS_SECURE_CHANNEL_ALREADY_ESTABLISHED /*-1084*/:
                return String.valueOf(result) + "Secure channel has been already established";
            case PT_STATUS_SESSION_NOT_AUTHENTICATED /*-1083*/:
                return String.valueOf(result) + "Session was not authenticated yet";
            case PT_STATUS_SENSOR_HW_ERROR /*-1082*/:
                return String.valueOf(result) + "Sensor hardware error occured";
            case PT_STATUS_SAFE_MODE /*-1081*/:
                return String.valueOf(result) + "Firmware is missing or corrupted, device is running in safe mode";
            case PT_STATUS_SENSOR_NOT_CALIBRATED /*-1080*/:
                return String.valueOf(result) + "Sensor is not calibrated";
            case PT_STATUS_UNSUPPORTED_SPEED /*-1079*/:
                return String.valueOf(result) + "Host hardware doesn't support requested communication speed";
            case PT_STATUS_DEVICE_SICK /*-1078*/:
                return String.valueOf(result) + "Device doesn't work as expected";
            case PT_STATUS_DEVICE_NOT_FOUND /*-1077*/:
                return String.valueOf(result) + "Device not found";
            case PT_STATUS_SUSPEND /*-1076*/:
                return String.valueOf(result) + "Connection interrupted because of suspend request";
            case PT_STATUS_OLD_VERSION /*-1075*/:
                return String.valueOf(result) + "Attempt to upgrade to older firmware version";
            case PT_STATUS_POWER_SHUTOFF /*-1074*/:
                return String.valueOf(result) + "Asynchronous power shut down";
            case PT_STATUS_GAIN_OFFSET /*-1073*/:
                return String.valueOf(result) + "Gain offset calibration error";
            case PT_STATUS_SENSOR_NOT_REPAIRABLE /*-1072*/:
                return String.valueOf(result) + "Sensor is not repairable";
            case PT_STATUS_TOO_MANY_BAD_LINES /*-1071*/:
                return String.valueOf(result) + "Too many bad lines";
            case PT_STATUS_SENSOR_OUT_OF_LIMITS /*-1070*/:
                return String.valueOf(result) + "The measured values are out of allowable limits";
            case PT_STATUS_NO_SENSOR /*-1069*/:
                return String.valueOf(result) + "No sensor";
            case PT_STATUS_SAME_VERSION /*-1068*/:
                return String.valueOf(result) + "Attempt to upgrade to same firmware version";
            case PT_STATUS_DIAGNOSTICS_FAILED /*-1067*/:
                return String.valueOf(result) + "Diagnostics failed";
            case PT_STATUS_LATCHUP_DETECTED /*-1066*/:
                return String.valueOf(result) + "Sensor latch-up event detected";
            case PT_STATUS_ANTISPOOFING_NOT_CAPTURED /*-1065*/:
                return String.valueOf(result) + "Antispoofing data were not captured";
            case PT_STATUS_CALIBRATION_FAILED /*-1064*/:
                return String.valueOf(result) + "Calibration failed";
            case PT_STATUS_NOT_SUPPORTED /*-1063*/:
                return String.valueOf(result) + "Requested functionality or value of parameter is not supported";
            case PT_STATUS_SWIPE_TOO_BAD /*-1062*/:
                return String.valueOf(result) + "Finger swipe is too bad for image reconstruction";
            case PT_STATUS_INVALID_PURPOSE /*-1061*/:
                return String.valueOf(result) + "Purpose parameter or BIR's purpose; is invalid for given operation";
            case PT_STATUS_I2C_EEPROM_ERROR /*-1060*/:
                return String.valueOf(result) + "I2C EEPROM error occured";
            case PT_STATUS_TOUCH_CHIP_ERROR /*-1059*/:
                return String.valueOf(result) + "Touch chip error occured";
            case PT_STATUS_SESSION_TERMINATED /*-1058*/:
                return String.valueOf(result) + "Session was terminated";
            case PT_STATUS_COMM_ERROR /*-1057*/:
                return String.valueOf(result) + "General communication error";
            case PT_STATUS_NOT_IMPLEMENTED /*-1056*/:
                return String.valueOf(result) + "Function or service is not implemented";
            case PT_STATUS_GOING_TO_SLEEP /*-1055*/:
                return String.valueOf(result) + "Power off attempt failed";
            case PT_STATUS_UNKNOWN_COMMAND /*-1054*/:
                return String.valueOf(result) + "Unknown command";
            case PT_STATUS_AUTHENTIFICATION_FAILED /*-1053*/:
                return String.valueOf(result) + "Authentification failed";
            case PT_STATUS_BIO_OPERATION_CANCELED /*-1052*/:
                return String.valueOf(result) + "Biometric operation canceled";
            case PT_STATUS_CONSOLIDATION_FAILED /*-1051*/:
                return String.valueOf(result) + "Failure of template consolidation";
            case PT_STATUS_BIOMETRIC_TIMEOUT /*-1050*/:
                return String.valueOf(result) + "Timeout for biometric operation has expired";
            case PT_STATUS_NO_TEMPLATE /*-1049*/:
                return String.valueOf(result) + "No template was captured in current session";
            case PT_STATUS_ACCESS_DENIED /*-1046*/:
                return String.valueOf(result) + "Access to operation is denied";
            case PT_STATUS_ANTISPOOFING_IMPORT /*-1045*/:
                return String.valueOf(result) + "Attempt to import antispoofing info to TFM";
            case PT_STATUS_ANTISPOOFING_EXPORT /*-1044*/:
                return String.valueOf(result) + "Attempt to export antispoofing info from TFM";
            case PT_STATUS_SLOT_NOT_FOUND /*-1043*/:
                return String.valueOf(result) + "Requested slot was not found";
            case PT_STATUS_BAD_BIO_TEMPLATE /*-1042*/:
                return String.valueOf(result) + "Bad biometric template";
            case PT_STATUS_TIMEOUT /*-1041*/:
                return String.valueOf(result) + "Timeout elapsed";
            case PT_STATUS_CANNOT_CONNECT /*-1040*/:
                return String.valueOf(result) + "Cannot connect to TFM";
            case PT_STATUS_ALREADY_OPENED /*-1039*/:
                return String.valueOf(result) + "Connection is already opened";
            case PT_STATUS_NOT_ENOUGH_TFM_MEMORY /*-1038*/:
                return String.valueOf(result) + "Not enough memory on TFM to process given operation";
            case PT_STATUS_WRONG_RESPONSE /*-1037*/:
                return String.valueOf(result) + "TFM has returned wrong or unexpected response";
            case PT_STATUS_INVALID_INPUT_BIR_FORM /*-1036*/:
                return String.valueOf(result) + "Invalid form of PT_INPUT_BIR structure";
            case PT_STATUS_FUNCTION_FAILED /*-1033*/:
                return String.valueOf(result) + "Function failed";
            case PT_STATUS_MORE_DATA /*-1010*/:
                return String.valueOf(result) + "There is more data to return than the supplied buffer can contain";
            case PT_STATUS_NOT_ENOUGH_PERMANENT_MEMORY /*-1009*/:
                return String.valueOf(result) + "Not enough permanent memory to store data";
            case PT_STATUS_DATA_TOO_LARGE /*-1008*/:
                return String.valueOf(result) + "Passed data are too large";
            case PT_STATUS_MALLOC_FAILED /*-1007*/:
                return String.valueOf(result) + "Failure of extern memory allocation function";
            case PT_STATUS_NOT_ENOUGH_MEMORY /*-1006*/:
                return String.valueOf(result) + "Not enough memory to process given operation";
            case PT_STATUS_INVALID_HANDLE /*-1005*/:
                return String.valueOf(result) + "Invalid handle error";
            case PT_STATUS_INVALID_PARAMETER /*-1004*/:
                return String.valueOf(result) + "Invalid parameter error";
            case PT_STATUS_API_ALREADY_INITIALIZED /*-1003*/:
                return String.valueOf(result) + "PerfectTrust API has been already initialized";
            case PT_STATUS_API_NOT_INIT /*-1002*/:
                return String.valueOf(result) + "PerfectTrust API wasn't initialized";
            case PT_STATUS_GENERAL_ERROR /*-1001*/:
                return String.valueOf(result) + "General or unknown error status. It is also possible that the function only partially succeeded, and that the device is in an inconsistent state.";
            case 0:
                return String.valueOf(result) + "Success return status";
            default:
                return String.valueOf(result) + "unknown";
        }
    }
}
