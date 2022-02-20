package io.msd.chat.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "USERS")
@Setter
@Getter
public class User {

	@Id
	@GeneratedValue
	private long id;
	

	@NotNull
	@Size(min = 3, max = 50)
	@Column(nullable = false)
	private String firstName;

	@NotNull
	@Size(min = 3, max = 50)
	@Column(nullable = false)
	private String lastName;

	@NotNull
	@Size(min = 5, max = 50)
	@Column(nullable=false, updatable = false, unique = true)
	private String email;
	
	@NotNull
	@Size(min = 5, max = 200)
	@Column(nullable=false, updatable = false, unique = true)
	private String userName;

	@Column(updatable = false)
	private String createdBy;
	
	@Column(updatable = false)
	private Date createdOn = new Date();	
	
	private String updatedBy;
	
	private Date updatedOn = new Date();
	
	@JsonProperty(value = "fullName")
	public String getFullName() {
		return firstName+" "+lastName;
	}
}
