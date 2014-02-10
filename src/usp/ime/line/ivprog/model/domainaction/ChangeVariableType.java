package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeVariableType extends DomainAction {

    private IVPProgram model;
    private short     lastType;
    private short     newType;
    private String     variableID;

    public ChangeVariableType(String name, String description) {
        super(name, description);
    }

    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }

    protected void executeAction() {
        lastType = model.changeVariableType(variableID, newType, _currentState);
    }

    protected void undoAction() {
        model.changeVariableType(variableID, lastType, _currentState);
    }

    public boolean equals(DomainAction a) {
        return false;
    }

    public String getVariableID() {
        return variableID;
    }

    public void setVariableID(String variableID) {
        this.variableID = variableID;
    }

    public short getNewType() {
        return newType;
    }

    public void setNewType(short expressionInteger) {
        this.newType = expressionInteger;
    }

}
