package cn.com.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.wechat.dao.StudentMapper;
import cn.com.wechat.entity.Student;
import cn.com.wechat.service.IStudentService;

@Service("studentService")
public class StudentServiceImpl implements IStudentService {

    @Resource
    private StudentMapper studentDao;
    
    @Override
    public int insert(Student stu) {
	return studentDao.insert(stu);
    }

    @Override
    public int delete(int id) {
	return studentDao.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Student stu) {
	return studentDao.updateByPrimaryKey(stu);
    }

    @Override
    public List<Student> findAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Student findById(int id) {
	return studentDao.selectByPrimaryKey(id);
    }

}
