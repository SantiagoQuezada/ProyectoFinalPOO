package Visual;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Logico.Plan;
import Logico.Empleado;
import Logico.Altice;
import Logico.Cliente;

public class CatalogoPlanes extends JFrame {

	private DefaultTableModel modeloTablaPlanes;
	private JTable tablaPlanes;
	private Empleado empleadoLogueado;
	private RoundedComboBox<String> cbFiltroCategoria;
	private RoundedComboBox<String> cbFiltroEstado;
	private RoundedTextField txtBuscarPlan;

	public CatalogoPlanes(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Altice - Catálogo de Planes");
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

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(0, 15));
		centerPanel.setBackground(new Color(245, 247, 250));
		centerPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
		
		JPanel panelTituloPlanes = new JPanel();
		panelTituloPlanes.setLayout(new BoxLayout(panelTituloPlanes, BoxLayout.Y_AXIS));
		panelTituloPlanes.setBackground(new Color(245, 247, 250));
		panelTituloPlanes.setBorder(new EmptyBorder(0, 0, 10, 0));
		
		JLabel lblTituloPlanes = new JLabel("📚 Catálogo de Planes");
		lblTituloPlanes.setFont(new Font("Arial", Font.BOLD, 32));
		lblTituloPlanes.setForeground(new Color(10, 10, 10));
		lblTituloPlanes.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblSubtituloPlanes = new JLabel("Administración del catálogo completo de planes y servicios disponibles.");
		lblSubtituloPlanes.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtituloPlanes.setForeground(new Color(100, 100, 100));
		lblSubtituloPlanes.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTituloPlanes.add(lblTituloPlanes);
		panelTituloPlanes.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTituloPlanes.add(lblSubtituloPlanes);

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		topPanel.setBackground(Color.WHITE);
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(new Font("Arial", Font.BOLD, 14));
		cbFiltroCategoria = new RoundedComboBox<>(15);
		cbFiltroCategoria.addItem("Todas");
		cbFiltroCategoria.addItem("Combinado");
		cbFiltroCategoria.addItem("Hogar");
		cbFiltroCategoria.addItem("Móvil");
		cbFiltroCategoria.addItem("Empresarial");
		cbFiltroCategoria.setPreferredSize(new Dimension(160, 35));

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
		cbFiltroEstado = new RoundedComboBox<>(15);
		cbFiltroEstado.addItem("Todos");
		cbFiltroEstado.addItem("Activos");
		cbFiltroEstado.addItem("Desactivados");
		cbFiltroEstado.setPreferredSize(new Dimension(150, 35));

		JLabel lblBuscar = new JLabel("Buscar Plan:");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		txtBuscarPlan = new RoundedTextField(15);
		txtBuscarPlan.setPreferredSize(new Dimension(250, 35));
		txtBuscarPlan.setFont(new Font("Arial", Font.PLAIN, 14));

		topPanel.add(lblCategoria);
		topPanel.add(cbFiltroCategoria);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(lblEstado);
		topPanel.add(cbFiltroEstado);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		topPanel.add(lblBuscar);
		topPanel.add(txtBuscarPlan);

		RoundedPanel topWrapper = new RoundedPanel(20);
		topWrapper.setLayout(new BorderLayout());
		topWrapper.setBackground(Color.WHITE);
		topWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
		topWrapper.add(topPanel, BorderLayout.CENTER);

		JPanel headerAndFilterPanel = new JPanel(new BorderLayout(0, 15));
		headerAndFilterPanel.setBackground(new Color(245, 247, 250));
		headerAndFilterPanel.add(panelTituloPlanes, BorderLayout.NORTH);
		headerAndFilterPanel.add(topWrapper, BorderLayout.CENTER);

		centerPanel.add(headerAndFilterPanel, BorderLayout.NORTH);

		String[] columnasPlanes = {"ID Plan", "Categoría", "Nombre del Plan", "Precio (RD$)", "Estado"};
		modeloTablaPlanes = new DefaultTableModel(null, columnasPlanes) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tablaPlanes = new JTable(modeloTablaPlanes);
		tablaPlanes.setFillsViewportHeight(true); 
		configurarTablaEstiloModerno(tablaPlanes);

		JScrollPane scrollPanePlanes = new JScrollPane(tablaPlanes);
		scrollPanePlanes.setBorder(BorderFactory.createEmptyBorder());
		scrollPanePlanes.getViewport().setBackground(Color.WHITE);

		RoundedPanel tableWrapper = new RoundedPanel(20);
		tableWrapper.setLayout(new BorderLayout());
		tableWrapper.setBackground(Color.WHITE);
		tableWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
		tableWrapper.add(scrollPanePlanes, BorderLayout.CENTER);

		centerPanel.add(tableWrapper, BorderLayout.CENTER);

		JPanel crudPanelPlanes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
		crudPanelPlanes.setBackground(new Color(245, 247, 250));

