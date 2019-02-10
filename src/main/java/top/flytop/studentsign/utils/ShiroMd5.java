package top.flytop.studentsign.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @ClassName ShiroMd5
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/2/10 19:59
 * @Version 1.0
 */
public class ShiroMd5 {
    //添加user的密码加密方法
    public static String md5Encrypt(String password, String salt) {
        String hashAlgorithmName = "MD5";//加密方式

        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);//以账号作为盐值

        int hashIterations = 2;//加密次数

        SimpleHash hash = new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations);

        return hash.toString();
    }
}
