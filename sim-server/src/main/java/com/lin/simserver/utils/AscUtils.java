package com.lin.simserver.utils;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.lin.simserver.entity.User;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/5 11:03
 */
public class AscUtils {
    // 秘钥
    private static final byte[] key = {-68, 13, 111, -22, 34, -4, 11, 45, 23, -53, 123, -99, -36, 12, -20, 44};

    private static SymmetricCrypto aes = null;

    static {
        aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
    }

    /**
     * 加密
     * @param content
     * @return
     */
    public static String encrypt(String content){
        return aes.encryptHex(content);
    }

    /**
     * 解密
     * @param content
     * @return
     */
    public static String decrypt(String content){
        return aes.decryptStr(content);
    }

    public static String generateToken(User user){
        String salt = "123";
        return AscUtils.encrypt(salt + "-" + user.getUserName() + "-" + user.getPassword() + "-" + user.getUserId());
    }
}
