package com.gym.gymmanagementsystem.controller;


import com.gym.gymmanagementsystem.pojo.Admin;
import com.gym.gymmanagementsystem.service.AdminService;
import com.gym.gymmanagementsystem.service.EmployeeService;
import com.gym.gymmanagementsystem.service.EquipmentService;
import com.gym.gymmanagementsystem.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.gymmanagementsystem.pojo.Member;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ApiLoginController {
//    管理员属性，常量
    private static final String SESSION_ADMIN = "admin";
//    以下是类成员变量
//    service 注入
    private final MemberService memberService;
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final EquipmentService equipmentService;
//以下是 构造函数参数（局部变量），因为成员变量和类变量名字一样，所以用this, 表示当前对象

//this 的作用就是告诉编译器："我要操作的是当前对象的成员变量，不是局部变量！"
public ApiLoginController(
        MemberService memberService,
        AdminService adminService,
        EmployeeService employeeService,
        EquipmentService equipmentService) {
    this.memberService = memberService;
    this.adminService = adminService;
    this.employeeService = employeeService;
    this.equipmentService = equipmentService;
}


    /**
     * 管理员登录接口
     * @param admin 包含用户名和密码的管理员对象（前端通常以表单形式提交，故不使用 @RequestBody）
     * @param session HTTP 会话对象，用于存储登录状态和统计数据
     * @return 登录结果响应，成功返回 200 及成功信息，失败返回 401 及错误提示
     */
//    处理POST请求
    @PostMapping("/adminLogin")
    public ResponseEntity<Map<String,Object>> adminLogin(Admin admin, HttpSession session) {
        // 调用服务层验证管理员账号密码，若验证通过返回管理员对象，否则返回 null
        Admin loggedIn = adminService.adminLogin(admin);
        
        if (loggedIn != null) {
            // 登录成功：将管理员基本信息存入 Session
            session.setAttribute("admin", loggedIn);
            
            // 获取并存储首页所需的统计数据（会员总数、员工总数等）到 Session 中
            putAdminMainDataInSession(session, loggedIn);
            
            // 返回登录成功的标准响应
            return ResponseEntity.ok(singleSuccess());
        } else {
            // 登录失败：返回未授权状态及错误信息
            return unauthorized("账号或密码有误");
        }
    }

    //通过下面这个方法，再用该方法来获取数据，会话层存储在body
    @GetMapping("/adminMain")
    public ResponseEntity<Map<String,Object>> adminMain(HttpSession session) {
        Map<String,Object> body=new HashMap<>();
        body.put("success", true);
        body.put("memberTotal",session.getAttribute("memberTotal"));
        body.put("employeeTotal",session.getAttribute("employeeTotal"));
        body.put("humanTotal",session.getAttribute("humanTotal"));
        body.put("equipmentTotal",session.getAttribute("equipmentTotal"));
        return ResponseEntity.ok(body);
    }

    // 返回成功信息的方法
    private static Map<String,Object> singleSuccess() {
//        创建键值对Map对象
        Map<String,Object> m = new HashMap<>();
        m.put("success", true);
        m.put("message", "登录成功");
        return m;
    }

    // 返回失败信息的方法 整体的信息，登录失败， 账号或密码有误
    private static ResponseEntity<Map<String,Object>> unauthorized(String message) {
//       key value
        Map<String,Object> m = new HashMap<>();
//        成功返回： false  message 账号或密码有误
        m.put("success", false);
        m.put("message", message);
//        body 内返回信息
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
/*
1. 告诉服务员你要什么状态的“套餐”：401套餐（即未授权套餐）
ResponseEntity.status(HttpStatus.UNAUTHORIZED)

2. 告诉服务员这个套餐里具体放什么“菜”：放 m 这道菜
    .body(m);

3. 服务员（Spring）给你端上来的就是一个完整的、装好的餐盘（ResponseEntity对象）。
        ResponseEntity.status(...).body(...) 是Spring MVC中用于精确控制HTTP响应的标准方式。
*/
    }



    //调用所对应的方法
    private void putAdminMainDataInSession(HttpSession session, Admin admin) {
        session.setAttribute(SESSION_ADMIN, admin);
        Integer memberTotal = memberService.selectTotalCount();
        Integer employeeTotal = employeeService.selectTotalCount();
        Integer humanTotal = memberTotal + employeeTotal;
        Integer equipmentTotal = equipmentService.selectTotalCount();
//        会话层存储
        session.setAttribute("memberTotal", memberTotal);
        session.setAttribute("employeeTotal", employeeTotal);
        session.setAttribute("humanTotal", humanTotal);
        session.setAttribute("equipmentTotal", equipmentTotal);
}
//会员登录接口
    @PostMapping("/userLogin")
    public ResponseEntity<Map<String,Object>> userLogin(Member member, HttpSession session) {
        Member loggedIn = memberService.userLogin(member);

        if (loggedIn != null) {
            session.setAttribute("user", loggedIn);
            return ResponseEntity.ok(singleSuccess());
        } else {
            return unauthorized("账号或密码有误");
        }
    }

    @GetMapping("/userMain")
    public ResponseEntity<Map<String,Object>> userMain(HttpSession session) {
        Member user = (Member) session.getAttribute("user");
        Map<String,Object> body = new HashMap<>();
        body.put("success", true);
        body.put("user", user);
        return ResponseEntity.ok(body);
    }

// ... existing code ...




}

/*
✅ 业务逻辑正确：登录成功后保存 Session 数据
✅ 代码结构清晰：方法职责分明
✅ 依赖注入规范：使用构造函数注入
✅ HTTP 响应控制得当：正确使用 ResponseEntity*/