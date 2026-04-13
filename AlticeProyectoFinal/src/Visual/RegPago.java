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
import java.util.Date;
import Logico.Pago;
import Logico.Cliente;
import Logico.Altice;

public class RegPago extends JDialog {

	private RoundedTextField txtIdPago;
	private RoundedComboBox<String> cbxCliente;
	private RoundedComboBox<String> cbxConcepto;
	private RoundedComboBox<String> cbxMetodo;
	private RoundedTextField txtMonto;
	private Pago pagoActual;

	public RegPago(Pago pago, boolean soloLectura) {
		this.pagoActual = pago;
		
		setModal(true);
		setResizable(false);
		setSize(550, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		headerPanel.setPreferredSize(new Dimension(550, 70));
		
		String tituloHeader = "Procesar Nuevo Pago";
		if (pagoActual != null) {
			tituloHeader = "Recibo de Pago Emitido";
		}
		
		JLabel lblDialogTitle = new JLabel((soloLectura ? "🧾 " : "💵 ") + tituloHeader);
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

		JLabel lblIdPago = new JLabel("No. Recibo:");
		lblIdPago.setFont(labelFont);
		lblIdPago.setForeground(labelColor);
		lblIdPago.setBounds(30, 30, 140, 35);
		contentPanel.add(lblIdPago);

		txtIdPago = new RoundedTextField(15);
		txtIdPago.setFont(new Font("Arial", Font.PLAIN, 14));
		if (pagoActual == null) {
			txtIdPago.setText(Altice.getInstance().generarIdPago());
		} else {
			txtIdPago.setText(pagoActual.getIdPago());
		}
		txtIdPago.setEditable(false);
		txtIdPago.setBackground(new Color(240, 240, 240));
		txtIdPago.setBounds(180, 30, 280, 35);
		contentPanel.add(txtIdPago);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(labelFont);
		lblCliente.setForeground(labelColor);
		lblCliente.setBounds(30, 80, 140, 35);
		contentPanel.add(lblCliente);

		cbxCliente = new RoundedComboBox<String>(15);
		cbxCliente.addItem("<Seleccione Cliente>");
		for(Cliente c : Altice.getInstance().getClientes()) {
		    if(c.getEstado().equals("Activo")) {
		        cbxCliente.addItem(c.getIdCliente() + " - " + c.getNombre());
		    }
		}
		cbxCliente.setBounds(180, 80, 280, 35);
		contentPanel.add(cbxCliente);

		JLabel lblConcepto = new JLabel("Concepto:");
		lblConcepto.setFont(labelFont);
		lblConcepto.setForeground(labelColor);
		lblConcepto.setBounds(30, 130, 140, 35);
		contentPanel.add(lblConcepto);

		cbxConcepto = new RoundedComboBox<String>(15);
		cbxConcepto.addItem("Mensualidad");
		cbxConcepto.addItem("Instalación");
		cbxConcepto.addItem("Compra de Equipo");
		cbxConcepto.addItem("Otros");
		cbxConcepto.setBounds(180, 130, 280, 35);
		contentPanel.add(cbxConcepto);

		JLabel lblMonto = new JLabel("Monto a Pagar:");
		lblMonto.setFont(labelFont);
		lblMonto.setForeground(new Color(0, 102, 204)); // Azul para resaltar el monto
		lblMonto.setBounds(30, 180, 140, 35);
		contentPanel.add(lblMonto);

		txtMonto = new RoundedTextField(15);
		txtMonto.setFont(new Font("Arial", Font.BOLD, 15));
		txtMonto.setBounds(180, 180, 280, 35);
		contentPanel.add(txtMonto);

		JLabel lblMetodo = new JLabel("Método de Pago:");
		lblMetodo.setFont(labelFont);
		lblMetodo.setForeground(labelColor);
		lblMetodo.setBounds(30, 230, 140, 35);
		contentPanel.add(lblMetodo);

		cbxMetodo = new RoundedComboBox<String>(15);
		cbxMetodo.addItem("Efectivo");
		cbxMetodo.addItem("Tarjeta de Crédito");
		cbxMetodo.addItem("Transferencia Bancaria");
		cbxMetodo.setBounds(180, 230, 280, 35);
		contentPanel.add(cbxMetodo);

        // --- Lógica Autocompletar Monto ---
        ActionListener updateMontoAction = e -> {
            if (cbxConcepto.getSelectedItem().toString().equals("Mensualidad") && cbxCliente.getSelectedIndex() > 0) {
                String selectedClient = (String) cbxCliente.getSelectedItem();
                String idCliente = selectedClient.split(" - ")[0];
                Cliente c = Altice.getInstance().getClienteById(idCliente);
                if(c != null && c.getPlan() != null) {
                    txtMonto.setText(String.valueOf(c.getPlan().getPrecio()));
                } else {
                    txtMonto.setText("0.0");
                }
            } else if (!soloLectura) {
                txtMonto.setText("");
            }
        };
        cbxCliente.addActionListener(updateMontoAction);
        cbxConcepto.addActionListener(updateMontoAction);

		if (pagoActual != null) {
			cbxCliente.addItem(pagoActual.getCliente().getIdCliente() + " - " + pagoActual.getCliente().getNombre());
			cbxCliente.setSelectedItem(pagoActual.getCliente().getIdCliente() + " - " + pagoActual.getCliente().getNombre());
			cbxConcepto.setSelectedItem(pagoActual.getConcepto());
			txtMonto.setText(String.valueOf(pagoActual.getMonto()));
			cbxMetodo.setSelectedItem(pagoActual.getMetodoPago());
		}

		if (soloLectura) {
			cbxCliente.setEnabled(false);
			cbxConcepto.setEnabled(false);
			cbxMetodo.setEnabled(false);
			txtMonto.setEditable(false);
			
			Color colorDeshabilitado = new Color(245, 245, 245);
			cbxCliente.setBackground(colorDeshabilitado);
			cbxConcepto.setBackground(colorDeshabilitado);
			cbxMetodo.setBackground(colorDeshabilitado);
			txtMonto.setBackground(colorDeshabilitado);
		}

		// --- Footer Buttons ---
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		buttonPane.setBackground(new Color(245, 247, 250));
		buttonPane.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		RoundedButton btnCancelar = new RoundedButton(soloLectura ? "Cerrar" : "Cancelar", 20);
		btnCancelar.setBackground(new Color(200, 200, 200));
		btnCancelar.setForeground(new Color(30, 30, 30));
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCancelar.setPreferredSize(new Dimension(120, 40));
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(e -> dispose());

		if (!soloLectura) {
			RoundedButton btnRegistrar = new RoundedButton("Procesar Pago", 20);
			btnRegistrar.setBackground(new Color(0, 150, 50)); // Verde para cobrar
			btnRegistrar.setForeground(Color.WHITE);
			btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
			btnRegistrar.setPreferredSize(new Dimension(160, 40));
			btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			btnRegistrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 130, 40)); }
				@Override
				public void mouseExited(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 150, 50)); }
			});

			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cbxCliente.getSelectedIndex() == 0 || txtMonto.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente y digitar el monto.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						String idPago = txtIdPago.getText();
						String selectedClientStr = (String) cbxCliente.getSelectedItem();
                        String idCliente = selectedClientStr.split(" - ")[0];
                        Cliente cliente = Altice.getInstance().getClienteById(idCliente);
                        
						String concepto = cbxConcepto.getSelectedItem().toString();
						String metodo = cbxMetodo.getSelectedItem().toString();
						float monto = Float.parseFloat(txtMonto.getText());

						Pago nuevoPago = new Pago(idPago, cliente, new Date(), monto, metodo, concepto);
						Altice.getInstance().registrarPago(nuevoPago);
						JOptionPane.showMessageDialog(null, "Pago procesado y registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			buttonPane.add(btnCancelar);
			buttonPane.add(btnRegistrar);
			getRootPane().setDefaultButton(btnRegistrar);
		} else {
			buttonPane.add(btnCancelar);
		}
	}

	// --- Componentes Personalizados ---
	
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