package com.bean.service.impl;


import com.bean.annotation.RpcService;
import com.bean.common.Blog;
import com.bean.service.BlogService;

@RpcService
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }
}
