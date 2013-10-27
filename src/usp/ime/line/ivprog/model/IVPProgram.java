package usp.ime.line.ivprog.model;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.listeners.IExpressionListener;
import usp.ime.line.ivprog.listeners.IFunctionListener;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.DataFactory;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComponent;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Operation;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Variable;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPProgram extends DomainModel {

	private HashMap globalVariables = null;
	private HashMap preDefinedFunctions = null;
	private HashMap functionMap = null;
	private DataFactory dataFactory = null;
	private List variableListeners;
	private List functionListeners;
	private HashMap expressionListeners;
	
	private String currentScope = "0";

	public IVPProgram() {
		globalVariables = new HashMap();
		preDefinedFunctions = new HashMap();
		functionMap = new HashMap();
		expressionListeners = new HashMap();
		dataFactory = new DataFactory();
		variableListeners = new Vector();
		functionListeners = new Vector();
	}

	public void initializeModel() {
		createFunction(ResourceBundleIVP.getString("mainFunctionName"), IVPConstants.FUNC_RETURN_VOID, null);
	}

	// Program actions
	public void createFunction(String name, short functionType, AssignmentState state) {
		Function f = (Function) dataFactory.createFunction();
		f.setFunctionName(name);
		f.setReturnType(functionType);
		functionMap.put(name, f);
		Services.getService().getModelMapping().put(f.getUniqueID(), f);
		for(int i = 0; i < functionListeners.size(); i++){
			IFunctionListener listener = (IFunctionListener) functionListeners.get(i);
			listener.functionCreated(f.getUniqueID());
		}
		if(state!=null) state.add(f);
	}

	public void removeFunction(String name, AssignmentState state) {
		//Framework
		state.remove((DomainObject) functionMap.get(name));
		functionMap.remove(name);
	}

	public String newChild(String containerID, short classID, AssignmentState state) {
		CodeComponent codeBlock = null;
		if(classID == IVPConstants.MODEL_WHILE){
			codeBlock = (CodeComposite) dataFactory.createWhile();
		} else if( classID == IVPConstants.MODEL_WRITE){
			codeBlock = (CodeComponent) dataFactory.createPrint();
		} else if( classID == IVPConstants.MODEL_ATTLINE){
			codeBlock = (CodeComponent) dataFactory.createAttributionLine();
		}
		Services.getService().getModelMapping().put(codeBlock.getUniqueID(), codeBlock);
		CodeComposite container = (CodeComposite) Services.getService().getModelMapping().get(containerID);
		codeBlock.setParentID(containerID);
		codeBlock.setScopeID(currentScope);
		container.addChild(codeBlock.getUniqueID());
		//Framework
		state.add(codeBlock);
		return codeBlock.getUniqueID();
	}

	public int removeChild(String containerID, String childID, AssignmentState state) {
		CodeComposite parent = (CodeComposite) Services.getService().getModelMapping().get(containerID);
		int index = 0;
		index = parent.removeChild(childID);
		ICodeListener codeListener = (ICodeListener) Services.getService().getController().getCodeListener().get(containerID);
		codeListener.childRemoved(childID);
		//Framework
		state.remove((DomainObject) Services.getService().getModelMapping().get(childID));
		return index;
	}

	// Function actions
	public void createParameter(String scopeID) {

	}

	public void removeParameter(String scopeID, String parameterID) {

	}

	public String createVariable(String scopeID, AssignmentState state) {
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		Variable newVar = (Variable) dataFactory.createVariable();
		newVar.setVariableName("newVar" + f.getVariableCount());
		newVar.setVariableType(Variable.TYPE_INTEGER);
		newVar.setScopeID(currentScope);
		Services.getService().getModelMapping().put(newVar.getUniqueID(), newVar);
		f.addLocalVariable(newVar.getUniqueID());
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.addedVariable(newVar.getUniqueID());
		}
		state.add(newVar);
		return newVar.getUniqueID();
	}

	public void removeVariable(String scopeID, String variableID, AssignmentState state) {
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		f.removeLocalVariable(variableID);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.removedVariable(variableID);
		}
		state.remove((DomainObject) Services.getService().getModelMapping().get(variableID));
	}
	
	public void restoreVariable(String scopeID, String variableID, AssignmentState state){
		Function f = (Function) Services.getService().getModelMapping().get(scopeID);
		f.addLocalVariable(variableID);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.variableRestored(variableID);
		}
		state.add((DomainObject) Services.getService().getModelMapping().get(variableID));
	}
	
	public String createExpression(String leftExpID, String holder, short operationType, AssignmentState state){
		// new expression ->    (leftExp SIGN newExp)
		Expression exp = null;
		if(operationType == Expression.EXPRESSION_VARIABLE){
			exp = (Expression) dataFactory.createVarReference();
		}else {
			exp = (Expression) dataFactory.createOperation();
			exp.setExpressionType(operationType);
			((Operation) exp).setExpressionA(leftExpID);
		}
		Services.getService().getModelMapping().put(exp.getUniqueID(), exp);
		IExpressionListener listener = (IExpressionListener) expressionListeners.get(holder);
		listener.expressionCreated(exp.getUniqueID());
		return exp.getUniqueID();
	}
	
	public void cleanExpressionField(String expressionID, AssignmentState state){
		
	}
	
	public void addExpressionListener(String id, IExpressionListener listener){
		expressionListeners.put(id, listener);
	}

	public void addVariableListener(IVariableListener listener){
		variableListeners.add(listener);
	}
	
	public void addFunctionListener(IFunctionListener listener){
		functionListeners.add(listener);
	}
	
	public String changeVariableName(String id, String name, AssignmentState state){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		String lastName = v.getVariableName();
		v.setVariableName(name);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableName(id, name, lastName);
		}
		state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
		return lastName;
	}
	
	public short changeVariableType(String id, short type, AssignmentState state){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		short lastType = v.getVariableType();
		v.setVariableType(type);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableType(id, type);
		}
		state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
		return lastType;
	}
	
	public void changeVariableInitialValue(String id, String value, AssignmentState state){
		Variable v = (Variable) Services.getService().getModelMapping().get(id);
		v.setVariableValue(value);
		for(int i=0; i<variableListeners.size(); i++){
			IVariableListener listener = (IVariableListener) variableListeners.get(i);
			listener.changeVariableValue(id, value);
		}
		state.updateState((DomainObject) Services.getService().getModelMapping().get(id));
	}

	public AssignmentState getNewAssignmentState() {
		return new AssignmentState();
	}

	public float AutomaticChecking(AssignmentState studentAnswer, AssignmentState expectedAnswer) {
		return 0;
	}

}
