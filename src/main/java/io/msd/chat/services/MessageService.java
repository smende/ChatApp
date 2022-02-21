package io.msd.chat.services;

import java.util.List;

import org.springframework.security.oauth2.core.user.OAuth2User;

import io.msd.chat.domain.Message;

public interface MessageService {
	public List<Message> getAllByConversationId(long conversationId);
	public Message getMessageById(long id);
	
	public Message addNewMessage(Message msg, OAuth2User principal) throws Exception ;
}