		RoundedButton btnVolver = crearBotonCRUD("\u25C0 Volver a Planes", new Color(108, 117, 125), new Color(130, 140, 150));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Planes planes = new Planes(empleadoLogueado);
				planes.setVisible(true);
				dispose();
			}
		});

		RoundedButton btnCrear = crearBotonCRUD("Registrar Plan", new Color(0, 102, 204), new Color(0, 80, 160));
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegPlan modalReg = new RegPlan(null, false);
				modalReg.setVisible(true);
				cargarPlanes();
			}
		});

		RoundedButton btnLeer = crearBotonCRUD("Ver Detalles", new Color(60, 60, 60), new Color(80, 80, 80));
		btnLeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaPlanes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idPlan = (String) modeloTablaPlanes.getValueAt(filaSeleccionada, 0);
					Plan plan = Altice.getInstance().getPlanById(idPlan);
					RegPlan modalReg = new RegPlan(plan, true);
					modalReg.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		RoundedButton btnActualizar = crearBotonCRUD("Editar Plan", new Color(230, 126, 34), new Color(200, 100, 20));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaPlanes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idPlan = (String) modeloTablaPlanes.getValueAt(filaSeleccionada, 0);
					Plan plan = Altice.getInstance().getPlanById(idPlan);
					RegPlan modalReg = new RegPlan(plan, false);
					modalReg.setVisible(true);
					cargarPlanes();
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		RoundedButton btnEliminar = crearBotonCRUD("Desactivar Plan", new Color(220, 53, 69), new Color(180, 40, 50));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaPlanes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idPlan = (String) modeloTablaPlanes.getValueAt(filaSeleccionada, 0);
					String estado = (String) modeloTablaPlanes.getValueAt(filaSeleccionada, 4);
					
					if(estado.equals("Desactivado")) {
						JOptionPane.showMessageDialog(null, "Este plan ya se encuentra desactivado.", "Información", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que desea desactivar el plan " + idPlan + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						Altice.getInstance().eliminarPlan(idPlan);
						cargarPlanes();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		RoundedButton btnAsignarPlan = crearBotonCRUD("Asignar a Cliente", new Color(40, 167, 69), new Color(30, 130, 50));
		btnAsignarPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaPlanes.getSelectedRow();
				String planPreseleccionado = null;
				if (fila >= 0) {
					planPreseleccionado = (String) modeloTablaPlanes.getValueAt(fila, 2);
				}
				mostrarMenuAsignacion(planPreseleccionado);
			}
		});

		crudPanelPlanes.add(btnVolver);
		crudPanelPlanes.add(Box.createRigidArea(new Dimension(30, 0))); // Separador
		crudPanelPlanes.add(btnCrear);
		crudPanelPlanes.add(btnLeer);
		crudPanelPlanes.add(btnActualizar);
		crudPanelPlanes.add(btnEliminar);
		crudPanelPlanes.add(Box.createRigidArea(new Dimension(30, 0)));
		crudPanelPlanes.add(btnAsignarPlan);
		
		centerPanel.add(crudPanelPlanes, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Catálogo de Planes");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);

		txtBuscarPlan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { cargarPlanes(); }
		});
		cbFiltroCategoria.addActionListener(e -> cargarPlanes());
		cbFiltroEstado.addActionListener(e -> cargarPlanes());

		cargarPlanes();
	}

	public CatalogoPlanes() {
		this(null);
	}

	private void cargarPlanes() {
		modeloTablaPlanes.setRowCount(0);
		
		String busq = txtBuscarPlan != null ? txtBuscarPlan.getText().toLowerCase() : "";
		String fCat = cbFiltroCategoria != null ? cbFiltroCategoria.getSelectedItem().toString() : "Todas";
		String fEstado = cbFiltroEstado != null ? cbFiltroEstado.getSelectedItem().toString() : "Todos";

		for (Plan p : Altice.getInstance().getPlanes()) {
			boolean matchBusq = p.getNombre().toLowerCase().contains(busq) || p.getIdPlan().toLowerCase().contains(busq);
			boolean matchCat = fCat.equals("Todas") || p.getCategoria().equalsIgnoreCase(fCat);
			boolean matchEstado = fEstado.equals("Todos") || p.getEstado().equalsIgnoreCase(fEstado);

			if (matchBusq && matchCat && matchEstado) {
				Object[] fila = new Object[5];
				fila[0] = p.getIdPlan();
				fila[1] = p.getCategoria();
				fila[2] = p.getNombre();
				fila[3] = String.format("$%.2f", p.getPrecio());
				fila[4] = p.getEstado();
				modeloTablaPlanes.addRow(fila);
			}
		}
	}

	private void mostrarMenuAsignacion(String planPreseleccionado) {
		JDialog dialog = new JDialog(this, "Asignación Rápida a Cliente", true);
		dialog.setSize(1000, 700); // Modificado a tamaño más grande
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		JLabel lblDialogTitle = new JLabel("➕ Asignación de Plan a Cliente");
		lblDialogTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lblDialogTitle.setForeground(Color.WHITE);
		headerPanel.add(lblDialogTitle);
		dialog.add(headerPanel, BorderLayout.NORTH);

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		topPanel.setBackground(Color.WHITE);
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblSeleccionarPlan = new JLabel("Plan a asignar: ");
		lblSeleccionarPlan.setFont(new Font("Arial", Font.BOLD, 14));
		lblSeleccionarPlan.setForeground(new Color(30, 30, 30));

		RoundedComboBox<String> cbPlanes = new RoundedComboBox<>(15);
		cbPlanes.setPreferredSize(new Dimension(350, 35));
		cbPlanes.addItem("<Seleccione un plan>");
		
		for (Plan p : Altice.getInstance().getPlanes()) {
			if (p.getEstado().equals("Activo")) {
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
		txtBuscarCliente.setPreferredSize(new Dimension(300, 35));
		txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 14));

		topPanel.add(lblSeleccionarPlan);
		topPanel.add(cbPlanes);
		topPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		topPanel.add(lblBuscar);
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

	private RoundedButton crearBotonCRUD(String texto, Color bgDefault, Color bgHover) {
		RoundedButton boton = new RoundedButton(texto, 25);
		boton.setBackground(bgDefault);
		boton.setForeground(Color.WHITE);
		
		boton.setFont(new Font("Arial", Font.BOLD, 14));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.setPreferredSize(new Dimension(190, 45));

		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(bgHover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(bgDefault);
			}
		});

		return boton;
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