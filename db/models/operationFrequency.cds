namespace com.sap.grc.ctrl;
context operationFrequency{
	entity OperationFrequency {

	  key frequencyId : String(3);
	  key LanguageKey : String(2);

	  frequencyText   : String;
	}	
}
