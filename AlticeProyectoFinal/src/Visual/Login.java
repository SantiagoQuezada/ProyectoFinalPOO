package Visual;

import Logico.Altice;
import Logico.Empleado;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.EventQueue;

public class Login extends JFrame {

	private RoundedTextField txtUsername;
	private RoundedPasswordField txtPassword;

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
		setTitle("Altice - Iniciar Sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 650);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(10, 10, 10));
		setLayout(new BorderLayout());

		JPanel wrapperPanel = new JPanel(new GridBagLayout());
		wrapperPanel.setBackground(new Color(10, 10, 10));

		RoundedPanel cardPanel = new RoundedPanel(40);
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		cardPanel.setBackground(Color.WHITE);
		cardPanel.setPreferredSize(new Dimension(400, 520));
		cardPanel.setBorder(new EmptyBorder(45, 40, 45, 40));

		JLabel lblLogo = new JLabel("\u221E Altice");
		lblLogo.setFont(new Font("Arial", Font.BOLD, 52));
		lblLogo.setForeground(new Color(10, 10, 10));
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Sistema de Gestión Interna");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(120, 120, 120));
		lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBackground(Color.WHITE);
		formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblUser = new JLabel("Usuario de Sistema");
		lblUser.setFont(new Font("Arial", Font.BOLD, 14));
		lblUser.setForeground(new Color(30, 30, 30));
		lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

		txtUsername = new RoundedTextField(20);
		txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
		txtUsername.setPreferredSize(new Dimension(320, 45));
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtPassword.requestFocus();
				}
			}
		});

		JLabel lblPass = new JLabel("Contraseña");
		lblPass.setFont(new Font("Arial", Font.BOLD, 14));
		lblPass.setForeground(new Color(30, 30, 30));
		lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

		txtPassword = new RoundedPasswordField(20);
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
		txtPassword.setPreferredSize(new Dimension(320, 45));
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

		formPanel.add(lblUser);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		formPanel.add(txtUsername);
		formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		formPanel.add(lblPass);
		formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		formPanel.add(txtPassword);

		RoundedButton btnLogin = new RoundedButton("INICIAR SESIÓN", 25);
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setBackground(new Color(10, 10, 10));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 15));
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		btnLogin.setPreferredSize(new Dimension(320, 50));

		ActionListener loginAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = txtUsername.getText();
				String pass = new String(txtPassword.getPassword());

				if (user.isEmpty() || pass.isEmpty()) {
					JOptionPane.showMessageDialog(Login.this, "Por favor, ingrese sus credenciales.", "Atención", JOptionPane.WARNING_MESSAGE);
					return;
				}

				Empleado empleado = Altice.getInstance().validarLogin(user, pass);

				if (empleado != null) {
					Principal principal = new Principal(empleado);
					principal.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
				}
			}
		};

		btnLogin.addActionListener(loginAction);

		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginAction.actionPerformed(null);
				}
			}
		});

		cardPanel.add(Box.createVerticalGlue());
		cardPanel.add(lblLogo);
		cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		cardPanel.add(lblSubtitulo);
		cardPanel.add(Box.createRigidArea(new Dimension(0, 45)));
		cardPanel.add(formPanel);
		cardPanel.add(Box.createRigidArea(new Dimension(0, 45)));
		cardPanel.add(btnLogin);
		cardPanel.add(Box.createVerticalGlue());

		wrapperPanel.add(cardPanel);

		add(wrapperPanel, BorderLayout.CENTER);
	}

	class RoundedPanel extends JPanel {
		private int radius;

		public RoundedPanel(int radius) {
			this.radius = radius;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.dispose();
			super.paintComponent(g);
		}
	}

	class RoundedTextField extends JTextField {
		private int radius;

		public RoundedTextField(int radius) {
			this.radius = radius;
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.WHITE);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.setColor(new Color(200, 200, 200));
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
			super.paintComponent(g);
		}
	}

	class RoundedPasswordField extends JPasswordField {
		private int radius;

		public RoundedPasswordField(int radius) {
			this.radius = radius;
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.WHITE);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.setColor(new Color(200, 200, 200));
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
			super.paintComponent(g);
		}
	}

	class RoundedButton extends JButton {
		private int radius;

		public RoundedButton(String text, int radius) {
			super(text);
			this.radius = radius;
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBorderPainted(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (getModel().isPressed()) {
				g2.setColor(new Color(40, 40, 40));
			} else if (getModel().isRollover()) {
				g2.setColor(new Color(60, 60, 60));
			} else {
				g2.setColor(getBackground());
			}
			
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.dispose();
			super.paintComponent(g);
		}
	}
}