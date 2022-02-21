package io.msd.chat.services;

import java.util.List;
import java.util.Set;

import org.springframework.security.oauth2.core.user.OAuth2User;

import io.msd.chat.domain.Conversation;

public interface ConversationService {

	public Conversation getById(long id);

	public List<Conversation> getAllRelatedToCurrentUser(OAuth2User principal);
	
	public Conversation getConversationByCurrentUserNameAndAnotherRecipientName(OAuth2User principal,String userName);

	public Conversation getConversationByCurrentUserNameAndAnotherRecipientNameAndCreateIfNotFound (OAuth2User principal,String userName) throws Exception;
	
	public Conversation createNewConversation(Set<String> userNames) throws Exception;
	
}
