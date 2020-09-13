package controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class HomeGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private final JLabel lblByCiroDe = new JLabel("By de Cristofaro Ciro");
	private JLabel Utenti_lblNewLabel ;
	private JLabel ProgettiAttivi_lblNewLabel;
	private JLabel ProgettiConclusi_lblNewLabel;
	
	/**
	 * Create the frame.
	 */
	public HomeGui(Controller ctrl) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Utenti_lblNewLabel.setText(ctrl.repositorydao.SommaUtenti());
				ProgettiAttivi_lblNewLabel.setText(ctrl.repositorydao.SommaProgettiAttivi());
				ProgettiConclusi_lblNewLabel.setText(ctrl.repositorydao.SommaProgettiConclusi());
			}
		});
		setResizable(false);
		setTitle("Home");
		this.ctrl=ctrl;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 390);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Manager");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Crea Utente");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.ApriCreazioneUtente();
				//ctrl.utenteGui.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Crea Progetto");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.ApriProgetto();
		//	ctrl.progetto.setVisible(true);
			}
		});
		
		JMenuItem mntmNewMenuItem_11 = new JMenuItem("Partecipa progetto");
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ctrl.PartecipaProgetto();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_11);
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Crea Package");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.CreaPackage();
				//ctrl.packageGui.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Crea Classe");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.CreaClasse();
				//ctrl.classeGui.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Aggiungi Metodo");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.CreaMetodo();
				//ctrl.metodoGui.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Chiudi Classe");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.ChiudiClasseGui();
			//	ctrl.chiudiClasseGui.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Chiudi Progetto");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.ChiudiProgettoGui();
				//ctrl.chiudiProgettoGui.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_6);
		
		JMenu mnNewMenu_1 = new JMenu("Test");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Crea test");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.TestGui();
				//sctrl.testGui.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_7);
		
		
	
		
		JMenu mnNewMenu_2 = new JMenu("Visualizza");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Visuallizza Progetto");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.VisualProgettoGui();
				//ctrl.visualProgettoGui.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_8);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Visualizza Classe");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.VisualClasseGui();
				//ctrl.visualClasseGui.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_9);
		
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Visualizza Test Effettuati");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.VisualTestEffettuati();
				//ctrl.visualTestEffetuatiGui.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_10);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(HomeGui.class.getResource("/icone/Repository.png")));
		lblNewLabel.setBounds(10, 22, 192, 192);
		contentPane.add(lblNewLabel);
		lblByCiroDe.setBounds(422, 295, 128, 36);
		contentPane.add(lblByCiroDe);
		
		JTextPane txtpnB = new JTextPane();
		txtpnB.setBackground(Color.WHITE);
		txtpnB.setEditable(false);
		txtpnB.setText("Benvenuto in Repository Software. In quest'area puoi decidere quale operazione svolgere. Cliccando sulla barra in alto a sinistra"
				+ " hai varie opzioni tra cui 'Manager' ,'Test', 'Visualizza'.\n Chiudendo questa finestra il programma verrà chiuso con essa");
		txtpnB.setBounds(223, 36, 321, 184);
		contentPane.add(txtpnB);
		
		JLabel lblNewLabel_1 = new JLabel("Utenti  presenti:");
		lblNewLabel_1.setBounds(10, 247, 95, 13);
		contentPane.add(lblNewLabel_1);
		
		 Utenti_lblNewLabel = new JLabel("");
		 Utenti_lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		 Utenti_lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Utenti_lblNewLabel.setBounds(10, 272, 74, 45);
		contentPane.add(Utenti_lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Progetti attivi:");
		lblNewLabel_2.setBounds(155, 247, 87, 13);
		contentPane.add(lblNewLabel_2);
		
		 ProgettiAttivi_lblNewLabel = new JLabel("");
		 ProgettiAttivi_lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		 ProgettiAttivi_lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ProgettiAttivi_lblNewLabel.setBounds(155, 273, 74, 44);
		contentPane.add(ProgettiAttivi_lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Progetti conclusi:");
		lblNewLabel_3.setBounds(279, 247, 105, 13);
		contentPane.add(lblNewLabel_3);
		
		 ProgettiConclusi_lblNewLabel = new JLabel("");
		 ProgettiConclusi_lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		 ProgettiConclusi_lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ProgettiConclusi_lblNewLabel.setBounds(279, 273, 74, 43);
		contentPane.add(ProgettiConclusi_lblNewLabel);
	}
}
