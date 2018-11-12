package com.lsz.common;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UuidMd5 {

    /**
     * 采用URL Base64字符，即把“+/”换成“-_”
     */
    private static final char[] toBase64URL = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_', '='
    };
    public static String uuidWith8Bit() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(toBase64URL[x % 0x3E]);
        }
        return shortBuffer.toString();

    }
    public static String md5With22Bit(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        BigInteger bigInteger = new BigInteger(1, secretBytes);
        long longLeft = bigInteger.longValue();
        long longRight = bigInteger.shiftRight(64).longValue();
        return getUUID22Ex(longLeft, longRight);
    }

    /**
     * 将随机UUID转换成22位字符串
     *
     * @return
     */
    public static String uuidWith22Bit() {
        UUID uuid = UUID.randomUUID();
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        return getUUID22Ex(msb, lsb);
    }

    private static String getUUID22Ex(long msb, long lsb) {
        char[] out = new char[24];
        int tmp = 0, idx = 0;
        // 循环写法
        int bit = 0, bt1 = 8, bt2 = 8;
        int mask = 0x00, offsetm = 0, offsetl = 0;

        for (; bit < 16; bit += 3, idx += 4) {
            offsetm = 64 - (bit + 3) * 8;
            offsetl = 0;
            tmp = 0;

            if (bt1 > 3) {
                mask = (1 << 8 * 3) - 1;
            } else if (bt1 >= 0) {
                mask = (1 << 8 * bt1) - 1;
                bt2 -= 3 - bt1;
            } else {
                mask = (1 << 8 * ((bt2 > 3) ? 3 : bt2)) - 1;
                bt2 -= 3;
            }
            if (bt1 > 0) {
                bt1 -= 3;
                tmp = (int) ((offsetm < 0) ? msb : (msb >>> offsetm) & mask);
                if (bt1 < 0) {
                    tmp <<= Math.abs(offsetm);
                    mask = (1 << 8 * Math.abs(bt1)) - 1;
                }
            }
            if (offsetm < 0) {
                offsetl = 64 + offsetm;
                tmp |= ((offsetl < 0) ? lsb : (lsb >>> offsetl)) & mask;
            }

            if (bit == 15) {
                out[idx + 3] = toBase64URL[64];
                out[idx + 2] = toBase64URL[64];
                tmp <<= 4;
            } else {
                out[idx + 3] = toBase64URL[tmp & 0x3f];
                tmp >>= 6;
                out[idx + 2] = toBase64URL[tmp & 0x3f];
                tmp >>= 6;
            }
            out[idx + 1] = toBase64URL[tmp & 0x3f];
            tmp >>= 6;
            out[idx] = toBase64URL[tmp & 0x3f];
        }

        return new String(out, 0, 22);
    }
}