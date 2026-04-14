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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;
import Logico.Empleado;
import Logico.Altice;
import Logico.Rol;
import Logico.Usuario;

public class RegEmpleado extends JDialog {

	private RoundedTextField txtIdEmpleado;
	private RoundedTextField txtCedula;
	private RoundedTextField txtNombre;
	private RoundedTextField txtTelefono;
	private RoundedTextField txtDireccion;
	private RoundedTextField txtSalario;
	private RoundedComboBox<String> cbxDepartamento;
	private RoundedTextField txtUsername;
	private RoundedPasswordField txtPassword;
	private RoundedComboBox<Rol> cbxRol;
	private Empleado empleadoActual;

	public RegEmpleado(Empleado empleado, boolean soloLectura) {
		this.empleadoActual = empleado;
		
		setModal(true);
		setResizable(false);
		setSize(550, 760); // Ventana más grande para que no se corte el Rol
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		// --- Header Panel ---
		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		headerPanel.setPreferredSize(new Dimension(550, 70));
		
		String tituloHeader = "Registrar Nuevo Empleado";
		if (empleadoActual != null) {
			tituloHeader = soloLectura ? "Detalles del Empleado" : "Modificar Empleado";
		}
		
		JLabel lblDialogTitle = new JLabel(tituloHeader);
		lblDialogTitle.setFont(new Font("Arial", Font.BOLD, 22));
		lblDialogTitle.setForeground(Color.WHITE);
		headerPanel.add(lblDialogTitle);
		getContentPane().add(headerPanel, BorderLayout.NORTH);

		// --- Main Content ---
		JPanel centerContainer = new JPanel(new BorderLayout());
		centerContainer.setBackground(new Color(245, 247, 250));
		centerContainer.setBorder(new EmptyBorder(20, 25, 10, 25));

		RoundedPanel contentPanel = new RoundedPanel(25);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		centerContainer.add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(centerContainer, BorderLayout.CENTER);

		Font labelFont = new Font("Arial", Font.BOLD, 14);
		Color labelColor = new Color(50, 50, 50);

		JLabel lblIdEmpleado = new JLabel("ID Empleado:");
		lblIdEmpleado.setFont(labelFont);
		lblIdEmpleado.setForeground(labelColor);
		lblIdEmpleado.setBounds(30, 30, 140, 35);
		contentPanel.add(lblIdEmpleado);

		txtIdEmpleado = new RoundedTextField(15);
		txtIdEmpleado.setFont(new Font("Arial", Font.PLAIN, 14));
		if (empleadoActual == null) {
			txtIdEmpleado.setText(Altice.getInstance().generarIdEmpleado());
		} else {
			txtIdEmpleado.setText(empleadoActual.getIdEmpleado());
		}
		txtIdEmpleado.setEditable(false);
		txtIdEmpleado.setBackground(new Color(240, 240, 240));
		txtIdEmpleado.setBounds(180, 30, 280, 35);
		contentPanel.add(txtIdEmpleado);

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setFont(labelFont);
		lblCedula.setForeground(labelColor);
		lblCedula.setBounds(30, 80, 140, 35);
		contentPanel.add(lblCedula);

		txtCedula = new RoundedTextField(15);
		txtCedula.setFont(new Font("Arial", Font.PLAIN, 14));
		txtCedula.setBounds(180, 80, 280, 35);
		aplicarFormato(txtCedula, "Cedula");
		contentPanel.add(txtCedula);

		txtCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) txtNombre.requestFocus();
			}
		});

		JLabel lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setFont(labelFont);
		lblNombre.setForeground(labelColor);
		lblNombre.setBounds(30, 130, 140, 35);
		contentPanel.add(lblNombre);

		txtNombre = new RoundedTextField(15);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
		txtNombre.setBounds(180, 130, 280, 35);
		contentPanel.add(txtNombre);

		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) txtTelefono.requestFocus();
			}
		});

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setFont(labelFont);
		lblTelefono.setForeground(labelColor);
		lblTelefono.setBounds(30, 180, 140, 35);
		contentPanel.add(lblTelefono);

		txtTelefono = new RoundedTextField(15);
		txtTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTelefono.setBounds(180, 180, 280, 35);
		aplicarFormato(txtTelefono, "Telefono");
		contentPanel.add(txtTelefono);

		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) txtDireccion.requestFocus();
			}
		});

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setFont(labelFont);
		lblDireccion.setForeground(labelColor);
		lblDireccion.setBounds(30, 230, 140, 35);
		contentPanel.add(lblDireccion);

		txtDireccion = new RoundedTextField(15);
		txtDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
		txtDireccion.setBounds(180, 230, 280, 35);
		contentPanel.add(txtDireccion);

		txtDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) cbxDepartamento.requestFocus();
			}
		});

		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setFont(labelFont);
		lblDepartamento.setForeground(labelColor);
		lblDepartamento.setBounds(30, 280, 140, 35);
		contentPanel.add(lblDepartamento);

		cbxDepartamento = new RoundedComboBox<String>(15);
		cbxDepartamento.addItem("Ventas");
		cbxDepartamento.addItem("Soporte Técnico");
		cbxDepartamento.addItem("Atención al Cliente");
		cbxDepartamento.addItem("Administración");
		cbxDepartamento.setBounds(180, 280, 280, 35);
		contentPanel.add(cbxDepartamento);

		JLabel lblSalario = new JLabel("Salario (RD$):");
		lblSalario.setFont(labelFont);
		lblSalario.setForeground(labelColor);
		lblSalario.setBounds(30, 330, 140, 35);
		contentPanel.add(lblSalario);

		txtSalario = new RoundedTextField(15);
		txtSalario.setFont(new Font("Arial", Font.PLAIN, 14));
		
		// Validar que solo acepte números y un punto decimal
		txtSalario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != '.') {
					e.consume();
				}
				if (c == '.' && txtSalario.getText().contains(".")) {
					e.consume();
				}
			}
		});
		txtSalario.setBounds(180, 330, 280, 35);
		contentPanel.add(txtSalario);

		JLabel lblUsername = new JLabel("Usuario Sis:");
		lblUsername.setFont(labelFont);
		lblUsername.setForeground(labelColor);
		lblUsername.setBounds(30, 380, 140, 35);
		contentPanel.add(lblUsername);

		txtUsername = new RoundedTextField(15);
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		txtUsername.setBounds(180, 380, 280, 35);
		contentPanel.add(txtUsername);

		JLabel lblPassword = new JLabel("Contraseña Sis:");
		lblPassword.setFont(labelFont);
		lblPassword.setForeground(labelColor);
		lblPassword.setBounds(30, 430, 140, 35);
		contentPanel.add(lblPassword);

		txtPassword = new RoundedPasswordField(15);
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPassword.setBounds(180, 430, 280, 35);
		contentPanel.add(txtPassword);

		JLabel lblRol = new JLabel("Rol de Sistema:");
		lblRol.setFont(labelFont);
		lblRol.setForeground(labelColor);
		lblRol.setBounds(30, 480, 140, 35);
		contentPanel.add(lblRol);

		cbxRol = new RoundedComboBox<Rol>(15);
		for (Rol rol : Rol.values()) {
			cbxRol.addItem(rol);
		}
		cbxRol.setBounds(180, 480, 280, 35);
		contentPanel.add(cbxRol);

		if (empleadoActual != null) {
			txtCedula.setText(empleadoActual.getCedula());
			txtNombre.setText(empleadoActual.getNombre());
			txtTelefono.setText(empleadoActual.getTelefono());
			txtDireccion.setText(empleadoActual.getDireccion());
			cbxDepartamento.setSelectedItem(empleadoActual.getDepartamento());
			txtSalario.setText(String.valueOf(empleadoActual.getSalario()));
			txtUsername.setText(empleadoActual.getUsuario().getUsername());
			txtPassword.setText(empleadoActual.getUsuario().getPassword());
			cbxRol.setSelectedItem(empleadoActual.getUsuario().getRol());
		}

		if (soloLectura) {
			txtCedula.setEditable(false);
			txtNombre.setEditable(false);
			txtTelefono.setEditable(false);
			txtDireccion.setEditable(false);
			cbxDepartamento.setEnabled(false);
			txtSalario.setEditable(false);
			txtUsername.setEditable(false);
			txtPassword.setEditable(false);
			cbxRol.setEnabled(false);
			
			Color colorDeshabilitado = new Color(245, 245, 245);
			txtCedula.setBackground(colorDeshabilitado);
			txtNombre.setBackground(colorDeshabilitado);
			txtTelefono.setBackground(colorDeshabilitado);
			txtDireccion.setBackground(colorDeshabilitado);
			txtSalario.setBackground(colorDeshabilitado);
			txtUsername.setBackground(colorDeshabilitado);
			txtPassword.setBackground(colorDeshabilitado);
			cbxDepartamento.setBackground(colorDeshabilitado);
			cbxRol.setBackground(colorDeshabilitado);
		}

		// --- Footer Buttons ---
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		buttonPane.setBackground(new Color(245, 247, 250));
		buttonPane.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		RoundedButton btnCancelar = new RoundedButton(soloLectura ? "Cerrar" : "Cancelar", 20);
		btnCancelar.setBackground(new Color(220, 53, 69)); // Rojo
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCancelar.setPreferredSize(new Dimension(120, 40));
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { btnCancelar.setBackground(new Color(180, 40, 50)); }
			@Override
			public void mouseExited(MouseEvent e) { btnCancelar.setBackground(new Color(220, 53, 69)); }
		});
		
		btnCancelar.addActionListener(e -> dispose());

		if (!soloLectura) {
			RoundedButton btnRegistrar = new RoundedButton(empleadoActual == null ? "Registrar" : "Guardar Cambios", 20);
			btnRegistrar.setBackground(new Color(0, 102, 204));
			btnRegistrar.setForeground(Color.WHITE);
			btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
			btnRegistrar.setPreferredSize(new Dimension(160, 40));
			btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			btnRegistrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 80, 160)); }
				@Override
				public void mouseExited(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 102, 204)); }
			});

			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtSalario.getText().isEmpty() || txtUsername.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						String id = txtIdEmpleado.getText();
						String cedula = txtCedula.getText();
						String nombre = txtNombre.getText();
						String telefono = txtTelefono.getText();
						String direccion = txtDireccion.getText();
						String departamento = cbxDepartamento.getSelectedItem().toString();
						float salario = Float.parseFloat(txtSalario.getText());
						String username = txtUsername.getText();
						String password = new String(txtPassword.getPassword());
						Rol rol = (Rol) cbxRol.getSelectedItem();
						String estado = (empleadoActual == null) ? "Activo" : empleadoActual.getEstado();

						// --- VALIDACIONES DE LONGITUD ---
						String cedulaRaw = cedula.replaceAll("-", "");
						if (cedulaRaw.length() != 11) {
							JOptionPane.showMessageDialog(null, "La cédula debe contener exactamente 11 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
							return;
						}

						String telefonoRaw = telefono.replaceAll("-", "");
						if (telefonoRaw.length() != 10) {
							JOptionPane.showMessageDialog(null, "El teléfono debe contener exactamente 10 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
							return;
						}

						// --- VALIDACIONES DE UNICIDAD ---
						for (Empleado emp : Altice.getInstance().getEmpleados()) {
							if (empleadoActual != null && emp.getIdEmpleado().equals(empleadoActual.getIdEmpleado())) {
								continue; // Saltar el empleado actual si estamos modificando
							}

							if (emp.getCedula().equals(cedula)) {
								JOptionPane.showMessageDialog(null, "Ya existe un empleado registrado con la cédula: " + cedula, "Dato Duplicado", JOptionPane.ERROR_MESSAGE);
								return;
							}

							if (emp.getTelefono().equals(telefono)) {
								JOptionPane.showMessageDialog(null, "Ya existe un empleado registrado con el teléfono: " + telefono, "Dato Duplicado", JOptionPane.ERROR_MESSAGE);
								return;
							}

							if (emp.getUsuario() != null && emp.getUsuario().getUsername().equalsIgnoreCase(username)) {
								JOptionPane.showMessageDialog(null, "El nombre de usuario '" + username + "' ya está en uso. Intente con otro.", "Usuario Duplicado", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}

						if (empleadoActual == null) {
							Usuario nuevoUsuario = new Usuario(username, password, rol);
							Empleado nuevoEmpleado = new Empleado(cedula, nombre, telefono, direccion, id, departamento, salario, nuevoUsuario, estado);
							Altice.getInstance().registrarEmpleado(nuevoEmpleado);
							JOptionPane.showMessageDialog(null, "Empleado registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
						} else {
							empleadoActual.setCedula(cedula);
							empleadoActual.setNombre(nombre);
							empleadoActual.setTelefono(telefono);
							empleadoActual.setDireccion(direccion);
							empleadoActual.setDepartamento(departamento);
							empleadoActual.setSalario(salario);
							empleadoActual.getUsuario().setUsername(username);
							empleadoActual.getUsuario().setPassword(password);
							empleadoActual.getUsuario().setRol(rol);
							empleadoActual.setEstado(estado);
							JOptionPane.showMessageDialog(null, "Empleado actualizado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
						}
						
						dispose();
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "El salario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			buttonPane.add(btnCancelar);
			buttonPane.add(btnRegistrar);
			getRootPane().setDefaultButton(btnRegistrar);
		} else {
			buttonPane.add(btnCancelar);
		}

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if(!soloLectura) {
					txtCedula.requestFocus();
				}
			}
		});
	}

	private void aplicarFormato(JTextField textField, String tipo) {
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE || 
					e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					return;
				}
				String text = textField.getText().replaceAll("[^0-9]", "");
				String formatted = "";
				if (tipo.equals("Cedula")) {
					if (text.length() > 3 && text.length() <= 10) {
						formatted = text.substring(0, 3) + "-" + text.substring(3);
					} else if (text.length() > 10) {
						formatted = text.substring(0, 3) + "-" + text.substring(3, 10) + "-" + text.substring(10, Math.min(text.length(), 11));
					} else {
						formatted = text;
					}
				} else if (tipo.equals("Telefono")) {
					if (text.length() > 3 && text.length() <= 6) {
						formatted = text.substring(0, 3) + "-" + text.substring(3);
					} else if (text.length() > 6) {
						formatted = text.substring(0, 3) + "-" + text.substring(3, 6) + "-" + text.substring(6, Math.min(text.length(), 10));
					} else {
						formatted = text;
					}
				}
				
				if (!formatted.equals(textField.getText())) {
					textField.setText(formatted);
				}
			}
		});
	}

	// --- Componentes Personalizados ---
	
	class RoundedComboBox<E> extends JComboBox<E> {
		private int radius;

		public RoundedComboBox(int radius) {
			super();
			this.radius = radius;
			setOpaque(false);
			setFont(new Font("Arial", Font.PLAIN, 14));
			setBackground(new Color(240, 240, 240)); // Fondo gris claro
			setForeground(new Color(50, 50, 50));
			setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

			setUI(new BasicComboBoxUI() {
				@Override
				protected JButton createArrowButton() {
					JButton button = new JButton("\u25BC"); // Flecha minimalista
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
					// Previene el dibujado del fondo cuadrado por defecto
				}
			});

			setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					label.setBorder(new EmptyBorder(8, 10, 8, 10));
					
					if (index == -1) {
						label.setOpaque(false);
						if (RoundedComboBox.this.isEnabled()) {
							label.setForeground(new Color(50, 50, 50));
						} else {
							label.setForeground(new Color(150, 150, 150));
						}
					} else {
						label.setOpaque(true);
						if (isSelected) {
							label.setBackground(new Color(0, 60, 130)); // Azul más oscuro
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
			
			g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
			super.paintComponent(g2);
			g2.dispose();
		}
		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(220, 220, 220));
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
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

	class RoundedPasswordField extends JPasswordField {
		private int radius;
		public RoundedPasswordField(int radius) {
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