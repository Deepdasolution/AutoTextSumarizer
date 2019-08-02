package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.User;
import repository.UserRepository;
import services.ForgotPasswordService;

@RestController
@RequestMapping(value="ats")
public class ForgotPassController{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;

	@RequestMapping(value="/forgotpassword/{toemail}", method=RequestMethod.GET)
	public String forgotPassword(@PathVariable String toemail) {
		User uemail = userRepository.findByUserEmail(toemail);
		if(uemail == null) {	
			return "Sorry Email is Incorrect";
			}
		
		else {
			return forgotPasswordService.sendMail(toemail);
			}
		}
}

