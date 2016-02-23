package gov.va.vba.web.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Pete Grazaitis on 8/3/2015.
 */
@RestController
@RequestMapping("/generate")
public class Generator {

    @RequestMapping(value = "/uuid",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> getUUID() {
        UUID generated = UUID.randomUUID();
        return new ResponseEntity(generated, HttpStatus.OK);
    }
}
