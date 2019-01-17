package com.bayuedekui.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class DESUtil {
    private static Key key;
    private static String KEY_STR = "myKey";
    private static String CHARSETNAMR="UTF-8";
    private static String ALGORITHM="DES";
            
    static {
        try {
            KeyGenerator generator=KeyGenerator.getInstance(ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(secureRandom);
            key=generator.generateKey();
            generator=null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     *  惊醒DES加密
     * @param str
     * @return
     */
    public static String getEncryptString(String str) throws Exception{
        BASE64Encoder base64Encoder=new BASE64Encoder();
            try {
                byte[] bytes=str.getBytes(CHARSETNAMR);
                Cipher cipher=Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE,key);
                byte[] doFinal = cipher.doFinal(bytes);
                return base64Encoder.encode(doFinal);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
    
    public static String getDecryptString(String str){
        BASE64Decoder base64Decoder=new BASE64Decoder();
        try {
            byte[] bytes = base64Decoder.decodeBuffer(str);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] doFinal=cipher.doFinal(bytes);
            return new String(doFinal, CHARSETNAMR);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getEncryptString("root"));
        System.out.println(getEncryptString("123456"));
//        System.out.println(getEncryptString("wxd7f6c5b8899fba83"));
//        System.out.println(getEncryptString("665ae80dba31fc91ab6191e7da4d676d"));
    }
}
