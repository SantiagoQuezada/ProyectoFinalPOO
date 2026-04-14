package Visual;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.DefaultListCellRenderer;
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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import Logico.Pago;
import Logico.Empleado;
import Logico.Altice;

public class Pagos extends JFrame {

	private DefaultTableModel modeloTabla;
	private JTable tablaPagos;
	private Empleado empleadoLogueado;

	private RoundedTextField txtBuscar;
	private RoundedComboBox<String> cbxFiltroMetodo;
	private RoundedComboBox<String> cbxFiltroConcepto;

	public Pagos(Empleado empleado) {
		this.empleadoLogueado = empleado;
		setTitle("Altice - Módulo de Pagos");
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
		String rolUsuario = (empleadoLogueado != null && empleadoLogueado.getUsuario() != null)
				? empleadoLogueado.getUsuario().getRol().toString()
				: "Admin";

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
		centerPanel.setBorder(new EmptyBorder(30, 60, 30, 60));

		JPanel headerAndFilterPanel = new JPanel(new BorderLayout(0, 15));
		headerAndFilterPanel.setBackground(new Color(245, 247, 250));

		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
		panelTitulo.setBackground(new Color(245, 247, 250));

		JLabel lblTituloPrincipal = new JLabel("Historial de Pagos y Facturación");
		lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 32));
		lblTituloPrincipal.setForeground(new Color(10, 10, 10));
		lblTituloPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel lblSubtitulo = new JLabel("Registro y consulta de todos los pagos realizados por los clientes.");
		lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(100, 100, 100));
		lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		panelTitulo.add(lblTituloPrincipal);
		panelTitulo.add(Box.createRigidArea(new Dimension(0, 8)));
		panelTitulo.add(lblSubtitulo);

		// --- Panel de Filtros ---
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		filterPanel.setBackground(Color.WHITE);
		filterPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel lblMetodo = new JLabel("Método:");
		lblMetodo.setFont(new Font("Arial", Font.BOLD, 14));
		cbxFiltroMetodo = new RoundedComboBox<>(15);
		cbxFiltroMetodo.addItem("Todos");
		cbxFiltroMetodo.addItem("Efectivo");
		cbxFiltroMetodo.addItem("Tarjeta de Crédito");
		cbxFiltroMetodo.addItem("Transferencia");
		cbxFiltroMetodo.setPreferredSize(new Dimension(170, 40));

		JLabel lblConcepto = new JLabel("Concepto:");
		lblConcepto.setFont(new Font("Arial", Font.BOLD, 14));
		cbxFiltroConcepto = new RoundedComboBox<>(15);
		cbxFiltroConcepto.addItem("Todos");
		cbxFiltroConcepto.addItem("Mensualidad");
		cbxFiltroConcepto.addItem("Instalación");
		cbxFiltroConcepto.addItem("Compra de Equipo");
		cbxFiltroConcepto.addItem("Otros");
		cbxFiltroConcepto.setPreferredSize(new Dimension(170, 40));

		JLabel lblBuscar = new JLabel("Buscar (Cliente/ID Pago):");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		txtBuscar = new RoundedTextField(15);
		txtBuscar.setPreferredSize(new Dimension(350, 40)); 
		txtBuscar.setFont(new Font("Arial", Font.PLAIN, 15));

		filterPanel.add(lblMetodo);
		filterPanel.add(cbxFiltroMetodo);
		filterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		filterPanel.add(lblConcepto);
		filterPanel.add(cbxFiltroConcepto);
		filterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		filterPanel.add(lblBuscar);
		filterPanel.add(txtBuscar);

		RoundedPanel filterWrapper = new RoundedPanel(20);
		filterWrapper.setLayout(new BorderLayout());
		filterWrapper.setBackground(Color.WHITE);
		filterWrapper.add(filterPanel, BorderLayout.CENTER);

		headerAndFilterPanel.add(panelTitulo, BorderLayout.NORTH);
		headerAndFilterPanel.add(filterWrapper, BorderLayout.CENTER);

		centerPanel.add(headerAndFilterPanel, BorderLayout.NORTH);

		
		String[] columnas = { "ID Pago", "Cédula / RNC", "Cliente", "Fecha", "Monto", "Concepto", "Método de Pago" };
		modeloTabla = new DefaultTableModel(null, columnas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaPagos = new JTable(modeloTabla);
		tablaPagos.setFillsViewportHeight(true);
		tablaPagos.setRowHeight(45);
		tablaPagos.setFont(new Font("Arial", Font.PLAIN, 14));
		tablaPagos.setForeground(new Color(15, 15, 15));
		tablaPagos.setGridColor(new Color(210, 210, 210));
		tablaPagos.setSelectionBackground(new Color(0, 102, 204));
		tablaPagos.setSelectionForeground(Color.WHITE);
		tablaPagos.setShowVerticalLines(false);
		tablaPagos.setShowHorizontalLines(true);
		tablaPagos.setIntercellSpacing(new Dimension(0, 0));
		tablaPagos.setBorder(BorderFactory.createEmptyBorder());

		JTableHeader header = tablaPagos.getTableHeader();
		header.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
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
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
				((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));

				if (!isSelected) {
					if (row % 2 == 0)
						c.setBackground(new Color(250, 250, 250));
					else
						c.setBackground(new Color(240, 240, 240));
					c.setForeground(new Color(15, 15, 15));
				}

				if (column == 4 && !isSelected) {
					c.setForeground(new Color(0, 150, 50));
					c.setFont(new Font("Arial", Font.BOLD, 14));
				} else if (column == 4 && isSelected) {
					c.setFont(new Font("Arial", Font.BOLD, 14));
				}
				return c;
			}
		};

		for (int i = 0; i < tablaPagos.getColumnCount(); i++) {
			tablaPagos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		tablaPagos.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaPagos.getColumnModel().getColumn(1).setPreferredWidth(130);
		tablaPagos.getColumnModel().getColumn(2).setPreferredWidth(220);
		tablaPagos.getColumnModel().getColumn(3).setPreferredWidth(140);
		tablaPagos.getColumnModel().getColumn(4).setPreferredWidth(100);
		tablaPagos.getColumnModel().getColumn(5).setPreferredWidth(150);
		tablaPagos.getColumnModel().getColumn(6).setPreferredWidth(150);

		JScrollPane scrollPane = new JScrollPane(tablaPagos);
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

		RoundedButton btnVolver = crearBotonCRUD("Volver al Inicio", new Color(0, 150, 136), new Color(0, 120, 110));
		btnVolver.addActionListener(e -> {
			Principal principal = new Principal(empleadoLogueado);
			principal.setVisible(true);
			dispose();
		});

		RoundedButton btnCrear = crearBotonCRUD("Registrar Pago", new Color(0, 102, 204), new Color(0, 80, 160));
		btnCrear.addActionListener(e -> {
			RegPago modalReg = new RegPago(null, false);
			modalReg.setVisible(true);
			cargarPagos();
		});

		RoundedButton btnLeer = crearBotonCRUD("Ver Recibo", new Color(60, 60, 60), new Color(80, 80, 80));
		btnLeer.addActionListener(e -> {
			int filaSeleccionada = tablaPagos.getSelectedRow();
			if (filaSeleccionada >= 0) {
				String idPago = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
				Pago pago = null;
				for (Pago p : Altice.getInstance().getPagos()) {
					if (p.getIdPago().equals(idPago)) {
						pago = p;
						break;
					}
				}
				if (pago != null) {
					RegPago modalReg = new RegPago(pago, true);
					modalReg.setVisible(true);
				}
			} else {
				javax.swing.JOptionPane.showMessageDialog(null, "Debe seleccionar un pago de la tabla.", "Atención",
						javax.swing.JOptionPane.WARNING_MESSAGE);
			}
		});

		crudPanel.add(btnVolver);
		crudPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		crudPanel.add(btnCrear);
		crudPanel.add(btnLeer);

		centerPanel.add(crudPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footerPanel.setBackground(new Color(245, 247, 250));
		footerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Módulo de Pagos");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 14));
		lblFooter.setForeground(new Color(150, 150, 150));
		footerPanel.add(lblFooter);

		add(footerPanel, BorderLayout.SOUTH);


		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cargarPagos();
			}
		});
		cbxFiltroMetodo.addActionListener(e -> cargarPagos());
		cbxFiltroConcepto.addActionListener(e -> cargarPagos());

		cargarPagos();
	}

	public Pagos() {
		this(null);
	}

	private void cargarPagos() {
		modeloTabla.setRowCount(0);

		String busq = txtBuscar != null ? txtBuscar.getText().toLowerCase() : "";
		String fMetodo = cbxFiltroMetodo != null ? cbxFiltroMetodo.getSelectedItem().toString() : "Todos";
		String fConcepto = cbxFiltroConcepto != null ? cbxFiltroConcepto.getSelectedItem().toString() : "Todos";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		for (Pago p : Altice.getInstance().getPagos()) {
			String identificacion = p.getCliente().getTipoCliente().equals("Empresarial") ? p.getCliente().getRnc()
					: p.getCliente().getCedula();

			boolean matchBusq = p.getCliente().getNombre().toLowerCase().contains(busq)
					|| p.getIdPago().toLowerCase().contains(busq) || identificacion.contains(busq);

			boolean matchMetodo = fMetodo.equals("Todos") || p.getMetodoPago().equalsIgnoreCase(fMetodo);
			boolean matchConcepto = fConcepto.equals("Todos") || p.getConcepto().equalsIgnoreCase(fConcepto);

			if (matchBusq && matchMetodo && matchConcepto) {
				Object[] fila = new Object[7];
				fila[0] = p.getIdPago();
				fila[1] = identificacion;
				fila[2] = p.getCliente().getNombre();
				fila[3] = sdf.format(p.getFecha());
				fila[4] = String.format("$%.2f", p.getMonto());
				fila[5] = p.getConcepto();
				fila[6] = p.getMetodoPago();
				modeloTabla.addRow(fila);
			}
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
		public int getIconWidth() {
			return 24;
		}

		@Override
		public int getIconHeight() {
			return 24;
		}
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
				public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
				}
			});

			setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					label.setBorder(new EmptyBorder(8, 10, 8, 10));
					label.setFont(new Font("Arial", Font.BOLD, 14));
					if (index == -1) {
						label.setOpaque(false);
						if (RoundedComboBox.this.isEnabled())
							label.setForeground(new Color(50, 50, 50));
						else
							label.setForeground(new Color(150, 150, 150));
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
			g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
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