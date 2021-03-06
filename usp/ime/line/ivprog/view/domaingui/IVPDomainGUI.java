
/*
 * iVProg2 - interactive Visual Programming to the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.view.domaingui;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.domain.DomainGUI;
import ilm.framework.domain.DomainModel;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.listeners.IFunctionListener;
import usp.ime.line.ivprog.model.domainaction.ChangeExpressionSign;
import usp.ime.line.ivprog.model.domainaction.ChangeForMode;
import usp.ime.line.ivprog.model.domainaction.ChangeValue;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableInitValue;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableName;
import usp.ime.line.ivprog.model.domainaction.ChangeVariableType;
import usp.ime.line.ivprog.model.domainaction.CreateChild;
import usp.ime.line.ivprog.model.domainaction.CreateExpression;
import usp.ime.line.ivprog.model.domainaction.CreateVariable;
import usp.ime.line.ivprog.model.domainaction.DeleteExpression;
import usp.ime.line.ivprog.model.domainaction.DeleteVariable;
import usp.ime.line.ivprog.model.domainaction.MoveComponent;
import usp.ime.line.ivprog.model.domainaction.RemoveChild;
import usp.ime.line.ivprog.model.domainaction.UpdateReferencedVariable;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariablePanel;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Color;
import java.awt.Font;

public class IVPDomainGUI extends DomainGUI implements IFunctionListener {

  private static final long serialVersionUID = 4725912646391705263L;
  private JPanel workspaceContainer;
  private JTabbedPane tabbedPane;
  private JPanel consolePanel;

  private RoundedJPanel consoleContainer;
  private IVPConsole consoleField;
  private JPanel playAndConsolePanel;
  private JLabel consoleLabel;
  private JButton btnPlay;
  private JButton btnErase;
  private Component verticalStrut;
  private Component verticalStrut_1;

  public IVPDomainGUI () {
    setPreferredSize(new Dimension(800, 600));
    setLayout(new BorderLayout(0, 0));
    initTabbedPane();
    }

  private void initTabbedPane () {
    JSplitPane splitPane = new JSplitPane();
    splitPane.setResizeWeight(1.0);
    splitPane.setDividerSize(3);
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    add(splitPane, BorderLayout.CENTER);
    workspaceContainer = new JPanel();
    splitPane.setLeftComponent(workspaceContainer);
    workspaceContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
    workspaceContainer.setLayout(new BorderLayout(0, 0));
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    workspaceContainer.add(tabbedPane, BorderLayout.CENTER);
    consolePanel = new JPanel();
    consolePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    splitPane.setRightComponent(consolePanel);
    consolePanel.setLayout(new BorderLayout(0, 0));
    playAndConsolePanel = new JPanel();
    playAndConsolePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    consolePanel.add(playAndConsolePanel, BorderLayout.WEST);
    playAndConsolePanel.setLayout(new BoxLayout(playAndConsolePanel, BoxLayout.Y_AXIS));
    Action playAction = new AbstractAction () {
      public void actionPerformed(ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_EXECUTE;");
        Services.getController().playCode();
        }
      };
    playAction.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/play.png")));
    playAction.putValue(Action.SHORT_DESCRIPTION, "Executa a fun��o principal.");
    consoleLabel = new JLabel();
    consoleLabel.setIcon(new ImageIcon(IVPDomainGUI.class.getResource("/usp/ime/line/resources/icons/console.png")));
    playAndConsolePanel.add(consoleLabel);
    verticalStrut = Box.createVerticalStrut(10);
    playAndConsolePanel.add(verticalStrut);
    btnPlay = new JButton(playAction);
    btnPlay.setUI(new IconButtonUI());
    playAndConsolePanel.add(btnPlay);
    verticalStrut_1 = Box.createVerticalStrut(10);
    playAndConsolePanel.add(verticalStrut_1);
    Action cleanAction = new AbstractAction () {
      public void actionPerformed(ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_CLEAN;");
        consoleField.clean();
        }
      };
    cleanAction.putValue(Action.SMALL_ICON,
            new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/erase.png")));
    cleanAction.putValue(Action.SHORT_DESCRIPTION, "Executa a fun��o principal.");
    btnErase = new JButton(cleanAction);
    btnErase.setUI(new IconButtonUI());
    playAndConsolePanel.add(btnErase);
    consoleContainer = new RoundedJPanel();
    consoleContainer.setBackgroundColor(FlatUIColors.CONSOLE_COLOR);
    consoleContainer.setArcs(new Dimension(10, 10));
    consoleContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
    consolePanel.add(consoleContainer, BorderLayout.CENTER);
    consoleContainer.setLayout(new BorderLayout(0, 0));
    consoleField = new IVPConsole();
    consoleContainer.add(consoleField);
    }

  public void update (Observable model, Object o) {
    if (o instanceof AssignmentState) {
      _state = (AssignmentState) o;
      }
    }

  public void addTab (String tabName, FunctionBodyUI function) {
    tabbedPane.add(tabName, function);
    }

  public void removeTabAtIndex (int index) {
    tabbedPane.remove(index);
    }

  public void updateFunction (FunctionBodyUI function) {
    if (tabbedPane.getTabCount() == 0) {
      tabbedPane.add(function.getName(), function);
      return;
      }
    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
      if (tabbedPane.getTitleAt(i).equals(function.getName())) {
        tabbedPane.remove(i);
        tabbedPane.add(function, i);
        }
      }
    }

  protected void initDomainGUI () {
    }

  public Vector getSelectedObjects () {
    return null;
    }

  public void functionCreated (String id) {
    updateFunction((FunctionBodyUI) Services.getRenderer().paint(id));
    }

  public void initDomainActionList (DomainModel model) {
    _actionList = new HashMap();
    CreateVariable newVar = new CreateVariable("newvar", "newvar");
    newVar.setDomainModel(model);
    _actionList.put("newvar", newVar);
    DeleteVariable delVar = new DeleteVariable("delvar", "delvar");
    delVar.setDomainModel(model);
    _actionList.put("delvar", delVar);
    ChangeVariableName changeVarName = new ChangeVariableName("changeVarName", "changeVarName");
    changeVarName.setDomainModel(model);
    _actionList.put("changeVarName", changeVarName);
    ChangeVariableType changeVarType = new ChangeVariableType("changeVarType", "changeVarType");
    changeVarType.setDomainModel(model);
    _actionList.put("changeVarType", changeVarType);
    ChangeVariableInitValue change = new ChangeVariableInitValue("changevariableinitvalue", "changevariableinitvalue");
    change.setDomainModel(model);
    _actionList.put("changevariableinitvalue", change);
    CreateChild newChild = new CreateChild("newchild", "newchild");
    newChild.setDomainModel(model);
    _actionList.put("newchild", newChild);
    RemoveChild removeChild = new RemoveChild("removechild", "removechild");
    removeChild.setDomainModel(model);
    _actionList.put("removechild", removeChild);
    CreateExpression createExpression = new CreateExpression("createexpression", "createexpression");
    createExpression.setDomainModel(model);
    _actionList.put("createexpression", createExpression);
    DeleteExpression deleteExpression = new DeleteExpression("deleteexpression", "deleteexpression");
    deleteExpression.setDomainModel(model);
    _actionList.put("deleteexpression", deleteExpression);
    ChangeExpressionSign changeExpressionSign = new ChangeExpressionSign("changeexpressionsign", "changeexpressionsign");
    changeExpressionSign.setDomainModel(model);
    _actionList.put("changeexpressionsign", changeExpressionSign);
    UpdateReferencedVariable upVar = new UpdateReferencedVariable("updateReferencedVar", "updateReferencedVar");
    upVar.setDomainModel(model);
    _actionList.put("updateReferencedVar", upVar);
    ChangeValue chV = new ChangeValue("changevalue", "changevalue");
    chV.setDomainModel(model);
    _actionList.put("changevalue", chV);
    MoveComponent mv = new MoveComponent("movecomponent", "movecomponent");
    mv.setDomainModel(model);
    _actionList.put("movecomponent", mv);
    ChangeForMode changeFor = new ChangeForMode("changeformode", "changeformode");
    changeFor.setDomainModel(model);
    _actionList.put("changeformode", changeFor);
    }

  public void addChild (String containerID, short childType, String context) {
    CreateChild newChild = (CreateChild) _actionList.get("newchild");
    newChild.setClassID(childType);
    newChild.setContainerID(containerID);
    newChild.setContext(context);
    newChild.execute();
    }

  public void removeChild (String containerID, String childID, String context) {
    RemoveChild removeChild = (RemoveChild) _actionList.get("removechild");
    removeChild.setChildID(childID);
    removeChild.setContext(context);
    removeChild.setContainerID(containerID);
    removeChild.execute();
    }

  public void moveChild (String child, String origin, String destiny, String originContext, String destinyContext, int dropIndex) {
    MoveComponent mv = (MoveComponent) _actionList.get("movecomponent");
    mv.setComponent(child);
    mv.setOrigin(origin);
    mv.setOriginContext(originContext);
    mv.setDestinyContext(destinyContext);
    mv.setDestiny(destiny);
    mv.setDropY(dropIndex);
    mv.execute();
    }

  public void addParameter (String scopeID) {
    }

  public void addVariable (String scopeID, String initValue) {
    CreateVariable newVar = (CreateVariable) _actionList.get("newvar");
    newVar.setScopeID(scopeID);
    newVar.setInitValue(initValue);
    newVar.execute();
    }

  public void deleteVariable (String scopeID, String id) {
    DeleteVariable delVar = (DeleteVariable) _actionList.get("delvar");
    delVar.setScopeID(scopeID);
    delVar.setVariableID(id);
    delVar.execute();
    }

  public void changeVariableName (String id, String name) {
    ChangeVariableName changeVarName = (ChangeVariableName) _actionList.get("changeVarName");
    changeVarName.setVariableID(id);
    changeVarName.setNewName(name);
    changeVarName.execute();
    }

  public void changeVariableType (String id, short expressionInteger) {
    ChangeVariableType changeVarType = (ChangeVariableType) _actionList.get("changeVarType");
    changeVarType.setVariableID(id);
    changeVarType.setNewType(expressionInteger);
    changeVarType.execute();
    }

  public void updateVariableReference (String referenceID, String newReferencedVar) {
    UpdateReferencedVariable upVar = (UpdateReferencedVariable) _actionList.get("updateReferencedVar");
    upVar.setReferenceID(referenceID);
    upVar.setNewVarID(newReferencedVar);
    upVar.execute();
    }

  public void changeVariableInitialValue (String id, String value) {
    ChangeVariableInitValue change = (ChangeVariableInitValue) _actionList.get("changevariableinitvalue");
    change.setNewValue(value);
    change.setVariableID(id);
    change.execute();
    }

  public void changeValue (String id, String newValue) {
    ChangeValue chV = (ChangeValue) _actionList.get("changevalue");
    chV.setId(id);
    chV.setNewValue(newValue);
    chV.execute();
    }

  public void createExpression (String leftExpID, String holder, short expressionType, short primitiveType, String context) {
    CreateExpression createExpression = (CreateExpression) _actionList.get("createexpression");
    createExpression.setExp1(leftExpID);
    createExpression.setHolder(holder);
    createExpression.setExpressionType(expressionType);
    createExpression.setContext(context);
    createExpression.setPrimitiveType(primitiveType);
    createExpression.execute();
    }

  public void deleteExpression (String id, String holder, String context, boolean isClean, boolean isComparison) {
    DeleteExpression deleteExpression = (DeleteExpression) _actionList.get("deleteexpression");
    deleteExpression.setExpression(id);
    deleteExpression.setHolder(holder);
    deleteExpression.setContext(context);
    deleteExpression.setClean(isClean);
    deleteExpression.setComparison(isComparison);
    deleteExpression.execute();
    }

  public void changeExpressionSign (String id, short expressionType, String context) {
    ChangeExpressionSign changeExpression = (ChangeExpressionSign) _actionList.get("changeexpressionsign");
    changeExpression.setExpressionID(id);
    changeExpression.setContext(context);
    changeExpression.setNewType(expressionType);
    changeExpression.execute();
    }

  public void changeForMode (int forMode, String modelID) {
    ChangeForMode change = (ChangeForMode) _actionList.get("changeformode");
    change.setNewMode(forMode);
    change.setForID(modelID);
    change.execute();
    }

  public HashMap getActionList () {
    return _actionList;
    }

  }
