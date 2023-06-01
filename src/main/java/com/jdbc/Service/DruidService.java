package com.jdbc.Service;

import com.jdbc.Dao.Student;
import com.jdbc.Dao.User;
import com.jdbc.config.JDBCUtils_Druid;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ComponentScan("com.jdbc")
public class DruidService {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils_Druid.getDataSource());
    public Connection conn=null;
    public PreparedStatement pstmt=null;
    public boolean add(Student student){

        try{
            conn=
            conn= JDBCUtils_Druid.getConnection();
            conn.setAutoCommit(false);//开启事物
            String sql="insert into stu(id,name,sex,high,weight)" +
                    "values(?,?,?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,student.getId());
            pstmt.setString(2,student.getName());
            pstmt.setString(3,student.getSex());
            pstmt.setDouble(4,student.getHigh());
            pstmt.setDouble(5,student.getWeight());
            int i = pstmt.executeUpdate();
            conn.commit();//提交事物
            if(i>0)
                return true;
            else return false;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        JDBCUtils_Druid.close(null,pstmt,conn);
        return true;
    }
    public User login(User loginUser){
        try {
            String sql="select * from user where username=? and password=? ";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getUsername(), loginUser.getPassword());
            return user;
        }catch (DataAccessException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<Student> selectAll(){
        List<Student> students = template.query("select * from stu",new BeanPropertyRowMapper<Student>(Student.class));
        return students;
    }
    public Student selectById(String id){
        String sql="select * from stu where id=?";
        Student student = template.queryForObject(sql, new BeanPropertyRowMapper<Student>(Student.class), id);
        return student;
    }
    public Student selectByName(String name){
        String sql="select * from stu where name=?";
        Student student = template.queryForObject(sql, new BeanPropertyRowMapper<Student>(Student.class),name);
        return student;
    }

    public int deleteById(String id){
        String sql="delete from stu where id=?";
        int update = template.update(sql,id);
        return update;
    }

    public int deleteByName(String name){
        String sql="delete from stu where name=?";
        int update = template.update(sql,name);
        return update;
    }

    public int updateById(Integer id, Map<String,Object> cur) throws SQLException {
        int update = 0;
        try {
            conn = JDBCUtils_Druid.getConnection();
            conn.setAutoCommit(false);//开启事物
            // 假设 conn 是已经创建好的数据库连接对象
            String sql = "UPDATE stu SET ";
            List<Object> params = new ArrayList<>();

            if (cur.containsKey("name") && !cur.get("name").equals("")) {
                sql += "name = ?, ";
                params.add(cur.get("name").toString());
            }
            if (cur.containsKey("sex") && cur.get("sex") != null) {
                sql += "chinese = ?, ";
                params.add(cur.get("sex").toString());
            }
            if (cur.containsKey("high") && cur.get("high") != null) {
                sql += "math = ?, ";
                params.add(cur.get("high").toString());
            }
            if (cur.containsKey("weight") && cur.get("weight") != null) {
                sql += "eng = ?, ";
                params.add(cur.get("weight").toString());
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += " WHERE id = ?";
            params.add(id);
            System.out.println(sql);

            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            update= stmt.executeUpdate();
            conn.commit();
            if(update>0){
                return update;
            }else{
                return 0;
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        JDBCUtils_Druid.close(null, pstmt, conn);
        return update;
    }
    public int updateByName(String name, Map<String,Object> cur) throws SQLException {
        int update = 0;
        try {
            conn = JDBCUtils_Druid.getConnection();
            conn.setAutoCommit(false);//开启事物
            // 假设 conn 是已经创建好的数据库连接对象
            String sql = "UPDATE stu SET ";
            List<Object> params = new ArrayList<>();

            if (cur.containsKey("sex") && cur.get("sex") != null) {
                sql += "chinese = ?, ";
                params.add(cur.get("sex").toString());
            }
            if (cur.containsKey("high") && cur.get("high") != null) {
                sql += "math = ?, ";
                params.add(cur.get("high").toString());
            }
            if (cur.containsKey("weight") && cur.get("weight") != null) {
                sql += "eng = ?, ";
                params.add(cur.get("weight").toString());
            }

// 去掉 SQL 语句末尾的逗号和空格
            sql = sql.substring(0, sql.length() - 2);

// 添加 WHERE 子句
            sql += " WHERE name = ?";
            params.add(name);

            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            update= stmt.executeUpdate();
            conn.commit();
            if(update>0){
                return update;
            }else{
                return 0;
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        JDBCUtils_Druid.close(null, pstmt, conn);
        return update;
    }
    public boolean addUser(User user){
        try{
            conn= JDBCUtils_Druid.getConnection();
            conn.setAutoCommit(false);//开启事物
            String sql="insert into user(username,password)" +
                    "values(?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());
            int i = pstmt.executeUpdate();
            conn.commit();//提交事物
            String sql2="select * from user where username=?";
            User usr2= template.queryForObject(sql2, new BeanPropertyRowMapper<User>(User.class),user.getUsername());
            if(i>0&&usr2!=null)
                return true;
            else return false;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        JDBCUtils_Druid.close(null,pstmt,conn);
        return true;
    }
}
