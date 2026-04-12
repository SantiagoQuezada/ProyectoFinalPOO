package Visual;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
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
import Logico.Plan;
import Logico.Altice;
import Logico.Empleado;
import Logico.Rol;

public class CatalogoPlanes extends JFrame {

	private DefaultTableModel modeloTabla;
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

		String rolStr = empleadoLogueado.getUsuario() != null ? empleadoLogueado.getUsuario().getRol().toString() : "SIN ROL";
		JLabel lblUser = new JLabel("Hola, " + empleadoLogueado.getNombre() + " | " + rolStr + "   \u2699   \u23FB");
		lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUser.setForeground(new Color(200, 200, 200));
		headerPanel.add(lblUser, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
		panelTitulo.setBackground(Color.WHITE);

		JLabel lblTituloPrincipal = new JLabel("\uD83D\uDCDA Catálogo de Planes");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 32));
		lblTituloPrincipal.setForeground(new Color(30, 30, 30));
		lblTituloPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Administración de los planes comerciales disponibles en el sistema.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTitulo.add(lblTituloPrincipal);
		panelTitulo.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTitulo.add(lblSubtitulo);
		panelTitulo.add(Box.createRigidArea(new Dimension(0, 30)));

		centerPanel.add(panelTitulo, BorderLayout.NORTH);

		String[] columnas = {"ID Plan", "Categoría", "Nombre del Plan", "Precio (RD$)"};
		modeloTabla = new DefaultTableModel(null, columnas);
		tablaPlanes = new JTable(modeloTabla);
		tablaPlanes.setRowHeight(35);
		tablaPlanes.setFont(new Font("Arial", Font.PLAIN, 15));
		tablaPlanes.setForeground(Color.BLACK);
		tablaPlanes.setGridColor(new Color(230, 230, 230));
		tablaPlanes.setSelectionBackground(new Color(220, 235, 255));
		tablaPlanes.setSelectionForeground(Color.BLACK);

		JTableHeader header = tablaPlanes.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 15));
		header.setBackground(new Color(245, 247, 250));
		header.setForeground(new Color(50, 50, 50));
		header.setPreferredSize(new Dimension(100, 40));

		JScrollPane scrollPane = new JScrollPane(tablaPlanes);
		scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
		crudPanel.setBackground(Color.WHITE);

		JButton btnCrear = crearBotonCRUD("Registrar Plan", true);
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
					String idPlan = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
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
					String idPlan = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					Plan plan = Altice.getInstance().getPlanById(idPlan);
					RegPlan modalReg = new RegPlan(plan, false);
					modalReg.setVisible(true);
					cargarPlanes();
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton btnEliminar = crearBotonCRUD("Eliminar Plan", false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaPlanes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idPlan = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar el plan " + idPlan + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						Altice.getInstance().eliminarPlan(idPlan);
						cargarPlanes();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		boolean tieneAccesoTotal = false;
		if (empleadoLogueado.getUsuario() != null) {
			Rol rol = empleadoLogueado.getUsuario().getRol();
			if (rol == Rol.ADMINISTRADOR || rol == Rol.GERENTE) {
				tieneAccesoTotal = true;
			}
		}

		btnActualizar.setEnabled(tieneAccesoTotal);
		btnEliminar.setEnabled(tieneAccesoTotal);

		crudPanel.add(btnCrear);
		crudPanel.add(btnLeer);
		crudPanel.add(btnActualizar);
		crudPanel.add(btnEliminar);

		centerPanel.add(crudPanel, BorderLayout.SOUTH);

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

	private void cargarPlanes() {
		modeloTabla.setRowCount(0);
		for (Plan p : Altice.getInstance().getPlanes()) {
			Object[] fila = new Object[4];
			fila[0] = p.getIdPlan();
			fila[1] = p.getCategoria();
			fila[2] = p.getNombre();
			fila[3] = String.format("$%.2f", p.getPrecio());
			modeloTabla.addRow(fila);
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
		boton.setPreferredSize(new Dimension(170, 45));
		return boton;
	}
}