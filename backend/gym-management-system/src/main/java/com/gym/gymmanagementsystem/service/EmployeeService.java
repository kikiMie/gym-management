package com.gym.gymmanagementsystem.service;

import com.gym.gymmanagementsystem.pojo.Employee;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService{
    Integer selectTotalCount();

//    添加一个List方法
    List<Employee> findAll();
//    创建对应的方法, 返回布尔值
    Boolean insertEmployee(Employee employee);

    Boolean deleteByEmployeeAccount(Integer employeeAccount);

    Boolean updateMemberByEmployeeAccount(Employee employee);
//跳转修改列表
    List<Employee> selectByEmployeeAccount(Integer employeeAccount);
}
