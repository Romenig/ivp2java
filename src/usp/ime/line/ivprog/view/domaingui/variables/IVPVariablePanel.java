package usp.ime.line.ivprog.view.domaingui.variables;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import com.l2fprod.common.demo.TaskPaneMain;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.controller.IVPController;
import usp.ime.line.ivprog.listeners.IVariableListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.model.utils.IVPVariableMap;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.FunctionBodyUI;
import usp.ime.line.ivprog.view.utils.BlueishButtonUI;
import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;

public class IVPVariablePanel extends JPanel implements IVariableListener {

	private static final long serialVersionUID = -2214975678822644250L;
	private JPanel container;
	private JButton addVarBtn;
	private RoundedJPanel varPanel;
	private JButton addParamBtn;
	private RoundedJPanel paramPanel;
	private String scopeID;
	private Vector variableList;
	private Vector paramList;
	
	private TreeMap varMap;
	private TreeMap paramMap;
	
	public IVPVariablePanel(String scopeID, boolean isMain) {
		setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		this.scopeID = scopeID;
		initialization(isMain);
		Services.getService().getController().getProgram().addVariableListener(this);
	}

	private void initialization(boolean isMain) {
		initVectors();
		initLayout();
		initContainer();
		if (!isMain) {
			initParamBtn();
			initParamPanel();
		}
		initAddVarBtn();
		initVarPanel();
	}

	private void initVectors() {
		variableList = new Vector();
		paramList = new Vector();
		varMap = new TreeMap();
		paramMap = new TreeMap();
	}

	private void initLayout() {
		setBackground(FlatUIColors.MAIN_BG);
		setLayout(new BorderLayout(0, 0));
	}

	private void initParamPanel() {
		paramPanel = new RoundedJPanel();
		paramPanel.setLayout(new DynamicFlowLayout(FlowLayout.LEFT, paramPanel,
				paramPanel.getClass(), 1));
		GridBagConstraints gbc_paramPanel = new GridBagConstraints();
		gbc_paramPanel.insets = new Insets(2, 0, 2, 0);
		gbc_paramPanel.fill = GridBagConstraints.BOTH;
		gbc_paramPanel.gridx = 1;
		gbc_paramPanel.gridy = 1;
		container.add(paramPanel, gbc_paramPanel);
	}

	private void initParamBtn() {
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().addParameter(scopeID);
			}
		};
		action.putValue(Action.SMALL_ICON,new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/plus_param.png")));
		action.putValue(Action.SHORT_DESCRIPTION,"Adiciona um novo par�metro � fun��o:" + "Principal");
		addParamBtn = new JButton(action);
		addParamBtn.setHorizontalAlignment(SwingConstants.LEFT);
		addParamBtn.setUI(new IconButtonUI());
		addParamBtn.setPreferredSize(new Dimension(95, 25));
		GridBagConstraints gbc_addParamBtn = new GridBagConstraints();
		gbc_addParamBtn.anchor = GridBagConstraints.WEST;
		gbc_addParamBtn.insets = new Insets(3, 3, 3, 3);
		gbc_addParamBtn.gridx = 0;
		gbc_addParamBtn.gridy = 1;
		container.add(addParamBtn, gbc_addParamBtn);
	}

	private void initVarPanel() {
		varPanel = new RoundedJPanel();
		varPanel.setLayout(new DynamicFlowLayout(FlowLayout.LEFT, varPanel, varPanel.getClass(), 1));
		GridBagConstraints gbc_varPanel = new GridBagConstraints();
		gbc_varPanel.insets = new Insets(5, 5, 5, 5);
		gbc_varPanel.fill = GridBagConstraints.BOTH;
		gbc_varPanel.gridx = 1;
		gbc_varPanel.gridy = 0;
		container.add(varPanel, gbc_varPanel);
	}

	private void initAddVarBtn() {
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Services.getService().getController().addVariable(scopeID);
			}
		};
		action.putValue(Action.SMALL_ICON, new ImageIcon(IVPVariablePanel.class.getResource("/usp/ime/line/resources/icons/plus_var.png")));
		action.putValue(Action.SHORT_DESCRIPTION, "Adiciona um novo par�metro � fun��o:" + "Principal");
		addVarBtn = new JButton(action);
		addVarBtn.setUI(new IconButtonUI());
		GridBagConstraints gbc_addVarBtn = new GridBagConstraints();
		gbc_addVarBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_addVarBtn.insets = new Insets(3, 3, 3, 3);
		gbc_addVarBtn.gridy = 0;
		container.add(addVarBtn, gbc_addVarBtn);
	}

	private void initContainer() {
		container = new JPanel();
		container.setBorder(new EmptyBorder(0, 0, 5, 0));
		container.setOpaque(false);
		add(container, BorderLayout.CENTER);
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[] { 0, 0, 0 };
		gbl_container.rowHeights = new int[] { 0, 0, 0 };
		gbl_container.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_container.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		container.setLayout(gbl_container);
	}
	
	public void repaintVarPanel(){
		varPanel.removeAll();
		Object[] keySetArray = varMap.keySet().toArray();
		for(int i = 0; i < keySetArray.length; i++){
			Component variableUI = (Component) varMap.get(keySetArray[i]);
			if(variableUI != null)
				varPanel.add(variableUI);
		}
		varPanel.revalidate();
		varPanel.repaint();
	}
	
	public void repaintParamPanel(){
		paramPanel.removeAll();
		String[] keySetArray =  (String[]) paramMap.keySet().toArray();
		for(int i = 0; i < keySetArray.length; i++){
			Component variableUI = (Component) paramMap.get(keySetArray[i]);
			if(variableUI != null)
				paramPanel.add(variableUI);
		}
		paramPanel.revalidate();
		paramPanel.repaint();
	}

	public void addedVariable(String id) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getRenderer().paint(id);
		varMap.put(id, variable);
		repaintVarPanel();
	}

	public void changeVariable(String id) {
		
	}

	public void removedVariable(String id) {
		varMap.put(id, null);
		repaintVarPanel();
	}

	public void changeVariableName(String id, String name, String lastName) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		if (variable != null) {
			variable.setVariableName(name);
		}

	}

	public void changeVariableValue(String id, String value) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		if(variable!=null){
			variable.setVariableValue(value);
		}
	}

	public void changeVariableType(String id, short type) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		if(variable!=null){
			variable.setVariableType(type);
		}
	}

	public void variableRestored(String id) {
		IVPVariableBasic variable = (IVPVariableBasic) Services.getService().getViewMapping().get(id);
		varMap.put(id, variable);
		repaintVarPanel();
	}

	public void updateReference(String id) {}
	
}
