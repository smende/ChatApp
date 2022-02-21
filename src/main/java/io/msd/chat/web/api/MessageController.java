package io.msd.chat.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.msd.chat.domain.Message;
import io.msd.chat.services.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@GetMapping("/conversation/{conversationId}")
	public List<Message> getAllByConversationId(@PathVariable long conversationId){
		return messageService.getAllByConversationId(conversationId);
	}
	
	@PostMapping("")
	public Message addNewMessage(@RequestBody Message msg, @AuthenticationPrincipal OAuth2User principal) throws Exception {
		return messageService.addNewMessage(msg, principal);
	}
	
}
