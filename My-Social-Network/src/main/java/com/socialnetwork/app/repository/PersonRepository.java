package com.socialnetwork.app.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.socialnetwork.app.model.Message;
import com.socialnetwork.app.model.Person;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Serializable> {

	Person findByEmail(String email);

	@Query("SELECT p FROM Person p " +
			"WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
			"ORDER BY p.fullName")
	Page<Person> findPeople(
			@Param("searchTerm") String searchTerm,
			Pageable pageRequest);

	@Query("SELECT p FROM Person p " +
			"WHERE (:person) MEMBER OF p.friendOf " +
			"   AND LOWER(p.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
			"ORDER BY p.fullName")
	Page<Person> findFriends(
			@Param("person") Person person,
			@Param("searchTerm") String searchTerm,
			Pageable pageRequest);

	@Query("SELECT p FROM Person p " +
			"WHERE (:person) MEMBER OF p.friends " +
			"   AND LOWER(p.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
			"ORDER BY p.fullName")
	Page<Person> findFriendOf(
			@Param("person") Person person,
			@Param("searchTerm") String searchTerm,
			Pageable pageRequest);
	
	
}

