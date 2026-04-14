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
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;
import Logico.Cliente;
import Logico.Plan;
import Logico.Altice;

public class RegCliente extends JDialog {

	private RoundedTextField txtIdCliente;
	private RoundedComboBox<String> cbxTipoCliente;
	private RoundedTextField txtRnc;
	private RoundedTextField txtCedula;
	private RoundedTextField txtNombre;
	private RoundedTextField txtTelefono;
	private RoundedTextField txtDireccion;
	private RoundedComboBox<String> cbxEstado;
	private RoundedComboBox<String> cbxPlanes;
	private Cliente clienteActual;

	private JLabel lblCedula;
	private JLabel lblNombre;

	public RegCliente(Cliente cliente, boolean soloLectura) {
		this.clienteActual = cliente;
		
		setModal(true);
		setResizable(false);
		setSize(550, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

	
		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		headerPanel.setPreferredSize(new Dimension(550, 70));
		
		String tituloHeader = "Registrar Nuevo Cliente";
		if (clienteActual != null) {
			tituloHeader = soloLectura ? "Detalles del Cliente" : "Modificar Cliente";
		}
		
		JLabel lblDialogTitle = new JLabel(tituloHeader);
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
		Color labelColor = new Color(50, 50, 50);

		JLabel lblIdCliente = new JLabel("ID Cliente:");
		lblIdCliente.setFont(labelFont);
		lblIdCliente.setForeground(labelColor);
		lblIdCliente.setBounds(30, 30, 140, 35);
		contentPanel.add(lblIdCliente);

		txtIdCliente = new RoundedTextField(15);
		txtIdCliente.setFont(new Font("Arial", Font.PLAIN, 14));
		if (clienteActual == null) {
			txtIdCliente.setText(Altice.getInstance().generarIdCliente());
		} else {
			txtIdCliente.setText(clienteActual.getIdCliente());
		}
		txtIdCliente.setEditable(false);
		txtIdCliente.setBackground(new Color(240, 240, 240));
		txtIdCliente.setBounds(180, 30, 280, 35);
		contentPanel.add(txtIdCliente);

		JLabel lblTipoCliente = new JLabel("Tipo Cliente:");
		lblTipoCliente.setFont(labelFont);
		lblTipoCliente.setForeground(labelColor);
		lblTipoCliente.setBounds(30, 80, 140, 35);
		contentPanel.add(lblTipoCliente);

		cbxTipoCliente = new RoundedComboBox<String>(15);
		cbxTipoCliente.addItem("Personal");
		cbxTipoCliente.addItem("Empresarial");
		cbxTipoCliente.setBounds(180, 80, 280, 35);
		contentPanel.add(cbxTipoCliente);

		JLabel lblRnc = new JLabel("RNC:");
		lblRnc.setFont(labelFont);
		lblRnc.setForeground(labelColor);
		lblRnc.setBounds(30, 130, 140, 35);
		contentPanel.add(lblRnc);

		txtRnc = new RoundedTextField(15);
		txtRnc.setFont(new Font("Arial", Font.PLAIN, 14));
		txtRnc.setBounds(180, 130, 280, 35);
		txtRnc.setEnabled(false);
		txtRnc.setBackground(new Color(240, 240, 240));
		aplicarFormato(txtRnc, "RNC");
		contentPanel.add(txtRnc);

		lblCedula = new JLabel("Cédula:");
		lblCedula.setFont(labelFont);
		lblCedula.setForeground(labelColor);
		lblCedula.setBounds(30, 180, 140, 35);
		contentPanel.add(lblCedula);

		txtCedula = new RoundedTextField(15);
		txtCedula.setFont(new Font("Arial", Font.PLAIN, 14));
		txtCedula.setBounds(180, 180, 280, 35);
		aplicarFormato(txtCedula, "Cedula");
		contentPanel.add(txtCedula);

		txtCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) txtNombre.requestFocus();
			}
		});

		lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setFont(labelFont);
		lblNombre.setForeground(labelColor);
		lblNombre.setBounds(30, 230, 140, 35);
		contentPanel.add(lblNombre);

		txtNombre = new RoundedTextField(15);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
		txtNombre.setBounds(180, 230, 280, 35);
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
		lblTelefono.setBounds(30, 280, 140, 35);
		contentPanel.add(lblTelefono);

		txtTelefono = new RoundedTextField(15);
		txtTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTelefono.setBounds(180, 280, 280, 35);
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
		lblDireccion.setBounds(30, 330, 140, 35);
		contentPanel.add(lblDireccion);

		txtDireccion = new RoundedTextField(15);
		txtDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
		txtDireccion.setBounds(180, 330, 280, 35);
		contentPanel.add(txtDireccion);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(labelFont);
		lblEstado.setForeground(labelColor);
		lblEstado.setBounds(30, 380, 140, 35);
		contentPanel.add(lblEstado);

		cbxEstado = new RoundedComboBox<String>(15);
		cbxEstado.addItem("Activo");
		cbxEstado.addItem("Inactivo");
		cbxEstado.addItem("Suspendido");
		cbxEstado.setBounds(180, 380, 280, 35);
		contentPanel.add(cbxEstado);

		JLabel lblPlan = new JLabel("Plan Inicial:");
		lblPlan.setFont(labelFont);
		lblPlan.setForeground(labelColor);
		lblPlan.setBounds(30, 430, 140, 35);
		contentPanel.add(lblPlan);

		cbxPlanes = new RoundedComboBox<String>(15);
		cbxPlanes.setBounds(180, 430, 280, 35);
		cargarPlanes();
		contentPanel.add(cbxPlanes);

		cbxTipoCliente.addActionListener(e -> {
			if (cbxTipoCliente.getSelectedItem().toString().equals("Empresarial")) {
				txtRnc.setEnabled(true);
				txtRnc.setBackground(Color.WHITE);
				lblNombre.setText("Razón Social:");
				lblCedula.setText("Cédula Representante:");
			} else {
				txtRnc.setEnabled(false);
				txtRnc.setBackground(new Color(240, 240, 240));
				txtRnc.setText("");
				lblNombre.setText("Nombre Completo:");
				lblCedula.setText("Cédula:");
			}
		});

		if (clienteActual != null) {
			cbxTipoCliente.setSelectedItem(clienteActual.getTipoCliente());
			txtRnc.setText(clienteActual.getRnc());
			txtCedula.setText(clienteActual.getCedula());
			txtNombre.setText(clienteActual.getNombre());
			txtTelefono.setText(clienteActual.getTelefono());
			txtDireccion.setText(clienteActual.getDireccion());
			cbxEstado.setSelectedItem(clienteActual.getEstado());
			
			if (clienteActual.getPlan() != null) {
				cbxPlanes.setSelectedItem(clienteActual.getPlan().getNombre() + " - $" + clienteActual.getPlan().getPrecio());
			}
		}

		if (soloLectura) {
			cbxTipoCliente.setEnabled(false);
			txtRnc.setEditable(false);
			txtCedula.setEditable(false);
			txtNombre.setEditable(false);
			txtTelefono.setEditable(false);
			txtDireccion.setEditable(false);
			cbxEstado.setEnabled(false);
			cbxPlanes.setEnabled(false);
			
			Color colorDeshabilitado = new Color(245, 245, 245);
			txtRnc.setBackground(colorDeshabilitado);
			txtCedula.setBackground(colorDeshabilitado);
			txtNombre.setBackground(colorDeshabilitado);
			txtTelefono.setBackground(colorDeshabilitado);
			txtDireccion.setBackground(colorDeshabilitado);
			cbxTipoCliente.setBackground(colorDeshabilitado);
			cbxEstado.setBackground(colorDeshabilitado);
			cbxPlanes.setBackground(colorDeshabilitado);
		}

	
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		buttonPane.setBackground(new Color(245, 247, 250));
		buttonPane.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		RoundedButton btnCancelar = new RoundedButton(soloLectura ? "Cerrar" : "Cancelar", 20);
		btnCancelar.setBackground(new Color(220, 53, 69)); 
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
			RoundedButton btnRegistrar = new RoundedButton(clienteActual == null ? "Registrar" : "Guardar Cambios", 20);
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
					String tipoCliente = cbxTipoCliente.getSelectedItem().toString();
					
					if (txtNombre.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (txtCedula.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "La cédula (o cédula del representante) es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (tipoCliente.equals("Empresarial") && txtRnc.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El RNC es obligatorio para clientes empresariales.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					String id = txtIdCliente.getText();
					String rnc = tipoCliente.equals("Empresarial") ? txtRnc.getText() : "N/A";
					String cedula = txtCedula.getText();
					String nombre = txtNombre.getText();
					String telefono = txtTelefono.getText();
					String direccion = txtDireccion.getText();
					String estado = cbxEstado.getSelectedItem().toString();

			
					String telefonoRaw = telefono.replaceAll("-", "");
					if (telefonoRaw.length() != 10) {
						JOptionPane.showMessageDialog(null, "El teléfono debe contener exactamente 10 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}

					String cedulaRaw = cedula.replaceAll("-", "");
					if (cedulaRaw.length() != 11) {
						JOptionPane.showMessageDialog(null, "La cédula debe contener exactamente 11 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (tipoCliente.equals("Empresarial")) {
						String rncRaw = rnc.replaceAll("-", "");
						if (rncRaw.length() != 9) {
							JOptionPane.showMessageDialog(null, "El RNC debe contener exactamente 9 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}

				
					for (Cliente c : Altice.getInstance().getClientes()) {
						if (clienteActual != null && c.getIdCliente().equals(clienteActual.getIdCliente())) {
							continue; 
						}

						if (c.getTelefono().equals(telefono)) {
							JOptionPane.showMessageDialog(null, "Ya existe un cliente registrado con el teléfono: " + telefono, "Dato Duplicado", JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (c.getCedula().equals(cedula)) {
							JOptionPane.showMessageDialog(null, "Ya existe un cliente o representante registrado con la cédula: " + cedula, "Dato Duplicado", JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (tipoCliente.equals("Empresarial") && c.getTipoCliente().equals("Empresarial") && c.getRnc() != null && c.getRnc().equals(rnc)) {
							JOptionPane.showMessageDialog(null, "Ya existe un cliente (empresa) registrado con el RNC: " + rnc, "Dato Duplicado", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}

					Plan planSeleccionado = null;
					if (cbxPlanes.getSelectedIndex() > 0) {
						String nombrePlan = ((String) cbxPlanes.getSelectedItem()).split(" - \\$")[0];
						for(Plan p : Altice.getInstance().getPlanes()) {
							if(p.getNombre().equals(nombrePlan)) {
								planSeleccionado = p;
								break;
							}
						}
					}

					if (clienteActual == null) {
						Cliente nuevoCliente = new Cliente(cedula, nombre, telefono, direccion, id, estado, null, tipoCliente, rnc);
						Altice.getInstance().registrarCliente(nuevoCliente);
						if (planSeleccionado != null) {
							Altice.getInstance().asignarPlanACliente(id, planSeleccionado.getNombre());
						}
						JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					} else {
						clienteActual.setTipoCliente(tipoCliente);
						clienteActual.setRnc(rnc);
						clienteActual.setCedula(cedula);
						clienteActual.setNombre(nombre);
						clienteActual.setTelefono(telefono);
						clienteActual.setDireccion(direccion);
						clienteActual.setEstado(estado);
						
						if (planSeleccionado != null && clienteActual.getPlan() != planSeleccionado) {
							Altice.getInstance().asignarPlanACliente(id, planSeleccionado.getNombre());
						} else if (planSeleccionado == null) {
							clienteActual.setPlan(null);
							clienteActual.setFechaAsignacionPlan(null);
						}
						
						JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
					
					dispose();
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
					if(cbxTipoCliente.getSelectedItem().equals("Personal")) {
						txtCedula.requestFocus();
					} else {
						txtRnc.requestFocus();
					}
				}
			}
		});
	}

	private void cargarPlanes() {
		cbxPlanes.addItem("<Seleccione un plan>");
		for (Plan plan : Altice.getInstance().getPlanes()) {
			cbxPlanes.addItem(plan.getNombre() + " - $" + plan.getPrecio());
		}
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
				} else if (tipo.equals("RNC")) {
					if (text.length() > 1 && text.length() <= 3) {
						formatted = text.substring(0, 1) + "-" + text.substring(1);
					} else if (text.length() > 3 && text.length() <= 8) {
						formatted = text.substring(0, 1) + "-" + text.substring(1, 3) + "-" + text.substring(3);
					} else if (text.length() > 8) {
						formatted = text.substring(0, 1) + "-" + text.substring(1, 3) + "-" + text.substring(3, 8) + "-" + text.substring(8, Math.min(text.length(), 9));
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

	class RoundedComboBox<E> extends JComboBox<E> {
		private int radius;

		public RoundedComboBox(int radius) {
			super();
			this.radius = radius;
			setOpaque(false);
			setFont(new Font("Arial", Font.PLAIN, 14));
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
			
			// El borde se dibuja al final para enmascarar cualquier imperfección en las esquinas
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