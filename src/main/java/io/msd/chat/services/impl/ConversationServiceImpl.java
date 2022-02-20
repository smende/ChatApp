package io.msd.chat.services.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.msd.chat.domain.Conversation;
import io.msd.chat.domain.Recipient;
import io.msd.chat.repo.ConversationRepo;
import io.msd.chat.services.ConversationService;
import io.msd.chat.services.RecipientsService;

@Service
@Transactional
public class ConversationServiceImpl implements ConversationService{

	@Autowired
	private ConversationRepo conversationRepo;
	
	@Autowired
	private RecipientsService recipientsService; 
	
	@Override
	public Conversation getById(long id) {
		 Conversation conv = conversationRepo.findById(id).get();
		 conv.setRecipients(recipientsService.getByConversationId(id));
		 return conv;
	}

	@Override
	public List<Conversation> getAllRelatedToCurrentUser(Principal principal) {
		
		List<Long> conversationIds = recipientsService.getAllConversationsIdsByUserName(principal.getName());
		
		List<Conversation> conversations = new ArrayList<>();
		
		conversationIds.forEach(a->{
			
			Conversation conv = getById(a);
			List<Recipient> others = conv.getRecipients().stream().filter(b -> !principal.getName().equals(b.getUserName() ) ).collect(Collectors.toList()) ;
			
			conv.setOthers(others);
			
			conversations.add(conv);
			
		});
		
		return conversations;
	}

	@Override
	public Conversation getConversationByCurrentUserNameAndAnotherRecipientName(Principal principal, String userName) {

		List<Long> conversationIds = recipientsService.getAllConversationsIdsByUserName(principal.getName());
		
		List<Conversation> conversations = new ArrayList<>();
		
		conversationIds.forEach(a->{
			
			Conversation conv = getById(a);

			List<Recipient> others = conv.getRecipients().stream().filter(b -> {
				
				return !principal.getName().equals(b.getUserName()) && userName.equals(b.getUserName());
			
			}).collect(Collectors.toList()) ;			
			conv.setOthers(others);
			
			if(others.size() >0)
			conversations.add(conv);			
		});
				
		return conversations.size() > 0 ? conversations.get(0) : null;
	}	
	
	@Override
	public Conversation createNewConversation(Set<String> userNames) throws Exception {
		
//		List<Recipient> recipients = conversation.getRecipients();
		
		if(userNames == null || userNames.size() < 2) {
			throw(new Exception("Need atleast 2 recipient usernames"));
		}

		Conversation newConversation = conversationRepo.save(new Conversation());
		
		
		List<Recipient> createdRecipients = userNames.stream().map(userName ->{
			return recipientsService.save(new Recipient(userName, newConversation));
		}).collect(Collectors.toList());
		
		newConversation.setRecipients(createdRecipients);
		
		return newConversation;
	}
	
}
