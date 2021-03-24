package com.socialnetwork.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;
import com.socialnetwork.app.model.Person;
import com.socialnetwork.app.service.PersonService;


@RestController
@RequestMapping(value = "/person")
public class PersonController {
	//"/api/people.json?size=1&searchTerm=Alex"

	@Autowired
    private PersonService personService;
	
	@RequestMapping(value = "/i") 
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		model.setViewName("viewProfile");
		return model;
	}
	
	@RequestMapping( value = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getPerson(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		
		Person person = personService.findById(id).get();
//		if (null == person) {
//			return ResponseEntity.notFound().build();
//		}
//		return new ResponseEntity<>(person, HttpStatus.OK);
		model.addObject("profile", person);
		model.setViewName("viewProfile");
		return model;
    }

    @RequestMapping(value = "/people", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    public Page<Person> getPeople(
            @RequestParam(name = "searchTerm", defaultValue = "", required = false) String searchTerm,
            @PageableDefault(size = 20) Pageable pageRequest) {
        Page<Person> people = personService.getPeople(searchTerm, pageRequest);
        return people;
    }
    
//    @RequestMapping(value = "/people", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
//    public Page<PersonView> getPeople(
//            @RequestParam(name = "searchTerm", defaultValue = "", required = false) String searchTerm,
//            @PageableDefault(size = 20) Pageable pageRequest) {
//        Page<Person> people = personService.getPeople(searchTerm, pageRequest);
//        return people.map(PersonView::new);
//    }

    @RequestMapping(value = "/friends/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    public Page<Person> getFriends(
    		@PathVariable("id") Long id,
            @RequestParam(name = "searchTerm", defaultValue = "", required = false) String searchTerm,
            @PageableDefault(size = 20) Pageable pageRequest) {
		Person profile = personService.findById(id).get();
        Page<Person> friends = personService.getFriends(profile, searchTerm, pageRequest);
        return friends;
    }

//    @RequestMapping(value = "/friendOf", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.PUT})
//    public Page<PersonView> getFriendOf(
//    		@PathVariable("id") Long id,
//            @RequestParam(name = "searchTerm", defaultValue = "", required = false) String searchTerm,
//            @PageableDefault(size = 20) Pageable pageRequest) {
//    	Person profile = personService.findById(id).get();
//        Page<Person> friendOf = personService.getFriendOf(profile, searchTerm, pageRequest);
//        return friendOf.map(PersonView::new);
//    }

    @RequestMapping(value = "/friends/add/{personId}/{friendId}", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.PUT})
    public ResponseEntity<Void> addFriend(
            @PathVariable("personId") Long personId, @PathVariable("friendId") Long friendId) {
        final Person person = personService.findById(personId).get();
        if (null == person) {
            return ResponseEntity.notFound().build();
        }
        final Person friend = personService.findById(friendId).get();
        personService.addFriend(person, friend);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/friends/remove/{personId}/{friendId}")
    public ResponseEntity<Void> removeFriend(
    		@PathVariable("personId") Long personId, @PathVariable("friendId") Long friendId) {
        final Person person = personService.findById(personId).get();
        if (null == person) {
            return ResponseEntity.notFound().build();
        }
        final Person friend = personService.findById(friendId).get();
        personService.removeFriend(person, friend);
        return ResponseEntity.ok().build();
    }
}
