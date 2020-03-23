package com.bin.system.common;

import org.apache.shiro.crypto.hash.Md5Hash;
import sun.security.provider.MD5;

import java.util.UUID;

/**
 * @author 朱彬
 * @date 2020/3/20 16:29
 */
public class MD5Utils {

    /**
     *生成UUID
     * @params []
     * @return java.lang.String
     */
    public static String creatUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     *加密
     * @params [source, salt, has]
     * @return java.lang.String
     */
    public static String md5(String source,String salt,Integer hashIterations){
        Md5Hash hash = new Md5Hash(source,salt,hashIterations);
        return hash.toString();
    }
}
