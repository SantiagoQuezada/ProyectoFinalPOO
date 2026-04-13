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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

		JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		leftHeaderPanel.setOpaque(false);

		RoundedButton btnVolver = new RoundedButton("\u25C0 Volver al Inicio", 20);
		btnVolver.setBackground(new Color(40, 40, 40));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Arial", Font.BOLD, 13));
		btnVolver.setPreferredSize(new Dimension(150, 35));
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnVolver.setBackground(new Color(60, 60, 60));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnVolver.setBackground(new Color(40, 40, 40));
			}
		});

		btnVolver.addActionListener(e -> {
			Principal principal = new Principal(empleadoLogueado);
			principal.setVisible(true);
			dispose();
		});

		JLabel lblLogo = new JLabel("  \u221E Altice");
		lblLogo.setFont(new Font("Arial", Font.BOLD, 28));
		lblLogo.setForeground(Color.WHITE);

		leftHeaderPanel.add(btnVolver);
		leftHeaderPanel.add(lblLogo);
		headerPanel.add(leftHeaderPanel, BorderLayout.WEST);

		JPanel rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		rightHeaderPanel.setOpaque(false);

		String nombreUsuario = (empleadoLogueado != null) ? empleadoLogueado.getNombre() : "Usuario";
		String rolUsuario = (empleadoLogueado != null && empleadoLogueado.getUsuario() != null) ? empleadoLogueado.getUsuario().getRol().toString() : "Admin";
		
		JLabel lblUser = new JLabel(" Hola, " + nombreUsuario + " (" + rolUsuario + ")");
		lblUser.setIcon(new UserIcon());
		lblUser.setFont(new Font("Arial", Font.PLAIN, 15));
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
		tarjeta.setBorder(new EmptyBorder(40, 20, 40, 20));

		JLabel lblIcono = new JLabel(icono);
		lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
		lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

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
		tarjeta.add(Box.createRigidArea(new Dimension(0, 25)));
		tarjeta.add(lblTitulo);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 10)));
		tarjeta.add(lblDesc);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 35)));
		tarjeta.add(boton);
		tarjeta.add(Box.createVerticalGlue());

		return tarjeta;
	}

	private void mostrarMenuAsignacion(String categoriaTitulo, String categoriaReal) {
		JDialog dialog = new JDialog(this, "Asignar Plan - " + categoriaTitulo, true);
		dialog.setSize(800, 600);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(new Color(245, 247, 250));

		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setBackground(new Color(245, 247, 250));
		panelCentral.setBorder(new EmptyBorder(30, 40, 30, 40));

		JPanel panelSelectorPlan = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelSelectorPlan.setBackground(new Color(245, 247, 250));
		panelSelectorPlan.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSeleccionarPlan = new JLabel("Plan a asignar: ");
		lblSeleccionarPlan.setFont(new Font("Arial", Font.BOLD, 15));
		lblSeleccionarPlan.setForeground(new Color(30, 30, 30));

		JComboBox<String> cbPlanes = new JComboBox<>();
		cbPlanes.setPreferredSize(new Dimension(350, 35));
		cbPlanes.setFont(new Font("Arial", Font.PLAIN, 14));
		cbPlanes.setBackground(Color.WHITE);
		cbPlanes.addItem("<Seleccione un plan>");
		
		for (Plan p : Altice.getInstance().getPlanes()) {
			if (p.getCategoria().equalsIgnoreCase(categoriaReal) && p.getEstado().equals("Activo")) {
				cbPlanes.addItem(p.getNombre() + " - $" + p.getPrecio());
			}
		}

		panelSelectorPlan.add(lblSeleccionarPlan);
		panelSelectorPlan.add(Box.createRigidArea(new Dimension(15, 0)));
		panelSelectorPlan.add(cbPlanes);

		JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelBuscador.setBackground(new Color(245, 247, 250));
		panelBuscador.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblBuscar = new JLabel("Buscar Cliente: ");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 15));
		lblBuscar.setForeground(new Color(30, 30, 30));
		
		JTextField txtBuscarCliente = new JTextField(30);
		txtBuscarCliente.setPreferredSize(new Dimension(350, 35));
		txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 14));
		txtBuscarCliente.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(200, 200, 200)), 
			new EmptyBorder(0, 10, 0, 10)
		));
		
		panelBuscador.add(lblBuscar);
		panelBuscador.add(Box.createRigidArea(new Dimension(12, 0)));
		panelBuscador.add(txtBuscarCliente);

		String[] columnasClientes = {"ID", "Cédula/RNC - Nombre", "Teléfono"};
		DefaultTableModel modeloTablaClientes = new DefaultTableModel(null, columnasClientes) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		JTable tablaClientes = new JTable(modeloTablaClientes);
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
		tableWrapper.setBorder(new EmptyBorder(5, 5, 5, 5));
		tableWrapper.add(scrollPaneClientes, BorderLayout.CENTER);

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

		panelCentral.add(panelSelectorPlan);
		panelCentral.add(Box.createRigidArea(new Dimension(0, 25)));
		panelCentral.add(panelBuscador);
		panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentral.add(tableWrapper);

		dialog.add(panelCentral, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		panelBotones.setBackground(new Color(245, 247, 250));

		RoundedButton btnCancelar = new RoundedButton("Cancelar", 20);
		btnCancelar.setBackground(new Color(200, 200, 200));
		btnCancelar.setForeground(new Color(30, 30, 30));
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCancelar.setPreferredSize(new Dimension(120, 40));
		btnCancelar.addActionListener(e -> dialog.dispose());

		RoundedButton btnGuardar = new RoundedButton("Confirmar Asignación", 20);
		btnGuardar.setBackground(new Color(0, 102, 204));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
		btnGuardar.setPreferredSize(new Dimension(180, 40));

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
		JDialog dialog = new JDialog(this, "Control de Asignaciones de Planes", true);
		dialog.setSize(1000, 600);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(new Color(245, 247, 250));

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
		topPanel.setBackground(new Color(245, 247, 250));
		topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

		JLabel lblEstado = new JLabel("Estado Plan:");
		lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
		JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Todos", "Activos", "Desactivados"});
		cbEstado.setFont(new Font("Arial", Font.PLAIN, 14));
		cbEstado.setBackground(Color.WHITE);

		JLabel lblTipo = new JLabel("Tipo Cliente:");
		lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
		JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Todos", "Personales", "Empresariales"});
		cbTipo.setFont(new Font("Arial", Font.PLAIN, 14));
		cbTipo.setBackground(Color.WHITE);

		JLabel lblBuscar = new JLabel("Buscar (Nombre/RNC):");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		JTextField txtBuscar = new JTextField(20);
		txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
		txtBuscar.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(200, 200, 200)), 
			new EmptyBorder(0, 10, 0, 10)
		));

		topPanel.add(lblEstado);
		topPanel.add(cbEstado);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(lblTipo);
		topPanel.add(cbTipo);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(lblBuscar);
		topPanel.add(txtBuscar);

		dialog.add(topPanel, BorderLayout.NORTH);

		String[] cols = {"Identificación", "Cliente", "Tipo Cliente", "Plan Asignado", "Estado Plan"};
		DefaultTableModel model = new DefaultTableModel(null, cols) {
			public boolean isCellEditable(int r, int c) { return false; }
		};
		JTable table = new JTable(model);
		configurarTablaEstiloModerno(table);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getViewport().setBackground(Color.WHITE);
		
		RoundedPanel tableWrapper = new RoundedPanel(20);
		tableWrapper.setLayout(new BorderLayout());
		tableWrapper.setBackground(Color.WHITE);
		tableWrapper.setBorder(new EmptyBorder(5, 5, 5, 5));
		tableWrapper.add(scroll, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(0, 30, 20, 30));
		centerPanel.setBackground(new Color(245, 247, 250));
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
		RoundedButton btnCerrar = new RoundedButton("Cerrar", 20);
		btnCerrar.setBackground(new Color(60, 60, 60));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCerrar.setPreferredSize(new Dimension(120, 40));
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
			g2.fillOval(x + 4, y, 8, 8);
			g2.fillArc(x, y + 9, 16, 12, 0, 180);
			g2.dispose();
		}
		@Override
		public int getIconWidth() { return 16; }
		@Override
		public int getIconHeight() { return 16; }
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