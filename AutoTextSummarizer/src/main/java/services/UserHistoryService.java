package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.UserHistory;
import repository.UserHistoryRepository;
import repository.UserRepository;

@Service
public class UserHistoryService {
	
	private UserHistoryRepository userHistoryRepository;
	
	@Autowired
	UserHistoryService(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
        
    }
	
//	public Optional<UserHistory> getUserHistory(int id) {
//        return userHistoryRepository.findById(id);
//    }
	
	public void saveHistory(UserHistory userHistory) {
		userHistoryRepository.save(userHistory);
		System.out.println("History is saved successfully");
	}
    

}
