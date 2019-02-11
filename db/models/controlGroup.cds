namespace com.sap.grc.ctrl;
context controlGroup{
	entity ControlGroup {
	  key groupId : String(3);
	  key languageKey: String(3);
	  groupText   : String;
	}	
}
