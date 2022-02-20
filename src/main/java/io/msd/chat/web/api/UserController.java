package io.msd.chat.web.api;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.msd.chat.domain.User;
import io.msd.chat.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public List<User> getAll(){
		return userService.getAll();
	}

	@GetMapping("/current")
	public User getCurrent(Principal principal){
		return userService.getByUserName(principal.getName());
	}
	
	@GetMapping("/principal")
	public String getPrincipalName(Principal principal){
		return principal.getName();
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
	
	@PostMapping("")
	public User save(@RequestBody User user, Principal principal) {
		return userService.save(user, principal);
	}
	
	@PutMapping("/{id}")
	public User update(@PathVariable long id, @RequestBody User user, Principal principal) {
		return userService.update(id,user, principal);
	}

}
