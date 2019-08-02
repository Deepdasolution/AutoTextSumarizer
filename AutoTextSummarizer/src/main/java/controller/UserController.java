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
import repository.UserRepository;
import services.UserService;
import summarizer.Summarizer;

@RestController
@RequestMapping("/ats")
public class UserController {

	private ArrayList<String> fs;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
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
		User u = userRepository.findByUserEmail(user.getUserEmail());
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
		String url=summary.getSourceUrl();
		System.out.println("Url: "+url);
		String a="";
		try {
	      Document document = Jsoup.connect(url).get();
	     // System.out.println(document.title());
	    
	      String html =String.valueOf(document.body());
	      Document doc = Jsoup.parse(html);
	      System.out.println(doc.title());
	      Elements paragraphs = doc.getElementsByTag("p");
	      a=paragraphs.text();
	     }catch(Exception e)
			{
				System.out.println("Error aayo"+e);
				
			}
		System.out.println(a);
		summary.setSummary(a);
		return summary;
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
		System.out.println("User login is: "+summary.isIsID());
		System.out.println("jpt is : "+summary.getJpt());
		
		Summarizer summarizer= new Summarizer();
		fs= new ArrayList<>(summarizer.callSummarizer(sno, source));
		for(String x: fs)
		{
			System.out.println(x);
		}
		summary.setFinalSummary(fs);
		return summary;
		 
	
	}
	
}
