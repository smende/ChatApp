package io.msd.chat.services;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import io.msd.chat.domain.Conversation;

public interface ConversationService {

	public Conversation getById(long id);

	public List<Conversation> getAllRelatedToCurrentUser(Principal principal);
	
	public Conversation getConversationByCurrentUserNameAndAnotherRecipientName(Principal principal,String userName);
	
	public Conversation createNewConversation(Set<String> userNames) throws Exception;
	
}
