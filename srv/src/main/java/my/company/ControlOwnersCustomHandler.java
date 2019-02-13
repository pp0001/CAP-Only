package my.company;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sdk.hana.connectivity.cds.CDSException;
import com.sap.cloud.sdk.hana.connectivity.cds.CDSQuery;
import com.sap.cloud.sdk.hana.connectivity.cds.CDSSelectQueryBuilder;
import com.sap.cloud.sdk.hana.connectivity.cds.ConditionBuilder;
import com.sap.cloud.sdk.hana.connectivity.handler.CDSDataSourceHandler;
import com.sap.cloud.sdk.hana.connectivity.handler.CDSDataSourceHandlerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.operations.Read;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.request.ReadRequest;
import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.cloud.sdk.service.prov.api.response.ReadResponse;

public class ControlOwnersCustomHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	//Get ControlsView("{control_id}")/controlOwners
	@Query(serviceName = "ControlService", entity = "ControlOwners", sourceEntity = "ControlsView")
	public QueryResponse queryControlOwners(QueryRequest queryRequest)
			throws ODataException, CDSException {

		// Retrieve the keys of the parent entity specified in the navigation
		Map<String, Object> sourceEntityKeys = queryRequest.getSourceKeys();

		// Add your implementation of the query operation here
		CDSDataSourceHandler handler = CDSDataSourceHandlerFactory.getHandler();
		CDSQuery cdsQuery = new CDSSelectQueryBuilder("ControlService.ControlOwners")
                .selectColumns("ID", "control_ID", "ownerName", "ownerEmail")
                .where(new ConditionBuilder().columnName("control_ID").EQ(sourceEntityKeys.get("ID")) )
                .build();
 
		List<EntityData> data = handler.executeQuery(cdsQuery).getResult();

		// Return an instance of QueryResponse in case of success
		return QueryResponse.setSuccess().setData(data).response();
	}
//
//	@Read(entity = "ControlOwners", serviceName = "ControlService")
//	public ReadResponse readControlOwners(ReadRequest req) {
//		logger.info("Read ControlOwners entity");
//      //TODO: add your custom logic...
//
//      //Object data = new Object();
//      //return ReadResponse.setSuccess().setData(data).response(); //use this API to return an item.
//      ErrorResponse errorResponse = ErrorResponse.getBuilder()
//        					.setMessage("Unimplemented Read Operation")
//        					.setStatusCode(500)
//        					.response();
//      return ReadResponse.setError(errorResponse);
//	}

//	@Update(entity = "ControlOwners", serviceName = "ControlService")
//	public UpdateResponse updateControlOwners(UpdateRequest req) {
//      //TODO: add your custom logic...
//
//      //return UpdateResponse.setSuccess().response(); //use this API if the item is successfully modified.
//      ErrorResponse errorResponse = ErrorResponse.getBuilder()
//        					.setMessage("Unimplemented Update Operation")
//        					.setStatusCode(500)
//        					.response();
//      return UpdateResponse.setError(errorResponse);
//	}

//	@Create(entity = "ControlOwners", serviceName = "ControlService")
//	public CreateResponse createControlOwners(CreateRequest req) {
//      //TODO: add your custom logic...
//
//      //return CreateResponse.setSuccess().response(); //use this API if the item is successfully created.
//      ErrorResponse errorResponse = ErrorResponse.getBuilder()
//        					.setMessage("Unimplemented Create Operation")
//        					.setStatusCode(500)
//        					.response();
//      return CreateResponse.setError(errorResponse);
//	}

//	@Delete(entity = "ControlOwners", serviceName = "ControlService")
//	public DeleteResponse deleteControlOwners(DeleteRequest req) {
//      //TODO: add your custom logic...
//
//      //return DeleteResponse.setSuccess().response(); //use this API if the item is successfully deleted.
//      ErrorResponse errorResponse = ErrorResponse.getBuilder()
//        					.setMessage("Unimplemented Delete Operation")
//        					.setStatusCode(500)
//        					.response();
//      return DeleteResponse.setError(errorResponse);
//	}

}
