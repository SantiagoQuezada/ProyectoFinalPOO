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
import Logico.Plan;
import Logico.Altice;

public class RegPlan extends JDialog {

	private RoundedTextField txtIdPlan;
	private RoundedComboBox<String> cbxCategoria;
	private RoundedTextField txtNombre;
	private RoundedTextField txtPrecio;
	private Plan planActual;

	public RegPlan(Plan plan, boolean soloLectura) {
		this.planActual = plan;
		
		setModal(true);
		setResizable(false);
		setSize(500, 480);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

	
		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		headerPanel.setPreferredSize(new Dimension(500, 70));
		
		String tituloHeader = "Registrar Nuevo Plan";
		if (planActual != null) {
			tituloHeader = soloLectura ? "Detalles del Plan" : "Modificar Plan";
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

		JLabel lblIdPlan = new JLabel("ID Plan:");
		lblIdPlan.setFont(labelFont);
		lblIdPlan.setForeground(labelColor);
		lblIdPlan.setBounds(30, 30, 120, 35);
		contentPanel.add(lblIdPlan);

		txtIdPlan = new RoundedTextField(15);
		txtIdPlan.setFont(new Font("Arial", Font.PLAIN, 14));
		if (planActual == null) {
			txtIdPlan.setText(Altice.getInstance().generarIdPlan());
		} else {
			txtIdPlan.setText(planActual.getIdPlan());
		}
		txtIdPlan.setEditable(false);
		txtIdPlan.setBackground(new Color(240, 240, 240));
		txtIdPlan.setBounds(160, 30, 250, 35);
		contentPanel.add(txtIdPlan);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(labelFont);
		lblCategoria.setForeground(labelColor);
		lblCategoria.setBounds(30, 80, 120, 35);
		contentPanel.add(lblCategoria);

		cbxCategoria = new RoundedComboBox<String>(15);
		cbxCategoria.addItem("Combinado");
		cbxCategoria.addItem("Hogar");
		cbxCategoria.addItem("Móvil");
		cbxCategoria.addItem("Empresarial");
		cbxCategoria.setBounds(160, 80, 250, 35);
		contentPanel.add(cbxCategoria);

		JLabel lblNombre = new JLabel("Nombre Plan:");
		lblNombre.setFont(labelFont);
		lblNombre.setForeground(labelColor);
		lblNombre.setBounds(30, 130, 120, 35);
		contentPanel.add(lblNombre);

		txtNombre = new RoundedTextField(15);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
		txtNombre.setBounds(160, 130, 250, 35);
		contentPanel.add(txtNombre);

		JLabel lblPrecio = new JLabel("Precio (RD$):");
		lblPrecio.setFont(labelFont);
		lblPrecio.setForeground(labelColor);
		lblPrecio.setBounds(30, 180, 120, 35);
		contentPanel.add(lblPrecio);

		txtPrecio = new RoundedTextField(15);
		txtPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrecio.setBounds(160, 180, 250, 35);
		
		txtPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		contentPanel.add(txtPrecio);

		if (planActual != null) {
			cbxCategoria.setSelectedItem(planActual.getCategoria());
			txtNombre.setText(planActual.getNombre());
			txtPrecio.setText(String.valueOf((int) planActual.getPrecio())); // Mostrado en formato int
		}

		if (soloLectura) {
			cbxCategoria.setEnabled(false);
			txtNombre.setEditable(false);
			txtPrecio.setEditable(false);
			txtNombre.setBackground(new Color(245, 245, 245));
			txtPrecio.setBackground(new Color(245, 245, 245));
			cbxCategoria.setBackground(new Color(245, 245, 245));
		}

		
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
			RoundedButton btnRegistrar = new RoundedButton(planActual == null ? "Registrar" : "Guardar Cambios", 20);
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
					if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						String id = txtIdPlan.getText();
						String categoria = cbxCategoria.getSelectedItem().toString();
						String nombre = txtNombre.getText();
						float precio = Float.parseFloat(txtPrecio.getText());

						if (planActual == null) {
							Plan nuevoPlan = new Plan(id, categoria, nombre, precio);
							Altice.getInstance().registrarPlan(nuevoPlan);
							JOptionPane.showMessageDialog(null, "Plan registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
						} else {
							planActual.setCategoria(categoria);
							planActual.setNombre(nombre);
							planActual.setPrecio(precio);
							JOptionPane.showMessageDialog(null, "Plan actualizado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
						}
						
						dispose();
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
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
					txtNombre.requestFocus();
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
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					label.setBorder(new EmptyBorder(8, 10, 8, 10));
					label.setFont(new Font("Arial", Font.BOLD, 14));
					
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