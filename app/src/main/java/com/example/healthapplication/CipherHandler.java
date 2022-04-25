package com.example.healthapplication;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherHandler {
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";   // The default scheme

    // To generate the first initial public key

    public void get_initial_key() throws NoSuchAlgorithmException {
        KeyGenerator key_generator = KeyGenerator.getInstance("AES");   // AES uses 128 bits
        key_generator.init(128); // Size of Key is 128 bits
        SecretKey secret_key = key_generator.generateKey(); // Actually generate the secret key for the first time
        byte[] encoded_key = secret_key.getEncoded();
        for(byte b : encoded_key){
            System.out.printf("%2X",b);
        }
        String encoded_string = Base64.encodeToString(encoded_key, Base64.NO_WRAP);
        Log.println(Log.VERBOSE, "The encoded key string", encoded_string);
    }

    public String encrypt_message(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String key_value = BuildConfig.SECRET_KEY;
        byte [] byte_of_key = Base64.decode(key_value, Base64.NO_WRAP);
        SecretKey secret_key = new SecretKeySpec(byte_of_key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secret_key);
        byte [] byte_of_message = message.getBytes();
        byte [] byte_of_encrypted_message = cipher.doFinal(byte_of_message);
        String s = "";
        for(byte b : byte_of_encrypted_message){
            System.out.printf("%2x", b);
        }
        return Base64.encodeToString(byte_of_encrypted_message, Base64.NO_WRAP);
    }

    public String decrypt_message(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String key_value = BuildConfig.SECRET_KEY;
        byte [] byte_of_key = Base64.decode(key_value, Base64.NO_WRAP);
        SecretKey secret_key = new SecretKeySpec(byte_of_key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secret_key);
        byte [] byte_of_encrypted_message = cipher.doFinal(Base64.decode(message.getBytes(), Base64.NO_WRAP));
        String s = "";
        for(byte b : byte_of_encrypted_message){
            System.out.printf("%2x", b);
        }
        return new String(byte_of_encrypted_message);
    }
}
