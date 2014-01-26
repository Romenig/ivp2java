package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.IOperationListener;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;

public abstract class OperationUI extends JPanel implements IDomainObjectUI, IOperationListener {

	private JLabel leftPar;
	protected ExpressionHolderUI expressionBaseUI_1;
	protected JLabel expSign;
	protected ExpressionHolderUI expressionBaseUI_2;
	private JLabel rightPar;
	protected String currentModelID;
	protected String parentModelID;
	protected String scopeModelID;
	protected String context;
	private JPanel expPanel;
	private boolean drawBorder = false;
	private boolean isEditing = false;
	protected JPopupMenu operationSignMenu;

	public OperationUI(String parent, String scope, String id) {
		currentModelID = id;
		scopeModelID = scope;
		parentModelID = parent;
		context = "";
		setOpaque(false);
		initLayout();
		initComponents();
		initOperationSignMenu();
		initSignal();
		Services.getService().getController().getProgram().addOperationListener(this);
	}
	
	public abstract void initOperationSignMenu();
	public abstract void initSignal(); 

	protected void initComponents() {
		initLeftParenthesis();
		initExpressionHolder1();
		initExpressionSign();
		initExpressionHolder2();
		initRightParenthesis();
	}

	private void initRightParenthesis() {
		rightPar = new JLabel(")");
		rightPar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(rightPar);
	}

	private void initExpressionHolder2() {
		expressionBaseUI_2 = new ExpressionHolderUI(currentModelID, scopeModelID);
		expressionBaseUI_2.setOperationContext("right");
		if(isEditing){
			expressionBaseUI_2.enableEdition();
		}else{
			expressionBaseUI_2.disableEdition();
		}
		add(expressionBaseUI_2);
	}

	private void initExpressionSign() {
		expPanel = new JPanel();
		expPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		expPanel.addMouseListener(new OperationMouseListener(expPanel));
		add(expPanel);
		expSign = new JLabel();
		expPanel.add(expSign);
		expSign.setFont(new Font("Tahoma", Font.PLAIN, 10));
	}

	private void initExpressionHolder1() {
		expressionBaseUI_1 = new ExpressionHolderUI(currentModelID, scopeModelID);
		expressionBaseUI_1.setOperationContext("left");
		if(isEditing){
			expressionBaseUI_1.enableEdition();
		}else{
			expressionBaseUI_1.disableEdition();
		}
		add(expressionBaseUI_1);
	}

	private void initLeftParenthesis() {
		leftPar = new JLabel("(");
		leftPar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(leftPar);
	}

	protected void initLayout() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(3);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
	}

	public ExpressionHolderUI getExpressionBaseUI_1() {
		return expressionBaseUI_1;
	}

	public void setExpressionBaseUI_1(JComponent expressionBaseUI_1) {
		this.expressionBaseUI_1.setExpression(expressionBaseUI_1);
		revalidate();
		repaint();
	}

	public ExpressionHolderUI getExpressionBaseUI_2() {
		return expressionBaseUI_2;
	}

	public void setExpressionBaseUI_2(JComponent expressionBaseUI_2) {
		this.expressionBaseUI_2.setExpression(expressionBaseUI_2);
		revalidate();
		repaint();
	}

	public String getModelID() {
		return currentModelID;
	}

	public String getModelParent() {
		return parentModelID;
	}

	public String getModelScope() {
		return scopeModelID;
	}

	public void setModelID(String id) {
		currentModelID = id;
		initSignal();
	}

	public void setModelParent(String id) {
		parentModelID = id;
	}

	public void setModelScope(String id) {
		scopeModelID = id;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}
	
	class OperationMouseListener implements MouseListener {
		private JPanel panel;
		public OperationMouseListener(JPanel p){ panel = p; }
		public void mouseClicked(MouseEvent arg0) { 
			if(isEditing){
				operationSignMenu.show(panel, 0, panel.getHeight());
				operationSignMenu.requestFocus();
			}
		}
		public void mouseEntered(MouseEvent e) {
			if(isEditing){
				panel.setBackground(FlatUIColors.HOVER_COLOR);
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}
		public void mouseExited(MouseEvent e) {
			if(isEditing){
				panel.setBackground(FlatUIColors.CODE_BG);
				e.getComponent().setCursor(Cursor.getDefaultCursor());
			}
		}
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
	}
	
	public void enableEdition(){
		isEditing = true;
		expressionBaseUI_1.enableEdition();
		expressionBaseUI_2.enableEdition();
	}
	
	public void disableEdition(){
		isEditing = false;
		expressionBaseUI_1.disableEdition();
		expressionBaseUI_2.disableEdition();
	}
	
	public void warningStateOn(){
		if(Services.getService().getViewMapping().get(parentModelID) instanceof ExpressionHolderUI){
			((ExpressionHolderUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
		}else if (Services.getService().getViewMapping().get(parentModelID) instanceof OperationUI){
			((OperationUI) Services.getService().getViewMapping().get(parentModelID)).warningStateOn();
		}else if(getParent() instanceof ExpressionHolderUI) {
			((ExpressionHolderUI)getParent()).warningStateOn();
		}else{
			enableEdition();
		}
	}

}