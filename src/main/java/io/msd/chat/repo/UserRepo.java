package io.msd.chat.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.msd.chat.domain.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long>{
		public List<User> findAllByFirstNameIgnoreCase(String firstName);
		public List<User> findAllByLastNameIgnoreCase(String lastName);
		public Optional<User> findByUserNameIgnoreCase(String userName);
}
