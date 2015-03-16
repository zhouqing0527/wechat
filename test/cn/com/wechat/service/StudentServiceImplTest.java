package cn.com.wechat.service;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.wechat.entity.Student;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class StudentServiceImplTest {
    
    @Resource
    private IStudentService studentService = null;
    
    @Test
    public void testFindById() {
	Student student = studentService.findById(1);
	System.out.println(student.getName());
    }

}
