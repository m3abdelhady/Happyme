package com.happy.me.dataaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findByEmailIgnoreCase(String email);
	public Optional<User> findByFacebookIdIgnoreCase(String facebookId);
	public Optional<User> findById(Long id);

	
    @Query("SELECT m FROM User m where m.createdBy = :id")
	public List<User> getCreatedUserById(@Param("id") User user);
	
}

