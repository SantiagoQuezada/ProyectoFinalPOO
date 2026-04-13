package Visual;

import Logico.Empleado;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.Timer;
import javax.swing.Icon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal extends JFrame {

	private Empleado empleadoLogueado;

	public Principal(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Altice - Panel de Control Principal");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(10, 10, 10));
		headerPanel.setPreferredSize(new Dimension(1000, 80));
		headerPanel.setBorder(new EmptyBorder(15, 40, 15, 40));

		JLabel lblLogo = new JLabel("\u221E Altice");
		lblLogo.setFont(new Font("Arial", Font.BOLD, 32));
		lblLogo.setForeground(Color.WHITE);
		headerPanel.add(lblLogo, BorderLayout.WEST);

		JPanel rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 5));
		rightHeaderPanel.setOpaque(false);

		String nombreUsuario = (empleadoLogueado != null) ? empleadoLogueado.getNombre() : "Usuario";
		String rolUsuario = (empleadoLogueado != null && empleadoLogueado.getUsuario() != null) ? empleadoLogueado.getUsuario().getRol().toString() : "Admin";
		
		JLabel lblUser = new JLabel(" Hola, " + nombreUsuario + " (" + rolUsuario + ")");
		lblUser.setIcon(new UserIcon()); // Icono de personita más grande
		lblUser.setFont(new Font("Arial", Font.BOLD, 17)); // Letras en Bold y más grandes
		lblUser.setForeground(new Color(220, 220, 220));

		RoundedButton btnLogout = new RoundedButton("Cerrar Sesión", 20);
		btnLogout.setBackground(new Color(40, 40, 40));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogout.setPreferredSize(new Dimension(140, 35));
		btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogout.setBackground(new Color(220, 50, 50));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogout.setBackground(new Color(40, 40, 40));
			}
		});

		btnLogout.addActionListener(e -> {
			Login login = new Login();
			login.setVisible(true);
			dispose();
		});

		rightHeaderPanel.add(lblUser);
		rightHeaderPanel.add(btnLogout);
		headerPanel.add(rightHeaderPanel, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);

		JPanel wrapperPanel = new JPanel(new GridBagLayout());
		wrapperPanel.setBackground(new Color(245, 247, 250));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);

		JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Gestión");
		lblBienvenida.setFont(new Font("Arial", Font.BOLD, 36));
		lblBienvenida.setForeground(new Color(10, 10, 10));
		lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblBienvenida);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JLabel lblInstruccion = new JLabel("Seleccione un módulo para comenzar a trabajar");
		lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 18));
		lblInstruccion.setForeground(new Color(100, 100, 100));
		lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblInstruccion);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));

		JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		cardsPanel.setOpaque(false);

		RoundedPanel cardClientes = crearTarjetaModulo(
			"\uD83D\uDC65", 
			"Módulo Clientes", 
			"Gestión de cartera y registros", 
			e -> {
				Clientes clientes = new Clientes(empleadoLogueado);
				clientes.setVisible(true);
				dispose();
			}
		);

		RoundedPanel cardPlanes = crearTarjetaModulo(
			"\uD83D\uDCE1", 
			"Módulo Planes", 
			"Asignación y catálogo de planes", 
			e -> {
				Planes planes = new Planes(empleadoLogueado);
				planes.setVisible(true);
				dispose();
			}
		);

		RoundedPanel cardEmpleados = crearTarjetaModulo(
			"\uD83D\uDCBC", 
			"Nómina y Staff", 
			"Administración de empleados", 
			e -> {
				Empleados empleados = new Empleados(empleadoLogueado);
				empleados.setVisible(true);
				dispose();
			}
		);

		RoundedPanel cardPagos = crearTarjetaModulo(
			"💳", 
			"Módulo Pagos", 
			"Facturación y cobros", 
			e -> {
				Pagos pagos = new Pagos(empleadoLogueado);
				pagos.setVisible(true);
				dispose();
			}
		);

		cardsPanel.add(cardClientes);
		cardsPanel.add(cardPlanes);
		cardsPanel.add(cardPagos);
		
		if (empleadoLogueado == null || empleadoLogueado.getUsuario() == null || 
			empleadoLogueado.getUsuario().getRol().toString().equals("GERENTE") || 
			empleadoLogueado.getUsuario().getRol().toString().equals("ADMINISTRADOR")) {
			cardsPanel.add(cardEmpleados);
		}

		centerPanel.add(cardsPanel);
		wrapperPanel.add(centerPanel);

		add(wrapperPanel, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(new EmptyBorder(15, 0, 20, 0));

		JLabel lblReloj = new JLabel();
		lblReloj.setFont(new Font("Arial", Font.BOLD, 18));
		lblReloj.setForeground(new Color(0, 102, 204)); 
		lblReloj.setAlignmentX(Component.CENTER_ALIGNMENT);

		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  |  hh:mm:ss a");
				lblReloj.setText(sdf.format(new Date()));
			}
		});
		timer.start();

		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Sistema de Gestión Interna");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 14));
		lblFooter.setForeground(new Color(150, 150, 150));
		lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);

		footerPanel.add(lblReloj);
		footerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);
	}

	public Principal() {
		this(null);
	}

	private RoundedPanel crearTarjetaModulo(String icono, String titulo, String desc, ActionListener accion) {
		RoundedPanel tarjeta = new RoundedPanel(40);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(320, 380));
		tarjeta.setBackground(Color.WHITE);
		tarjeta.setBorder(new EmptyBorder(40, 30, 40, 30));

		JLabel lblIcono = new JLabel(icono);
		lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
		lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIcono.setHorizontalAlignment(JLabel.CENTER);

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitulo.setForeground(new Color(10, 10, 10));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);

		// Ancho fijo en HTML para forzar el centrado
		JLabel lblDesc = new JLabel("<html><div style='text-align: center; width: 220px;'>" + desc + "</div></html>");
		lblDesc.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDesc.setForeground(new Color(120, 120, 120));
		lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDesc.setHorizontalAlignment(JLabel.CENTER);

		RoundedButton boton = new RoundedButton("ACCEDER", 25);
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		boton.setBackground(new Color(0, 102, 204)); 
		boton.setForeground(Color.WHITE);
		boton.setFont(new Font("Arial", Font.BOLD, 14));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.setMaximumSize(new Dimension(260, 50));
		boton.setPreferredSize(new Dimension(260, 50));

		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(new Color(0, 80, 160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(0, 102, 204));
			}
		});

		boton.addActionListener(accion);

		tarjeta.add(Box.createVerticalGlue());
		tarjeta.add(lblIcono);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 25)));
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 10)));
		tarjeta.add(lblDesc);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 40)));
		tarjeta.add(boton);
		tarjeta.add(Box.createVerticalGlue());

		return tarjeta;
	}

	// Icono personalizado de usuario con tamaño y proporciones mejoradas
	class UserIcon implements Icon {
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(220, 220, 220));
			
			// Cabeza más grande y centrada
			g2.fillOval(x + 6, y + 2, 12, 12);
			// Cuerpo proporcionado al nuevo tamaño
			g2.fillArc(x, y + 15, 24, 18, 0, 180);
			
			g2.dispose();
		}

		@Override
		public int getIconWidth() {
			return 24; // Ancho ajustado
		}

		@Override
		public int getIconHeight() {
			return 24; // Altura ajustada
		}
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
			
			g2.setColor(new Color(235, 235, 235));
			g2.fillRoundRect(2, 2, getWidth()-2, getHeight()-2, radius, radius);
			
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, radius, radius);
			
			g2.setColor(new Color(220, 220, 220));
			g2.drawRoundRect(0, 0, getWidth() - 4, getHeight() - 4, radius, radius);
			
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
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.dispose();
			super.paintComponent(g);
		}
	}
}