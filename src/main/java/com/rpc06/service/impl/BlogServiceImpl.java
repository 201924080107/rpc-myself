package com.rpc06.service.impl;


import com.rpc06.annotation.RpcService;
import com.rpc06.common.Blog;
import com.rpc06.service.BlogService;

@RpcService(name = "BlogService")
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        return blog;
    }
}
