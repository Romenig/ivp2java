package usp.ime.line.ivprog.view.domaingui;

import javax.swing.JPanel;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariablePanel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class IVPFunctionBody extends JPanel {
	
	private static final long serialVersionUID = -1559611466195605109L;
	private String name = null;
	private short type = -1;
	private IVPVariablePanel variablesPanel;
	private IVPContainer canvas;
	private Function function = null;
	
	public IVPFunctionBody(Function function, boolean isMain) {
		setLayout(new BorderLayout(0, 0));
		variablesPanel = new IVPVariablePanel(function, isMain);
		add(variablesPanel, BorderLayout.NORTH);
		canvas = new IVPContainer(false, function);
		add(canvas, BorderLayout.CENTER);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public Function getDataFunction() {
		return function;
	}

	public void setDataFunction(Function function) {
		this.function = function;
	}

	
}