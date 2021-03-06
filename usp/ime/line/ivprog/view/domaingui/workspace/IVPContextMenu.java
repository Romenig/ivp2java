
/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.view.domaingui.workspace;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.model.utils.IVPConstants;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class IVPContextMenu extends RoundedJPanel {

  private static final long serialVersionUID = 3814837809047109772L;
  private IVPContainer container = null;
  private JPanel btnPanel;
  private JPanel menuPanel;
  private JPanel buttonsContainer;
  private JButton plusBtn;
  private JPopupMenu menu;
  private String context;

  // Start preparation to "context menu"
  public IVPContextMenu (IVPContainer c, String context) {
    this.context = context;
    container = c;
    initialization();
    initPanels();
    initPopupMenu();
    initPlusBtn();
    }

  private void initPopupMenu () {
    menu = new JPopupMenu();
    menu.setBackground(FlatUIColors.MAIN_BG);
    Action createWhile = new AbstractAction () {
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_MODEL_WHILE;");
        Services.getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_WHILE, context);
        }
      };
    createWhile.putValue(Action.SMALL_ICON, new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/loop_while.png")));
    createWhile.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPContextMenu.while.tip"));
    createWhile.putValue(Action.NAME, ResourceBundleIVP.getString("IVPContextMenu.while.text"));
    Action createfOR = new AbstractAction () {
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_MODEL_FOR;");
        Services.getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_FOR, context);
        }
      };
    createfOR.putValue(Action.SMALL_ICON, new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/loop-n.png")));
    createfOR.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPContextMenu.for.tip"));
    createfOR.putValue(Action.NAME, ResourceBundleIVP.getString("IVPContextMenu.for.text"));
    Action createifElse = new AbstractAction () {
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_MODEL_IFELSE;");
        Services.getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_IFELSE, context);
        }
      };
    createifElse.putValue(Action.SMALL_ICON, new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/if.png")));
    createifElse.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPContextMenu.if.tip"));
    createifElse.putValue(Action.NAME, ResourceBundleIVP.getString("IVPContextMenu.if.text"));
    Action createRead = new AbstractAction () {
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_MODEL_READ;");
        Services.getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_READ, context);
        }
      };
    createRead.putValue(Action.SMALL_ICON,
            new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/incoming.png")));
    createRead.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPContextMenu.read.tip"));
    createRead.putValue(Action.NAME, ResourceBundleIVP.getString("IVPContextMenu.read.text"));
    Action createPrint = new AbstractAction () {
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_MODEL_WRITE;");
        Services.getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_WRITE, context);
        }
      };
    createPrint.putValue(Action.SMALL_ICON, new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/outcoming.png")));
    createPrint.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPContextMenu.escrita.tip"));
    createPrint.putValue(Action.NAME, ResourceBundleIVP.getString("IVPContextMenu.escrita.text"));
    Action createAtt = new AbstractAction () {
      public void actionPerformed (ActionEvent e) {
        Tracking.track("event=CLICK;where=BTN_MODEL_ATTLINE;");
        Services.getController().addChild(container.getCodeComposite(), IVPConstants.MODEL_ATTLINE, context);
        }
      };
    createAtt.putValue(Action.SMALL_ICON, new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/att.png")));
    createAtt.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPContextMenu.att.tip"));
    createAtt.putValue(Action.NAME, ResourceBundleIVP.getString("IVPContextMenu.att.text")); // "x := 2"
    menu.add(createAtt);
    menu.add(createWhile);
    menu.add(createfOR);
    menu.add(createifElse);
    menu.add(createRead);
    menu.add(createPrint);
    }

  private void initialization () {
    setBorder(new EmptyBorder(2, 2, 2, 2));
    setLayout(new BorderLayout(0, 0));
    setBackground(FlatUIColors.MAIN_BG);
    }

  private void initPanels () {
    btnPanel = new JPanel();
    btnPanel.setBackground(FlatUIColors.MAIN_BG);
    btnPanel.setLayout(new BorderLayout());
    add(btnPanel, BorderLayout.WEST);
    menuPanel = new JPanel();
    menuPanel.setBackground(FlatUIColors.MAIN_BG);
    menuPanel.setLayout(new BorderLayout());
    add(menuPanel, BorderLayout.CENTER);
    buttonsContainer = new JPanel();
    buttonsContainer.setVisible(false);
    buttonsContainer.setBackground(FlatUIColors.MAIN_BG);
    buttonsContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
    menuPanel.revalidate();
    menuPanel.repaint();
    menuPanel.add(buttonsContainer);
    }

  // Class to manage button to "add new command"
  // " + command"
  private void initPlusBtn () {
    Action action = new AbstractAction () {
      public void actionPerformed(ActionEvent e) {
        // Services.getController().addVariable(scopeID, "1"); //TODO the similar one use to "add variable" (in 'usp/ime/line/ivprog/view/domaingui/variables/IVPVariablePanel.java')
        Tracking.track("event=CLICK;where=BTN_OPEN_CODE_MENU;");
        menu.show(plusBtn, 0, plusBtn.getHeight());
        }
     };
    // Button to add "new command"
    action.putValue(Action.SMALL_ICON, new ImageIcon(IVPContextMenu.class.getResource("/usp/ime/line/resources/icons/add_main_command.png")));
    action.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("IVPContextMenu.insert.command")); // tooltip "Add new command (as "read, write or repeat")
    plusBtn = new JButton(action);
    plusBtn.setUI(new IconButtonUI());

    btnPanel.add(plusBtn);
    }

  private void hideMenu () {
    Runnable r = new Runnable () {
      public void run () {
        int delay = 1;
        ((JComponent) getParent()).revalidate();
        ((JComponent) getParent()).repaint();
        for (int i = 0; i > -menuPanel.getWidth(); i--) {
          try {
            Thread.sleep(delay);
            movePanel(new Point(i, 0));
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
          }
        buttonsContainer.setVisible(false);
        plusBtn.setEnabled(true);
        }
      };
    Thread t = new Thread(r);
    t.start();
    }

  private void movePanel (final Point p) {
    SwingUtilities.invokeLater(new Runnable () {
      public void run () {
        buttonsContainer.setLocation(p);
        }
      });
    }

  }
