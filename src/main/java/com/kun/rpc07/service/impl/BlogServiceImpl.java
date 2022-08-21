package com.kun.rpc07.service.impl;


import com.kun.rpc07.annotation.RpcService;
import com.kun.rpc07.common.Blog;
import com.kun.rpc07.service.BlogService;

@RpcService(name = "BlogService")
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        return blog;
    }
}
