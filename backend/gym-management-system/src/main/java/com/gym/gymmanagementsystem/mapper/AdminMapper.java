package com.gym.gymmanagementsystem.mapper;

import com.gym.gymmanagementsystem.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员数据访问层接口
 */
@Mapper // ← 告诉 MyBatis：这是一个 Mapper 接口
public interface AdminMapper {

    /**
     * 根据账号和密码查询管理员信息
     *
     * @param admin 包含账号和密码的管理员对象
     * @return 匹配的管理员对象，若未找到则返回 null
     */
    // 这个方法会对应一条 SQL 查询语句，查询这个用户名和密码是否存在，返回类型是 一个实例类
    Admin selectByAccountAndPassword(Admin admin);

    // 方式二示例：使用注解代替 XML
//    @Mapper
//    public interface AdminMapper {
//        @Select("SELECT * FROM admin WHERE account = #{account} AND password = #{password}")
//        Admin selectByAccountAndPassword(Admin admin);
//    }

}
/*
@Mapper：MyBatis 扫描到这个注解，为 AdminMapper 接口创建代理对象
方法调用：当您在 Service 层调用 selectByAccountAndPassword() 时
SQL 执行：MyBatis 会找到对应的 SQL 语句（通常在 XML 文件中）
数据库操作：执行 SQL 查询 admin 表
结果映射：将查询结果自动映射回 Admin 对象
总结
@Mapper = "我是 MyBatis 的 Mapper 接口"
真正的数据库映射 = 实体类（Admin）+ SQL 语句（XML 或注解）
三者关系：@Mapper 接口 → SQL 映射 → 实体类（数据库表映射）*/