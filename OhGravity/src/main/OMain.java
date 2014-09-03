package main;

import gui.OGravityWindow;

import java.awt.EventQueue;

public class OMain {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unused")
			public void run() {
				try {
					OGravityWindow window = new OGravityWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
