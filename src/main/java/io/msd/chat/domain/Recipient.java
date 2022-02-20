package io.msd.chat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="RECIPIENTS")
@Setter
@Getter
@NoArgsConstructor
public class Recipient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "CONVERSATION_ID", referencedColumnName = "ID", updatable = false, nullable = false)
	@JsonIgnoreProperties(value = {"recipients"})
	private Conversation conversation;
	
	@Column(nullable = false, updatable = false)
	@Size(max = 400)
	private String userName;
	
	@Transient
	@JsonIncludeProperties(value = {"firstName","lastName","id","userName","fullName"})
	private User user;
	
	public Recipient(String userName, Conversation conversation) {
		this.userName = userName;
		this.conversation = conversation;
	}
	
}
