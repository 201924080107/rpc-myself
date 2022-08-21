package com.rpc05.service.impl;


import com.rpc05.annotation.RpcService;
import com.rpc05.common.Blog;
import com.rpc05.service.BlogService;

@RpcService(name = "BlogService")
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        return blog;
    }
}
