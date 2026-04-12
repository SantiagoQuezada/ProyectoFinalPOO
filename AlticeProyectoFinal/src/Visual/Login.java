package Visual;

import Logico.Altice;
import Logico.Empleado;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

	private JTextField txtUsername;
	private JPasswordField txtPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("Altice - Inicio de Sesión");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(15, 15, 15));
		headerPanel.setPreferredSize(new Dimension(400, 70));
		headerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

		JLabel lblLogo = new JLabel("<html><span style='font-size:24px; font-family: Arial;'><b>\u221E Altice</b></span></html>");
		lblLogo.setForeground(Color.WHITE);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(lblLogo, BorderLayout.CENTER);

		add(headerPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

		JLabel lblTitulo = new JLabel("Iniciar Sesión");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitulo.setForeground(new Color(30, 30, 30));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblTitulo);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		JLabel lblUser = new JLabel("Usuario del Sistema:");
		lblUser.setFont(new Font("Arial", Font.BOLD, 13));
		lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
		centerPanel.add(lblUser);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		txtUsername = new JTextField();
		txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		centerPanel.add(txtUsername);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setFont(new Font("Arial", Font.BOLD, 13));
		lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);
		centerPanel.add(lblPass);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		txtPassword = new JPasswordField();
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		centerPanel.add(txtPassword);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		JButton btnLogin = new JButton("Ingresar");
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setBackground(new Color(15, 15, 15));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFocusPainted(false);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());

				Empleado empleadoLogueado = Altice.getInstance().validarLogin(username, password);

				if (empleadoLogueado != null) {
					Principal principal = new Principal(empleadoLogueado);
					principal.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		centerPanel.add(btnLogin);
		add(centerPanel, BorderLayout.CENTER);
	}
}