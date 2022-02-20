package io.msd.chat.services;

import java.security.Principal;
import java.util.List;

import io.msd.chat.domain.Message;

public interface MessageService {
	public List<Message> getAllByConversationId(long conversationId);
	public Message getMessageById(long id);
	
	public Message addNewMessage(Message msg, Principal principal) throws Exception ;
}
