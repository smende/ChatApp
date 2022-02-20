package io.msd.chat.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "MESSAGES")
@Setter
@Getter
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "FROM_USER_ID", referencedColumnName = "ID", updatable = false, nullable = false)
	private User fromUser;
	
	@Column(nullable = false, updatable = false)
	@Size(min = 1, max = 2000)
	private String message;

	@Column(updatable = false)
	private Date createdOn = new Date();
	
	@ManyToOne
	@JoinColumn(name = "CONVERSATION_ID", referencedColumnName = "ID", nullable = false, updatable = false)
	private Conversation conversation;
	
	@Transient
	private User toUser;
	
}
