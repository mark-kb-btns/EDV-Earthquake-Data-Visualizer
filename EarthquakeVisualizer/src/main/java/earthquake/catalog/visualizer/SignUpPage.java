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

import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;

import com.mysql.cj.xdevapi.Statement;

import javax.swing.border.BevelBorder;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class SignUpPage extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField retypePasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpPage frame = new SignUpPage();
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
	public SignUpPage() {
		setTitle("Earthquake Data Visualizer Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel signUpPanel = new JPanel();
		signUpPanel.setBackground(new Color(245, 245, 247));
		signUpPanel.setBounds(313, 0, 322, 394);
		contentPane.add(signUpPanel);
		signUpPanel.setLayout(null);
		
		JLabel lblSignUp = new JLabel("SIGN UP");
		lblSignUp.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
		lblSignUp.setForeground(new Color(29, 29, 31));
		lblSignUp.setBounds(125, 38, 98, 36);
		signUpPanel.add(lblSignUp);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 12));
		lblUsername.setForeground(new Color(29, 29, 31));
		lblUsername.setBounds(33, 86, 80, 14);
		signUpPanel.add(lblUsername);
		
		usernameField = new JTextField();
		usernameField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		usernameField.setBounds(33, 111, 257, 27);
		signUpPanel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPassword.setForeground(new Color(29, 29, 31));
		lblPassword.setBounds(33, 156, 66, 14);
		signUpPanel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		passwordField.setBounds(33, 181, 257, 27);
		signUpPanel.add(passwordField);
		
		retypePasswordField = new JPasswordField();
		retypePasswordField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		retypePasswordField.setBounds(33, 244, 257, 27);
		signUpPanel.add(retypePasswordField);
		
		JLabel lblRetypePassword = new JLabel("Re-type Password");
		lblRetypePassword.setForeground(new Color(29, 29, 31));
		lblRetypePassword.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRetypePassword.setBounds(33, 219, 116, 14);
		signUpPanel.add(lblRetypePassword);
		
		JLabel lblAlreadyRegistered = new JLabel("Already registered?");
		lblAlreadyRegistered.setForeground(new Color(29, 29, 31));
		lblAlreadyRegistered.setFont(new Font("Arial", Font.PLAIN, 11));
		lblAlreadyRegistered.setBounds(33, 331, 104, 14);
		signUpPanel.add(lblAlreadyRegistered);
		
		JLabel lblSignUp_1 = new JLabel("<html><u>Log in</u></html>");
		lblSignUp_1.setForeground(new Color(0, 120, 212));
		lblSignUp_1.setFont(new Font("Arial", Font.PLAIN, 11));
		lblSignUp_1.setBounds(136, 331, 46, 14);
		signUpPanel.add(lblSignUp_1);
		
		lblSignUp_1.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
               LoginPage login = new LoginPage();
               login.setVisible(true);
               dispose();
            }
		});
		
		JPanel appNamePanel = new JPanel();
		appNamePanel.setBackground(new Color(0, 120, 212));
		appNamePanel.setBounds(0, 0, 322, 394);
		contentPane.add(appNamePanel);
		appNamePanel.setLayout(null);
		
		JLabel lblICon = new JLabel("");
		lblICon.setIcon(new ImageIcon("C:\\Users\\Melo\\OneDrive\\Pictures\\download (1).png"));
		lblICon.setBounds(73, 121, 130, 93);
		appNamePanel.add(lblICon);
		
		JLabel lblName = new JLabel("EDV");
		lblName.setForeground(new Color(29, 29, 31));
		lblName.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 39));
		lblName.setBounds(105, 193, 98, 45);
		appNamePanel.add(lblName);
		
		final JButton btnRegister = new JButton("Register");
		btnRegister.setBackground(new Color(0, 120, 212));
		btnRegister.setFont(new Font("Arial", Font.BOLD, 12));
		btnRegister.setForeground(new Color(29, 29, 31));
		btnRegister.setBounds(201, 295, 89, 23);
		signUpPanel.add(btnRegister);
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				String retypePassword = retypePasswordField.getText();
				
				//System.out.println(password);
				//System.out.println(retypePassword);
				
				if (username.equals("") || password.equals("") || retypePassword.equals("")) {
                    JOptionPane.showMessageDialog((Component) e.getSource(), "Some fields are empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
			 } else if (!password.equals(retypePassword)) {
				    JOptionPane.showMessageDialog((Component) e.getSource(), "Passwords don't match!", "Error",
				            JOptionPane.ERROR_MESSAGE);
				} else {
				try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/edvdb1", "root", "");

                    String query = "INSERT INTO login_tbl (username, password) VALUES ('" + username + "', '" + password + "')";

                    java.sql.Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(btnRegister, "This is alredy exist");
                    } else {
                        JOptionPane.showMessageDialog(btnRegister,
                            "Welcome! "
                            + "Your account is sucessfully created.");
                        LoginPage login = new LoginPage();
                        login.setVisible(true);
                        dispose();
                    }
                    connection.close();
                } catch (Exception exception) {
                    //exception.printStackTrace();
                	JOptionPane.showMessageDialog((Component) e.getSource(), "Error in establishing connection.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
			}
			}
			
		});
		
		
	}

}
