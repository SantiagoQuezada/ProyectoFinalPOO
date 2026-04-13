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
	private Empleado empleadoLogueado;

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
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
		
		JPanel panelTituloPlanes = new JPanel();
		panelTituloPlanes.setLayout(new BoxLayout(panelTituloPlanes, BoxLayout.Y_AXIS));
		panelTituloPlanes.setBackground(Color.WHITE);
		
		JLabel lblTituloPlanes = new JLabel("\uD83D\uDCDA Catálogo de Planes");
		lblTituloPlanes.setFont(new Font("Arial", Font.BOLD, 32));
		lblTituloPlanes.setForeground(new Color(30, 30, 30));
		lblTituloPlanes.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblSubtituloPlanes = new JLabel("Administración del catálogo completo de planes disponibles.");
		lblSubtituloPlanes.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtituloPlanes.setForeground(new Color(100, 100, 100));
		lblSubtituloPlanes.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTituloPlanes.add(lblTituloPlanes);
		panelTituloPlanes.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTituloPlanes.add(lblSubtituloPlanes);
		panelTituloPlanes.add(Box.createRigidArea(new Dimension(0, 30)));

		centerPanel.add(panelTituloPlanes, BorderLayout.NORTH);

		String[] columnasPlanes = {"ID Plan", "Categoría", "Nombre del Plan", "Precio (RD$)", "Estado"};
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
		centerPanel.add(scrollPanePlanes, BorderLayout.CENTER);

		JPanel crudPanelPlanes = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
		crudPanelPlanes.setBackground(Color.WHITE);

		JButton btnCrear = crearBotonCRUD("Registrar Plan", false);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegPlan modalReg = new RegPlan(null, false);
				modalReg.setVisible(true);
				cargarPlanes();
			}
		});

		JButton btnLeer = crearBotonCRUD("Ver Detalles", false);
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

		JButton btnActualizar = crearBotonCRUD("Editar Plan", false);
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

		JButton btnEliminar = crearBotonCRUD("Desactivar Plan", false);
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

		JButton btnAsignarPlan = crearBotonCRUD("Asignar Plan a Cliente", true);
		btnAsignarPlan.setPreferredSize(new Dimension(200, 45));
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

		cargarPlanes();
	}

	public CatalogoPlanes() {
		this(null);
	}

	private void cargarPlanes() {
		modeloTablaPlanes.setRowCount(0);
		for (Plan p : Altice.getInstance().getPlanes()) {
			Object[] fila = new Object[5];
			fila[0] = p.getIdPlan();
			fila[1] = p.getCategoria();
			fila[2] = p.getNombre();
			fila[3] = String.format("$%.2f", p.getPrecio());
			fila[4] = p.getEstado();
			modeloTablaPlanes.addRow(fila);
		}
	}

	private void mostrarMenuAsignacion(String planPreseleccionado) {
		JDialog dialog = new JDialog(this, "Asignación Rápida a Cliente", true);
		dialog.setSize(750, 600);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(Color.WHITE);

		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setBorder(new EmptyBorder(30, 40, 30, 40));

		JPanel panelSelectorPlan = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelSelectorPlan.setBackground(Color.WHITE);
		panelSelectorPlan.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSeleccionarPlan = new JLabel("Plan a asignar: ");
		lblSeleccionarPlan.setFont(new Font("Arial", Font.BOLD, 15));

		JComboBox<String> cbPlanes = new JComboBox<>();
		cbPlanes.setPreferredSize(new Dimension(350, 35));
		cbPlanes.setFont(new Font("Arial", Font.PLAIN, 15));
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

		panelSelectorPlan.add(lblSeleccionarPlan);
		panelSelectorPlan.add(Box.createRigidArea(new Dimension(15, 0)));
		panelSelectorPlan.add(cbPlanes);

		JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelBuscador.setBackground(Color.WHITE);
		panelBuscador.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblBuscar = new JLabel("Buscar Cliente: ");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 15));
		
		JTextField txtBuscarCliente = new JTextField(30);
		txtBuscarCliente.setPreferredSize(new Dimension(350, 35));
		txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		
		panelBuscador.add(lblBuscar);
		panelBuscador.add(Box.createRigidArea(new Dimension(12, 0)));
		panelBuscador.add(txtBuscarCliente);

		String[] columnasClientes = {"ID", "Cédula/RNC - Nombre", "Teléfono"};
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
			public void keyReleased(KeyEvent e) {
				cargarClientesFiltrados.run();
			}
		});

		cargarClientesFiltrados.run();

		panelCentral.add(panelSelectorPlan);
		panelCentral.add(Box.createRigidArea(new Dimension(0, 25)));
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
		boton.setPreferredSize(new Dimension(140, 45));
		return boton;
	}
}