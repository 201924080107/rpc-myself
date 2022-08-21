package com.kun.rpc04.service.impl;


import com.kun.rpc04.common.Blog;
import com.kun.rpc04.service.BlogService;
import com.kun.rpc04.annotation.RpcService;

@RpcService(name = "BlogService")
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        return blog;
    }
}
