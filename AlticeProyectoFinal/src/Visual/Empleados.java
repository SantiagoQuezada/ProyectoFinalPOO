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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.BorderFactory;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Logico.Empleado;
import Logico.Altice;

public class Empleados extends JFrame {

	private DefaultTableModel modeloTabla;
	private JTable tablaEmpleados;
	private Empleado empleadoLogueado;

	public Empleados(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Altice - Módulo de Empleados");
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

		String nombreUsuario;
		if (empleadoLogueado != null) {
			nombreUsuario = empleadoLogueado.getNombre();
		} else {
			nombreUsuario = "Usuario";
		}

		String rolUsuario;
		if (empleadoLogueado != null && empleadoLogueado.getUsuario() != null) {
			rolUsuario = empleadoLogueado.getUsuario().getRol().toString();
		} else {
			rolUsuario = "Admin";
		}
		
		JLabel lblUser = new JLabel(" Hola, " + nombreUsuario + " (" + rolUsuario + ")");
		lblUser.setIcon(new UserIcon());
		lblUser.setFont(new Font("Arial", Font.BOLD, 17));
		lblUser.setForeground(new Color(220, 220, 220));
		
		rightHeaderPanel.add(lblUser);
		headerPanel.add(rightHeaderPanel, BorderLayout.EAST);

		add(headerPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(new Color(245, 247, 250));
		centerPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
		panelTitulo.setBackground(new Color(245, 247, 250));
		panelTitulo.setBorder(new EmptyBorder(0, 0, 30, 0));

		JLabel lblTituloPrincipal = new JLabel("👔 Nómina de Empleados");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 32));
		lblTituloPrincipal.setForeground(new Color(10, 10, 10));
		lblTituloPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Gestión del personal y sus cargos dentro de la empresa.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTitulo.add(lblTituloPrincipal);
		panelTitulo.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTitulo.add(lblSubtitulo);

		centerPanel.add(panelTitulo, BorderLayout.NORTH);

		String[] columnas = {"ID", "Cédula", "Nombre Completo", "Teléfono", "Departamento", "Rol de Sistema", "Salario"};
		modeloTabla = new DefaultTableModel(null, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tablaEmpleados = new JTable(modeloTabla);
		tablaEmpleados.setFillsViewportHeight(true);
		tablaEmpleados.setRowHeight(45);
		tablaEmpleados.setFont(new Font("Arial", Font.PLAIN, 14));
		tablaEmpleados.setForeground(new Color(15, 15, 15));
		tablaEmpleados.setGridColor(new Color(210, 210, 210));
		tablaEmpleados.setSelectionBackground(new Color(0, 102, 204));
		tablaEmpleados.setSelectionForeground(Color.WHITE);
		tablaEmpleados.setShowVerticalLines(false);
		tablaEmpleados.setShowHorizontalLines(true);
		tablaEmpleados.setIntercellSpacing(new Dimension(0, 0));
		tablaEmpleados.setBorder(BorderFactory.createEmptyBorder());

		JTableHeader header = tablaEmpleados.getTableHeader();
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
		header.setPreferredSize(new Dimension(100, 50));
		header.setReorderingAllowed(false);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
				((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));
				
				if (!isSelected) {
					if (row % 2 == 0) {
						c.setBackground(new Color(245, 245, 245));
					} else {
						c.setBackground(new Color(235, 235, 235));
					}
					c.setForeground(new Color(15, 15, 15));
				}
				return c;
			}
		};

		for (int i = 0; i < tablaEmpleados.getColumnCount(); i++) {
			tablaEmpleados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(60);
		tablaEmpleados.getColumnModel().getColumn(1).setPreferredWidth(120);
		tablaEmpleados.getColumnModel().getColumn(2).setPreferredWidth(250);
		tablaEmpleados.getColumnModel().getColumn(3).setPreferredWidth(120);
		tablaEmpleados.getColumnModel().getColumn(4).setPreferredWidth(150);
		tablaEmpleados.getColumnModel().getColumn(5).setPreferredWidth(120);
		tablaEmpleados.getColumnModel().getColumn(6).setPreferredWidth(100);

		JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.getViewport().setBackground(Color.WHITE);

		RoundedPanel tableWrapper = new RoundedPanel(20);
		tableWrapper.setLayout(new BorderLayout());
		tableWrapper.setBackground(Color.WHITE);
		tableWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
		tableWrapper.add(scrollPane, BorderLayout.CENTER);

		centerPanel.add(tableWrapper, BorderLayout.CENTER);

		JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
		crudPanel.setBackground(new Color(245, 247, 250));

		RoundedButton btnVolver = crearBotonCRUD("\u25C0 Volver al Inicio", new Color(40, 40, 40), new Color(60, 60, 60));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal principal = new Principal(empleadoLogueado);
				principal.setVisible(true);
				dispose();
			}
		});

		RoundedButton btnCrear = crearBotonCRUD("Agregar Empleado", new Color(0, 102, 204), new Color(0, 80, 160));
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegEmpleado modalReg = new RegEmpleado(null, false);
				modalReg.setVisible(true);
				cargarEmpleados();
			}
		});

		RoundedButton btnLeer = crearBotonCRUD("Ver Detalles", new Color(60, 60, 60), new Color(80, 80, 80));
		btnLeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaEmpleados.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idEmpleado = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					Empleado empleado = Altice.getInstance().getEmpleadoById(idEmpleado);
					RegEmpleado modalReg = new RegEmpleado(empleado, true);
					modalReg.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		RoundedButton btnActualizar = crearBotonCRUD("Editar Seleccionado", new Color(230, 126, 34), new Color(200, 100, 20));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaEmpleados.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idEmpleado = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					Empleado empleado = Altice.getInstance().getEmpleadoById(idEmpleado);
					RegEmpleado modalReg = new RegEmpleado(empleado, false);
					modalReg.setVisible(true);
					cargarEmpleados();
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		RoundedButton btnEliminar = crearBotonCRUD("Eliminar Empleado", new Color(220, 53, 69), new Color(180, 40, 50));
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

		crudPanel.add(btnVolver);
		crudPanel.add(Box.createRigidArea(new Dimension(30, 0))); // Separador
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
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 14));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);

		cargarEmpleados();
	}

	public Empleados() {
		this(null);
	}

	private void cargarEmpleados() {
		modeloTabla.setRowCount(0);
		for (Empleado e : Altice.getInstance().getEmpleados()) {
			Object[] fila = new Object[7];
			fila[0] = e.getIdEmpleado();
			fila[1] = e.getCedula();
			fila[2] = e.getNombre();
			fila[3] = e.getTelefono();
			fila[4] = e.getDepartamento();
			fila[5] = e.getUsuario() != null ? e.getUsuario().getRol().toString() : "N/A";
			fila[6] = String.format("$%.2f", e.getSalario());
			modeloTabla.addRow(fila);
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