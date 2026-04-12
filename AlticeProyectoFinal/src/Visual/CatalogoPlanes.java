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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Logico.Plan;
import Logico.Empleado;
import Logico.Altice;
import Logico.Cliente;

public class CatalogoPlanes extends JFrame {

	private DefaultTableModel modeloTablaPlanes;
	private JTable tablaPlanes;
	private DefaultTableModel modeloTablaClientes;
	private JTable tablaClientes;
	private Empleado empleadoLogueado;
	private JComboBox<String> cbPlanesDisponibles;

	public CatalogoPlanes(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Sistema de Gestión - Catálogo de Planes");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(15, 15, 15));
		headerPanel.setPreferredSize(new Dimension(1000, 70));
		headerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

		JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		leftHeaderPanel.setOpaque(false);

		JButton btnVolver = new JButton("\u25C0 Volver a Planes");
		btnVolver.setBackground(new Color(40, 40, 40));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(new EmptyBorder(8, 15, 8, 15));
		btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Planes planes = new Planes(empleadoLogueado);
				planes.setVisible(true);
				dispose();
			}
		});

		JLabel lblLogo = new JLabel("<html><span style='font-size:20px; font-family: Arial;'><b>\u221E Altice</b></span></html>");
		lblLogo.setForeground(Color.WHITE);

		leftHeaderPanel.add(btnVolver);
		leftHeaderPanel.add(lblLogo);
		headerPanel.add(leftHeaderPanel, BorderLayout.WEST);

		String nombreUsuario = (empleadoLogueado != null) ? empleadoLogueado.getNombre() : "Usuario";
		String rolUsuario = (empleadoLogueado != null && empleadoLogueado.getUsuario() != null) ? empleadoLogueado.getUsuario().getRol().toString() : "";
		
		JLabel lblUser = new JLabel("Hola, " + nombreUsuario + " | " + rolUsuario + "   \u2699   \u23FB");
		lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUser.setForeground(new Color(200, 200, 200));
		headerPanel.add(lblUser, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(20, 0));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(Color.WHITE);
		
		JPanel panelTituloPlanes = new JPanel();
		panelTituloPlanes.setLayout(new BoxLayout(panelTituloPlanes, BoxLayout.Y_AXIS));
		panelTituloPlanes.setBackground(Color.WHITE);
		
		JLabel lblTituloPlanes = new JLabel("\uD83D\uDCDA Catálogo de Planes");
		lblTituloPlanes.setFont(new Font("Arial", Font.BOLD, 24));
		lblTituloPlanes.setForeground(new Color(30, 30, 30));
		lblTituloPlanes.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblSubtituloPlanes = new JLabel("Administración de planes disponibles.");
		lblSubtituloPlanes.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSubtituloPlanes.setForeground(new Color(100, 100, 100));
		lblSubtituloPlanes.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTituloPlanes.add(lblTituloPlanes);
		panelTituloPlanes.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTituloPlanes.add(lblSubtituloPlanes);
		panelTituloPlanes.add(Box.createRigidArea(new Dimension(0, 15)));

		leftPanel.add(panelTituloPlanes, BorderLayout.NORTH);

		String[] columnasPlanes = {"ID Plan", "Categoría", "Nombre del Plan", "Precio (RD$)"};
		modeloTablaPlanes = new DefaultTableModel(null, columnasPlanes);
		tablaPlanes = new JTable(modeloTablaPlanes);
		tablaPlanes.setRowHeight(35);
		tablaPlanes.setFont(new Font("Arial", Font.PLAIN, 15));
		tablaPlanes.setForeground(Color.BLACK);
		tablaPlanes.setGridColor(new Color(230, 230, 230));
		tablaPlanes.setSelectionBackground(new Color(220, 235, 255));
		tablaPlanes.setSelectionForeground(Color.BLACK);

		JTableHeader headerPlanes = tablaPlanes.getTableHeader();
		headerPlanes.setFont(new Font("Arial", Font.BOLD, 15));
		headerPlanes.setBackground(new Color(245, 247, 250));
		headerPlanes.setForeground(new Color(50, 50, 50));
		headerPlanes.setPreferredSize(new Dimension(100, 40));

		JScrollPane scrollPanePlanes = new JScrollPane(tablaPlanes);
		scrollPanePlanes.setBorder(new LineBorder(new Color(220, 220, 220), 1));
		leftPanel.add(scrollPanePlanes, BorderLayout.CENTER);

		JPanel crudPanelPlanes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		crudPanelPlanes.setBackground(Color.WHITE);

		JButton btnCrear = crearBotonCRUD("Registrar", true);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegPlan modalReg = new RegPlan(null, false);
				modalReg.setVisible(true);
				cargarPlanes();
				actualizarPlanesDisponibles();
			}
		});

		JButton btnLeer = crearBotonCRUD("Ver", false);
		btnLeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaPlanes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idPlan = (String) modeloTablaPlanes.getValueAt(filaSeleccionada, 0);
					Plan plan = Altice.getInstance().getPlanById(idPlan);
					RegPlan modalReg = new RegPlan(plan, true);
					modalReg.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton btnActualizar = crearBotonCRUD("Editar", false);
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaPlanes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idPlan = (String) modeloTablaPlanes.getValueAt(filaSeleccionada, 0);
					Plan plan = Altice.getInstance().getPlanById(idPlan);
					RegPlan modalReg = new RegPlan(plan, false);
					modalReg.setVisible(true);
					cargarPlanes();
					actualizarPlanesDisponibles();
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton btnEliminar = crearBotonCRUD("Eliminar", false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaPlanes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idPlan = (String) modeloTablaPlanes.getValueAt(filaSeleccionada, 0);
					int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar el plan " + idPlan + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						Altice.getInstance().eliminarPlan(idPlan);
						cargarPlanes();
						actualizarPlanesDisponibles();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		crudPanelPlanes.add(btnCrear);
		crudPanelPlanes.add(btnLeer);
		crudPanelPlanes.add(btnActualizar);
		crudPanelPlanes.add(btnEliminar);
		
		leftPanel.add(crudPanelPlanes, BorderLayout.SOUTH);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(Color.WHITE);
		
		JPanel panelTituloClientes = new JPanel();
		panelTituloClientes.setLayout(new BoxLayout(panelTituloClientes, BoxLayout.Y_AXIS));
		panelTituloClientes.setBackground(Color.WHITE);
		
		JLabel lblTituloClientes = new JLabel("\uD83D\uDC65 Asignación Rápida");
		lblTituloClientes.setFont(new Font("Arial", Font.BOLD, 24));
		lblTituloClientes.setForeground(new Color(30, 30, 30));
		lblTituloClientes.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblSubtituloClientes = new JLabel("Seleccione un plan de la lista y busque un cliente en la tabla.");
		lblSubtituloClientes.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSubtituloClientes.setForeground(new Color(100, 100, 100));
		lblSubtituloClientes.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTituloClientes.add(lblTituloClientes);
		panelTituloClientes.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTituloClientes.add(lblSubtituloClientes);
		
		JPanel panelSelectorPlan = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelSelectorPlan.setBackground(Color.WHITE);
		panelSelectorPlan.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSeleccionarPlan = new JLabel("Plan a asignar: ");
		lblSeleccionarPlan.setFont(new Font("Arial", Font.BOLD, 14));

		cbPlanesDisponibles = new JComboBox<String>();
		cbPlanesDisponibles.setPreferredSize(new Dimension(250, 30));
		cbPlanesDisponibles.setFont(new Font("Arial", Font.PLAIN, 14));
		actualizarPlanesDisponibles();

		panelSelectorPlan.add(lblSeleccionarPlan);
		panelSelectorPlan.add(cbPlanesDisponibles);

		panelTituloClientes.add(Box.createRigidArea(new Dimension(0, 10)));
		panelTituloClientes.add(panelSelectorPlan);

		JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelBuscador.setBackground(Color.WHITE);
		panelBuscador.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblBuscar = new JLabel("Buscar Cliente: ");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		
		JTextField txtBuscarCliente = new JTextField(20);
		txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 14));
		txtBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cargarClientesFiltrados(txtBuscarCliente.getText());
			}
		});
		
		panelBuscador.add(lblBuscar);
		panelBuscador.add(txtBuscarCliente);
		
		panelTituloClientes.add(Box.createRigidArea(new Dimension(0, 10)));
		panelTituloClientes.add(panelBuscador);
		panelTituloClientes.add(Box.createRigidArea(new Dimension(0, 15)));

		rightPanel.add(panelTituloClientes, BorderLayout.NORTH);

		String[] columnasClientes = {"ID", "Cédula/Nombre"};
		modeloTablaClientes = new DefaultTableModel(null, columnasClientes);
		tablaClientes = new JTable(modeloTablaClientes);
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

		JScrollPane scrollPaneClientes = new JScrollPane(tablaClientes);
		scrollPaneClientes.setBorder(new LineBorder(new Color(220, 220, 220), 1));
		rightPanel.add(scrollPaneClientes, BorderLayout.CENTER);

		JPanel crudPanelAsignar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		crudPanelAsignar.setBackground(Color.WHITE);
		
		JButton btnAsignarPlan = new JButton("Asignar Plan a Cliente");
		btnAsignarPlan.setBackground(new Color(15, 15, 15));
		btnAsignarPlan.setForeground(Color.WHITE);
		btnAsignarPlan.setFont(new Font("Arial", Font.BOLD, 13));
		btnAsignarPlan.setFocusPainted(false);
		btnAsignarPlan.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAsignarPlan.setPreferredSize(new Dimension(200, 45));
		
		btnAsignarPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaCliente = tablaClientes.getSelectedRow();
				int indexPlan = cbPlanesDisponibles.getSelectedIndex();
				
				if(indexPlan <= 0) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan del desplegable.", "Atención", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(filaCliente < 0) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				String nombrePlan = ((String) cbPlanesDisponibles.getSelectedItem()).split(" - ")[0];
				String idCliente = (String) modeloTablaClientes.getValueAt(filaCliente, 0);
				
				int confirm = JOptionPane.showConfirmDialog(null, "¿Confirmar la asignación del plan '" + nombrePlan + "' al cliente " + idCliente + "?", "Confirmar Asignación", JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					Altice.getInstance().asignarPlanACliente(idCliente, nombrePlan);
					JOptionPane.showMessageDialog(null, "Plan asignado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		crudPanelAsignar.add(btnAsignarPlan);
		rightPanel.add(crudPanelAsignar, BorderLayout.SOUTH);

		centerPanel.add(leftPanel, BorderLayout.CENTER);
		
		rightPanel.setPreferredSize(new Dimension(450, 0));
		centerPanel.add(rightPanel, BorderLayout.EAST);

		add(centerPanel, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Catálogo de Planes");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);

		cargarPlanes();
		cargarClientesFiltrados("");
	}

	public CatalogoPlanes() {
		this(null);
	}

	private void cargarPlanes() {
		modeloTablaPlanes.setRowCount(0);
		for (Plan p : Altice.getInstance().getPlanes()) {
			Object[] fila = new Object[4];
			fila[0] = p.getIdPlan();
			fila[1] = p.getCategoria();
			fila[2] = p.getNombre();
			fila[3] = String.format("$%.2f", p.getPrecio());
			modeloTablaPlanes.addRow(fila);
		}
	}
	
	private void cargarClientesFiltrados(String filtro) {
		modeloTablaClientes.setRowCount(0);
		String busqueda = filtro.toLowerCase();
		
		for(Cliente c : Altice.getInstance().getClientes()) {
			if (c.getNombre().toLowerCase().contains(busqueda) || 
				c.getCedula().contains(busqueda) || 
				c.getTelefono().contains(busqueda)) {
				
				Object[] fila = new Object[2];
				fila[0] = c.getIdCliente();
				fila[1] = c.getCedula() + " - " + c.getNombre();
				modeloTablaClientes.addRow(fila);
			}
		}
	}

	private void actualizarPlanesDisponibles() {
		cbPlanesDisponibles.removeAllItems();
		cbPlanesDisponibles.addItem("<Seleccione un plan>");
		for (Plan p : Altice.getInstance().getPlanes()) {
			cbPlanesDisponibles.addItem(p.getNombre() + " - $" + p.getPrecio());
		}
	}

	private JButton crearBotonCRUD(String texto, boolean esPrincipal) {
		JButton boton = new JButton(texto);
		if (esPrincipal) {
			boton.setBackground(new Color(15, 15, 15));
			boton.setForeground(Color.WHITE);
		} else {
			boton.setBackground(new Color(240, 240, 240));
			boton.setForeground(new Color(30, 30, 30));
			boton.setBorder(new LineBorder(new Color(200, 200, 200), 1));
		}
		
		boton.setFont(new Font("Arial", Font.BOLD, 13));
		boton.setFocusPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		boton.setPreferredSize(new Dimension(100, 40));
		return boton;
	}
}