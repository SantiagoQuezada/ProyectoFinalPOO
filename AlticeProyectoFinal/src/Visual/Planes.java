package Visual;

import Logico.Altice;
import Logico.Empleado;
import Logico.Cliente;
import Logico.Plan;
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
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

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

		String rolStr = empleadoLogueado != null && empleadoLogueado.getUsuario() != null ? empleadoLogueado.getUsuario().getRol().toString() : "SIN ROL";
		String nombreStr = empleadoLogueado != null ? empleadoLogueado.getNombre() : "Usuario";
		JLabel lblUser = new JLabel("Hola, " + nombreStr + " | " + rolStr + "   \u2699   \u23FB");
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

		JPanel panelBotonesTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		panelBotonesTop.setOpaque(false);

		JButton btnCatalogo = new JButton("\u2699 Administrar Catálogo");
		btnCatalogo.setBackground(new Color(40, 40, 40));
		btnCatalogo.setForeground(Color.WHITE);
		btnCatalogo.setFocusPainted(false);
		btnCatalogo.setFont(new Font("Arial", Font.BOLD, 14));
		btnCatalogo.setPreferredSize(new Dimension(240, 45));
		btnCatalogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCatalogo.addActionListener(e -> {
			CatalogoPlanes cat = new CatalogoPlanes(empleadoLogueado);
			cat.setVisible(true);
			dispose();
		});

		JButton btnContratos = new JButton("\uD83D\uDCC4 Ver Contratos Activos");
		btnContratos.setBackground(new Color(40, 40, 40));
		btnContratos.setForeground(Color.WHITE);
		btnContratos.setFocusPainted(false);
		btnContratos.setFont(new Font("Arial", Font.BOLD, 14));
		btnContratos.setPreferredSize(new Dimension(240, 45));
		btnContratos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnContratos.addActionListener(e -> mostrarContratosActivos());

		panelBotonesTop.add(btnCatalogo);
		panelBotonesTop.add(btnContratos);

		centerPanel.add(panelBotonesTop);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

		JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		cardsPanel.setOpaque(false);

		String[] planesTripleplay = Altice.getInstance().obtenerNombresPlanesPorCategoria("Combinado");
		String[] planesHogar = Altice.getInstance().obtenerNombresPlanesPorCategoria("Hogar");
		String[] planesMoviles = Altice.getInstance().obtenerNombresPlanesPorCategoria("Móvil");

		JPanel cardCombinados = crearTarjetaPlan("\uD83D\uDCFA", "COMBINADOS", "Tripleplay (TV + Internet + Voz)", planesTripleplay, "Combinado");
		JPanel cardHogar = crearTarjetaPlan("\uD83C\uDFE0", "HOGAR", "Internet Fibra Óptica", planesHogar, "Hogar");
		JPanel cardMoviles = crearTarjetaPlan("\uD83D\uDCF1", "MÓVILES", "Pospago e Ilimitados", planesMoviles, "Móvil");

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

	public Planes() {
		this(null);
	}

	private JPanel crearTarjetaPlan(String icono, String titulo, String subtitulo, String[] planesDisponibles, String categoriaReal) {
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

		boton.addActionListener(e -> mostrarMenuAsignacion(subtitulo, planesDisponibles, categoriaReal));

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

	private void mostrarMenuAsignacion(String categoriaTitulo, String[] planesDisponibles, String categoriaReal) {
		JDialog dialog = new JDialog(this, "Asignar Plan - " + categoriaTitulo, true);
		dialog.setSize(750, 650);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(Color.WHITE);

		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setBorder(new EmptyBorder(30, 40, 30, 40));

		int minimoMeses = 12;
		if (categoriaReal.equalsIgnoreCase("Hogar")) {
			minimoMeses = 16;
		}

		JPanel panelSelectorPlan = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelSelectorPlan.setBackground(Color.WHITE);
		panelSelectorPlan.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSeleccionarPlan = new JLabel("1. Seleccionar el nuevo Plan a aplicar: ");
		lblSeleccionarPlan.setFont(new Font("Arial", Font.BOLD, 15));

		JComboBox<String> cbPlanes = new JComboBox<>();
		cbPlanes.setPreferredSize(new Dimension(350, 35));
		cbPlanes.setFont(new Font("Arial", Font.PLAIN, 15));
		cbPlanes.addItem("<Seleccione un plan>");
		
		for (Plan p : Altice.getInstance().getPlanes()) {
			if (p.getCategoria().equalsIgnoreCase(categoriaReal)) {
				cbPlanes.addItem(p.getNombre() + " - $" + p.getPrecio());
			}
		}

		panelSelectorPlan.add(lblSeleccionarPlan);
		panelSelectorPlan.add(Box.createRigidArea(new Dimension(15, 0)));
		panelSelectorPlan.add(cbPlanes);

		JPanel panelMeses = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelMeses.setBackground(Color.WHITE);
		panelMeses.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblMeses = new JLabel("2. Meses de Contrato (Mínimo " + minimoMeses + "): ");
		lblMeses.setFont(new Font("Arial", Font.BOLD, 15));

		JTextField txtMeses = new JTextField(String.valueOf(minimoMeses));
		txtMeses.setPreferredSize(new Dimension(100, 35));
		txtMeses.setFont(new Font("Arial", Font.PLAIN, 15));

		panelMeses.add(lblMeses);
		panelMeses.add(Box.createRigidArea(new Dimension(15, 0)));
		panelMeses.add(txtMeses);

		JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelBuscador.setBackground(Color.WHITE);
		panelBuscador.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblBuscar = new JLabel("3. Buscar Cliente: ");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 15));
		
		JTextField txtBuscarCliente = new JTextField(30);
		txtBuscarCliente.setPreferredSize(new Dimension(350, 35));
		txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		
		panelBuscador.add(lblBuscar);
		panelBuscador.add(Box.createRigidArea(new Dimension(12, 0)));
		panelBuscador.add(txtBuscarCliente);

		String[] columnasClientes = {"ID", "Cédula/Nombre", "Teléfono"};
		DefaultTableModel modeloTablaClientes = new DefaultTableModel(null, columnasClientes) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JTable tablaClientes = new JTable(modeloTablaClientes);
		tablaClientes.setRowHeight(35);
		tablaClientes.setFont(new Font("Arial", Font.PLAIN, 15));
		tablaClientes.setForeground(Color.BLACK);
		tablaClientes.setGridColor(new Color(230, 230, 230));
		tablaClientes.setSelectionBackground(new Color(220, 235, 255));
		tablaClientes.setSelectionForeground(Color.BLACK);

		JTableHeader headerClientes = tablaClientes.getTableHeader();
		headerClientes.setFont(new Font("Arial", Font.BOLD, 15));
		headerClientes.setBackground(new Color(245, 247, 250));
		headerClientes.setForeground(new Color(50, 50, 50));
		headerClientes.setPreferredSize(new Dimension(100, 40));

		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(80);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(350);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(150);

		JScrollPane scrollPaneClientes = new JScrollPane(tablaClientes);
		scrollPaneClientes.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPaneClientes.setBorder(new LineBorder(new Color(220, 220, 220), 1));

		Runnable cargarClientesFiltrados = () -> {
			modeloTablaClientes.setRowCount(0);
			String busqueda = txtBuscarCliente.getText().toLowerCase();
			for(Cliente c : Altice.getInstance().getClientes()) {
				if (c.getNombre().toLowerCase().contains(busqueda) || 
					c.getCedula().contains(busqueda) || 
					c.getTelefono().contains(busqueda)) {
					
					Object[] fila = new Object[3];
					fila[0] = c.getIdCliente();
					fila[1] = c.getCedula() + " - " + c.getNombre();
					fila[2] = c.getTelefono();
					modeloTablaClientes.addRow(fila);
				}
			}
		};

		txtBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cargarClientesFiltrados.run();
			}
		});

		cargarClientesFiltrados.run();

		panelCentral.add(panelSelectorPlan);
		panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentral.add(panelMeses);
		panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentral.add(panelBuscador);
		panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentral.add(scrollPaneClientes);

		dialog.add(panelCentral, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		panelBotones.setBackground(new Color(245, 247, 250));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setPreferredSize(new Dimension(120, 40));
		btnCancelar.addActionListener(e -> dialog.dispose());

		JButton btnGuardar = new JButton("Confirmar Asignación");
		btnGuardar.setBackground(new Color(15, 15, 15));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
		btnGuardar.setFocusPainted(false);
		btnGuardar.setPreferredSize(new Dimension(180, 40));

		final int minRequerido = minimoMeses;

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
			
			try {
				int meses = Integer.parseInt(txtMeses.getText());
				if (meses < minRequerido) {
					JOptionPane.showMessageDialog(dialog, "El mínimo de meses para este plan es " + minRequerido + ".", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String nombrePlan = ((String) cbPlanes.getSelectedItem()).split(" - \\$")[0];
				String idCliente = (String) modeloTablaClientes.getValueAt(filaCliente, 0);

				Altice.getInstance().asignarPlanACliente(idCliente, nombrePlan, meses);
				JOptionPane.showMessageDialog(dialog, "El plan '" + nombrePlan + "' ha sido asignado exitosamente al cliente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				dialog.dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(dialog, "Ingrese un número válido para los meses de contrato.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		panelBotones.add(btnCancelar);
		panelBotones.add(btnGuardar);

		dialog.add(panelBotones, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void mostrarContratosActivos() {
		JDialog dialog = new JDialog(this, "Listado de Contratos Activos", true);
		dialog.setSize(900, 500);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(Color.WHITE);

		JPanel panelCentral = new JPanel(new BorderLayout());
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setBorder(new EmptyBorder(20, 30, 20, 30));

		JLabel lblTitulo = new JLabel("Clientes con Planes Activos");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
		panelCentral.add(lblTitulo, BorderLayout.NORTH);

		String[] columnas = {"ID Cliente", "Nombre del Cliente", "Plan Activo", "Fecha Asignación", "Meses Totales", "Meses Restantes"};
		DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(35);
		tabla.setFont(new Font("Arial", Font.PLAIN, 14));
		tabla.setGridColor(new Color(230, 230, 230));
		tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		tabla.getTableHeader().setBackground(new Color(245, 247, 250));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (Cliente c : Altice.getInstance().getClientes()) {
			if (c.getPlan() != null) {
				String fecha = c.getFechaAsignacionPlan() != null ? sdf.format(c.getFechaAsignacionPlan()) : "N/A";
				modelo.addRow(new Object[]{
					c.getIdCliente(),
					c.getNombre(),
					c.getPlan().getNombre(),
					fecha,
					c.getMesesContrato(),
					c.getMesesRestantes()
				});
			}
		}

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(new LineBorder(new Color(220, 220, 220), 1));
		panelCentral.add(scroll, BorderLayout.CENTER);

		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelSur.setBackground(Color.WHITE);
		panelSur.setBorder(new EmptyBorder(10, 0, 0, 0));

		JButton btnCerrar = new JButton("Cerrar Vista");
		btnCerrar.setBackground(new Color(15, 15, 15));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
		btnCerrar.setFocusPainted(false);
		btnCerrar.setPreferredSize(new Dimension(120, 40));
		btnCerrar.addActionListener(e -> dialog.dispose());

		panelSur.add(btnCerrar);
		panelCentral.add(panelSur, BorderLayout.SOUTH);

		dialog.add(panelCentral, BorderLayout.CENTER);
		dialog.setVisible(true);
	}
}