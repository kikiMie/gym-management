# 健身房管理系统 (Gym Management System)
## 项目简介
基于 Spring Boot + MyBatis 开发的健身房管理系统，提供会员管理、课程安排、设备管理等功能的 RESTful API。
## 技术栈
- **后端框架**: Spring Boot
- **持久层**: MyBatis
- **数据库**: MySQL 8.0+
- **开发语言**: Java 21
- **构建工具**: Maven
- **其他**: Lombok
## 项目结构
```
gym-management-system/
├── src/main/java/com/gym/gymmanagementsystem/
│   ├── config/              # 配置类
│   │   └── WebMvcConfig.java
│   ├── controller/          # 控制器层（RESTful API）
│   │   ├── ApiChatController.java
│   │   ├── ApiClassController.java
│   │   ├── ApiEmployeeController.java
│   │   ├── ApiEquipmentController.java
│   │   ├── ApiLoginController.java
│   │   ├── ApiMemberController.java
│   │   └── ApiUserController.java
│   ├── mapper/              # MyBatis Mapper 接口
│   │   ├── AdminMapper.java
│   │   ├── ClassOrderMapper.java
│   │   ├── ClassTableMapper.java
│   │   ├── EmployeeMapper.java
│   │   ├── EquipmentMapper.java
│   │   └── MemberMapper.java
│   ├── pojo/                # 实体类
│   │   ├── Admin.java
│   │   ├── ClassOrder.java
│   │   ├── ClassTable.java
│   │   ├── Employee.java
│   │   ├── Equipment.java
│   │   └── Member.java
│   ├── security/            # 安全认证
│   │   └── SessionAuthInterceptor.java
│   ├── service/             # 服务层接口
│   │   ├── impl/            # 服务层实现
│   │   └── *.java           # 各业务接口
│   └── GymManagementSystemApplication.java  # 启动类
├── src/main/resources/
│   ├── mapper/              # MyBatis XML 映射文件
│   │   ├── AdminMapper.xml
│   │   ├── ClassOrderMapper.xml
│   │   ├── ClassTableMapper.xml
│   │   ├── EmployeeMapper.xml
│   │   ├── EquipmentMapper.xml
│   │   └── MemberMapper.xml
│   └── application.yml      # 应用配置文件
└── pom.xml                  # Maven 配置文件
```
## 主要功能模块

### 1. 用户认证
- 登录/登出
- Session 认证拦截

### 2. 会员管理
- 会员信息 CRUD
- 会员查询

### 3. 课程管理
- 课程表管理
- 课程订单管理

### 4. 员工管理
- 员工信息 CRUD

### 5. 设备管理
- 健身器材 CRUD

### 6. 聊天功能
- API 聊天接口
## 环境要求
- JDK 21+
- Maven 3.6+
- MySQL 8.0+

## 快速开始

### 1. 克隆项目
```
bash
git clone git@github.com:kikiMie/gym-management-system.git
cd gym-management-system
```
### 2. 配置数据库
编辑 `src/main/resources/application.yml`，配置 MySQL 连接信息：
```
yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: your_username
    password: your_password
```
### 3. 编译项目
```
bash
mvn clean package
```
### 4. 运行项目
```
bash
mvn spring-boot:run
```
或使用 JAR 包运行：
```
bash
java -jar target/gym-management-system-*.jar
```
## API 接口
所有 API 接口均以 `/api/` 为前缀，受 Session 认证保护。

## 注意事项
- YAML 配置文件使用 2 个空格缩进
- `@Service` 注解应放在实现类上
- MyBatis Mapper 方法不能使用静态调用
- Mapper XML 的 namespace 必须与接口全限定名一致
```


您可以将以上内容保存为 `README.md` 文件放在项目根目录下。这份文档包含了：

1. ✅ 简洁的项目结构树
2. ✅ 技术栈说明
3. ✅ 主要功能模块
4. ✅ 快速开始指南
5. ✅ 常见注意事项（基于您的项目经验）

需要我调整或补充什么内容吗？
