package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import models.User;

import repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//    private BCryptPasswordEncoder encoder; 
//	
	@Autowired
    UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
            
    }
	
	public User findByUserEmail(String userEmail) {
		User user = userRepository.findByUserEmail(userEmail);
		return user;
	}
	
	public Optional<User> getUser(Integer Id) {
        return userRepository.findById(Id);
    }    

	
	//Create New User
    public boolean createUser(User user) {
    	//user.setUserPass(encoder.encode(user.getUserPass()));
        User savedUser = userRepository.save(user);
        return true;
    }
    
    //Select all users
    public List<User> getUsers(User user){
		return userRepository.findAll();
		
	}
	
}
