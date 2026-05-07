package com.gym.gymmanagementsystem.controller;

import com.gym.gymmanagementsystem.pojo.Employee;
import com.gym.gymmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
//注入对应的employee的前面的地址
@RequestMapping("/api/employee")
public class ApiEmployeeController {

    @Autowired
    private  EmployeeService employeeService; //    不能用final，因为变量需要重新赋值，final不允许重新赋值

//    查询所有内容用get
    @GetMapping("/selEmployee")
    public Map<String,Object> selectEmployee(){
//  使用 service获取员工列表
//  List<Employee> 的声明 和 XML 里的 resultType="Employee" 决定了数据从数据库出来时就是 Employee
        List<Employee> employeeList = employeeService.findAll();
//        第一步：造箱       创建一个 Map 对象（类似字典）
        HashMap<String,Object> resp = new HashMap<>();
        resp.put("success",true);//key:value 键值对键："success"（字符串） 值：true（布尔值） 结果：{"success": true}
//       第二步：往箱子放东西
        resp.put("employeeList",employeeList);

        return resp;//将map对象返回给前端

    }

//    添加员工信息， ResponseEntity是处理http请求
    @PostMapping("/addEmployee")
    public ResponseEntity<Map<String,Object>> addEmployee(Employee employee){ //收到前端传来的“半成品”数据
//        后端自动补全“工号”和“时间”
//        添加的员工信息，需要初始一个固定的位数，后面随机
        Random random = new Random();
        String account1 ="1010";//        创建当前账户的前四位
        for (int i=0;i<5;i++) {//        后五位生成随机字符串
            account1 += random.nextInt(10);
        }
        int account = Integer.parseInt(account1);//        需要把字符串转换为int类型
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDay = simpleDateFormat.format(date);//        转换成我们定义的时间格式
        employee.setEmployeeAccount(account);
        employee.setEntryTime(nowDay);
//        调用service方法，传入员工对象
        employeeService.insertEmployee(employee); //把“完整版”数据保存到数据库
 //        HashMap存的是引用地址
        HashMap<String, Object> resp = new HashMap<>();

        resp.put("success",true);
        resp.put("message","添加成功");
        return ResponseEntity.ok(resp);   //告诉前端：“办好了，这是结果”
    }

//    删除员工
        @PostMapping("/delEmployee")
        public ResponseEntity<Map<String, Object>> deleteEmployee(Integer employeeAccount) {
        employeeService.deleteByEmployeeAccount(employeeAccount);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        return ResponseEntity.ok(resp);
}


        @PostMapping("/updateEmployee")
        public ResponseEntity<Map<String, Object>> updateEmployee(Employee employee) {
        employeeService.updateMemberByEmployeeAccount(employee);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        return ResponseEntity.ok(resp);
    }

//    跳转修改列表
        @GetMapping("/toUpdateEmployee")
        public Map<String, Object> toUpdateEmployee(Integer employeeAccount) {
        List<Employee> employeeList = employeeService.selectByEmployeeAccount(employeeAccount);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("employeeList", employeeList);
        return resp;
}
}
