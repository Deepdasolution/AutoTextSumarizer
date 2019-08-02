package controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import models.Test;

@Controller

public class TestController {
//	
//	@RequestMapping(value="/view")
//	public String viewPage()
//	{
//		return "test";
//	}
//	
	@RequestMapping(value="/myapp/user", method=RequestMethod.POST,produces=MediaType.ALL_VALUE)
	@ResponseBody
	public Test updateUser(@RequestBody Test test)
	{
		System.out.println("Student Name: "+test.getName()+"Student Hobby: "+test.getHobby());
		test.setName("Deep Dai");
		test.setHobby("Singing");
		return test;
	}
	

}
