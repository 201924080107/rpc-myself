package com.json;

import com.alibaba.fastjson.JSON;
import com.bean.common.User;

public class TestJSON {
    public static void main(String[] args) {
        Test test = new Test("jjw");
        String s = JSON.toJSONString(test);
        String s1 = s +  "/n";
        int length = s.length() - 1;
        s1.substring(0,length);
        Test test1 = (Test)JSON.parseObject(s, Test.class);
        test1.print();
        System.out.println(test1.getName());
    }
}
