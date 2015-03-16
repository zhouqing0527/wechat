package cn.com.wechat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.wechat.entity.Student;
import cn.com.wechat.service.IStudentService;
import cn.com.wechat.service.TodayInHistoryService;

@Controller
public class StudentController {

    private static Logger logger = Logger.getLogger(StudentController.class);
    
    @Resource
    IStudentService studentService;

    @RequestMapping("/")
    public ModelAndView welcome() {
	logger.info("--------welcome---------");
	ModelAndView mv = new ModelAndView();
	Student student = studentService.findById(1);
	mv.addObject("student", student);
	mv.setViewName("showStudent");
	return mv;
    }

    @RequestMapping("/showStudent")
    public String showStudent(HttpServletRequest request, Model model, @Param("id") String id) {
	if (id != null && !id.equals("")) {
	    Student student = studentService.findById(Integer.parseInt(id));
	    student.setName("Test");
	    model.addAttribute("student", student);
	}
	return "showStudent";
    }

    @RequestMapping("/test")
    public String test(HttpServletRequest request, Model model) {
	String source = TodayInHistoryService.getTodayHistoryInfo();
	model.addAttribute("source", source);
	return "test";
    }

}
