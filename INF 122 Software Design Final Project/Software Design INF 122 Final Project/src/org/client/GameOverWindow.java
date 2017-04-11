package org.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverWindow extends JFrame {

	private static final long serialVersionUID = -6789240568747827270L;
	private JPanel contentPane;
	
	public GameOverWindow(String message) {
		setTitle("Game over");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{288, 0};
		gbl_contentPane.rowHeights = new int[]{15, 119, 25, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel mainPanel = new JPanel();
		GridBagConstraints gbc_mainPanel = new GridBagConstraints();
		gbc_mainPanel.ipady = 5;
		gbc_mainPanel.ipadx = 5;
		gbc_mainPanel.anchor = GridBagConstraints.NORTH;
		gbc_mainPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_mainPanel.insets = new Insets(0, 0, 5, 0);
		gbc_mainPanel.gridx = 0;
		gbc_mainPanel.gridy = 0;
		contentPane.add(mainPanel, gbc_mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		JLabel lblGameOverMessage = new JLabel("<html><div style='text-align': center;'>" + message + "</div></html>", SwingConstants.CENTER);
		GridBagConstraints gbc_lblGameOverMessage = new GridBagConstraints();
		gbc_lblGameOverMessage.ipady = 5;
		gbc_lblGameOverMessage.ipadx = 5;
		gbc_lblGameOverMessage.fill = GridBagConstraints.BOTH;
		gbc_lblGameOverMessage.insets = new Insets(0, 0, 5, 0);
		gbc_lblGameOverMessage.gridx = 0;
		gbc_lblGameOverMessage.gridy = 1;
		contentPane.add(lblGameOverMessage, gbc_lblGameOverMessage);
		
		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				closeThisWindow();
			}
		});
		GridBagConstraints gbc_btnCloseButton = new GridBagConstraints();
		gbc_btnCloseButton.ipady = 5;
		gbc_btnCloseButton.ipadx = 5;
		gbc_btnCloseButton.anchor = GridBagConstraints.SOUTH;
		gbc_btnCloseButton.gridx = 0;
		gbc_btnCloseButton.gridy = 2;
		contentPane.add(btnCloseButton, gbc_btnCloseButton);
		
		pack();
	}
	
	private void closeThisWindow() {
		this.dispose();
	}
}
