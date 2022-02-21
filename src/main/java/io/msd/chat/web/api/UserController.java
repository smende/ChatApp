package io.msd.chat.web.api;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.msd.chat.domain.User;
import io.msd.chat.services.UserService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/user")
@Log4j2
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public List<User> getAll(){
		return userService.getAll();
	}

	@GetMapping("/current")
	public User getCurrent(@AuthenticationPrincipal OAuth2User principal){
		log.info("Calling currentUser API...");
		return userService.getCurrentUser(principal);
	}
	
	@GetMapping("/principal")
	public Object getPrincipalName(@AuthenticationPrincipal OAuth2User principal){
		return principal.getAttributes();
	}
	
	@GetMapping("first-name/{firstName}")
	public List<User> getAllByFirstName(@PathVariable String firstName) {
		return userService.getAllByFirstName(firstName);
	}

	@GetMapping("last-name/{lastName}")
	public List<User> getAllByLastName(@PathVariable String lastName) {
		return userService.getAllByLastName(lastName);
	}
	
	@GetMapping("username/{userName}")
	public User getByUserName(@PathVariable String userName) {
		try {
			return userService.getByUserName(userName);
		}catch(NoSuchElementException e) {
			return null;
		}
	}
	
//	@PostMapping("")
//	public User save(@RequestBody User user, OAuth2User principal) {
//		return userService.save(user, principal);
//	}
//	
//	@PutMapping("/{id}")
//	public User update(@PathVariable long id, @RequestBody User user, OAuth2User principal) {
//		return userService.update(id,user, principal);
//	}

}
