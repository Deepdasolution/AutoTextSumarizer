package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import models.User;
import models.UserHistory;
import repository.UserHistoryRepository;
import services.UserHistoryService;

@RestController
@RequestMapping(value="/ats")
public class HistoryController {
	
	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	UserHistoryRepository userHistoryRepository;
	
	
	@RequestMapping(value="/history",method=RequestMethod.POST)
	 public UserHistory getUserHistory(@RequestBody User user) {
		System.out.println(user.getId());
		System.out.println(user.getUserEmail());
		int id=1;
		String type="URL";
		String summary="Deep Dai Summary";
		String source="Source of Summary";
		int contime=17;
		UserHistory uh= new UserHistory();
		uh.setId(id);
		uh.setInputText(source);
		
		uh.setSummary(summary);
		uh.setTimeConsumed(contime);
		uh.setType(type);      
		return uh;
	 }
	

}
