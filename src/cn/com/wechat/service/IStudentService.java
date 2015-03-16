package cn.com.wechat.service;

import java.util.List;

import cn.com.wechat.entity.Student;

public interface IStudentService {

    public int insert(Student stu);  
    
    public int delete(int id);  
      
    public int update(Student stu);  
      
    public List<Student> findAll();  
      
    public Student findById(int id); 

}
