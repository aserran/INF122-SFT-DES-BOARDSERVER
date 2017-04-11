package org.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutWindow extends JFrame {
	private static final long serialVersionUID = -5257954002998646695L;

	private static final String teamNamesText 
			= "Francisco Arca<br>Solomon Chan<br>Vincent Chang<br>Matthew Frazer<br>"
					+ "Kent Han<br>Isaac Pak<br>Anthony Serrano<br>Dennis Tian<br>Minhtri Tran";

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutWindow() {
		setTitle("About");
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
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.ipady = 5;
		gbc_panel.ipadx = 5;
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblAbout = new JLabel("<html>Informatics 122 Final Project Group 6</html>", SwingConstants.CENTER);
		lblAbout.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.add(lblAbout);
		
		JLabel lblTeamNames = new JLabel("<html><div style='text-align': center;'>" + teamNamesText + "</div></html>", SwingConstants.CENTER);
		GridBagConstraints gbc_lblTeamNames = new GridBagConstraints();
		gbc_lblTeamNames.ipady = 5;
		gbc_lblTeamNames.ipadx = 5;
		gbc_lblTeamNames.fill = GridBagConstraints.BOTH;
		gbc_lblTeamNames.insets = new Insets(0, 0, 5, 0);
		gbc_lblTeamNames.gridx = 0;
		gbc_lblTeamNames.gridy = 1;
		contentPane.add(lblTeamNames, gbc_lblTeamNames);
		
		JButton btnAboutOkButton = new JButton("Close");
		btnAboutOkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				closeThisWindow();
			}
		});
		GridBagConstraints gbc_btnAboutOkButton = new GridBagConstraints();
		gbc_btnAboutOkButton.ipady = 5;
		gbc_btnAboutOkButton.ipadx = 5;
		gbc_btnAboutOkButton.anchor = GridBagConstraints.SOUTH;
		gbc_btnAboutOkButton.gridx = 0;
		gbc_btnAboutOkButton.gridy = 2;
		contentPane.add(btnAboutOkButton, gbc_btnAboutOkButton);
		
		pack();
	}
	
	private void closeThisWindow() {
		this.dispose();
	}

}
