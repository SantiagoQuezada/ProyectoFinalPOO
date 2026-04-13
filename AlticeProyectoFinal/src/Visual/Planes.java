package Visual;

import Logico.Altice;
import Logico.Cliente;
import Logico.Plan;
import Logico.Empleado;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
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
import java.awt.Rectangle;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Planes extends JFrame {

	private Empleado empleadoLogueado;

	public Planes(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Altice - Módulo de Planes");
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

		JPanel rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		rightHeaderPanel.setOpaque(false);

		String nombreUsuario = (empleadoLogueado != null) ? empleadoLogueado.getNombre() : "Usuario";
		String rolUsuario = (empleadoLogueado != null && empleadoLogueado.getUsuario() != null) ? empleadoLogueado.getUsuario().getRol().toString() : "Admin";
		
		JLabel lblUser = new JLabel(" Hola, " + nombreUsuario + " (" + rolUsuario + ")");
		lblUser.setIcon(new UserIcon());
		lblUser.setFont(new Font("Arial", Font.BOLD, 17));
		lblUser.setForeground(new Color(220, 220, 220));
		
		rightHeaderPanel.add(lblUser);
		headerPanel.add(rightHeaderPanel, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);

		JPanel wrapperPanel = new JPanel(new GridBagLayout());
		wrapperPanel.setBackground(new Color(245, 247, 250));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);

		JLabel lblTituloPrincipal = new JLabel("📡 Gestión de Planes y Servicios");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 36));
		lblTituloPrincipal.setForeground(new Color(10, 10, 10));
		lblTituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblTituloPrincipal);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		JLabel lblSubtitulo = new JLabel("Selecciona una categoría para asignar un plan a un cliente de forma rápida.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblSubtitulo);

		centerPanel.add(Box.createRigidArea(new Dimension(0, 35)));

		JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		actionPanel.setOpaque(false);

		// Botón de Volver en gris metálico
		RoundedButton btnVolver = new RoundedButton("\u25C0 Volver al Inicio", 25);
		btnVolver.setBackground(new Color(108, 117, 125));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
		btnVolver.setPreferredSize(new Dimension(190, 45));
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { btnVolver.setBackground(new Color(130, 140, 150)); }
			@Override
			public void mouseExited(MouseEvent e) { btnVolver.setBackground(new Color(108, 117, 125)); }
		});
		btnVolver.addActionListener(e -> {
			Principal principal = new Principal(empleadoLogueado);
			principal.setVisible(true);
			dispose();
		});

		RoundedButton btnCatalogo = new RoundedButton("\u2699 Administrar Catálogo de Planes", 25);
		btnCatalogo.setBackground(new Color(0, 102, 204));
		btnCatalogo.setForeground(Color.WHITE);
		btnCatalogo.setFont(new Font("Arial", Font.BOLD, 14));
		btnCatalogo.setPreferredSize(new Dimension(280, 45));
		btnCatalogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCatalogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { btnCatalogo.setBackground(new Color(0, 80, 160)); }
			@Override
			public void mouseExited(MouseEvent e) { btnCatalogo.setBackground(new Color(0, 102, 204)); }
		});
		btnCatalogo.addActionListener(e -> {
			CatalogoPlanes cat = new CatalogoPlanes(empleadoLogueado);
			cat.setVisible(true);
			dispose();
		});

		RoundedButton btnConsultas = new RoundedButton("\uD83D\uDD0D Consultar Asignaciones", 25);
		btnConsultas.setBackground(new Color(60, 60, 60));
		btnConsultas.setForeground(Color.WHITE);
		btnConsultas.setFont(new Font("Arial", Font.BOLD, 14));
		btnConsultas.setPreferredSize(new Dimension(280, 45));
		btnConsultas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnConsultas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { btnConsultas.setBackground(new Color(80, 80, 80)); }
			@Override
			public void mouseExited(MouseEvent e) { btnConsultas.setBackground(new Color(60, 60, 60)); }
		});
		btnConsultas.addActionListener(e -> mostrarMenuConsultas());

		actionPanel.add(btnVolver);
		actionPanel.add(btnCatalogo);
		actionPanel.add(btnConsultas);

		centerPanel.add(actionPanel);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 35)));

		JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
		cardsPanel.setOpaque(false);

		RoundedPanel cardCombinados = crearTarjetaPlan("\uD83D\uDCFA", "COMBINADOS", "Tripleplay (TV + Internet + Voz)", "Combinado");
		RoundedPanel cardHogar = crearTarjetaPlan("\uD83C\uDFE0", "HOGAR", "Internet Fibra Óptica", "Hogar");
		RoundedPanel cardMoviles = crearTarjetaPlan("\uD83D\uDCF1", "MÓVILES", "Pospago e Ilimitados", "Móvil");
		RoundedPanel cardEmpresariales = crearTarjetaPlan("\uD83C\uDFE2", "EMPRESARIALES", "Soluciones Corporativas", "Empresarial");

		cardsPanel.add(cardCombinados);
		cardsPanel.add(cardHogar);
		cardsPanel.add(cardMoviles);
		cardsPanel.add(cardEmpresariales);

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

	public Planes() {
		this(null);
	}

	private RoundedPanel crearTarjetaPlan(String icono, String titulo, String subtitulo, String categoriaReal) {
		RoundedPanel tarjeta = new RoundedPanel(30);
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setPreferredSize(new Dimension(280, 380));
		tarjeta.setBackground(Color.WHITE);
		tarjeta.setBorder(new EmptyBorder(30, 20, 30, 20));

		JLabel lblIcono = new JLabel(icono);
		lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));
		lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIcono.setHorizontalAlignment(JLabel.CENTER);
		lblIcono.setVerticalAlignment(JLabel.CENTER);
		lblIcono.setPreferredSize(new Dimension(100, 80));

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setForeground(new Color(10, 10, 10));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblDesc = new JLabel("<html><div style='text-align: center; width: 200px;'>" + subtitulo + "</div></html>");
		lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDesc.setForeground(new Color(120, 120, 120));
		lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

		RoundedButton boton = new RoundedButton("ASIGNAR PLAN", 25);
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		boton.setBackground(new Color(240, 240, 240));
		boton.setForeground(new Color(30, 30, 30));
		boton.setFont(new Font("Arial", Font.BOLD, 13));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.setPreferredSize(new Dimension(220, 45));
		boton.setMaximumSize(new Dimension(220, 45));

		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(new Color(0, 102, 204));
				boton.setForeground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(240, 240, 240));
				boton.setForeground(new Color(30, 30, 30));
			}
		});

		boton.addActionListener(e -> mostrarMenuAsignacion(titulo, categoriaReal));

		tarjeta.add(Box.createVerticalGlue());
		tarjeta.add(lblIcono);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 10)));
		tarjeta.add(lblDesc);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 30)));
		tarjeta.add(boton);
		tarjeta.add(Box.createVerticalGlue());

		return tarjeta;
	}

	private void mostrarMenuAsignacion(String categoriaTitulo, String categoriaReal) {
		JDialog dialog = new JDialog(this, "Asignación de Plan", true);
		dialog.setSize(1200, 750); // Modal expandido
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		JLabel lblDialogTitle = new JLabel("➕ Asignar Plan: " + categoriaTitulo);
		lblDialogTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lblDialogTitle.setForeground(Color.WHITE);
		headerPanel.add(lblDialogTitle);
		dialog.add(headerPanel, BorderLayout.NORTH);

		// Layout en X_AXIS para que los elementos nunca salten de línea
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setOpaque(false);
		topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

		JLabel lblSeleccionarPlan = new JLabel("Plan a asignar: ");
		lblSeleccionarPlan.setFont(new Font("Arial", Font.BOLD, 14));
		lblSeleccionarPlan.setForeground(new Color(30, 30, 30));

		RoundedComboBox<String> cbPlanes = new RoundedComboBox<>(15);
		cbPlanes.setMaximumSize(new Dimension(350, 35));
		cbPlanes.addItem("<Seleccione un plan>");
		
		for (Plan p : Altice.getInstance().getPlanes()) {
			if (p.getCategoria().equalsIgnoreCase(categoriaReal) && p.getEstado().equals("Activo")) {
				cbPlanes.addItem(p.getNombre() + " - $" + p.getPrecio());
			}
		}

		if (planPreseleccionado != null) {
			for (int i = 0; i < cbPlanes.getItemCount(); i++) {
				if (cbPlanes.getItemAt(i).startsWith(planPreseleccionado + " -")) {
					cbPlanes.setSelectedIndex(i);
					break;
				}
			}
		}

		JLabel lblBuscar = new JLabel("Buscar Cliente: ");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		lblBuscar.setForeground(new Color(30, 30, 30));
		
		RoundedTextField txtBuscarCliente = new RoundedTextField(15);
		txtBuscarCliente.setMaximumSize(new Dimension(300, 35));
		txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 14));

		topPanel.add(lblSeleccionarPlan);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(cbPlanes);
		
		topPanel.add(Box.createHorizontalGlue()); // Empuja hacia la derecha el buscador
		
		topPanel.add(lblBuscar);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(txtBuscarCliente);

		RoundedPanel topWrapper = new RoundedPanel(20);
		topWrapper.setLayout(new BorderLayout());
		topWrapper.setBackground(Color.WHITE);
		topWrapper.setBorder(new EmptyBorder(5, 5, 5, 5));
		topWrapper.add(topPanel, BorderLayout.CENTER);

		String[] columnasClientes = {"ID", "Cédula/RNC - Nombre", "Teléfono"};
		DefaultTableModel modeloTablaClientes = new DefaultTableModel(null, columnasClientes) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		JTable tablaClientes = new JTable(modeloTablaClientes);
		tablaClientes.setFillsViewportHeight(true);
		configurarTablaEstiloModerno(tablaClientes);

		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(80);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(400);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(150);

		JScrollPane scrollPaneClientes = new JScrollPane(tablaClientes);
		scrollPaneClientes.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPaneClientes.setBorder(BorderFactory.createEmptyBorder());
		scrollPaneClientes.getViewport().setBackground(Color.WHITE);
		
		RoundedPanel tableWrapper = new RoundedPanel(20);
		tableWrapper.setLayout(new BorderLayout());
		tableWrapper.setBackground(Color.WHITE);
		tableWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
		tableWrapper.add(scrollPaneClientes, BorderLayout.CENTER);

		JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
		centerPanel.setBorder(new EmptyBorder(25, 30, 20, 30));
		centerPanel.setBackground(new Color(245, 247, 250));
		centerPanel.add(topWrapper, BorderLayout.NORTH);
		centerPanel.add(tableWrapper, BorderLayout.CENTER);

		dialog.add(centerPanel, BorderLayout.CENTER);

		Runnable cargarClientesFiltrados = () -> {
			modeloTablaClientes.setRowCount(0);
			String busqueda = txtBuscarCliente.getText().toLowerCase();
			for(Cliente c : Altice.getInstance().getClientes()) {
				boolean matchRnc = c.getRnc() != null && c.getRnc().contains(busqueda);
				if (c.getNombre().toLowerCase().contains(busqueda) || 
					c.getCedula().contains(busqueda) || 
					c.getTelefono().contains(busqueda) || matchRnc) {
					
					String ident = c.getTipoCliente().equals("Empresarial") ? c.getRnc() : c.getCedula();
					Object[] fila = new Object[3];
					fila[0] = c.getIdCliente();
					fila[1] = ident + " - " + c.getNombre();
					fila[2] = c.getTelefono();
					modeloTablaClientes.addRow(fila);
				}
			}
		};

		txtBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { cargarClientesFiltrados.run(); }
		});

		cargarClientesFiltrados.run();

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		panelBotones.setBackground(new Color(245, 247, 250));
		panelBotones.setBorder(new EmptyBorder(0, 20, 20, 20));

		RoundedButton btnCancelar = new RoundedButton("Cancelar", 20);
		btnCancelar.setBackground(new Color(200, 200, 200));
		btnCancelar.setForeground(new Color(30, 30, 30));
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCancelar.setPreferredSize(new Dimension(120, 45));
		btnCancelar.addActionListener(e -> dialog.dispose());

		RoundedButton btnGuardar = new RoundedButton("Confirmar Asignación", 20);
		btnGuardar.setBackground(new Color(0, 102, 204));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
		btnGuardar.setPreferredSize(new Dimension(190, 45));

		btnGuardar.addActionListener(e -> {
			int indexPlan = cbPlanes.getSelectedIndex();
			int filaCliente = tablaClientes.getSelectedRow();
			
			if(indexPlan <= 0) {
				JOptionPane.showMessageDialog(dialog, "Debe seleccionar un plan del desplegable.", "Atención", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(filaCliente < 0) {
				JOptionPane.showMessageDialog(dialog, "Debe seleccionar un cliente de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String nombrePlan = ((String) cbPlanes.getSelectedItem()).split(" - \\$")[0];
			String idCliente = (String) modeloTablaClientes.getValueAt(filaCliente, 0);
			
			Altice.getInstance().asignarPlanACliente(idCliente, nombrePlan);
			JOptionPane.showMessageDialog(dialog, "El plan '" + nombrePlan + "' ha sido asignado exitosamente al cliente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			dialog.dispose();
		});

		panelBotones.add(btnCancelar);
		panelBotones.add(btnGuardar);

		dialog.add(panelBotones, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void mostrarMenuConsultas() {
		JDialog dialog = new JDialog(this, "Control de Asignaciones", true);
		dialog.setSize(1200, 750); // Modal expandido
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		JLabel lblDialogTitle = new JLabel("🔍 Control de Asignaciones de Planes");
		lblDialogTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lblDialogTitle.setForeground(Color.WHITE);
		headerPanel.add(lblDialogTitle);
		dialog.add(headerPanel, BorderLayout.NORTH);

		// Layout en X_AXIS para que los elementos nunca salten de línea
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setOpaque(false);
		topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

		JLabel lblEstado = new JLabel("Estado Plan:");
		lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
		RoundedComboBox<String> cbEstado = new RoundedComboBox<>(15);
		cbEstado.addItem("Todos");
		cbEstado.addItem("Activos");
		cbEstado.addItem("Desactivados");
		cbEstado.setMaximumSize(new Dimension(160, 35));

		JLabel lblTipo = new JLabel("Tipo Cliente:");
		lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
		RoundedComboBox<String> cbTipo = new RoundedComboBox<>(15);
		cbTipo.addItem("Todos");
		cbTipo.addItem("Personales");
		cbTipo.addItem("Empresariales");
		cbTipo.setMaximumSize(new Dimension(160, 35));

		JLabel lblBuscar = new JLabel("Buscar (Nombre/RNC):");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		RoundedTextField txtBuscar = new RoundedTextField(15);
		txtBuscar.setMaximumSize(new Dimension(250, 35));
		txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));

		topPanel.add(lblEstado);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(cbEstado);
		topPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		topPanel.add(lblTipo);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(cbTipo);
		
		topPanel.add(Box.createHorizontalGlue()); // Empuja hacia la derecha
		
		topPanel.add(lblBuscar);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(txtBuscar);

		RoundedPanel topWrapper = new RoundedPanel(20);
		topWrapper.setLayout(new BorderLayout());
		topWrapper.setBackground(Color.WHITE);
		topWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
		topWrapper.add(topPanel, BorderLayout.CENTER);

		String[] cols = {"Identificación", "Cliente", "Tipo Cliente", "Plan Asignado", "Estado Plan"};
		DefaultTableModel model = new DefaultTableModel(null, cols) {
			public boolean isCellEditable(int r, int c) { return false; }
		};
		JTable table = new JTable(model);
		table.setFillsViewportHeight(true);
		configurarTablaEstiloModerno(table);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getViewport().setBackground(Color.WHITE);
		
		RoundedPanel tableWrapper = new RoundedPanel(20);
		tableWrapper.setLayout(new BorderLayout());
		tableWrapper.setBackground(Color.WHITE);
		tableWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
		tableWrapper.add(scroll, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
		centerPanel.setBorder(new EmptyBorder(25, 30, 20, 30));
		centerPanel.setBackground(new Color(245, 247, 250));
		centerPanel.add(topWrapper, BorderLayout.NORTH);
		centerPanel.add(tableWrapper, BorderLayout.CENTER);

		dialog.add(centerPanel, BorderLayout.CENTER);

		Runnable cargar = () -> {
			model.setRowCount(0);
			String busq = txtBuscar.getText().toLowerCase();
			String fEstado = cbEstado.getSelectedItem().toString();
			String fTipo = cbTipo.getSelectedItem().toString();

			for (Cliente c : Altice.getInstance().getClientes()) {
				String ident = c.getTipoCliente().equalsIgnoreCase("Empresarial") ? c.getRnc() : c.getCedula();
				if (ident == null) ident = "";

				boolean matchBusq = c.getNombre().toLowerCase().contains(busq) || ident.toLowerCase().contains(busq);
				
				boolean matchTipo = fTipo.equals("Todos") || 
								   (fTipo.equals("Personales") && c.getTipoCliente().equalsIgnoreCase("Personal")) || 
								   (fTipo.equals("Empresariales") && c.getTipoCliente().equalsIgnoreCase("Empresarial"));

				String nombreP = "Sin Plan";
				String estadoP = "N/A";
				if (c.getPlan() != null) {
					nombreP = c.getPlan().getNombre();
					estadoP = c.getPlan().getEstado();
				}

				boolean matchEstado = fEstado.equals("Todos") || 
									 (fEstado.equals("Activos") && estadoP.equalsIgnoreCase("Activo")) || 
									 (fEstado.equals("Desactivados") && estadoP.equalsIgnoreCase("Desactivado"));

				if (matchBusq && matchTipo && matchEstado) {
					model.addRow(new Object[]{ident, c.getNombre(), c.getTipoCliente(), nombreP, estadoP});
				}
			}
		};

		txtBuscar.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) { cargar.run(); }
		});
		cbEstado.addActionListener(e -> cargar.run());
		cbTipo.addActionListener(e -> cargar.run());
		cargar.run();

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		bottomPanel.setBackground(new Color(245, 247, 250));
		bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

		RoundedButton btnCerrar = new RoundedButton("Cerrar", 20);
		btnCerrar.setBackground(new Color(60, 60, 60));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCerrar.setPreferredSize(new Dimension(130, 45));
		btnCerrar.addActionListener(e -> dialog.dispose());
		bottomPanel.add(btnCerrar);

		dialog.add(bottomPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void configurarTablaEstiloModerno(JTable tabla) {
		tabla.setRowHeight(40);
		tabla.setFont(new Font("Arial", Font.PLAIN, 14));
		tabla.setForeground(new Color(15, 15, 15));
		tabla.setGridColor(new Color(220, 220, 220));
		tabla.setSelectionBackground(new Color(0, 102, 204));
		tabla.setSelectionForeground(Color.WHITE);
		tabla.setShowVerticalLines(false);
		tabla.setShowHorizontalLines(true);
		tabla.setIntercellSpacing(new Dimension(0, 0));
		tabla.setBorder(BorderFactory.createEmptyBorder());

		JTableHeader header = tabla.getTableHeader();
		header.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				label.setBackground(new Color(15, 15, 15));
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.BOLD, 13));
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setBorder(new EmptyBorder(10, 10, 10, 10));
				return label;
			}
		});
		header.setPreferredSize(new Dimension(100, 45));
		header.setReorderingAllowed(false);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
				((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));
				
				if (!isSelected) {
					if (row % 2 == 0) c.setBackground(new Color(250, 250, 250));
					else c.setBackground(new Color(240, 240, 240));
					c.setForeground(new Color(15, 15, 15));
				}
				return c;
			}
		};

		for (int i = 0; i < tabla.getColumnCount(); i++) {
			tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	class UserIcon implements Icon {
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(220, 220, 220));
			g2.fillOval(x + 6, y + 2, 12, 12);
			g2.fillArc(x, y + 15, 24, 18, 0, 180);
			g2.dispose();
		}
		@Override
		public int getIconWidth() { return 24; }
		@Override
		public int getIconHeight() { return 24; }
	}

	class RoundedComboBox<E> extends JComboBox<E> {
		private int radius;

		public RoundedComboBox(int radius) {
			super();
			this.radius = radius;
			setOpaque(false);
			setFont(new Font("Arial", Font.BOLD, 14));
			setBackground(new Color(240, 240, 240));
			setForeground(new Color(50, 50, 50));
			setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

			setUI(new BasicComboBoxUI() {
				@Override
				protected JButton createArrowButton() {
					JButton button = new JButton("\u25BC");
					button.setFont(new Font("Arial", Font.PLAIN, 10));
					button.setForeground(new Color(150, 150, 150));
					button.setContentAreaFilled(false);
					button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
					button.setCursor(new Cursor(Cursor.HAND_CURSOR));
					button.setFocusPainted(false);
					button.setOpaque(false);
					return button;
				}
				@Override
				public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {}
			});

			setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					label.setBorder(new EmptyBorder(8, 10, 8, 10));
					label.setFont(new Font("Arial", Font.BOLD, 14));
					if (index == -1) {
						label.setOpaque(false);
						if (RoundedComboBox.this.isEnabled()) label.setForeground(new Color(50, 50, 50));
						else label.setForeground(new Color(150, 150, 150));
					} else {
						label.setOpaque(true);
						if (isSelected) {
							label.setBackground(new Color(0, 60, 130));
							label.setForeground(Color.WHITE);
						} else {
							label.setBackground(Color.WHITE);
							label.setForeground(new Color(50, 50, 50));
						}
					}
					return label;
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
			super.paintComponent(g2);
			g2.dispose();
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(200, 200, 200));
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
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
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.setColor(new Color(220, 220, 220));
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
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
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
			super.paintComponent(g2);
			g2.dispose();
		}
		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(200, 200, 200));
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
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