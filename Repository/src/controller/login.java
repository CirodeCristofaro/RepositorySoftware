package controller;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class login extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTextField host_textField;
	private JTextField Porta_textField;
	private JTextField Servizio_textField;
	private JTextField Utente_textField;
	private JTextField Schema_textField;
	private JPasswordField Password_passwordField;
	public login(Controller ctrl) {
		setResizable(false);
		setTitle("Login");
		this.ctrl=ctrl;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Host");
		lblNewLabel.setBounds(10, 24, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Porta");
		lblNewLabel_1.setBounds(10, 47, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Servizio");
		lblNewLabel_2.setBounds(10, 70, 65, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Utente");
		lblNewLabel_3.setBounds(10, 93, 45, 13);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Schema");
		lblNewLabel_4.setBounds(10, 116, 45, 13);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("password");
		lblNewLabel_5.setBounds(10, 143, 65, 13);
		contentPane.add(lblNewLabel_5);
		
		host_textField = new JTextField();
		host_textField.setText("localhost");
		host_textField.setBounds(70, 21, 253, 19);
		contentPane.add(host_textField);
		host_textField.setColumns(10);
		
		Porta_textField = new JTextField();
		Porta_textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String regex="[0-9]+";
				Pattern p = Pattern.compile(regex);
				if(!p.matches(regex, Porta_textField.getText())) {
					Porta_textField.setText("");
				}
			}
		});
		
		Porta_textField.setText("1521");
		Porta_textField.setBounds(70, 44, 253, 19);
		contentPane.add(Porta_textField);
		Porta_textField.setColumns(10);
		
		Servizio_textField = new JTextField();
		Servizio_textField.setText("xe");
		Servizio_textField.setBounds(70, 67, 253, 19);
		contentPane.add(Servizio_textField);
		Servizio_textField.setColumns(10);
		
		Utente_textField = new JTextField();
		Utente_textField.setText("utente");
		Utente_textField.setBounds(70, 90, 253, 19);
		contentPane.add(Utente_textField);
		Utente_textField.setColumns(10);
		
		Schema_textField = new JTextField();
		Schema_textField.setText("REPOSITORYSOFTWARE");
		Schema_textField.setBounds(70, 113, 253, 19);
		contentPane.add(Schema_textField);
		Schema_textField.setColumns(10);
		

		Password_passwordField = new JPasswordField("123");
		Password_passwordField.setToolTipText("");
		Password_passwordField.setBounds(70, 141, 253, 17);
		contentPane.add(Password_passwordField);
		
		
		JButton Login_btnNewButton = new JButton("Accedi");
		Login_btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int porta=Integer.parseInt(Porta_textField.getText());
				String password=new String(Password_passwordField.getPassword());
				ctrl.ConnessioneEffettuata(host_textField.getText(),porta, Servizio_textField.getText(), Utente_textField.getText(), Schema_textField.getText(), password);
				
			}
		});
		Login_btnNewButton.setBounds(227, 173, 85, 21);
		contentPane.add(Login_btnNewButton);
		
	}
}
