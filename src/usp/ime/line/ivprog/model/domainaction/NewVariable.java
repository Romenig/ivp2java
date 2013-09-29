package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class NewVariable extends DomainAction{
	
	private IVPProgram model;
	private String scopeID;
	private String varID;
	
	public NewVariable(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram)m;
	}

	protected void executeAction() {
		varID = model.createVariable(scopeID, _currentState);
	}

	protected void undoAction() {
		model.removeVariable(scopeID, varID, _currentState);
	}

	public boolean equals(DomainAction a) {
		return false;
	}
	
	public String getScopeID() {
		return scopeID;
	}

	public void setScopeID(String scopeID) {
		this.scopeID = scopeID;
	}

	public String getVarID() {
		return varID;
	}

	public void setVarID(String varID) {
		this.varID = varID;
	}


}
