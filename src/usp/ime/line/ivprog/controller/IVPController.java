package usp.ime.line.ivprog.controller;

import ilm.framework.domain.DomainModel;

import java.awt.event.ComponentListener;
import java.util.HashMap;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.IVPProgram;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableName;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableType;
import usp.ime.line.ivprog.model.domainaction.CreateExpression;
import usp.ime.line.ivprog.model.domainaction.DeleteVariable;
import usp.ime.line.ivprog.model.domainaction.NewChild;
import usp.ime.line.ivprog.model.domainaction.NewVariable;
import usp.ime.line.ivprog.model.domainaction.RemoveChild;
import usp.ime.line.ivprog.view.domaingui.IVPDomainGUI;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;

public class IVPController {

	private IVPProgram program = null;
	private IVPDomainGUI gui = null;
	private HashMap actionList;
	private HashMap codeListener;
	
	public IVPController(){
		actionList = new HashMap();
		codeListener = new HashMap();
	}
	
	public HashMap getCodeListener(){
		return codeListener;
	}

	public HashMap getActionList(){
		return actionList;
	}
	
	public IVPProgram getProgram() {
		return program;
	}

	public void setProgram(IVPProgram program) {
		this.program = program;
	}

	public IVPDomainGUI getGui() {
		return gui;
	}

	public void setGui(IVPDomainGUI gui) {
		this.gui = gui;
	}

	public void initializeModel() {
		program.initializeModel();
	}

	public void showExecutionEnvironment() {
	
	}

	public void showConstructionEnvironment() {
	
	}

	public void addChild(String containerID, short childType) {
		NewChild newChild = (NewChild) actionList.get("newchild");
		newChild.setClassID(childType);
		newChild.setContainerID(containerID);
		newChild.execute();
		ICodeListener listener = (ICodeListener) codeListener.get(containerID);
		listener.childAdded(newChild.getObjectID());
	}

	public void addParameter(String scopeID) {

	}

	public void addVariable(String scopeID) {
		NewVariable newVar = (NewVariable) actionList.get("newvar");
		newVar.setScopeID(scopeID);
		newVar.execute();
	}

	public void deleteVariable(String scopeID, String id) {
		DeleteVariable delVar = (DeleteVariable) actionList.get("delvar");
		delVar.setScopeID(scopeID);
		delVar.setVariableID(id);
		delVar.execute();
	}
	
	public void changeVariableName(String id, String name){
		ChangeVariableName changeVarName = (ChangeVariableName) actionList.get("changeVarName");
		changeVarName.setVariableID(id);
		changeVarName.setNewName(name);
		changeVarName.execute();
	}
	
	public void changeVariableType(String id, short type){
		ChangeVariableType changeVarType = (ChangeVariableType) actionList.get("changeVarType");
		changeVarType.setVariableID(id);
		changeVarType.setNewType(type);
		changeVarType.execute();
	}
	
	//TODO: DomainAction
	public void changeVariableInitialValue(String id, String value){
		//program.changeVariableInitialValue(id, value);
	}
	
	public void createExpression(String leftExpID, String holder, short expressionType){
		CreateExpression createExpression = (CreateExpression) actionList.get("createexpression");
		createExpression.setExp1(leftExpID);
		createExpression.setHolder(holder);
		createExpression.setExpressionType(expressionType);
		createExpression.execute();
	}
	
	public void initDomainActionList(DomainModel model) {
		NewVariable newVar = new NewVariable("newvar","newvar");
		newVar.setDomainModel(model);
		actionList.put("newvar", newVar);
		
		DeleteVariable delVar = new DeleteVariable("delvar", "delvar");
		delVar.setDomainModel(model);
		actionList.put("delvar", delVar);
		
		ChangeVariableName changeVarName = new ChangeVariableName("changeVarName", "changeVarName");
		changeVarName.setDomainModel(model);
		actionList.put("changeVarName", changeVarName);
		
		ChangeVariableType changeVarType = new ChangeVariableType("changeVarType", "changeVarType");
		changeVarType.setDomainModel(model);
		actionList.put("changeVarType", changeVarType);
		
		NewChild newChild = new NewChild("newchild", "newchild");
		newChild.setDomainModel(model);
		actionList.put("newchild", newChild);
		
		RemoveChild removeChild = new RemoveChild("removechild", "removechild");
		removeChild.setDomainModel(model);
		actionList.put("removechild", removeChild);
		
		CreateExpression createExpression = new CreateExpression("createexpression","createexpression");
		createExpression.setDomainModel(model);
		actionList.put("createexpression", createExpression);
	}
	
	public void addComponentListener(ICodeListener listener, String id){
		codeListener.put(id, listener);
	}

	public void removeChild(String containerID, String childID) {
		RemoveChild removeChild = (RemoveChild) actionList.get("removechild");
		removeChild.setChildID(childID);
		removeChild.setContainerID(containerID);
		removeChild.execute();
	}

}
;