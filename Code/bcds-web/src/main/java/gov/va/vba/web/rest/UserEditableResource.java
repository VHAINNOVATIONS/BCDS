package gov.va.vba.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import gov.va.vba.persistence.entity.UserEditable;
import gov.va.vba.persistence.repository.UserEditableRepository;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UserEditableResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserEditableRepository userEditableRepository;
    
    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/allusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserEditable>> getAll() {
        LOGGER.debug("REST request to get all Users");
        return new ResponseEntity<>(userEditableRepository.findAll(), HttpStatus.OK) ;
    }
    
    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/allusers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserEditable> getUserEditable(@PathVariable Long id) {
        LOGGER.debug("REST request to get  UserEditable");
        return new ResponseEntity<>(userEditableRepository.findOne(id), HttpStatus.OK);
    }

    
    /**
     * PUT  /users -> update existing  user.
     */
    @RequestMapping(value = "/allusers", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateUser(@RequestBody UserEditable userEditable) throws URISyntaxException {
        LOGGER.debug("REST request to update User : {}", userEditable);
        if (userEditable.getId() != null){
        	userEditableRepository.save(userEditable);
        } else {
        	LOGGER.debug("################### REST request to update  UserEditable has not been performed");
        }
        return ResponseEntity.ok().build();
    }
}
