# 健身房管理系统 (Gym Management System)
## 项目概述
这是一个基于前后端分离架构的健身房管理系统，提供会员管理、课程管理、设备管理、员工管理等功能。

## 技术栈

### 后端
- **框架**: Spring Boot
- **数据库访问**: MyBatis
- **安全认证**: Session-based Authentication
- **构建工具**: Maven

### 前端
- **框架**: Vue 3 + TypeScript
- **路由**: Vue Router
- **HTTP客户端**: Axios (通过 api/client.ts)
- **构建工具**: Vite

## 项目结构

```

gym-management/
├── backend/gym-management-system/          # 后端项目根目录
│   ├── src/main/java/com/gym/
│   │   ├── controller/                     # REST API控制器
│   │   │   ├── ApiChatController.java      # 聊天相关接口
│   │   │   ├── ApiClassController.java     # 课程相关接口
│   │   │   ├── ApiEmployeeController.java  # 员工相关接口
│   │   │   ├── ApiEquipmentController.java # 设备相关接口
│   │   │   ├── ApiLoginController.java     # 登录认证接口
│   │   │   ├── ApiMemberController.java    # 会员相关接口
│   │   │   └── ApiUserController.java      # 用户相关接口
│   │   ├── mapper/                         # MyBatis Mapper接口
│   │   ├── pojo/                           # 实体类
│   │   ├── service/                        # 业务逻辑层
│   │   │   └── impl/                       # 服务实现类
│   │   ├── security/                       # 安全配置
│   │   └── config/                         # 系统配置
│   └── src/main/resources/
│       ├── mapper/                         # MyBatis XML映射文件
│       └── application.yml                 # 应用配置文件
│
└── frontend/front/                         # 前端项目根目录
    ├── src/
    │   ├── pages/                          # 页面组件
    │   │   ├── Admin*.vue                  # 管理员相关页面
    │   │   ├── Class*.vue                  # 课程管理页面
    │   │   ├── Employee*.vue               # 员工管理页面
    │   │   ├── Equipment*.vue              # 设备管理页面
    │   │   └── Member*.vue                 # 会员管理页面
    │   ├── router/                         # 路由配置
    │   ├── api/                            # API请求封装
    │   └── components/                     # 公共组件
    └── public/                             # 静态资源
```
## 核心功能模块

### 1. 会员管理 (Member)
- 会员信息查询
- 会员添加/删除/修改
- 会员卡管理

### 2. 课程管理 (Class)
- 课程列表查看
- 课程预约
- 订单管理

### 3. 员工管理 (Employee)
- 员工信息管理
- 员工增删改查

### 4. 设备管理 (Equipment)
- 设备信息维护
- 设备状态跟踪

### 5. 系统管理
- 管理员登录
- 用户权限控制
- Session认证

## 快速开始

### 后端启动
```
bash
cd backend/gym-management-system
mvn spring-boot:run
```
### 前端启动
```
bash
cd frontend/front
npm install
npm run dev
```
## 配置文件
- **后端配置**: `backend/gym-management-system/src/main/resources/application.yml`
- **前端路由**: `frontend/front/src/router/index.ts`
- **API配置**: `frontend/front/src/api/client.ts`
```


这个简化版的文档包含了您项目的核心信息，包括技术栈、项目结构、主要功能模块和快速启动指南。如果您需要添加更多细节（如数据库设计、API文档等），请告诉我！
