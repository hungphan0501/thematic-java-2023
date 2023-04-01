package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	User getUserById(int id);

	@Query("SELECT u.password FROM User u WHERE u.email=?1")
	String getPass(String email);
}
