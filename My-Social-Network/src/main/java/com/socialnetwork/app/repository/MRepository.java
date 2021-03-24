package com.socialnetwork.app.repository;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.socialnetwork.app.model.Message;
import com.socialnetwork.app.model.Person;

@Repository
public interface MRepository extends CrudRepository<Message, Serializable> {

//	@Query("SELECT m " +
//			"FROM Message m " +
//			"WHERE m.sender = :sender AND m.recipient = :recipient " +
//			"   OR m.sender = :recipient AND m.recipient = :sender " +
//			"ORDER BY m.posted DESC")
	List<Message> findByRecipientOrSenderOrderByPostedDesc(
			Person sender,
			Person recipient);

	@Query("SELECT m " +
			"FROM Message m " +
			"WHERE m.id IN (" +
			"   SELECT MAX(l.id) " +
			"   FROM Message l " +
			"   WHERE l.sender = :person OR l.recipient = :person " +
			"   GROUP BY " +
			"       CASE " +
			"           WHEN l.recipient = :person THEN l.sender " +
			"           WHEN l.sender = :person THEN l.recipient " +
			"           ELSE :person " +
			"       END) " +
			"ORDER BY m.posted DESC")
	List<Message> findLastMessagesByPerson(@Param("person") Person person);

}


