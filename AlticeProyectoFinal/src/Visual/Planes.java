package Visual;

import Logico.Altice;
import Logico.Empleado;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Planes extends JFrame {

	private Empleado empleadoLogueado;

	public Planes(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Sistema de Gestión - Módulo de Planes");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(15, 15, 15));
		headerPanel.setPreferredSize(new Dimension(1000, 70));
		headerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

		JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		leftHeaderPanel.setOpaque(false);

		JButton btnVolver = new JButton("\u25C0 Volver al Inicio");
		btnVolver.setBackground(new Color(40, 40, 40));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(new EmptyBorder(8, 15, 8, 15));
		btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(e -> {
			Principal principal = new Principal(empleadoLogueado);
			principal.setVisible(true);
			dispose();
		});

		JLabel lblLogo = new JLabel("<html><span style='font-size:20px; font-family: Arial;'><b>\u221E Altice</b></span></html>");
		lblLogo.setForeground(Color.WHITE);

		leftHeaderPanel.add(btnVolver);
		leftHeaderPanel.add(lblLogo);
		headerPanel.add(leftHeaderPanel, BorderLayout.WEST);

		String rolStr = empleadoLogueado.getUsuario() != null ? empleadoLogueado.getUsuario().getRol().toString() : "SIN ROL";
		JLabel lblUser = new JLabel("Hola, " + empleadoLogueado.getNombre() + " | " + rolStr + "   \u2699   \u23FB");
		lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUser.setForeground(new Color(200, 200, 200));
		headerPanel.add(lblUser, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);

		JPanel wrapperPanel = new JPanel(new GridBagLayout());
		wrapperPanel.setBackground(new Color(245, 247, 250));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);

		JLabel lblTituloPrincipal = new JLabel("Gestión de Planes y Servicios");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 36));
		lblTituloPrincipal.setForeground(new Color(30, 30, 30));
		lblTituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblTituloPrincipal);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		JLabel lblSubtitulo = new JLabel("Selecciona una categoría para asignar un plan a un cliente de forma rápida.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblSubtitulo);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		JButton btnCatalogo = new JButton("\u2699 Administrar Catálogo de Planes");
		btnCatalogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCatalogo.setBackground(new Color(40, 40, 40));
		btnCatalogo.setForeground(Color.WHITE);
		btnCatalogo.setFocusPainted(false);
		btnCatalogo.setFont(new Font("Arial", Font.BOLD, 14));
		btnCatalogo.setPreferredSize(new Dimension(280, 45));
		btnCatalogo.setMaximumSize(new Dimension(280, 45));
		btnCatalogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCatalogo.addActionListener(e -> {
			CatalogoPlanes cat = new CatalogoPlanes(empleadoLogueado);
			cat.setVisible(true);
			dispose();
		});
		centerPanel.add(btnCatalogo);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		cardsPanel.setOpaque(false);

		String[] planesTripleplay = Altice.getInstance().obtenerNombresPlanesPorCategoria("Combinado");
		String[] planesHogar = Altice.getInstance().obtenerNombresPlanesPorCategoria("Hogar");
		String[] planesMoviles = Altice.getInstance().obtenerNombresPlanesPorCategoria("Móvil");

		JPanel cardCombinados = crearTarjetaPlan("\uD83D\uDCFA", "COMBINADOS", "Tripleplay (TV + Internet + Voz)", planesTripleplay);
		JPanel cardHogar = crearTarjetaPlan("\uD83C\uDFE0", "HOGAR", "Internet Fibra Óptica", planesHogar);
		JPanel cardMoviles = crearTarjetaPlan("\uD83D\uDCF1", "MÓVILES", "Pospago e Ilimitados", planesMoviles);

		cardsPanel.add(cardCombinados);
		cardsPanel.add(cardHogar);
		cardsPanel.add(cardMoviles);

		centerPanel.add(cardsPanel);
		wrapperPanel.add(centerPanel);

		add(wrapperPanel, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Módulo de Planes");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);
	}

	private JPanel crearTarjetaPlan(String icono, String titulo, String subtitulo, String[] planesDisponibles) {
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

		JLabel lblDesc = new JLabel(subtitulo);
		lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDesc.setForeground(new Color(100, 100, 100));
		lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton boton = new JButton("ASIGNAR PLAN");
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		boton.setBackground(new Color(240, 240, 240));
		boton.setForeground(new Color(30, 30, 30));
		boton.setFocusPainted(false);
		boton.setFont(new Font("Arial", Font.BOLD, 13));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.setPreferredSize(new Dimension(180, 45));
		boton.setMaximumSize(new Dimension(200, 45));
		boton.setBorder(new LineBorder(new Color(200, 200, 200), 1));

		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(new Color(220, 220, 220));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(240, 240, 240));
			}
		});

		boton.addActionListener(e -> mostrarMenuAsignacion(subtitulo, planesDisponibles));

		tarjeta.add(Box.createVerticalGlue());
		tarjeta.add(lblIcono);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 20)));
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 10)));
		tarjeta.add(lblDesc);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 30)));
		tarjeta.add(boton);
		tarjeta.add(Box.createVerticalGlue());

		return tarjeta;
	}

	private void mostrarMenuAsignacion(String categoria, String[] planesDisponibles) {
		JDialog dialog = new JDialog(this, "Asignar Plan - " + categoria, true);
		dialog.setSize(500, 320);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(Color.WHITE);

		JPanel panelFormulario = new JPanel(new GridLayout(4, 1, 10, 10));
		panelFormulario.setBackground(Color.WHITE);
		panelFormulario.setBorder(new EmptyBorder(25, 40, 25, 40));

		String[] clientesRegistrados = Altice.getInstance().obtenerNombresClientes();

		JComboBox<String> cbClientes = new JComboBox<>(clientesRegistrados);
		JComboBox<String> cbPlanes = new JComboBox<>(planesDisponibles);

		cbClientes.setBackground(Color.WHITE);
		cbClientes.setFont(new Font("Arial", Font.PLAIN, 14));
		cbPlanes.setBackground(Color.WHITE);
		cbPlanes.setFont(new Font("Arial", Font.PLAIN, 14));

		JLabel lblSelCliente = new JLabel("1. Seleccionar Cliente de la base de datos:");
		lblSelCliente.setFont(new Font("Arial", Font.BOLD, 13));
		JLabel lblSelPlan = new JLabel("2. Seleccionar el nuevo Plan a aplicar:");
		lblSelPlan.setFont(new Font("Arial", Font.BOLD, 13));

		panelFormulario.add(lblSelCliente);
		panelFormulario.add(cbClientes);
		panelFormulario.add(lblSelPlan);
		panelFormulario.add(cbPlanes);

		dialog.add(panelFormulario, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		panelBotones.setBackground(new Color(245, 247, 250));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
		btnCancelar.setFocusPainted(false);
		btnCancelar.addActionListener(e -> dialog.dispose());

		JButton btnGuardar = new JButton("Confirmar Asignación");
		btnGuardar.setBackground(new Color(15, 15, 15));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
		btnGuardar.setFocusPainted(false);

		btnGuardar.addActionListener(e -> {
			if(cbClientes.getSelectedIndex() == 0 || cbPlanes.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(dialog, "Por favor, seleccione un cliente y un plan.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			} else {
				String clienteSelec = (String) cbClientes.getSelectedItem();
				String planSelec = (String) cbPlanes.getSelectedItem();

				String idCliente = clienteSelec.split(" - ")[0];
				Altice.getInstance().asignarPlanACliente(idCliente, planSelec);

				JOptionPane.showMessageDialog(dialog, "El plan ha sido asignado exitosamente al cliente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				dialog.dispose();
			}
		});

		panelBotones.add(btnCancelar);
		panelBotones.add(btnGuardar);

		dialog.add(panelBotones, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}
}