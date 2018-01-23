package com.cs.utils.rsa;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：RSA加密解密工具类
 *
 * @version 1.0
 */
public class RSAUtils {
    private static Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    public static final String KEY_ALGORITHM = "RSA"; // 加密方式
    // 数字签名 签名/验证算法
    private static final String SIGNATURE_ALGORRITHM = "SHA1withRSA";
    // 公钥变量名
    private static final String PUBLIC_KEY = "RSAPublicKey";
    // 私钥变量名
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    // RSA密钥长度,默认为1024,密钥长度必须是64的倍数,范围在512~65526位之间
    private static final int KEY_SIZE = 1024;
    // RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
    // RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * 读取密钥文件内容
     *
     * @param key_file:文件路径
     * @return
     */
    private static String getKeyContent(String key_file) {
        File file = new File(key_file);
        BufferedReader br = null;
        InputStream ins = null;
        StringBuffer sReturnBuf = new StringBuffer();
        try {
            ins = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            String readStr = null;
            readStr = br.readLine();
            while (readStr != null) {
                sReturnBuf.append(readStr);
                readStr = br.readLine();
            }
        } catch (IOException e) {
            logger.error("读取密钥异常,文件地址: {}",key_file);
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                    ins = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return sReturnBuf.toString();
    }

    /**
     * 生成密钥
     *
     * @return 密钥Map
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> generateKey() throws NoSuchAlgorithmException {
        // 实例化实钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 封装密钥
        Map<String, String> keyMap = new HashMap<String, String>(2);
        keyMap.put(PUBLIC_KEY, Base64.encodeBase64String(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, Base64.encodeBase64String(privateKey.getEncoded()));
        return keyMap;
    }

    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key  私钥
     * @return String 加密数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String encryptByPrivateKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException  {
        byte[] keybyte = Base64.decodeBase64(key);
        // 取得私钥 PKCS8EncodedKeySpec封装私钥的类
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keybyte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] databyte = data.getBytes();
        int inputLen = databyte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(databyte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
            }
            out.write(cache);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encbyte = out.toByteArray();
        String enc = Base64.encodeBase64String(encbyte);
        out.close();
        return enc;
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  公钥
     * @return String 加密数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String encryptByPublicKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException  {
        byte[] keybyte = Base64.decodeBase64(key);
        // 取得公钥 X509EncodedKeySpec封装公钥的类
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keybyte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] databyte = data.getBytes("UTF-8");
        int inputLen = databyte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(databyte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
            }
            out.write(cache);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encbyte = out.toByteArray();
        String enc = Base64.encodeBase64String(encbyte);
        out.close();
        return enc;
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  私钥
     * @return String 解密数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String decryptByPrivateKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException  {
        byte[] keybyte = Base64.decodeBase64(key);
        // 取得私钥 PKCS8EncodedKeySpec封装私钥的类
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keybyte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] databyte = Base64.decodeBase64(data);
        int inputLen = databyte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(databyte, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
            }
            out.write(cache);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decbyte = out.toByteArray();
        String dec = new String(decbyte,"UTF-8");
        out.close();
        return dec;
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  公钥
     * @return String 解密数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String decryptByPublicKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException  {
        byte[] keybyte = Base64.decodeBase64(key);
        // 取得公钥 X509EncodedKeySpec封装公钥的类
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keybyte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] databyte = Base64.decodeBase64(data);
        int inputLen = databyte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(databyte, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(databyte, offSet, inputLen - offSet);
            }
            out.write(cache);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decbyte = out.toByteArray();
        String dec = new String(decbyte);
        out.close();
        return dec;
    }

    /**
     * 私钥签名
     *
     * @param data       待签名的加密数据
     * @param privateKey 私钥
     * @return String 数字签名
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(String data, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException  {
        byte[] privateKeyByte = Base64.decodeBase64(privateKey);
        // 转接私钥材料
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORRITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(Base64.decodeBase64(data));
        // 签名
        byte[] signbyte = signature.sign();
        String signstr = Base64.encodeBase64String(signbyte);
        return signstr;
    }

    /**
     * 公钥校验
     *
     * @param data      待校验数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(String data, String publicKey, String sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException  {
        byte[] publicKeyByte = Base64.decodeBase64(publicKey);
        // 转接公钥材料
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyByte);
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORRITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(Base64.decodeBase64(data));
        // 验证
        boolean isVerify = signature.verify(Base64.decodeBase64(sign));
        return isVerify;
    }

    public static void main(String[] args) throws Exception{
        String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM+RTUHuNQ+V7JXuI/6IMu+s21YWM9QvbsXidRDJYErxGU0KyXvX4QeCD6eAWIo2v9k1xNz29aLmTj9q6/P2ylXsuHuk8D+oVRJlgJ89xv3aySZxZ+fnOtlyQsGV9fPdj+N6D2u85dxDcvBU95QhTh4kFUG293pr/6LFC0xMWycvAgMBAAECgYAmNXkjSdY6qD6E2vGFSU0XEZrOngesZ47cnnMuQ+OV6AvgQrwUMAkceLSpANdLmgh2048JnexZsEt3IReOMT/NfH0cZXxneJHShlyyXBICNeg/ADU0WgKFEQgchSlMHXxwzWL5Fa0OvCgvFqP6pLjaA4i6fD3GeaoxpM5k8azSAQJBAO3l1gzwE9h9FXiK69L3OxFQ0g/Md8oLFpJQdaHxUofZmoIgfDLTD/iivtAI8DlHFN06ctVj2UB8e4Ro/1+B2tkCQQDfXKTcDtXOoL/6N7kAya+bErVOKke6PVyaZ3viPU2rFxXU4eWCeT1/Styp4AN3kkRuLl9pjMgOo4HtHFJbJ/1HAkEAwWxethQ4voss+t5Q0S2MjA6fTJMdTlvnlTzv96SDSsslMeoBsT0kPi7sX670yk097IWKOm+9gw3zS3wnLlshOQJAc13soOTgthr9TYYefjOuChC9qKPZ7G3pOK8eo5o6VZZU5mHPI3bvtqG6G9viro3T3n0BNwlKA5WcdBXey8p53QJBAJtSkaaqTyzU7nYqPQRKruZkIs6BSRicB1GrrkcfnKi9o10bW6nU6ZDMuELlS1QNQcOE/EbFnNN3UC/pgREOosE=";
        String RSA_YT_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPkU1B7jUPleyV7iP+iDLvrNtWFjPUL27F4nUQyWBK8RlNCsl71+EHgg+ngFiKNr/ZNcTc9vWi5k4/auvz9spV7Lh7pPA/qFUSZYCfPcb92skmcWfn5zrZckLBlfXz3Y/jeg9rvOXcQ3LwVPeUIU4eJBVBtvd6a/+ixQtMTFsnLwIDAQAB";
        String data = "busi_partner=101001&dt_order=20130521175800&money_order=12.10&name_goods=%E5%95%86%E5%93%81%E5%90%8D%E7%A7%B0&notify_url=http%3A%2F%2Fwww.baidu.com&no_order=20130521175800&oid_partner=201103171000000000&sign_type=RSA";
        String privateDate = encryptByPrivateKey(data, RSA_PRIVATE);
        System.out.println("encrypt=" + privateDate);
        String publicData = decryptByPublicKey(privateDate, RSA_YT_PUBLIC);
        System.out.println("decrypt=" + publicData);
        /*String encrypt = LianLianPaySecurity.encrypt(data, RSA_YT_PUBLIC);
        System.out.println("encrypt=="+encrypt);
        String decrypt = LianLianPaySecurity.decrypt(encrypt, RSA_PRIVATE);
        System.out.println("decrypt=="+decrypt);*/
    }

}