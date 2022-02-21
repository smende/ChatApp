package io.msd.chat.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import io.msd.chat.domain.Conversation;
import io.msd.chat.domain.Recipient;
import io.msd.chat.repo.ConversationRepo;
import io.msd.chat.services.ConversationService;
import io.msd.chat.services.RecipientsService;
import io.msd.chat.services.UserService;

@Service
@Transactional
public class ConversationServiceImpl implements ConversationService{

	@Autowired
	private ConversationRepo conversationRepo;
	
	@Autowired
	private RecipientsService recipientsService; 
	
	@Autowired
	private UserService userService;
	
	@Override
	public Conversation getById(long id) {
		 Conversation conv = conversationRepo.findById(id).get();
		 conv.setRecipients(recipientsService.getByConversationId(id));
		 return conv;
	}

	@Override
	public List<Conversation> getAllRelatedToCurrentUser(OAuth2User principal) {
		
		List<Long> conversationIds = recipientsService.getAllConversationsIdsByUserName(principal.getAttribute("email"));
		
		List<Conversation> conversations = new ArrayList<>();
		
		conversationIds.forEach(a->{
			
			Conversation conv = getById(a);
			List<Recipient> others = conv.getRecipients().stream().filter(b -> !principal.getAttribute("email").equals(b.getUserName() ) ).collect(Collectors.toList()) ;
			
			conv.setOthers(others);
			
			conversations.add(conv);
			
		});
		
		return conversations;
	}

	@Override
	public Conversation getConversationByCurrentUserNameAndAnotherRecipientName(OAuth2User principal, String userName) {

		List<Long> conversationIds = recipientsService.getAllConversationsIdsByUserName(principal.getAttribute("email"));
		
		List<Conversation> conversations = new ArrayList<>();
		
		conversationIds.forEach(a->{
			
			Conversation conv = getById(a);

			List<Recipient> others = conv.getRecipients().stream().filter(b -> {
				
				return !principal.getAttribute("email").equals(b.getUserName()) && userName.equals(b.getUserName());
			
			}).collect(Collectors.toList()) ;			
			conv.setOthers(others);
			
			if(others.size() >0)
			conversations.add(conv);			
		});
				
		return conversations.size() > 0 ? conversations.get(0) : null;
	}	
	
	@Override
	public Conversation getConversationByCurrentUserNameAndAnotherRecipientNameAndCreateIfNotFound(OAuth2User principal, String userName) throws Exception {

		Conversation conversatin = getConversationByCurrentUserNameAndAnotherRecipientName(principal, userName);
		
		if(conversatin != null)			
			return conversatin;
		
		Set<String> userNames = new HashSet<>();
					userNames.add(userName);
					userNames.add(principal.getAttribute("email"));
					
		return createNewConversation(userNames);
	}	
	
	@Override
	public Conversation createNewConversation(Set<String> userNames) throws Exception {
		
//		List<Recipient> recipients = conversation.getRecipients();
		
		if(userNames == null || userNames.size() < 2) {
			throw(new Exception("Need atleast 2 recipient usernames"));
		}

		List<String> invalidUserNames = userNames.stream().filter(userName ->{
			
			boolean isInvalid = false;
						
			try {
				userService.getByUserName(userName);	
			}catch(NoSuchElementException e) {
				isInvalid = true;
			}
						
			return isInvalid;
			
		}).collect(Collectors.toList());
		
		if(invalidUserNames != null && invalidUserNames.size() >0) {
			throw(new Exception("Invalid username(s) : "+invalidUserNames.toString()));			
		}
		
		
		Conversation newConversation = conversationRepo.save(new Conversation());
		
		
		List<Recipient> createdRecipients = userNames.stream().map(userName ->{
			return recipientsService.save(new Recipient(userName, newConversation));
		}).collect(Collectors.toList());
		
		newConversation.setRecipients(createdRecipients);
		
		return newConversation;
	}
	
}
