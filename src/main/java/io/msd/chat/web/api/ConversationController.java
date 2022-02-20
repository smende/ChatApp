package io.msd.chat.web.api;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.msd.chat.domain.Conversation;
import io.msd.chat.services.ConversationService;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

	@Autowired
	private ConversationService conversationService;
	
	
	@GetMapping("/{id}")
	public Conversation getById(@PathVariable long id) {
		try {
			return conversationService.getById(id);
		}catch(Exception e) {
			return null;
		}
	}
	
	@GetMapping("/own")
	public List<Conversation> getById(Principal principal) {
		return conversationService.getAllRelatedToCurrentUser(principal);
	}	

	@GetMapping("/own-with-other-username/{otherUserName}")
	public Conversation getById(Principal principal, @PathVariable String otherUserName) {
		return conversationService.getConversationByCurrentUserNameAndAnotherRecipientName(principal, otherUserName);
	}
}
