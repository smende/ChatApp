package io.msd.chat.services;

import java.util.List;

import io.msd.chat.domain.Recipient;

public interface RecipientsService {
	public Recipient getById(long id);
	public List<Recipient> getByConversationId(long conversationId);
	public Recipient save(Recipient recipient);
	
	public List<Long> getAllConversationsIdsByUserName(String userName);
	
}
