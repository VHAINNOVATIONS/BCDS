package gov.va.vba.service;

import gov.va.vba.vbms.webservices.external.ratinginfo.BenefitsException;
import gov.va.vba.vbms.webservices.external.ratinginfo.RbaProfile;
import gov.va.vba.vbms.webservices.external.ratinginfo.ReadCurrentRatingProfile;
import gov.va.vba.vbms.webservices.external.ratinginfo.ReadCurrentRatingProfileResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by 582163 on 2/28/2016.
 */

@Endpoint
public class RatingProfileMockService {
    private static final String NAMESPACE_URI = "http://services.rba.benefits.vba.va.gov/";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "readCurrentRatingProfile")
    @ResponsePayload
    public ReadCurrentRatingProfileResponse readCurrentRatingProfile(@RequestPayload ReadCurrentRatingProfile request) throws BenefitsException {

        ReadCurrentRatingProfileResponse response = new ReadCurrentRatingProfileResponse();
        return response;
    }
}

