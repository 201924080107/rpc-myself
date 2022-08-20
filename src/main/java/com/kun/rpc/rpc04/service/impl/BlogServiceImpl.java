package com.kun.rpc.rpc04.service.impl;


import com.kun.rpc.rpc04.annotation.RpcService;
import com.kun.rpc.rpc04.common.Blog;
import com.kun.rpc.rpc04.service.BlogService;

@RpcService(name = "com.kun.BlogService")
public class BlogServiceImpl implements BlogService {
    static {
        System.out.println("实例化");
    }
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }
}
