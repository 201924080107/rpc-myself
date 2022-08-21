package com.rpc04.service.impl;


import com.rpc04.annotation.RpcService;
import com.rpc04.common.Blog;
import com.rpc04.service.BlogService;

@RpcService(name = "BlogService")
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        return blog;
    }
}
