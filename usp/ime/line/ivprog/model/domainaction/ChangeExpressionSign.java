/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 *
 * @see : ilm/framework/assignment/model/DomainAction.java
 * 
 */

package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeExpressionSign extends DomainAction {

  private IVPProgram model;
  private String expressionID = "";
  private String context = "";
  private short lastType;
  private short newType;

  public ChangeExpressionSign (String name, String description) {
    super(name, description);
    }

  public void setDomainModel (DomainModel m) {
    model = (IVPProgram) m;
    }

  protected void executeAction () {
    lastType = model.changeOperationSign(expressionID, context, newType, _currentState);
    }

  protected void undoAction () {
    model.changeOperationSign(expressionID, context, lastType, _currentState);
    }

  public boolean equals (DomainAction a) {
    return false;
    }

  public String getExpressionID () {
    return expressionID;
    }

  public void setExpressionID (String expressionID) {
    this.expressionID = expressionID;
    }

  public short getLastType () {
    return lastType;
    }

  public void setLastType (short lastType) {
    this.lastType = lastType;
    }

  public short getNewType () {
    return newType;
    }

  public void setNewType (short newType) {
    this.newType = newType;
    }

  public String getContext () {
    return context;
    }

  public void setContext (String context) {
    this.context = context;
    }

  public String toString () {
    String str = "";
    str += "<changeexpressionsign>\n" + "   <expressionid>" + expressionID + "</expressionid>\n" + "   <context>" + context
           + "</context>\n" + "   <lasttype>" + lastType + "</lasttype>\n" + "   <newtype>" + newType + "</newtype>\n"
           + "</changeexpressionsign>\n";
    return str;
    }

  }
