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
import Logico.Empleado;
import Logico.Altice;

public class Empleados extends JFrame {

	private DefaultTableModel modeloTabla;
	private JTable tablaEmpleados;

	public Empleados() {
		setTitle("Sistema de Gestión - Módulo de Empleados");
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

		JButton btnVolver = new JButton("\u25C0 Volver al Inicio");
		btnVolver.setBackground(new Color(40, 40, 40));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(new EmptyBorder(8, 15, 8, 15));
		btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal principal = new Principal();
				principal.setVisible(true);
				dispose();
			}
		});

		JLabel lblLogo = new JLabel("<html><span style='font-size:20px; font-family: Arial;'><b>\u221E Altice</b></span></html>");
		lblLogo.setForeground(Color.WHITE);

		leftHeaderPanel.add(btnVolver);
		leftHeaderPanel.add(lblLogo);
		headerPanel.add(leftHeaderPanel, BorderLayout.WEST);

		JLabel lblUser = new JLabel("Hola, Juan Pérez | Administrador   \u2699   \u23FB");
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

		JLabel lblTituloPrincipal = new JLabel("\uD83D\uDC65 Nómina de Empleados");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 32));
		lblTituloPrincipal.setForeground(new Color(30, 30, 30));
		lblTituloPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Gestión del personal y sus cargos dentro de la empresa.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTitulo.add(lblTituloPrincipal);
		panelTitulo.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTitulo.add(lblSubtitulo);
		panelTitulo.add(Box.createRigidArea(new Dimension(0, 30)));

		centerPanel.add(panelTitulo, BorderLayout.NORTH);

		String[] columnas = {"ID Empleado", "Nombre Completo", "Departamento"};
		modeloTabla = new DefaultTableModel(null, columnas);
		tablaEmpleados = new JTable(modeloTabla);
		tablaEmpleados.setRowHeight(35);
		tablaEmpleados.setFont(new Font("Arial", Font.PLAIN, 15));
		tablaEmpleados.setForeground(Color.BLACK);
		tablaEmpleados.setGridColor(new Color(230, 230, 230));
		tablaEmpleados.setSelectionBackground(new Color(220, 235, 255));
		tablaEmpleados.setSelectionForeground(Color.BLACK);

		JTableHeader header = tablaEmpleados.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 15));
		header.setBackground(new Color(245, 247, 250));
		header.setForeground(new Color(50, 50, 50));
		header.setPreferredSize(new Dimension(100, 40));

		JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
		scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
		crudPanel.setBackground(Color.WHITE);

		JButton btnCrear = crearBotonCRUD("Agregar Nuevo", true);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegEmpleado modalReg = new RegEmpleado();
				modalReg.setVisible(true);
				cargarEmpleados();
			}
		});
		
		JButton btnLeer = crearBotonCRUD("Ver Detalles", false);
		
		JButton btnActualizar = crearBotonCRUD("Editar Seleccionado", false);
		
		JButton btnEliminar = crearBotonCRUD("Eliminar Empleado", false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaEmpleados.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idEmpleado = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar al empleado " + idEmpleado + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						Altice.getInstance().eliminarEmpleado(idEmpleado);
						cargarEmpleados();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		crudPanel.add(btnCrear);
		crudPanel.add(btnLeer);
		crudPanel.add(btnActualizar);
		crudPanel.add(btnEliminar);

		centerPanel.add(crudPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Módulo de Empleados");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);

		cargarEmpleados();
	}

	private void cargarEmpleados() {
		modeloTabla.setRowCount(0);
		for (Empleado e : Altice.getInstance().getEmpleados()) {
			Object[] fila = new Object[3];
			fila[0] = e.getIdEmpleado();
			fila[1] = e.getNombre();
			fila[2] = e.getDepartamento();
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