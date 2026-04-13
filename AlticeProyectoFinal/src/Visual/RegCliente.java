package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.awt.Rectangle; 
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;
import Logico.Contrato;
import Logico.Cliente;
import Logico.Plan;
import Logico.Altice;

public class RegCliente extends JDialog {

	private RoundedTextField txtIdCliente;
	private RoundedTextField txtNombre;
	private RoundedTextField txtCedula;
	private RoundedTextField txtTelefono;
	private RoundedTextField txtDireccion;
	private RoundedComboBox<String> cbxTipoCliente;
	private RoundedTextField txtRnc;
	private RoundedComboBox<String> cbxPlan;
	private RoundedComboBox<String> cbxDuracion;
	
	private Cliente clienteActual;

	public RegCliente() {
		this(null, false);
	}

	public RegCliente(Cliente cliente, boolean soloLectura) {
		this.clienteActual = cliente;
		
		setModal(true);
		setResizable(false);
		setSize(800, 550);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
		headerPanel.setBackground(new Color(15, 15, 15));
		headerPanel.setPreferredSize(new Dimension(800, 70));
		
		JLabel lblDialogTitle = new JLabel(clienteActual == null ? "👤 Registrar Nuevo Cliente" : "👤 Información de Cliente");
		lblDialogTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lblDialogTitle.setForeground(Color.WHITE);
		headerPanel.add(lblDialogTitle);
		getContentPane().add(headerPanel, BorderLayout.NORTH);

		JPanel centerContainer = new JPanel(new BorderLayout());
		centerContainer.setBackground(new Color(245, 247, 250));
		centerContainer.setBorder(new EmptyBorder(20, 25, 10, 25));

		RoundedPanel contentPanel = new RoundedPanel(25);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		centerContainer.add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(centerContainer, BorderLayout.CENTER);

		Font labelFont = new Font("Arial", Font.BOLD, 14);
		Color labelColor = new Color(70, 70, 70);

		JLabel lblIdCliente = new JLabel("ID Cliente:");
		lblIdCliente.setFont(labelFont);
		lblIdCliente.setForeground(labelColor);
		lblIdCliente.setBounds(30, 30, 110, 35);
		contentPanel.add(lblIdCliente);

		txtIdCliente = new RoundedTextField(15);
		txtIdCliente.setFont(new Font("Arial", Font.PLAIN, 14));
		txtIdCliente.setText(clienteActual == null ? "CLI-" + String.format("%04d", Altice.getInstance().getClientes().size() + 1) : clienteActual.getIdCliente());
		txtIdCliente.setEditable(false);
		txtIdCliente.setBackground(new Color(240, 240, 240));
		txtIdCliente.setBounds(140, 30, 210, 35);
		contentPanel.add(txtIdCliente);

		JLabel lblTipoCliente = new JLabel("Tipo Cliente:");
		lblTipoCliente.setFont(labelFont);
		lblTipoCliente.setForeground(labelColor);
		lblTipoCliente.setBounds(390, 30, 120, 35);
		contentPanel.add(lblTipoCliente);

		cbxTipoCliente = new RoundedComboBox<String>(15);
		cbxTipoCliente.addItem("Personal");
		cbxTipoCliente.addItem("Empresarial");
		cbxTipoCliente.setBounds(510, 30, 210, 35);
		contentPanel.add(cbxTipoCliente);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(labelFont);
		lblNombre.setForeground(labelColor);
		lblNombre.setBounds(30, 90, 110, 35);
		contentPanel.add(lblNombre);

		txtNombre = new RoundedTextField(15);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
		txtNombre.setBounds(140, 90, 210, 35);
		contentPanel.add(txtNombre);

		JLabel lblRnc = new JLabel("RNC:");
		lblRnc.setFont(labelFont);
		lblRnc.setForeground(labelColor);
		lblRnc.setBounds(390, 90, 120, 35);
		contentPanel.add(lblRnc);

		txtRnc = new RoundedTextField(15);
		txtRnc.setFont(new Font("Arial", Font.PLAIN, 14));
		txtRnc.setBounds(510, 90, 210, 35);
		txtRnc.setEnabled(false);
		txtRnc.setBackground(new Color(240, 240, 240));
		contentPanel.add(txtRnc);

		cbxTipoCliente.addActionListener(e -> {
			if (cbxTipoCliente.getSelectedIndex() == 1) {
				txtRnc.setEnabled(true);
				txtRnc.setBackground(Color.WHITE);
			} else {
				txtRnc.setEnabled(false);
				txtRnc.setBackground(new Color(240, 240, 240));
				txtRnc.setText("");
			}
		});

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setFont(labelFont);
		lblCedula.setForeground(labelColor);
		lblCedula.setBounds(30, 150, 110, 35);
		contentPanel.add(lblCedula);

		txtCedula = new RoundedTextField(15);
		txtCedula.setFont(new Font("Arial", Font.PLAIN, 14));
		txtCedula.setBounds(140, 150, 210, 35);
		contentPanel.add(txtCedula);

		JLabel lblPlan = new JLabel("Plan a Contratar:");
		lblPlan.setFont(labelFont);
		lblPlan.setForeground(labelColor);
		lblPlan.setBounds(390, 150, 120, 35);
		contentPanel.add(lblPlan);

		cbxPlan = new RoundedComboBox<String>(15);
		if (Altice.getInstance().getPlanes().isEmpty()) {
			cbxPlan.addItem("Sin planes disponibles");
			cbxPlan.setEnabled(false);
		} else {
			for (Plan p : Altice.getInstance().getPlanes()) {
				cbxPlan.addItem(p.getNombre() + " - $" + p.getPrecio());
			}
		}
		cbxPlan.setBounds(510, 150, 210, 35);
		contentPanel.add(cbxPlan);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setFont(labelFont);
		lblTelefono.setForeground(labelColor);
		lblTelefono.setBounds(30, 210, 110, 35);
		contentPanel.add(lblTelefono);

		txtTelefono = new RoundedTextField(15);
		txtTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTelefono.setBounds(140, 210, 210, 35);
		contentPanel.add(txtTelefono);

		JLabel lblDuracion = new JLabel("Tiempo Contrato:");
		lblDuracion.setFont(labelFont);
		lblDuracion.setForeground(labelColor);
		lblDuracion.setBounds(390, 210, 130, 35);
		contentPanel.add(lblDuracion);

		cbxDuracion = new RoundedComboBox<String>(15);
		cbxDuracion.addItem("12 Meses");
		cbxDuracion.addItem("18 Meses");
		cbxDuracion.addItem("24 Meses");
		cbxDuracion.setBounds(520, 210, 200, 35);
		contentPanel.add(cbxDuracion);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setFont(labelFont);
		lblDireccion.setForeground(labelColor);
		lblDireccion.setBounds(30, 270, 110, 35);
		contentPanel.add(lblDireccion);

		txtDireccion = new RoundedTextField(15);
		txtDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
		txtDireccion.setBounds(140, 270, 580, 35);
		contentPanel.add(txtDireccion);

		if (clienteActual != null) {
			txtNombre.setText(clienteActual.getNombre());
			txtCedula.setText(clienteActual.getCedula());
			txtTelefono.setText(clienteActual.getTelefono());
			txtDireccion.setText(clienteActual.getDireccion());
			if (clienteActual.getTipoCliente() != null && clienteActual.getTipoCliente().equalsIgnoreCase("Empresarial")) {
				cbxTipoCliente.setSelectedIndex(1);
				txtRnc.setText(clienteActual.getRnc());
			} else {
				cbxTipoCliente.setSelectedIndex(0);
			}
			
			if (clienteActual.getPlan() != null && cbxPlan.isEnabled()) {
				for (int i = 0; i < Altice.getInstance().getPlanes().size(); i++) {
					if (Altice.getInstance().getPlanes().get(i).getIdPlan().equals(clienteActual.getPlan().getIdPlan())) {
						cbxPlan.setSelectedIndex(i);
						break;
					}
				}
			}
			
			if (clienteActual.getContrato() != null) {
				int duracion = clienteActual.getContrato().getDuracionMeses();
				if (duracion == 12) cbxDuracion.setSelectedIndex(0);
				else if (duracion == 18) cbxDuracion.setSelectedIndex(1);
				else if (duracion == 24) cbxDuracion.setSelectedIndex(2);
			}
			
			if (soloLectura) {
				txtNombre.setEditable(false);
				txtCedula.setEditable(false);
				txtTelefono.setEditable(false);
				txtDireccion.setEditable(false);
				txtRnc.setEditable(false);
				cbxTipoCliente.setEnabled(false);
				cbxPlan.setEnabled(false);
				cbxDuracion.setEnabled(false);
			}
		}

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		buttonPane.setBackground(new Color(245, 247, 250));
		buttonPane.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		RoundedButton btnCancelar = new RoundedButton(soloLectura ? "Cerrar" : "Cancelar", 20);
		btnCancelar.setBackground(new Color(200, 200, 200));
		btnCancelar.setForeground(new Color(30, 30, 30));
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCancelar.setPreferredSize(new Dimension(130, 45));
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(e -> dispose());
		buttonPane.add(btnCancelar);

		if (!soloLectura) {
			RoundedButton btnRegistrar = new RoundedButton(clienteActual == null ? "Registrar Cliente" : "Guardar Cambios", 20);
			btnRegistrar.setBackground(new Color(0, 102, 204)); 
			btnRegistrar.setForeground(Color.WHITE);
			btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
			btnRegistrar.setPreferredSize(new Dimension(180, 45));
			btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			btnRegistrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 80, 180)); }
				@Override
				public void mouseExited(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 102, 204)); }
			});
	
			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nombre = txtNombre.getText().trim();
					String cedula = txtCedula.getText().trim();
					String telefono = txtTelefono.getText().trim();
					String direccion = txtDireccion.getText().trim();
					String rnc = cbxTipoCliente.getSelectedIndex() == 1 ? txtRnc.getText().trim() : "";

					// 1. Validar campos vacíos
					if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe completar todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (cbxTipoCliente.getSelectedIndex() == 1 && rnc.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar el RNC para clientes empresariales.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (Altice.getInstance().getPlanes().isEmpty()) {
						JOptionPane.showMessageDialog(null, "No puede registrar un cliente sin planes disponibles en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// 2. Validaciones con Regex para evitar números en texto y letras en números
					if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
						JOptionPane.showMessageDialog(null, "Dato '" + nombre + "' no válido en la parte de Nombre.\nNo se permiten números ni caracteres especiales.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (!cedula.matches("^[0-9\\-]+$")) {
						JOptionPane.showMessageDialog(null, "Dato '" + cedula + "' no válido en la parte de Cédula.\nSolo se permiten números y guiones.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (!telefono.matches("^[0-9\\-\\+\\s]+$")) {
						JOptionPane.showMessageDialog(null, "Dato '" + telefono + "' no válido en la parte de Teléfono.\nSolo se permiten números, guiones y el signo +.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (cbxTipoCliente.getSelectedIndex() == 1 && !rnc.matches("^[0-9\\-]+$")) {
						JOptionPane.showMessageDialog(null, "Dato '" + rnc + "' no válido en la parte de RNC.\nSolo se permiten números y guiones.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
	
					String idCliente = txtIdCliente.getText();
					String tipoCliente = cbxTipoCliente.getSelectedItem().toString();
					Plan planSeleccionado = Altice.getInstance().getPlanes().get(cbxPlan.getSelectedIndex());
					int duracion = Integer.parseInt(cbxDuracion.getSelectedItem().toString().split(" ")[0]);
	
					if (clienteActual == null) {
						Date fechaInicio = new Date();
						Calendar cal = Calendar.getInstance();
						cal.setTime(fechaInicio);
						cal.add(Calendar.MONTH, duracion);
						Date fechaFin = cal.getTime();
		
						String idContrato = "CON-" + String.format("%04d", Altice.getInstance().getClientes().size() + 1);
						Contrato nuevoContrato = new Contrato(idContrato, fechaInicio, fechaFin, "Activo", null, duracion);
						
						Cliente nuevoCliente = new Cliente(cedula, nombre, telefono, direccion, idCliente, tipoCliente, rnc, planSeleccionado, nuevoContrato, "Activo");
						nuevoCliente.agregarDeuda(planSeleccionado.getPrecio());
		
						Altice.getInstance().getClientes().add(nuevoCliente);
						JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente con una deuda inicial de $" + planSeleccionado.getPrecio(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
					} else {
						clienteActual.setNombre(nombre);
						clienteActual.setCedula(cedula);
						clienteActual.setTelefono(telefono);
						clienteActual.setDireccion(direccion);
						clienteActual.setTipoCliente(tipoCliente);
						clienteActual.setRnc(rnc);
						clienteActual.setPlan(planSeleccionado);
						if (clienteActual.getContrato() != null) {
							clienteActual.getContrato().setDuracionMeses(duracion);
						}
						JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					}
					
					dispose();
				}
			});
			buttonPane.add(btnRegistrar);
			getRootPane().setDefaultButton(btnRegistrar);
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
			g2.setColor(new Color(0, 0, 0, 15));
			g2.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, radius, radius);
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, radius, radius);
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