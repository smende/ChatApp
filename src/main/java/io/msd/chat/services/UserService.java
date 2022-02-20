package io.msd.chat.services;

import java.security.Principal;
import java.util.List;

import io.msd.chat.domain.User;

public interface UserService {
	public List<User> getAll();
	public User getById(long id);
	public List<User> getAllByFirstName(String firstName);
	public List<User> getAllByLastName(String lastName);
	public User getByUserName(String userName);
	public User save(User user, Principal principal);
	public User update(long id, User user, Principal principal);
}
