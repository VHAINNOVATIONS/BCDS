package gov.va.vba.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.va.vba.persistence.entity.Veteran;
import gov.va.vba.service.VeteranService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ProSphere User on 11/2/2016.
 */
@RestController
@RequestMapping("/api")
public class VeteranController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VeteranController.class);

    @Autowired
    private VeteranService veteranService;


    @RequestMapping(value = "/veterans/{veteranId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Veteran getVeteran(@PathVariable Long veteranId) {
        LOGGER.debug("REST request to get first few Claims");
        return veteranService.getVeteranById(veteranId);
    }

}
