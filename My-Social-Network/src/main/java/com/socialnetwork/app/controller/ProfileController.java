package com.socialnetwork.app.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.socialnetwork.app.model.ChangePassword;
import com.socialnetwork.app.model.ContactInformation;
import com.socialnetwork.app.model.LoginDTO;
import com.socialnetwork.app.model.Person;
import com.socialnetwork.app.model.SignUp;
import com.socialnetwork.app.service.PersonService;


@RestController
@RequestMapping(value = "/profile")
//@RequiredArgsConstructor
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private PersonService personService;

    //TODO fix this
    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> login(LoginDTO loginDTO) {

        if (null == loginDTO) {
            log.warn("Attempt getting unauthorised profile information failed");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Person person = personService.findByEmail(loginDTO.getEmail());

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @RequestMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Person> signUp(@Valid @RequestBody SignUp signupDetails) throws URISyntaxException {
        log.debug("REST request to sign up a new profile: {}", signupDetails);

        final Person existingProfile = personService.findByEmail(signupDetails.getEmail());
        if (existingProfile != null) {
            log.debug("Attempt sign up email: {} failed! E-mail is already used by another contact: {}",
            		signupDetails.getEmail(), existingProfile);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Person profile = personService.create(
        		signupDetails.getFirstName(),
        		signupDetails.getLastName(),
        		signupDetails.getEmail(),
        		signupDetails.getPassword());
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @RequestMapping(
    	      value = "/updateContact/{profileId}",
    	      method = {RequestMethod.POST, RequestMethod.GET},
    	      consumes = {MediaType.APPLICATION_JSON_VALUE})   
    public ResponseEntity<Person> updatePerson(@PathVariable("profileId") Integer profileId,
            @Valid @RequestBody ContactInformation contactInfo, HttpServletRequest request) {
		Person existingPerson = personService.findById(Long.valueOf(profileId)).get();
		if (existingPerson == null) {
			return new ResponseEntity<>(existingPerson, HttpStatus.BAD_REQUEST);
		}
	     existingPerson.setFirstName(contactInfo.getFirstName());
         existingPerson.setLastName(contactInfo.getLastName());
         existingPerson.setEmail(contactInfo.getEmail());
         existingPerson.setPhone(contactInfo.getPhone());
         existingPerson.setBirthDate(contactInfo.getBirthDate());
         existingPerson.setGender(contactInfo.getGender());
         personService.update(existingPerson);
        return new ResponseEntity<>(existingPerson, HttpStatus.OK);

    }

    @RequestMapping(value = "/changePassword/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> changePassword(@PathVariable("profileId") Long profileId,
            @Valid @RequestBody ChangePassword pwd) throws URISyntaxException {

    	Person profile = personService.findById(profileId).get();
		if (profile == null) {
			return new ResponseEntity<>(profile, HttpStatus.UNAUTHORIZED);
		}

        final String currentPwd = pwd.getCurrentPassword();
        final String newPwd = pwd.getPassword();
        if (!personService.hasValidPassword(profile, currentPwd)) {
            return new ResponseEntity<>("Password could not be updated", HttpStatus.BAD_REQUEST);
        }
        personService.changePassword(profile, newPwd);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
    	e.printStackTrace();
    }

}

