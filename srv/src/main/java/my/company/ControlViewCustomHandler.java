package my.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmException;

import com.sap.cloud.sdk.hana.connectivity.cds.CDSException;
import com.sap.cloud.sdk.hana.connectivity.cds.CDSQuery;
import com.sap.cloud.sdk.hana.connectivity.cds.CDSSelectQueryBuilder;
import com.sap.cloud.sdk.hana.connectivity.cds.ConditionBuilder;
import com.sap.cloud.sdk.hana.connectivity.handler.CDSDataSourceHandler;
import com.sap.cloud.sdk.hana.connectivity.handler.CDSDataSourceHandlerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;
import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.operations.Create;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.operations.Read;
import com.sap.cloud.sdk.service.prov.api.operations.Update;
import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.request.ReadRequest;
import com.sap.cloud.sdk.service.prov.api.request.UpdateRequest;
import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.cloud.sdk.service.prov.api.response.ReadResponse;
import com.sap.cloud.sdk.service.prov.api.response.ReadResponseBuilder;
import com.sap.cloud.sdk.service.prov.api.response.UpdateResponse;

public class ControlViewCustomHandler {
	
	
	//Get ControlsView("{control_id}")
	@Read(entity = "ControlsView", serviceName = "ControlService")
	public ReadResponse readControlView(ReadRequest readRequest, ExtensionHelper extensionHelper) 
			throws DatasourceException, EdmException {
        DataSourceHandler handler = extensionHelper.getHandler();

//        EntityMetadata entityMetadata = readRequest.getEntityMetadata();
        List<String> elements = Arrays.asList("ID", "controlName", "controlDesc", "controlGroup",
        		"controlRiskLevel", "controlSignificance", "operationFrequency", "validFrom", "validTo");
//        entityMetadata.getElementNames()
        EntityData controlEntityData = handler.executeRead("Controls",readRequest.getKeys(), elements);
        
        Map<String, Object> ownerKey = new HashMap<>();
        ownerKey.put("control_ID", controlEntityData.getElementValue("ID"));
        List<String> ownerElementNames = Arrays.asList("ID", "ownerName", "ownerEmail", "control_ID");
        EntityData ownerEntityData = handler.executeRead("ControlOwners", ownerKey, ownerElementNames);
        
        Map<String, Object> localeKey = new HashMap<>();
        localeKey.put("locale", readRequest.getLocale());
        localeKey.put("control_ID", controlEntityData.getElementValue("ID"));
        List<String> localeElementNames = Arrays.asList("locale", "controlName", "controlDesc", "control_ID");
        EntityData localeEntityData = handler.executeRead("texts", localeKey, localeElementNames);
        
        /*
         * Form the the response object by first forming the ReadResponse Builder by calling ReadResponse.setSuccess();
         * Then set the entityData into the builder and get the ReadResponse by calling the response() method.
         */
        ReadResponseBuilder builder = ReadResponse.setSuccess();
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("ID", controlEntityData.getElementValue("ID"));
        if(localeEntityData != null) {
        	propertiesMap.put("controlName", localeEntityData.getElementValue("controlName"));
            propertiesMap.put("controlDesc", localeEntityData.getElementValue("controlDesc"));
        }else {
        	propertiesMap.put("controlName", controlEntityData.getElementValue("controlName"));
            propertiesMap.put("controlDesc", controlEntityData.getElementValue("controlDesc"));
        }
        
        propertiesMap.put("ownerName", ownerEntityData.getElementValue("ownerName"));
		EntityData result = EntityData.createFromMap(propertiesMap, Arrays.asList("ID"), "ControlsView");
        ReadResponse rr = builder.setData(result).response();
         
        return rr;
	         
	}
	
	@Update(entity = "ControlsView", serviceName = "ControlService")
	public UpdateResponse modifyCustomer(UpdateRequest updateRequest, ExtensionHelper extensionHelper) 
			throws DatasourceException {

        EntityData entityData = updateRequest.getData();
        DataSourceHandler handler = extensionHelper.getHandler();
        
//        Object controlID = entityData.getElementValue("ID");
        List<String> elements = Arrays.asList("ID", "controlName", "controlDesc", "controlGroup",
        		"controlRiskLevel", "controlSignificance", "operationFrequency", "validFrom", "validTo");
        EntityData controlEntityData = handler.executeRead("Controls",updateRequest.getKeys(), elements);
        if(controlEntityData != null) {
        	HashMap<String, Object> controlMap = new HashMap<>();
            controlMap.put("controlName", entityData.getElementValue("controlName"));
            
            EntityData createdData_01 = EntityData.createFrom(entityData, "Controls");
            
            EntityData createdData = EntityData.createFromMap(controlMap, Arrays.asList("ID"), "Controls");
            
            EntityData ed = handler.executeUpdate(createdData, updateRequest.getKeys(), false);
        }
        
         
        UpdateResponse updateResponse = UpdateResponse.setSuccess().response();
         
        return updateResponse;
	         
	}
	
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
        	Locale locale = createRequest.getLocale();
        	Map<String, Object> controlTextMap = new HashMap<>();
        	controlTextMap.put("locale", locale);
        	controlTextMap.put("control_ID", controlMap.get("ID"));
        	controlTextMap.put("controlName", controlMap.get("controlName"));
        	controlTextMap.put("controlDesc", controlMap.get("controlDesc"));
            
            EntityData controlTextData = EntityData.createFromMap(controlTextMap, 
            		Arrays.asList("locale","control_ID"), "texts");
            EntityData controlTextEntity = handler.executeInsert(controlTextData, true);           
            
            
        	Map<String, Object> controlOwnerMap = new HashMap<>();
            String ownersName = (String) entityData.getElementValue("ownerName");
            String[] owners = ownersName.split(";");
            for(String name: owners) {
//            	UUID.randomUUID()
            	controlOwnerMap.put("ID", Math.random()*10000);
                controlOwnerMap.put("ownerName", name);
                controlOwnerMap.put("control_ID", controlMap.get("ID"));
                EntityData createdOwnerData = EntityData.createFromMap(controlOwnerMap, 
                		Arrays.asList("ID"), "ControlOwners");

                EntityData createdOwnerEntity = handler.executeInsert(createdOwnerData, true);
            }
            
        }  

        CreateResponse createResponse = CreateResponse.setSuccess().setData(createdEntity).response();
        return createResponse;
         
    }


}
