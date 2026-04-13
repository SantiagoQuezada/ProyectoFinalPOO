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
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.Date;
import java.text.SimpleDateFormat;
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
		
		// Si es solo lectura (Ver Recibo o Recibo post-pago), dibujamos un ticket moderno
		if (soloLectura && pagoActual != null) {
			construirUI_Recibo();
		} else {
			// Si es para pagar, dibujamos el formulario
			construirUI_Formulario();
		}
	}

	// =========================================================================================
	// INTERFAZ 1: FORMULARIO DE PAGO
	// =========================================================================================
	private void construirUI_Formulario() {
		setModal(true);
		setResizable(false);
		setSize(550, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
		headerPanel.setBackground(new Color(10, 10, 10));
		headerPanel.setPreferredSize(new Dimension(550, 70));
		
		JLabel lblDialogTitle = new JLabel("💵 Procesar Nuevo Pago");
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
		txtIdPago.setText(Altice.getInstance().generarIdPago());
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
		lblMonto.setForeground(new Color(0, 102, 204));
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

		// Lógica Autocompletar Monto Mensualidad
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
		    } else {
		        txtMonto.setText("");
		    }
		};
		cbxCliente.addActionListener(updateMontoAction);
		cbxConcepto.addActionListener(updateMontoAction);

		// Footer Buttons
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		buttonPane.setBackground(new Color(245, 247, 250));
		buttonPane.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		RoundedButton btnCancelar = new RoundedButton("Cancelar", 20);
		btnCancelar.setBackground(new Color(200, 200, 200));
		btnCancelar.setForeground(new Color(30, 30, 30));
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
		btnCancelar.setPreferredSize(new Dimension(120, 40));
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(e -> dispose());

		RoundedButton btnRegistrar = new RoundedButton("Procesar Pago", 20);
		btnRegistrar.setBackground(new Color(0, 150, 50)); 
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
					
					dispose();
					// ¡Magia! En vez de un simple mensajito, abrimos el ticket elegante.
					RegPago reciboGrafico = new RegPago(nuevoPago, true);
					reciboGrafico.setVisible(true);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El monto debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		buttonPane.add(btnCancelar);
		buttonPane.add(btnRegistrar);
		getRootPane().setDefaultButton(btnRegistrar);
	}

	// =========================================================================================
	// INTERFAZ 2: TICKET/RECIBO "BONITO"
	// =========================================================================================
	private void construirUI_Recibo() {
		setModal(true);
		setResizable(false);
		setSize(420, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(240, 242, 245));

		JPanel paddingPanel = new JPanel(new BorderLayout());
		paddingPanel.setOpaque(false);
		paddingPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

		// Panel que simula el papel impreso
		RoundedPanel ticketPanel = new RoundedPanel(20);
		ticketPanel.setBackground(Color.WHITE);
		ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
		ticketPanel.setBorder(new EmptyBorder(30, 25, 30, 25));

		JLabel lblLogo = new JLabel("\u221E Altice");
		lblLogo.setFont(new Font("Arial", Font.BOLD, 32));
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblTitle = new JLabel("COMPROBANTE DE PAGO");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 13));
		lblTitle.setForeground(new Color(120, 120, 120));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		ticketPanel.add(lblLogo);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		ticketPanel.add(lblTitle);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JLabel sep1 = new JLabel("--------------------------------------------------------------");
		sep1.setForeground(new Color(200, 200, 200));
		sep1.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPanel.add(sep1);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  hh:mm a");
		
		ticketPanel.add(crearFilaDetalle("No. Recibo:", pagoActual.getIdPago()));
		ticketPanel.add(crearFilaDetalle("Fecha:", sdf.format(pagoActual.getFecha())));
		
		String idClient = pagoActual.getCliente().getTipoCliente().equals("Empresarial") ? pagoActual.getCliente().getRnc() : pagoActual.getCliente().getCedula();
		ticketPanel.add(crearFilaDetalle("Identificación:", idClient));
		ticketPanel.add(crearFilaDetalle("Cliente:", pagoActual.getCliente().getNombre()));
		ticketPanel.add(crearFilaDetalle("Concepto:", pagoActual.getConcepto()));
		ticketPanel.add(crearFilaDetalle("Método Pago:", pagoActual.getMetodoPago()));

		ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		JLabel sep2 = new JLabel("--------------------------------------------------------------");
		sep2.setForeground(new Color(200, 200, 200));
		sep2.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPanel.add(sep2);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JLabel lblTotalText = new JLabel("TOTAL PAGADO");
		lblTotalText.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalText.setForeground(new Color(100, 100, 100));
		lblTotalText.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblTotalAmount = new JLabel(String.format("$%.2f", pagoActual.getMonto()));
		lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 36));
		lblTotalAmount.setForeground(new Color(0, 150, 50));
		lblTotalAmount.setAlignmentX(Component.CENTER_ALIGNMENT);

		ticketPanel.add(lblTotalText);
		ticketPanel.add(lblTotalAmount);
		
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		JLabel lblGracias = new JLabel("¡Gracias por preferir a Altice!");
		lblGracias.setFont(new Font("Arial", Font.ITALIC, 13));
		lblGracias.setForeground(new Color(150, 150, 150));
		lblGracias.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPanel.add(lblGracias);

		paddingPanel.add(ticketPanel, BorderLayout.CENTER);
		getContentPane().add(paddingPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.setBackground(new Color(240, 242, 245));
		bottomPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

		RoundedButton btnCerrar = new RoundedButton("Cerrar Comprobante", 20);
		btnCerrar.setBackground(new Color(60, 60, 60));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCerrar.setPreferredSize(new Dimension(200, 45));
		btnCerrar.addActionListener(e -> dispose());
		bottomPanel.add(btnCerrar);

		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}

	private JPanel crearFilaDetalle(String label, String valor) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(400, 25));
		
		JLabel lblIzq = new JLabel(label);
		lblIzq.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIzq.setForeground(new Color(120, 120, 120));
		
		JLabel lblDer = new JLabel(valor);
		lblDer.setFont(new Font("Arial", Font.BOLD, 14));
		lblDer.setForeground(new Color(30, 30, 30));
		lblDer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel.add(lblIzq, BorderLayout.WEST);
		panel.add(lblDer, BorderLayout.EAST);
		panel.setBorder(new EmptyBorder(3, 0, 3, 0));
		return panel;
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