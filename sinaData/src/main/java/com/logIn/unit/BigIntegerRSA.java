package com.logIn.unit;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
public class BigIntegerRSA {
    /**
     * µ«»Îº”√‹
     * @param modeHex
     * @param exponentHex
     * @param messageg
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public String rsaCrypt(String modeHex, String exponentHex, String messageg)
            throws IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException,
            UnsupportedEncodingException {
        KeyFactory factory = KeyFactory.getInstance("RSA");

        BigInteger m = new BigInteger(modeHex, 16); /* public exponent */
        BigInteger e = new BigInteger(exponentHex, 16); /* modulus */
        RSAPublicKeySpec spec = new RSAPublicKeySpec(m, e);

        RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);
        Cipher enc = Cipher.getInstance("RSA");
        enc.init(Cipher.ENCRYPT_MODE, pub);

        byte[] encryptedContentKey = enc.doFinal(messageg.getBytes("GB2312"));

        return new String(Hex.encodeHex(encryptedContentKey));
    }
}
