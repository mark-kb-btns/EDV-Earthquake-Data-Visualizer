package earthquake.catalog.visualizer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;

import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.JPasswordField;

public class LoginPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		setTitle("Earthquake Data Visualizer Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel logInPanel = new JPanel();
		logInPanel.setBackground(new Color(245, 245, 247));
		logInPanel.setBounds(316, 0, 322, 394);
		contentPane.add(logInPanel);
		logInPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("LOG IN");
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
		lblNewLabel_1.setForeground(new Color(29, 29, 31));
		lblNewLabel_1.setBounds(133, 40, 73, 36);
		logInPanel.add(lblNewLabel_1);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Arial", Font.PLAIN, 12));
		usernameField.setForeground(new Color(29, 29, 31));
		usernameField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		usernameField.setBackground(new Color(255, 255, 255));
		usernameField.setBounds(33, 130, 257, 27);
		logInPanel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 12));
		lblUsername.setForeground(new Color(29, 29, 31));
		lblUsername.setBounds(33, 105, 80, 14);
		logInPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(29, 29, 31));
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPassword.setBounds(33, 187, 80, 14);
		logInPanel.add(lblPassword);
		
		JLabel lblNotRegistered = new JLabel("Not registered?");
		lblNotRegistered.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNotRegistered.setForeground(new Color(29, 29, 31));
		lblNotRegistered.setBounds(33, 326, 89, 14);
		logInPanel.add(lblNotRegistered);
		
		JLabel lblSignUp = new JLabel("<html><u>Sign up</u></html>");
		lblSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSignUp.setForeground(new Color(0, 120, 212));
		lblSignUp.setFont(new Font("Arial", Font.PLAIN, 11));
		lblSignUp.setBounds(113, 326, 46, 14);
		logInPanel.add(lblSignUp);
		
		lblSignUp.addMouseListener(new MouseAdapter(){
			@Override
            public void mouseClicked(MouseEvent e) {
               SignUpPage signup = new SignUpPage();
               signup.setVisible(true);
               dispose();
            }
		});
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		passwordField_1.setBounds(33, 212, 257, 27);
		logInPanel.add(passwordField_1);
		
		JPanel appNamePanel = new JPanel();
		appNamePanel.setBackground(new Color(0, 120, 212));
		appNamePanel.setBounds(0, 0, 322, 394);
		contentPane.add(appNamePanel);
		appNamePanel.setLayout(null);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setForeground(new Color(29, 29, 31));
		lblIcon.setBounds(73, 121, 130, 93);
		appNamePanel.add(lblIcon);
		lblIcon.setIcon(new ImageIcon("C:\\Users\\Melo\\OneDrive\\Pictures\\download (1).png"));
		
		JLabel lblNewLabel = new JLabel("EDV");
		lblNewLabel.setForeground(new Color(29, 29, 31));
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 39));
		lblNewLabel.setBounds(105, 193, 98, 45);
		appNamePanel.add(lblNewLabel);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogIn.setBackground(new Color(0, 120, 212));
		btnLogIn.setFont(new Font("Arial", Font.BOLD, 12));
		btnLogIn.setForeground(new Color(29, 29, 31));
		btnLogIn.setBounds(201, 288, 89, 23);
		logInPanel.add(btnLogIn);
		
		btnLogIn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 String username = usernameField.getText();
				 String password = passwordField_1.getText();
				 
				 if (username.equals("") || password.equals("")) {
	                    JOptionPane.showMessageDialog((Component) e.getSource(), "Some fields are empty.", "Error",
	                            JOptionPane.ERROR_MESSAGE);
				 } else {
					 try {
		                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/edvdb1", "root", "");
		                    PreparedStatement pst = connection.prepareStatement("SELECT * FROM login_tbl WHERE username=? AND password=?");
	                        pst.setString(1, username);
	                        pst.setString(2, password);
	                        ResultSet rs = pst.executeQuery(); 
	                        
	                        if(rs.next()) {
	                        	String username1 = rs.getString("username");
	                            String password1 = rs.getString("password");

	                            if (username.equals(username1) && password.equals(password1)) {
	                                JOptionPane.showMessageDialog((Component) e.getSource(), "Successfully logged in!",
	                                        "Success", JOptionPane.INFORMATION_MESSAGE);
	                                Main page = new Main();
	                                page.frmEarthquakeDataVisualizer.setVisible(true);
	                                LoginPage.this.dispose(); // Close the login page
	                                page.validate(); // Validate the components
	                                page.repaint(); // Repaint the frame
	                            } else {
	                                JOptionPane.showMessageDialog((Component) e.getSource(),
	                                        "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
	                            }
	                        } else {
	                            JOptionPane.showMessageDialog((Component) e.getSource(), "Invalid username or password.",
	                                    "Error", JOptionPane.ERROR_MESSAGE);
	                        }
	                        
				 } catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					 JOptionPane.showMessageDialog((Component) e.getSource(), "Error in establishing connection.",
                             "Error", JOptionPane.ERROR_MESSAGE);
				} 

			 }
			 }
		});
		
	}
}
	
