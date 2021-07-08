package com.lin.sim.utils;

import org.junit.Test;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/8 12:20
 */
public class AscUtilsTest {

    @Test
    public void ascGen(){
        String encrypt = AscUtils.encrypt("123");
        System.out.println(encrypt);
    }


}
