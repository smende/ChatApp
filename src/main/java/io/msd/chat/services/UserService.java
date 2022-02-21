package io.msd.chat.services;

import java.util.List;

import org.springframework.security.oauth2.core.user.OAuth2User;

import io.msd.chat.domain.User;

public interface UserService {
	public List<User> getAll();
	public User getById(long id);
	
	public User getCurrentUser(OAuth2User principal);
	
	public List<User> getAllByFirstName(String firstName);
	public List<User> getAllByLastName(String lastName);
	public User getByUserName(String userName);
	public User save(User user, OAuth2User principal);
	public User update(long id, User user, OAuth2User principal);
}
