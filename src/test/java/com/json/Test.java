package com.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Test {
    private String name;
    public void print(){
        System.out.println("test");
    }
}
