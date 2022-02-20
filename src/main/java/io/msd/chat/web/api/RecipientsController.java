package io.msd.chat.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.msd.chat.domain.Recipient;
import io.msd.chat.services.RecipientsService;

@RestController
@RequestMapping("/api/recipients")
public class RecipientsController {

	@Autowired
	private RecipientsService recipientsService;

	@GetMapping("/conversation-id/{conversationId}")
	public List<Recipient> getAllByConversationId(@PathVariable long conversationId){
		return recipientsService.getByConversationId(conversationId);
	}
	
}
