package cm.security.dak.services;

public class Comparateur {

    public static byte[] empreinteEnrollee;
    public static byte[] empreinteScannee;

    public double compareByteArrays(byte[] a, byte[] b) {
        int n = Math.min(a.length, b.length), nLarge = Math.max(a.length, b.length);
        int unequalCount = nLarge - n;
        for (int i=0; i<n; i++)
            if (a[i] != b[i]) unequalCount++;
        return unequalCount * 100.0 / nLarge;
    }

    public double comparer() {
        return 0;
    }
}
