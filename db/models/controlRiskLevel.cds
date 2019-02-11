namespace com.sap.grc.ctrl;
context controlRiskLevel{
	entity ControlRiskLevels {
		
	  key riskLevelId : String(3);
	  key LanguageKey : String(2);

	  riskLevelText   : String;
	}	
}
