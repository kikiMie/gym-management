package com.gym.gymmanagementsystem.pojo;
//这个注解引入getter和setter方法
import lombok.Data;
//实体类
@Data
public class Admin {
//    adminAccount 管理员账户
    private Integer adminAccount;
    private String adminPassword;
}
