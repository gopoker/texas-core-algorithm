package com.deeppoker.texas.core.playing;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Test {
    public static void main(String[] args) {

        byte[] x = Longs.toByteArray(12345678l);
        byte[] y = Ints.toByteArray(123);

        byte[] z = Bytes.concat(x, y);


        String a = Hex.encodeHexString(z);
        System.out.println(a);
        String b = Base64.encodeBase64String(z);
        System.out.println(b);

    }
}
