package demo;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Yejing
 *
 */
public class Md5Util {

    private static final String ALGORITHM = "MD5";

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     * 使用指定的加密方式加密数据
     *
     * @param algorithm 加密方式
     * @param str 需要加密的数据
     * @return String 加密后的数据
     */
    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用MD5加密数据
     *
     * @param str 需要加密的数据
     * @return String 加密后的数据（32位）
     */
    public static String encodeByMD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(str.getBytes());
            BigInteger hash = new BigInteger(1, messageDigest.digest());
            String result = hash.toString(16);
            while (result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将byte数组转化为16进制字符串
     *
     * @param bytes byte数组
     * @return 16进制字符串
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] << 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 根据盐值加密密码
     * @param password 需要加密的密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public static String getPassword(String password, String salt) {
        String passwordSHA1 = Md5Util.encode("SHA1", password);
        String password_salt = Md5Util.encode("SHA1", passwordSHA1 + salt);
        return password_salt;
    }

    /**
     * 根据盐值加密密码
     * @param password 需要加密的密码
     * @param salt     盐值
     * @return         加密后的密码
     */
    public static String encodeBySalt(String password, String salt) {
        // 盐MD5加密
        String md5Salt = encodeByMD5(salt);
        // 加盐密码
        String  passwordBySalt =  getPassword(password, md5Salt);
        return passwordBySalt;
    }

    public static void main(String[] args) {
    }
}
