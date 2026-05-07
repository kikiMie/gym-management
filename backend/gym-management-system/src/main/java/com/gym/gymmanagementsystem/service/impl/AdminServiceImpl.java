package com.gym.gymmanagementsystem.service.impl;

import com.gym.gymmanagementsystem.mapper.AdminMapper;
import com.gym.gymmanagementsystem.pojo.Admin;
import com.gym.gymmanagementsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 管理员服务层实现类
 * 实现管理员相关的业务逻辑
 */
//实现 需要重写一下方法
@Service
public class AdminServiceImpl implements AdminService {
//注解注入都是小写   AdminMapper
//    可以作为引用类型：AdminMapper adminMapper;  这是声明一个变量
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin adminLogin(Admin admin) {
        //       ✅ "通过接口引用，调用实现类的方法"
        return adminMapper.selectByAccountAndPassword(admin);
    }
}
