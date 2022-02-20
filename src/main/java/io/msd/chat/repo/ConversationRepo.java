package io.msd.chat.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.msd.chat.domain.Conversation;

@Repository
public interface ConversationRepo extends CrudRepository<Conversation, Long>{

}
