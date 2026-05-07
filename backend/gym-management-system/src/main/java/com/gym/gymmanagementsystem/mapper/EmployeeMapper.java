package com.gym.gymmanagementsystem.mapper;

import com.gym.gymmanagementsystem.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    Integer selectTotalCount();
//查询方法是，获取所有的数据,返回是一个list列表的员工数据
    List<Employee> findAll();

//    添加员工
    Boolean insertEmployee(Employee employee);

    Boolean deleteByEmployeeAccount(Integer employeeAccount);

    Boolean updateMemberByEmployeeAccount(Employee employee);

    List<Employee> selectByEmployeeAccount(Integer employeeAccount);
}
