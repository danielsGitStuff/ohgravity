package gui;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.border.Border;

public class OTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2226994622069296583L;

	@Override
	public void setBorder(Border border) {
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(Color.BLACK);
	}

}
