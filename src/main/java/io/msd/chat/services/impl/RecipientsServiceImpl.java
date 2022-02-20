package io.msd.chat.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.msd.chat.domain.Recipient;
import io.msd.chat.repo.RecipientsRepo;
import io.msd.chat.services.RecipientsService;
import io.msd.chat.services.UserService;

@Service
public class RecipientsServiceImpl implements RecipientsService {

	@Autowired
	private RecipientsRepo recipientsRepo;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public Recipient getById(long id) {
		return recipientsRepo.findById(id).get();
	}

	@Override
	public List<Recipient> getByConversationId(long conversationId) {
		return recipientsRepo.findAllByConversationId(conversationId).stream().map(a -> {
			a.setUser(userService.getByUserName(a.getUserName()));			
			return a;
		}).collect(Collectors.toList());
	}

	@Override
	public Recipient save(Recipient recipient) {
		return recipientsRepo.save(recipient);
	}
	
	@Override
	public List<Long> getAllConversationsIdsByUserName(String userName) {
		 return recipientsRepo.findAllByUserNameIgnoreCase(userName).stream().map(a -> a.getConversation().getId()).collect(Collectors.toList());		 
	}
	
}
