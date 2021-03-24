package com.socialnetwork.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.socialnetwork.app.model.Person;
import com.socialnetwork.app.repository.PersonRepository;


@Service
//@RequiredArgsConstructor
public class PersonService {

	@Autowired
    private  PasswordEncoder passwordEncoder;
	@Autowired
	private  PersonRepository personRepository;

	@Transactional(readOnly = true)
	public Optional<Person> findById(Long id) {
		return personRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Person findByEmail(String email) {
		return personRepository.findByEmail(email);
	}

	@Transactional(readOnly = true)
	public Page<Person> getPeople(String searchTerm, Pageable pageRequest) {
		return personRepository.findPeople(searchTerm, pageRequest);
	}

	@Transactional(readOnly = true)
	public Page<Person> getFriends(Person person, String searchTerm, Pageable pageRequest) {
		return personRepository.findFriends(person, searchTerm, pageRequest);
	}

	@Transactional(readOnly = true)
	public Page<Person> getFriendOf(Person person, String searchTerm, Pageable pageRequest) {
		return personRepository.findFriendOf(person, searchTerm, pageRequest);
	}

	@Transactional
	public void addFriend(Person myProfile, Person friend) {
		if (!myProfile.hasFriend(friend)) {
			myProfile.getFriends().add(friend);
			update(myProfile);
		}
	}

	@Transactional
	public void removeFriend(Person myProfile, Person friend) {
		if (myProfile.hasFriend(friend)) {
			myProfile.getFriends().remove(friend);
			update(myProfile);
        }
	}

	@Transactional
	public void update(Person person) {
		personRepository.save(person);
	}

	@Transactional
	public Person create(String firstName, String lastName, String email, String password) {
		 Person person = new Person();
         person.setFirstName(firstName);
         person.setLastName(lastName);
         person.setEmail(email);
         person.setPassword(passwordEncoder.encode(password));
		return personRepository.save(person);
	}

	public boolean hasValidPassword(Person person, String pwd) {
	    return passwordEncoder.matches(pwd, person.getPassword());
	}

    public void changePassword(Person person, String pwd) {
	    person.setPassword(passwordEncoder.encode(pwd));
        personRepository.save(person);
    }

}
