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

public class ChangeValue extends DomainAction {

  private IVPProgram model;
  private String id = "";
  private String lastValue = "";
  private String newValue = "";
  private String context = "";
  private String holder = "";

  public ChangeValue (String name, String description) {
    super(name, description);
    }

  public void setDomainModel (DomainModel m) {
    model = (IVPProgram) m;
    }

  protected void executeAction () {
    lastValue = model.changeValue(id, newValue, _currentState);
    }

  protected void undoAction () {
    model.changeValue(id, lastValue, _currentState);
    }

  public boolean equals (DomainAction a) {
    return false;
    }

  public IVPProgram getModel () {
    return model;
    }

  public void setModel(IVPProgram model) {
    this.model = model;
    }

  public String getId () {
    return id;
    }

  public void setId (String id) {
    this.id = id;
    }

  public String getLastValue () {
    return lastValue;
    }

  public void setLastValue (String lastValue) {
    this.lastValue = lastValue;
    }

  public String getNewValue () {
    return newValue;
    }

  public void setNewValue (String newValue) {
    this.newValue = newValue;
    }

  public String getContext () {
    return context;
    }

  public void setContext (String context) {
    this.context = context;
    }

  public String getHolder () {
    return holder;
    }

  public void setHolder (String holder) {
    this.holder = holder;
    }

  public String toString () {
    String str = "";
    str += "<changevalue>\n" + "   <id>" + id + "</id>\n" + "   <lastvalue>" + lastValue + "</lastvalue>\n" + "   <newvalue>"
            + newValue + "</newvalue>\n" + "   <context>" + context + "</context>\n" + "   <holder>" + holder + "</holder>\n"
            + "</changevalue>\n";
    return str;
    }

  }
