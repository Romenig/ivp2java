/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 * Create a field on expression
 * @see: AttributionLineUI.java : addComponents(): blockContent(): when crating a new attribution, it is borned with no right side, indicating the message "select a variable" (AttributionLineUI.lblNewLabel)
 * @see: ExpressionHolderUI.java: 
 * @see: ExpressionFieldUI.java :
 * 
 */

package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

// import usp.ime.line.ivprog.model.utils.Services;
import ilm.framework.assignment.model.DomainAction; // to use 'DomainAction.staticExecutingInSilence'

import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.utils.IconButtonUI;

public class ExpressionFieldUI extends JPanel {

  private JButton btnEdit;
  private ExpressionHolderUI expressionHolderUI;
  private ImageIcon open;
  private ImageIcon closed;
  private JLabel lockerIcon;

  // @see AttributionLineUI.java: addComponents(): blockContent(): attribution is created empty, only with the space to the use define the variable
  // Default is: expression is rised with the locker open -- editable - here only the image, the action is actually in './usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/ExpressionHolderUI.java'
  private boolean isEditing = true;  // is under edition - can change varialbe
  private boolean isBlocked = false; // is not blocked   - the lock is open and the user can edit variables
   //command is rised open/editable - here only the image, the action is actually in ''


  //printParents(this);
  public static void printParents (java.awt.Component component) {
    java.awt.Component comp = component;
    while (comp!=null) {
      System.out.println(" " + comp.toString());
      comp = comp.getParent();
      }
    }


  // Called by:
  // * AttributionLineUI.java: initialization()
  // * WhileUI.java: initExpression()
  public ExpressionFieldUI (String parent, String scope) {
    //D System.out.println("ExpressionFieldUI: " + parent + ", " + scope);
    //D tring str=""; System.err.println(str.charAt(3)); } catch (Exception e1) { e1.printStackTrace(); }
    if (DomainAction.getExecutingInSilence()) {
      // ./src/ilm/framework/assignment/model/DomainAction.java: static boolean staticExecutingInSilence = true => is reading from a file
      this.isEditing = false;
      this.isBlocked = true;
      } //D System.out.println("ExpressionFieldUI: DomainAction.staticExecutingInSilence=" + DomainAction.getExecutingInSilence());

    //
    initLayout();
    initExpressionHolder(parent, scope);
    initEditionBtn();
    }

  //D System.err.println("isBlocked="+isBlocked);

  private void initEditionBtn () {
    // Icons presenting expression/command edition with a locker
    open = new ImageIcon(ExpressionFieldUI.class.getResource("/usp/ime/line/resources/icons/locker_opened.png"));
    closed = new ImageIcon(ExpressionFieldUI.class.getResource("/usp/ime/line/resources/icons/locker_closed.png"));
    lockerIcon = new JLabel();
    if (isEditing)
      lockerIcon.setIcon(open);
    else
      lockerIcon.setIcon(closed);
    Action edition = new AbstractAction () { // click on locker
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_CODE_EDITIONLOCKER_" + isBlocked + ";");
        //D System.out.println("ExpressionFieldUI.java: initEditionBtn(): isBlocked=" + isBlocked);
        if (!isBlocked) {
          if (isEditing) {
            expressionHolderUI.disableEdition();
            isEditing = false;
            lockerIcon.setIcon(closed);
            lockerIcon.repaint();
            }
          else {
            expressionHolderUI.enableEdition();
            isEditing = true;
            lockerIcon.setIcon(open);
            lockerIcon.repaint();
            }
          }
        }
      };
    btnEdit = new JButton(edition);
    btnEdit.add(lockerIcon);
    btnEdit.setIcon(new ImageIcon(ExpressionFieldUI.class.getResource("/usp/ime/line/resources/icons/pog.png")));
    btnEdit.setUI(new IconButtonUI());
    add(btnEdit);
    }

  private void initExpressionHolder (String parent, String scope) {
    expressionHolderUI = new ExpressionHolderUI(parent, scope);
    add(expressionHolderUI);
    }

  private void initLayout () {
    setOpaque(false);
    FlowLayout flowLayout = (FlowLayout) getLayout();
    flowLayout.setAlignment(FlowLayout.LEFT);
    flowLayout.setVgap(1);
    flowLayout.setHgap(1);
    }

  public void setHolderContent (JComponent expression) {
    expressionHolderUI.setExpression(expression);
    }

  public void setComparison (boolean isComparison) {
    expressionHolderUI.setComparison(isComparison);
    }

  public void setEdition (boolean edit) {
    //try { String str=""; System.err.println(str.charAt(3)); } catch (Exception e) { e.printStackTrace(); }
    if (edit) {
      lockerIcon.setIcon(open);
      expressionHolderUI.enableEdition();
      }
    else {
      lockerIcon.setIcon(closed);
      expressionHolderUI.disableEdition();
      }
    isEditing = edit;
    }

  public boolean isEdition () {
    return isEditing;
    }

  public boolean isBlocked () {
    return isBlocked;
    }

  // Called by: ForUI.initIncrementField(...), IfElseUI.initExpression(...), initExpression(...)
  public void setBlocked (boolean isBlocked) {
    this.isBlocked = isBlocked;
    }

  public void setHoldingType (short type) {
    expressionHolderUI.setHoldingType(type);
    }

  public short getHoldingType () {
    return expressionHolderUI.getHoldingType();
    }

  public void hideMenu (boolean hide) {
    expressionHolderUI.setHideMenu(hide);
    }

  public void setForHeader (boolean b) {
    expressionHolderUI.setForHeader(b);
    }

  public void setForContext (String s) {
    expressionHolderUI.setForContext(s);
    }

  public boolean isContentSet () {
    boolean isCSet = true;
    if (!expressionHolderUI.isCSet()) {
      isCSet = false;
      }
    return isCSet;
    }

  }
