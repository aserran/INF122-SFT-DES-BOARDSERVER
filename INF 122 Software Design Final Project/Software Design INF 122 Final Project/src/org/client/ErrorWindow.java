package org.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ErrorWindow extends JFrame {
	private static final long serialVersionUID = -2449580429747186344L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ErrorWindow(String errorMessage) {
		setMinimumSize(new Dimension(200, 50));
		setMaximumSize(new Dimension(200, 800));
		setResizable(false);
		setTitle("Error");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 230, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblErrormessage = new JLabel("<html>" + errorMessage + "</html>");
		GridBagConstraints gbc_lblErrormessage = new GridBagConstraints();
		gbc_lblErrormessage.ipady = 5;
		gbc_lblErrormessage.ipadx = 5;
		gbc_lblErrormessage.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrormessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrormessage.gridx = 0;
		gbc_lblErrormessage.gridy = 1;
		contentPane.add(lblErrormessage, gbc_lblErrormessage);
		
		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				closeThisWindow();
			}
		});
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.anchor = GridBagConstraints.SOUTH;
		gbc_btnClose.insets = new Insets(0, 0, 5, 0);
		gbc_btnClose.gridx = 0;
		gbc_btnClose.gridy = 2;
		contentPane.add(btnClose, gbc_btnClose);
		
		pack();
	}
	
	private void closeThisWindow() {
		this.dispose();
	}

}
