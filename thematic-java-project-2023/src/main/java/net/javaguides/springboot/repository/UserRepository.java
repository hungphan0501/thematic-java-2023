package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.User;

import java.sql.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	User getUserById(int id);

	@Query("SELECT u.password FROM User u WHERE u.email=?1")
	String getPass(String email);

	@Query("SELECT  COUNT(DISTINCT o.idUser) FROM Orders o WHERE o.createAt BETWEEN ?1 AND ?2")
	int countUserWithRoleUser(Date dateStart, Date dateEnd);

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
	List<User> getAllUser();

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER' AND u.name LIKE CONCAT('%', :name, '%')")
	List<User> getAllByName(String name);

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER' AND u.email=?1")
	List<User> findAllByEmail(String email);
}
