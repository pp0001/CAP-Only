package my.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;
import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.operations.Create;
import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;

public class ControlService {
	
	@Create(entity = "ControlsView", serviceName = "ControlService")
    public CreateResponse addCustomer(CreateRequest createRequest, ExtensionHelper extensionHelper)
    		throws DatasourceException {

        DataSourceHandler handler = extensionHelper.getHandler();
         
        EntityData entityData = createRequest.getData();
        
        Map<String, Object> controlMap = new HashMap<>();
        controlMap.put("ID", entityData.getElementValue("ID"));
        controlMap.put("controlName", entityData.getElementValue("controlName"));
        controlMap.put("controlDesc", entityData.getElementValue("controlDesc"));
        EntityData createdData = EntityData.createFromMap(controlMap, Arrays.asList("ID"), "Controls");
        EntityData createdEntity = handler.executeInsert(createdData, true);
        
        if(createdEntity != null) {
        	Map<String, Object> controlOwnerMap = new HashMap<>();
            String ownersName = (String) entityData.getElementValue("ownerName");
            String[] owners = ownersName.split(";");
            for(String name: owners) {
//            	UUID.randomUUID()
            	controlOwnerMap.put("ID", Math.random()*10);
                controlOwnerMap.put("ownerName", name);
                controlOwnerMap.put("control_ID", controlMap.get("ID"));
                EntityData createdOwnerData = EntityData.createFromMap(controlOwnerMap, Arrays.asList("ID"), "ControlOwners");
                /* The executeInsert method takes 2 parameters ,first is the entityData which is the current request data and second is a boolean value which is to indicate whether we
                 * want the created entity back. If we give this as true, the handler would internally make another get call and return the created entity as entityData.
                 * Here we give this value as true since we need to return it back in the CreateResponse.
                 */
                EntityData createdOwnerEntity = handler.executeInsert(createdOwnerData, true);
            }
            
        }  
         
        /*
         * We first call setSuccess to get the CreateResponseBuilder, then we set the createdEntity using the setData method, and then we call the response method which would return the
            CreateResponse object which we can then return from the Extension.
        */
        CreateResponse createResponse = CreateResponse.setSuccess().setData(createdEntity).response();
        return createResponse;
         
    }


}
