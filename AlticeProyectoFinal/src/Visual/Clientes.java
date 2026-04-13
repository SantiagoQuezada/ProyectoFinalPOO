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
import java.text.SimpleDateFormat;
import Logico.Cliente;
import Logico.Empleado;
import Logico.Altice;

public class Clientes extends JFrame {

	private DefaultTableModel modeloTabla;
	private JTable tablaClientes;
	private Empleado empleadoLogueado;

	public Clientes(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Altice - Módulo de Clientes");
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

		JLabel lblTituloPrincipal = new JLabel("👥 Listado de Clientes Activos");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 32));
		lblTituloPrincipal.setForeground(new Color(10, 10, 10));
		lblTituloPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Consulta y gestión integral de la cartera de clientes y sus planes.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTitulo.add(lblTituloPrincipal);
		panelTitulo.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTitulo.add(lblSubtitulo);

		centerPanel.add(panelTitulo, BorderLayout.NORTH);

		String[] columnas = {"ID", "Tipo", "Cédula / RNC", "Nombre o Empresa", "Teléfono", "Estado", "Plan Contratado", "Fecha Asignación"};
		modeloTabla = new DefaultTableModel(null, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tablaClientes = new JTable(modeloTabla);
		tablaClientes.setFillsViewportHeight(true);
		tablaClientes.setRowHeight(45);
		tablaClientes.setFont(new Font("Arial", Font.PLAIN, 14));
		tablaClientes.setForeground(new Color(15, 15, 15));
		tablaClientes.setGridColor(new Color(210, 210, 210));
		tablaClientes.setSelectionBackground(new Color(0, 102, 204));
		tablaClientes.setSelectionForeground(Color.WHITE);
		tablaClientes.setShowVerticalLines(false);
		tablaClientes.setShowHorizontalLines(true);
		tablaClientes.setIntercellSpacing(new Dimension(0, 0));
		tablaClientes.setBorder(BorderFactory.createEmptyBorder());

		JTableHeader header = tablaClientes.getTableHeader();
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
						c.setBackground(new Color(250, 250, 250));
					} else {
						c.setBackground(new Color(240, 240, 240));
					}
					c.setForeground(new Color(15, 15, 15));
				}
				return c;
			}
		};

		for (int i = 0; i < tablaClientes.getColumnCount(); i++) {
			tablaClientes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(60);
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(130);
		tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(220);
		tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(120);
		tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(80);
		tablaClientes.getColumnModel().getColumn(6).setPreferredWidth(180);
		tablaClientes.getColumnModel().getColumn(7).setPreferredWidth(130);

		JScrollPane scrollPane = new JScrollPane(tablaClientes);
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

		RoundedButton btnCrear = crearBotonCRUD("Registrar Cliente", new Color(0, 102, 204), new Color(0, 80, 160));
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegCliente modalReg = new RegCliente(null, false);
				modalReg.setVisible(true);
				cargarClientes();
			}
		});

		RoundedButton btnLeer = crearBotonCRUD("Ver Detalles", new Color(60, 60, 60), new Color(80, 80, 80));
		btnLeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaClientes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idCliente = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					Cliente cliente = Altice.getInstance().getClienteById(idCliente);
					RegCliente modalReg = new RegCliente(cliente, true);
					modalReg.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		RoundedButton btnActualizar = crearBotonCRUD("Editar Seleccionado", new Color(230, 126, 34), new Color(200, 100, 20));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaClientes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idCliente = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					Cliente cliente = Altice.getInstance().getClienteById(idCliente);
					RegCliente modalReg = new RegCliente(cliente, false);
					modalReg.setVisible(true);
					cargarClientes();
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		RoundedButton btnEliminar = crearBotonCRUD("Dar de Baja", new Color(220, 53, 69), new Color(180, 40, 50));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaClientes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String idCliente = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
					int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que desea dar de baja al cliente " + idCliente + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						Altice.getInstance().eliminarCliente(idCliente);
						cargarClientes();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
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
		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Módulo de Clientes");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 14));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);

		cargarClientes();
	}

	public Clientes() {
		this(null);
	}

	private void cargarClientes() {
		modeloTabla.setRowCount(0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		for (Cliente c : Altice.getInstance().getClientes()) {
			String nombrePlan;
			String fecha;
			if (c.getPlan() != null) {
				nombrePlan = c.getPlan().getNombre();
				if (c.getFechaAsignacionPlan() != null) {
					fecha = sdf.format(c.getFechaAsignacionPlan());
				} else {
					fecha = "N/A";
				}
			} else {
				nombrePlan = "Sin Plan";
				fecha = "N/A";
			}
			
			String identificacion;
			if (c.getTipoCliente().equals("Empresarial")) {
				identificacion = c.getRnc();
			} else {
				identificacion = c.getCedula();
			}
			
			Object[] fila = new Object[8];
			fila[0] = c.getIdCliente();
			fila[1] = c.getTipoCliente();
			fila[2] = identificacion;
			fila[3] = c.getNombre();
			fila[4] = c.getTelefono();
			fila[5] = c.getEstado();
			fila[6] = nombrePlan;
			fila[7] = fecha;
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