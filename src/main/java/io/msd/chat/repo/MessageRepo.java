package io.msd.chat.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.msd.chat.domain.Message;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long>{
		List<Message> findAllByConversationId(long conversationId);
}
