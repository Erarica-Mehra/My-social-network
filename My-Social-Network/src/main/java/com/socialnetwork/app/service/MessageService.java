package com.socialnetwork.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialnetwork.app.model.Message;
import com.socialnetwork.app.model.Person;
import com.socialnetwork.app.repository.MRepository;

@Service
public class MessageService {

	@Autowired
     private MRepository messageRepository;

	public List<Message> getDialog(Person sender, Person recipient) {
		return messageRepository.findByRecipientOrSenderOrderByPostedDesc(sender, recipient);
	}

	public List<Message> getLastMessages(Person person) {
		return messageRepository.findLastMessagesByPerson(person);
	}

	public Message send(Message message) {
		return messageRepository.save(message);
	}

}
