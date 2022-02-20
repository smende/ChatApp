package io.msd.chat.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.msd.chat.domain.Recipient;

public interface RecipientsRepo extends CrudRepository<Recipient, Long>{
	
	List<Recipient> findAllByConversationId(long conversationId);
	
	List<Recipient> findAllByUserNameIgnoreCase(String userName);
	
}
