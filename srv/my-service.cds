using com.cap.only from '../db/models/control';
using com.sap.grc.ctrl.controlGroup from '../db/models/controlGroup';
using com.sap.grc.ctrl.controlSignificance from '../db/models/controlSignificance';
using com.sap.grc.ctrl.controlRiskLevel from '../db/models/controlRiskLevel';
using com.sap.grc.ctrl.operationFrequency from '../db/models/operationFrequency';

service ControlService {
  entity ControlOwners @insert as projection on only.control.ControlOwners;
  entity ControlGroups @insert @update @read as projection on controlGroup.ControlGroup;
  entity Controls @read @insert @update as projection on only.control.Controls;
  entity texts @readonly as projection on only.control.Controls_texts;
  entity ControlRiskLevels @read @insert @update as projection on controlRiskLevel.ControlRiskLevels;
  entity ControlSignificances @read @insert @update as projection on controlSignificance.ControlSignificances;
  entity OperationFrequencies @read @insert @update as projection on operationFrequency.OperationFrequency;
  entity ControlsView as projection on only.control.ControlsView;
}
