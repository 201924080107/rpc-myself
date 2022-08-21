package com.kun.rpc04.common;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    // 客户端和服务端共有的，模拟RPC中传输的信息
    private Integer id;
    private String userName;
    private Boolean sex;
}
