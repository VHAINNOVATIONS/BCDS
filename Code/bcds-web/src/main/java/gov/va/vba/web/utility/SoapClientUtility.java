package gov.va.vba.web.utility;

import org.springframework.stereotype.Service;

import gov.va.vba.bcdss.models.BcdsModelingPortService;
import gov.va.vba.bcdss.models.GetDdmModelRequest;
import gov.va.vba.bcdss.models.GetDdmModelResponse;

@Service
public class SoapClientUtility {
	
	public GetDdmModelResponse ddmModelRequest(GetDdmModelRequest getDdmModelRequest){
		BcdsModelingPortService bcdsModelingPortService = new BcdsModelingPortService();
		return bcdsModelingPortService.getBcdsModelingPortSoap11().getDdmModel(getDdmModelRequest);
	}
	

}
