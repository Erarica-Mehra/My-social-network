package com.socialnetwork.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.socialnetwork.app.model.Message;
import com.socialnetwork.app.model.MessagePost;
import com.socialnetwork.app.model.Person;
import com.socialnetwork.app.service.MessageService;
import com.socialnetwork.app.service.PersonService;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

	@Autowired
    private  MessageService messageService;
    @Autowired
    private  PersonService personService;

	@RequestMapping( value = "/dialog/{senderId}/{recipientId}", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    public ResponseEntity<List<Message>> getDialog(
            @PathVariable("senderId") Long senderId, @PathVariable("recipientId") Long recipientId) {
        Person sender = personService.findById(senderId).get();
        Person recipient = personService.findById(senderId).get();
        if (sender == null || recipient == null) {
            return ResponseEntity.notFound().build();
        }
        List<Message> messages = messageService.getDialog(sender, recipient);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

	@RequestMapping( value = "/last", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    public ResponseEntity<List<Message>> getLastMessages(@PathVariable("id") Long id) {
        Person profile = personService.findById(id).get();
        List<Message> messages =messageService.getLastMessages(profile);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

	@RequestMapping( value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
    public void send(@RequestBody @Valid MessagePost messagePost) {
        Message message = new Message();
        message.setBody(messagePost.getBody());
        message.setSender(personService.findById(messagePost.getSender()).get());
        message.setRecipient(personService.findById(messagePost.getRecipient()).get());
        messageService.send(message);
    }

//    private List<MessageView> map(List<Message> messages) {
//        return messages.stream()
//                .map(MessageView::new)
//                .collect(toList());
//    }

}
