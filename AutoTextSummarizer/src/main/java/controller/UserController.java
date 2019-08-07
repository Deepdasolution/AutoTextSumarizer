package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import models.Summary;
import models.User;
import models.UserHistory;
import repository.UserRepository;
import services.SummaryService;
import services.UserHistoryService;
import services.UserService;
import summarizer.Summarizer;

@RestController
@RequestMapping("/ats")
public class UserController {

	private User user;	
	private ArrayList<String> fs;	
	
	
	private UserHistory userHistory;
	
	@Autowired
	private UserHistoryService userHistoryService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SummaryService summaryService;
	private String tempSummary;
	
	//Select all from table
	@GetMapping("/users")
	public List<User> getUsers(User users){
		return userService.getUsers(users);
		
	}
	
	//Create new User
	@PostMapping("/register")
	public boolean createUsers(@RequestBody User user){
		return userService.createUser(user);
		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public User login(@RequestBody User user) {
		System.out.println(user.getUserEmail());
		User u = userService.findByUserEmail(user.getUserEmail(),user.getUserPass());
		if(u==null)
		{
			System.out.println("Sorry Not Logged In");
			return null;
		}
		
		else {
			System.out.println("Successful");
			return u;
		}
	    
	}
	
	//Select particular user with id
	@GetMapping("/user/{userId}")
	  public Optional<User>getUser(@PathVariable Integer userId) {
	        return userService.getUser(userId);
	    }
	
	@RequestMapping(value="/getcontent", method=RequestMethod.POST)
	public Summary getSourceContent(@RequestBody Summary summary)
	{
		return summaryService.getSourceContent(summary);
	}
	
	@RequestMapping(value="/summary", method=RequestMethod.POST)
	public Summary getsummary(@RequestBody Summary summary)
	{
		String url=summary.getSourceUrl();
		int sno=summary.getSentenceNumber();
		String source=summary.getSourceText();	
		System.out.println("no: "+sno);
		System.out.println("Sentence: "+source);
		System.out.println("Url: "+url);
		System.out.println("User Login Activity: "+summary.isUserActive());
		System.out.println("user Id is : "+summary.getUserId());
		
		Summarizer summarizer= new Summarizer();
		fs= new ArrayList<>(summarizer.callSummarizer(sno, source));
		for(String x: fs)
		{
			System.out.println(x);
			tempSummary=tempSummary+x;
			System.out.println("\t Deep: "+tempSummary);
			
		}
		summary.setFinalSummary(fs);
		System.out.println("Summary seted");
		user= new User();
		//Save the histroy
		user.setId(summary.getUserId());
		
		System.out.println("user is seted");
		userHistory= new UserHistory();
		
		userHistory.setSummary(tempSummary);
		System.out.println("temp seted");
		
		userHistory.setTimeConsumed(10);
		System.out.println("time consumed seted");
		
		userHistory.setUser(user);
		System.out.println("Summary seted");
		
		userHistory.setSource(source);
		System.out.println("source seted");
		
		userHistoryService.saveHistory(userHistory);
		System.out.println("history saved");
				
		return summary;
		
		
		 
	
	}
	
}
