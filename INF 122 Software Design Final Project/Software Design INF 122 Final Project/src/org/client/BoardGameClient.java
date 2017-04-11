package org.client;

import java.awt.EventQueue;

public class BoardGameClient
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI gui = new ClientGUI();
					gui.makeVisible();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
