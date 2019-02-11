namespace com.sap.grc.ctrl;
context controlSignificance{
	entity ControlSignificances {

	  key significanceId : String(3);
	  key LanguageKey : String(2);

	  significanceText   : String;
	}	
}
