package io.msd.chat.services.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.msd.chat.domain.User;
import io.msd.chat.repo.UserRepo;
import io.msd.chat.services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public List<User> getAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public User getById(long id) {
		return userRepo.findById(id).get();
	}

	@Override
	public List<User> getAllByFirstName(String firstName) {
		return userRepo.findAllByFirstNameIgnoreCase(firstName.trim());
	}

	@Override
	public List<User> getAllByLastName(String lastName) {
		return userRepo.findAllByLastNameIgnoreCase(lastName.trim());
	}

	@Override
	public User getByUserName(String userName) {
		return userRepo.findByUserNameIgnoreCase(userName.trim()).get();
	}

	@Override
	public User save(User user, Principal principal) {
		
		user.setFirstName(user.getFirstName() != null ? user.getFirstName().trim() : null);
		user.setLastName(user.getLastName() != null ? user.getLastName().trim() : null);

		user.setUserName(user.getUserName() != null ? user.getUserName().trim() : null);
		user.setEmail(user.getEmail() != null ? user.getEmail().trim() : null);	
		
		user.setCreatedBy(principal.getName());
		user.setUpdatedBy(principal.getName());

		return userRepo.save(user);
	}

	@Override
	public User update(long id, User user, Principal principal) {
		User originalUser = getById(id);

		if(user.getFirstName() != null && !user.getFirstName().trim().equals(originalUser.getFirstName())) {
			originalUser.setFirstName(user.getFirstName().trim());
		}

		if(user.getLastName() != null && !user.getLastName().trim().equals(originalUser.getLastName())) {
			originalUser.setLastName(user.getLastName().trim());
		}

		originalUser.setUpdatedBy(principal.getName());
		originalUser.setUpdatedOn(new Date());
		
		return userRepo.save(originalUser);		
	}	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new org.springframework.security.core.userdetails.User(username,"",true,true,true,true,authorities);
	}

}
