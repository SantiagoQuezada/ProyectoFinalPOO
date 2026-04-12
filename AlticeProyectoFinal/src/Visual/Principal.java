package Visual;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Principal extends JFrame {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		setTitle("Sistema de Gestión - Principal");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(15, 15, 15));
		headerPanel.setPreferredSize(new Dimension(1000, 70));
		headerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

		JLabel lblLogo = new JLabel("<html><span style='font-size:20px; font-family: Arial;'><b>\u221E Altice</b></span></html>");
		lblLogo.setForeground(Color.WHITE);
		headerPanel.add(lblLogo, BorderLayout.WEST);

		JLabel lblUser = new JLabel("Hola, Juan Pérez | Administrador   \u2699   \u23FB");
		lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUser.setForeground(new Color(200, 200, 200));
		headerPanel.add(lblUser, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);

		JPanel wrapperPanel = new JPanel(new GridBagLayout());
		wrapperPanel.setBackground(new Color(245, 247, 250));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);

		JLabel lblTituloPrincipal = new JLabel("Panel de Control Principal");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 36));
		lblTituloPrincipal.setForeground(new Color(30, 30, 30));
		lblTituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblTituloPrincipal);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		JLabel lblSubtitulo = new JLabel("Bienvenido al sistema interno. Gestiona las operaciones clave de la empresa.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblSubtitulo);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 60)));

		JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		cardsPanel.setOpaque(false);

		JPanel cardPlanes = crearTarjeta(
				"\uD83D\uDCC8", 
				"PLANES", 
				"Gestionar paquetes, ofertas y<br>tarifas de servicios.", 
				"VER PLANES");

		JPanel cardEmpleados = crearTarjeta(
				"\uD83D\uDC65", 
				"EMPLEADOS", 
				"Base de datos del personal,<br>roles y perfiles.", 
				"VER EMPLEADOS");

		JPanel cardClientes = crearTarjeta(
				"\uD83D\uDCC1", 
				"CLIENTES", 
				"Consultar registros, servicios<br>activos y facturación.", 
				"VER CLIENTES");

		cardsPanel.add(cardPlanes);
		cardsPanel.add(cardEmpleados);
		cardsPanel.add(cardClientes);

		centerPanel.add(cardsPanel);
		wrapperPanel.add(centerPanel);

		add(wrapperPanel, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Sistema de Gestión Interna | Términos y Condiciones");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);
	}

	private JPanel crearTarjeta(String icono, String titulo, String descripcion, String textoBoton) {
		JPanel tarjeta = new JPanel();
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(300, 360));
		tarjeta.setBackground(Color.WHITE);

		tarjeta.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(220, 220, 220), 1, true),
				new EmptyBorder(30, 25, 30, 25)
		));

		JLabel lblIcono = new JLabel(icono);
		lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
		lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblTitulo = new JLabel("<html><div style='text-align: center; color: #1a1a1a;'>" + titulo + "</div></html>");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblDesc = new JLabel("<html><div style='text-align: center; color: #666666;'>" + descripcion + "</div></html>");
		lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton boton = new JButton(textoBoton);
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		boton.setBackground(new Color(15, 15, 15));
		boton.setForeground(Color.WHITE);
		boton.setFocusPainted(false);
		boton.setFont(new Font("Arial", Font.BOLD, 13));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.setPreferredSize(new Dimension(180, 45));
		boton.setMaximumSize(new Dimension(200, 45));
		boton.setBorder(BorderFactory.createEmptyBorder());

		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(new Color(50, 50, 50));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(15, 15, 15));
			}
		});

		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textoBoton.equals("VER PLANES")) {
					Planes ventanaPlanes = new Planes();
					ventanaPlanes.setVisible(true);
					dispose();
				} else if (textoBoton.equals("VER EMPLEADOS")) {
					Empleados ventanaEmpleados = new Empleados();
					ventanaEmpleados.setVisible(true);
					dispose();
				} else if (textoBoton.equals("VER CLIENTES")) {
					Clientes ventanaClientes = new Clientes();
					ventanaClientes.setVisible(true);
					dispose();
				}
			}
		});

		tarjeta.add(Box.createVerticalGlue());
		tarjeta.add(lblIcono);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 20)));
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
		tarjeta.add(lblDesc);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 30)));
		tarjeta.add(boton);
		tarjeta.add(Box.createVerticalGlue());

		return tarjeta;
	}
}