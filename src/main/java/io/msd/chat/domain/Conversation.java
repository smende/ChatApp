package io.msd.chat.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity(name="CONVERSATIONS")
@Setter
@Getter
public class Conversation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Transient
	@OneToMany(mappedBy = "CONVERSATION_ID", targetEntity = Recipient.class)
	@JsonIgnoreProperties(value = {"conversation"})
	private List<Recipient> recipients = new ArrayList<>();
	
	
	@Transient
	@JsonIgnoreProperties(value = {"conversation"})
	private List<Recipient> others = new ArrayList<>();
}
