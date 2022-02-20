package io.msd.chat.services.impl;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.msd.chat.domain.Conversation;
import io.msd.chat.domain.Message;
import io.msd.chat.domain.User;
import io.msd.chat.repo.MessageRepo;
import io.msd.chat.services.ConversationService;
import io.msd.chat.services.MessageService;
import io.msd.chat.services.UserService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConversationService conversationService;
	
	@Override
	public List<Message> getAllByConversationId(long conversationId) {
		return messageRepo.findAllByConversationId(conversationId);
	}

	@Override
	public Message getMessageById(long id) {
		return messageRepo.findById(id).get();
	}

	@Override
	public Message addNewMessage(Message msg, Principal principal) throws Exception {
		
		User fromUser = userService.getByUserName(principal.getName());
		
		User toUser = null;
		Conversation conversation = null;
		
		if(msg.getConversation() != null)
			conversation = conversationService.getById(msg.getConversation().getId());
		else if(msg.getToUser() != null && msg.getToUser().getUserName() != null) {
			toUser = userService.getByUserName(msg.getToUser().getUserName());
			
			conversation = conversationService.getConversationByCurrentUserNameAndAnotherRecipientName(principal,toUser.getUserName());
			
		}else {
			throw(new Exception(" To_recipient username or conversation id is needed"));			
		}
				
		
		if(conversation == null) {
		/**
		 * Conversation is not created yet for both users...
		 */

			Set<String> userNames = new HashSet<>();
						userNames.add(principal.getName());
						userNames.add(msg.getToUser().getUserName());
			
			conversation = conversationService.createNewConversation(userNames);		
		}
		
		msg.setConversation(conversation);
		msg.setFromUser(fromUser);
		msg.setToUser(toUser);
		
		return messageRepo.save(msg);		
	}

}
