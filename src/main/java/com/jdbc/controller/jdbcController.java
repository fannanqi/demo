package com.jdbc.controller;
import com.jdbc.Dao.Student;
import com.jdbc.Dao.User;
import com.jdbc.Service.DruidService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jdbc")
public class jdbcController {
    @Autowired
    private HttpServletRequest request;
    DruidService druidService=new DruidService();
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User loginUser = druidService.login(user);
        if(loginUser!=null){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "ok";
        }else {
            return "err";
        }
    }
    @PostMapping("/regist")
    public String regist(@RequestBody User registUser) {
        boolean flag=druidService.addUser(registUser);
        if (flag) {
            return "ok";
        }
        else {
            return "err";
        }
    }
    @PostMapping("/selectAllServlet")
    public List<Student> selectAll(){
        List<Student> students = this.druidService.selectAll();
        return students;
    }
    @GetMapping ("/selectByIdServlet")
    public Student selectByIdServlet(@RequestParam String id){
        System.out.println(id);
        Student student=null;
        if(id!=null) {
            student = druidService.selectById(id);
        }
        //写数据
        return student;
    }
    @RequestMapping("/selectByNameServlet")
    public Student selectByNameServlet(@RequestParam(value = "name") String name){
        Student student=new Student();
        if(name!=null) {
            student = druidService.selectByName(name);
        }
        return student;
    }
    @RequestMapping("/deleteByIdServlet")
    public String deleteByIdServlet(@RequestParam String id){
        int result=0;//判断是否删除成功
        if(id!=null){
            result = druidService.deleteById(id);
        }
        if (result==0){
            return "error";
        }else {
            return "success";
        }
    }
    @RequestMapping("/deleteByNameServlet")
    public String deleteByNameServlet(@RequestParam(value = "name") String name){
        int result=0;//判断是否删除成功
        if(name!=null){
            result = druidService.deleteByName(name);
        }
        if (result==0){
            return "error";
        }else {
            return "success";
        }
    }
    @RequestMapping("/addServlet")
    public String addServlet(@RequestBody Student student){
        boolean flag = druidService.add(student);
        if (flag){
            return "success";
        }else {
            return "error";
        }
    }
    @RequestMapping("/updateByIdServlet")
    public String updateByIdServlet(@RequestBody Student student){
        System.out.println(student.toString());
        Map<String,Object> cur=new HashMap<>();
        int update=0;
        cur.put("name",student.getName());
        cur.put("sex",student.getSex());
        cur.put("high",student.getHigh());
        cur.put("weight",student.getWeight());
        try {
            update = druidService.updateById(student.getId(),cur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(update>0){
            return "success";
        }else {
            return "error";
        }
    }
    @RequestMapping("/updateByNameServlet")
    public String updateByNameServlet(@RequestBody Student student) {
        Map<String,Object> cur=new HashMap<>();
        int update=0;
        cur.put("sex",student.getSex());
        cur.put("high",student.getHigh());
        cur.put("weight",student.getWeight());
        try {
            update = druidService.updateByName(student.getName(), cur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(update>0){
            return "success";
        }else {
            return "error";
        }
    }
}
