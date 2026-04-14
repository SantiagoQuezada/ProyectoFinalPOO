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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Timer;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.Date;
import java.text.SimpleDateFormat;
import Logico.Pago;
import Logico.Cliente;
import Logico.Altice;

public class RegPago extends JDialog {

	private RoundedTextField txtIdPago;
	private RoundedTextField txtFecha;
	private RoundedTextField txtBuscarCliente;
	private JList<String> listClientes;
	private DefaultListModel<String> listModelClientes;
	private RoundedTextField txtDeudaActiva;
	private RoundedComboBox<String> cbxComprobante;
	private RoundedTextField txtRncPersonal;
	private RoundedComboBox<String> cbxConcepto;
	private RoundedComboBox<String> cbxMetodo;
	private RoundedTextField txtMonto;
	private Pago pagoActual;

	public RegPago(Pago pago, boolean soloLectura) {
		this.pagoActual = pago;
		
		if (soloLectura && pagoActual != null) {
			construirUI_Recibo();
		} else {
			construirUI_Formulario();
		}
	}

	private void construirUI_Formulario() {
		setModal(true);
		setResizable(false);
		setSize(620, 850); 
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 247, 250));

		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
		headerPanel.setBackground(new Color(15, 15, 15));
		headerPanel.setPreferredSize(new Dimension(620, 70));
		
		JLabel lblDialogTitle = new JLabel("💳 Procesar Nuevo Pago");
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

		JLabel lblIdPago = new JLabel("No. Recibo:");
		lblIdPago.setFont(labelFont);
		lblIdPago.setForeground(labelColor);
		lblIdPago.setBounds(30, 25, 140, 35);
		contentPanel.add(lblIdPago);

		txtIdPago = new RoundedTextField(15);
		txtIdPago.setFont(new Font("Arial", Font.PLAIN, 14));
		txtIdPago.setText(Altice.getInstance().generarIdPago());
		txtIdPago.setEditable(false);
		txtIdPago.setBackground(new Color(240, 240, 240));
		txtIdPago.setBounds(180, 25, 350, 35);
		contentPanel.add(txtIdPago);

		JLabel lblFecha = new JLabel("Fecha Actual:");
		lblFecha.setFont(labelFont);
		lblFecha.setForeground(labelColor);
		lblFecha.setBounds(30, 75, 140, 35);
		contentPanel.add(lblFecha);

		txtFecha = new RoundedTextField(15);
		txtFecha.setFont(new Font("Arial", Font.BOLD, 14));
		txtFecha.setEditable(false);
		txtFecha.setBackground(new Color(240, 240, 240));
		txtFecha.setBounds(180, 75, 350, 35);
		contentPanel.add(txtFecha);

		Timer timer = new Timer(1000, e -> {
			txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date()));
		});
		timer.start();

		JLabel lblBuscarCliente = new JLabel("Buscar Cliente:");
		lblBuscarCliente.setFont(labelFont);
		lblBuscarCliente.setForeground(labelColor);
		lblBuscarCliente.setBounds(30, 125, 140, 35);
		contentPanel.add(lblBuscarCliente);

		txtBuscarCliente = new RoundedTextField(15);
		txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 14));
		txtBuscarCliente.setBounds(180, 125, 350, 35);
		contentPanel.add(txtBuscarCliente);

		JLabel lblListaClientes = new JLabel("Seleccione:");
		lblListaClientes.setFont(labelFont);
		lblListaClientes.setForeground(labelColor);
		lblListaClientes.setBounds(30, 170, 140, 35);
		contentPanel.add(lblListaClientes);

		listModelClientes = new DefaultListModel<>();
		listClientes = new JList<>(listModelClientes);
		listClientes.setFont(new Font("Arial", Font.PLAIN, 14));
		listClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listClientes.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				label.setBorder(new EmptyBorder(8, 10, 8, 10));
				if (isSelected) {
					label.setBackground(new Color(0, 102, 204));
					label.setForeground(Color.WHITE);
				} else {
					label.setBackground(Color.WHITE);
					label.setForeground(new Color(30, 30, 30));
				}
				return label;
			}
		});

		JScrollPane scrollClientes = new JScrollPane(listClientes);
		scrollClientes.setBounds(180, 170, 350, 110);
		scrollClientes.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
		contentPanel.add(scrollClientes);

		cargarClientesLista("");

		txtBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cargarClientesLista(txtBuscarCliente.getText().toLowerCase());
			}
		});

		JLabel lblDeudaActiva = new JLabel("Deuda Activa:");
		lblDeudaActiva.setFont(labelFont);
		lblDeudaActiva.setForeground(new Color(200, 50, 50));
		lblDeudaActiva.setBounds(30, 290, 140, 35);
		contentPanel.add(lblDeudaActiva);

		txtDeudaActiva = new RoundedTextField(15);
		txtDeudaActiva.setFont(new Font("Arial", Font.BOLD, 15));
		txtDeudaActiva.setForeground(new Color(200, 50, 50));
		txtDeudaActiva.setBackground(new Color(255, 235, 235)); 
		txtDeudaActiva.setEditable(false);
		txtDeudaActiva.setBounds(180, 290, 350, 35);
		contentPanel.add(txtDeudaActiva);

		JLabel lblComprobante = new JLabel("Tipo Comprobante:");
		lblComprobante.setFont(labelFont);
		lblComprobante.setForeground(labelColor);
		lblComprobante.setBounds(30, 345, 140, 35);
		contentPanel.add(lblComprobante);

		cbxComprobante = new RoundedComboBox<String>(15);
		cbxComprobante.addItem("Normal (Consumidor Final)");
		cbxComprobante.addItem("Comprobante Fiscal (Empresarial)");
		cbxComprobante.setBounds(180, 345, 350, 35);
		contentPanel.add(cbxComprobante);

		JLabel lblRncPersonal = new JLabel("RNC Cliente:");
		lblRncPersonal.setFont(labelFont);
		lblRncPersonal.setForeground(labelColor);
		lblRncPersonal.setBounds(30, 395, 140, 35);
		contentPanel.add(lblRncPersonal);

		txtRncPersonal = new RoundedTextField(15);
		txtRncPersonal.setFont(new Font("Arial", Font.PLAIN, 14));
		txtRncPersonal.setBounds(180, 395, 350, 35);
		txtRncPersonal.setEnabled(false);
		aplicarFormatoRNC(txtRncPersonal); // Aplicando formato de guiones automáticamente
		contentPanel.add(txtRncPersonal);

		cbxComprobante.addActionListener(e -> {
			boolean isFiscal = cbxComprobante.getSelectedIndex() == 1;
			txtRncPersonal.setEnabled(isFiscal);
			if (!isFiscal) {
				txtRncPersonal.setText("");
			}
		});

		JLabel lblConcepto = new JLabel("Concepto:");
		lblConcepto.setFont(labelFont);
		lblConcepto.setForeground(labelColor);
		lblConcepto.setBounds(30, 445, 140, 35);
		contentPanel.add(lblConcepto);

		cbxConcepto = new RoundedComboBox<String>(15);
		cbxConcepto.addItem("Mensualidad");
		cbxConcepto.addItem("Instalación");
		cbxConcepto.addItem("Compra de Equipo");
		cbxConcepto.addItem("Otros");
		cbxConcepto.setBounds(180, 445, 350, 35);
		contentPanel.add(cbxConcepto);

		JLabel lblMetodo = new JLabel("Método de Pago:");
		lblMetodo.setFont(labelFont);
		lblMetodo.setForeground(labelColor);
		lblMetodo.setBounds(30, 495, 140, 35);
		contentPanel.add(lblMetodo);

		cbxMetodo = new RoundedComboBox<String>(15);
		cbxMetodo.addItem("Efectivo");
		cbxMetodo.addItem("Tarjeta de Crédito");
		cbxMetodo.addItem("Transferencia Bancaria");
		cbxMetodo.setBounds(180, 495, 350, 35);
		contentPanel.add(cbxMetodo);

		JLabel lblMonto = new JLabel("Monto a Pagar:");
		lblMonto.setFont(new Font("Arial", Font.BOLD, 16));
		lblMonto.setForeground(new Color(0, 102, 204));
		lblMonto.setBounds(30, 560, 140, 45);
		contentPanel.add(lblMonto);

		txtMonto = new RoundedTextField(20);
		txtMonto.setFont(new Font("Arial", Font.BOLD, 22));
		txtMonto.setForeground(new Color(0, 150, 50));
		txtMonto.setBounds(180, 560, 350, 45);
		contentPanel.add(txtMonto);

		listClientes.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				actualizarMonto();
				actualizarDatosCliente();
			}
		});
		cbxConcepto.addActionListener(e -> actualizarMonto());

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		buttonPane.setBackground(new Color(245, 247, 250));
		buttonPane.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		RoundedButton btnCancelar = new RoundedButton("Cancelar", 20);
		btnCancelar.setBackground(new Color(200, 200, 200));
		btnCancelar.setForeground(new Color(30, 30, 30));
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCancelar.setPreferredSize(new Dimension(130, 45));
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(e -> dispose());

		RoundedButton btnRegistrar = new RoundedButton("Procesar Pago", 20);
		btnRegistrar.setBackground(new Color(0, 150, 50)); 
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
		btnRegistrar.setPreferredSize(new Dimension(170, 45));
		btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnRegistrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 130, 40)); }
			@Override
			public void mouseExited(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 150, 50)); }
		});

		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedClientStr = listClientes.getSelectedValue();
				if (selectedClientStr == null || txtMonto.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe buscar y seleccionar un cliente de la lista, y digitar el monto.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String rncIngresado = txtRncPersonal.getText().trim();
				if (cbxComprobante.getSelectedIndex() == 1) {
					if (rncIngresado.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar un RNC válido para generar el Comprobante Fiscal.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Validación de RNC
					if (!rncIngresado.matches("^[0-9\\-]+$")) {
						JOptionPane.showMessageDialog(null, "Dato '" + rncIngresado + "' no válido en la parte de RNC.\nSolo se permiten números y guiones.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// Validación del Monto con Try-Catch
				float monto = 0;
				try {
					monto = Float.parseFloat(txtMonto.getText().trim());
					if (monto <= 0) throw new NumberFormatException();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Dato '" + txtMonto.getText() + "' no válido en la parte de Monto.\nSolo se permiten números mayores a cero.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String idPago = txtIdPago.getText();
				String idCliente = selectedClientStr.split(" - ")[0];
				Cliente cliente = Altice.getInstance().getClienteById(idCliente);
				
				String tipoComp = cbxComprobante.getSelectedItem().toString().contains("Fiscal") ? "Comprobante Fiscal" : "Consumidor Final";
				String conceptoFinal = cbxConcepto.getSelectedItem().toString() + " | " + tipoComp;
				
				if (cbxComprobante.getSelectedIndex() == 1) {
					conceptoFinal += " | RNC:" + rncIngresado;
				}
				
				String metodo = cbxMetodo.getSelectedItem().toString();

				Pago nuevoPago = new Pago(idPago, cliente, new Date(), monto, metodo, conceptoFinal);
				Altice.getInstance().registrarPago(nuevoPago);
				
				cliente.reducirDeuda(monto);
				
				dispose();
				RegPago reciboGrafico = new RegPago(nuevoPago, true);
				reciboGrafico.setVisible(true);
			}
		});

		buttonPane.add(btnCancelar);
		buttonPane.add(btnRegistrar);
		getRootPane().setDefaultButton(btnRegistrar);
	}

	private void aplicarFormatoRNC(JTextField textField) {
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE || 
					e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					return;
				}
				String text = textField.getText().replaceAll("[^0-9]", "");
				String formatted = "";
				
				if (text.length() > 1 && text.length() <= 3) {
					formatted = text.substring(0, 1) + "-" + text.substring(1);
				} else if (text.length() > 3 && text.length() <= 8) {
					formatted = text.substring(0, 1) + "-" + text.substring(1, 3) + "-" + text.substring(3);
				} else if (text.length() > 8) {
					formatted = text.substring(0, 1) + "-" + text.substring(1, 3) + "-" + text.substring(3, 8) + "-" + text.substring(8, Math.min(text.length(), 9));
				} else {
					formatted = text;
				}
				
				if (!formatted.equals(textField.getText())) {
					textField.setText(formatted);
				}
			}
		});
	}

	private void actualizarMonto() {
		if (cbxConcepto.getSelectedItem() != null && cbxConcepto.getSelectedItem().toString().equals("Mensualidad")) {
			String selectedClient = listClientes.getSelectedValue();
			if (selectedClient != null && !selectedClient.isEmpty()) {
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
		} else {
			txtMonto.setText("");
		}
	}
	
	private void actualizarDatosCliente() {
		String selectedClient = listClientes.getSelectedValue();
		if (selectedClient != null && !selectedClient.isEmpty()) {
			String idCliente = selectedClient.split(" - ")[0];
			Cliente c = Altice.getInstance().getClienteById(idCliente);
			
			if(c != null && c.getTipoCliente().equals("Empresarial")) {
				cbxComprobante.setSelectedIndex(1);
				txtRncPersonal.setText(c.getRnc() != null ? c.getRnc() : "");
			} else {
				cbxComprobante.setSelectedIndex(0);
				txtRncPersonal.setText("");
			}

			if (c != null) {
				txtDeudaActiva.setText("$" + String.format("%.2f", c.getDeudaActiva()));
			}
		} else {
			txtDeudaActiva.setText("");
		}
	}

	private void cargarClientesLista(String busqueda) {
		listModelClientes.clear();
		for(Cliente c : Altice.getInstance().getClientes()) {
			if(c.getEstado().equals("Activo")) {
				String identificacion = c.getTipoCliente().equals("Empresarial") ? c.getRnc() : c.getCedula();
				if(identificacion == null) identificacion = "";
				
				if(c.getNombre().toLowerCase().contains(busqueda) || 
				   c.getIdCliente().toLowerCase().contains(busqueda) || 
				   identificacion.toLowerCase().contains(busqueda)) {
					listModelClientes.addElement(c.getIdCliente() + " - " + c.getNombre() + " (" + (c.getTipoCliente().equals("Empresarial") ? "RNC" : "Cédula") + ": " + identificacion + ")");
				}
			}
		}
	}

	private void construirUI_Recibo() {
		setModal(true);
		setResizable(true);
		setSize(680, 960);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(230, 235, 240)); 

		JPanel paddingPanel = new JPanel(new BorderLayout());
		paddingPanel.setBackground(new Color(230, 235, 240));
		paddingPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

		RoundedPanel ticketPanel = new RoundedPanel(25);
		ticketPanel.setBackground(Color.WHITE);
		ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
		ticketPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

		boolean isFiscal = pagoActual.getConcepto().contains("Fiscal");

		JLabel lblLogo = new JLabel("\u221E Altice");
		lblLogo.setFont(new Font("Arial", Font.BOLD, 48));
		lblLogo.setForeground(new Color(15, 15, 15));
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblTitle = new JLabel(isFiscal ? "FACTURA DE CRÉDITO FISCAL" : "FACTURA COMERCIAL");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitle.setForeground(new Color(120, 120, 120));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel lblTotalAmount = new JLabel(String.format("$%.2f", pagoActual.getMonto()));
		lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 56));
		lblTotalAmount.setForeground(new Color(15, 15, 15));
		lblTotalAmount.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblExito = new JLabel("Pago realizado con éxito");
		lblExito.setFont(new Font("Arial", Font.BOLD, 16));
		lblExito.setForeground(new Color(0, 150, 50));
		lblExito.setAlignmentX(Component.CENTER_ALIGNMENT);

		ticketPanel.add(lblLogo);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		ticketPanel.add(lblTitle);
		
		if (isFiscal) {
			JLabel lblAlticeRnc = new JLabel("RNC Emisor: 1-30-01867-0");
			lblAlticeRnc.setFont(new Font("Arial", Font.PLAIN, 15));
			lblAlticeRnc.setForeground(new Color(100, 100, 100));
			lblAlticeRnc.setAlignmentX(Component.CENTER_ALIGNMENT);
			ticketPanel.add(Box.createRigidArea(new Dimension(0, 5)));
			ticketPanel.add(lblAlticeRnc);
		}

		ticketPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		ticketPanel.add(lblTotalAmount);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		ticketPanel.add(lblExito);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 35)));

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(new Color(230, 230, 230));
		sep1.setBackground(new Color(230, 230, 230));
		sep1.setMaximumSize(new Dimension(500, 2));
		sep1.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPanel.add(sep1);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 25)));

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
		
		ticketPanel.add(crearFilaDetalle("Fecha y Hora", sdf.format(pagoActual.getFecha())));
		ticketPanel.add(crearFilaDetalle("No. Recibo", pagoActual.getIdPago()));
		
		String ncfBase = isFiscal ? "B0100000" : "B0200000";
		String secID = pagoActual.getIdPago().replaceAll("\\D+", "");
		if (secID.isEmpty()) secID = "123";
		String ncfFinal = ncfBase + secID;
		
		ticketPanel.add(crearFilaDetalle("NCF", ncfFinal));
		ticketPanel.add(crearFilaDetalle("Validez", isFiscal ? "Crédito Fiscal" : "Consumidor Final"));
		
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		JSeparator sep2 = new JSeparator();
		sep2.setForeground(new Color(230, 230, 230));
		sep2.setBackground(new Color(230, 230, 230));
		sep2.setMaximumSize(new Dimension(500, 2));
		sep2.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPanel.add(sep2);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		String idClient = pagoActual.getCliente().getTipoCliente().equals("Empresarial") ? pagoActual.getCliente().getRnc() : pagoActual.getCliente().getCedula();
		String tipoId = pagoActual.getCliente().getTipoCliente().equals("Empresarial") ? "RNC Cliente" : "Cédula";
		String nombreLabel = isFiscal ? "Razón Social" : "Cliente";
		
		if (isFiscal && pagoActual.getConcepto().contains("RNC:")) {
			String[] parts = pagoActual.getConcepto().split("RNC:");
			if (parts.length > 1) {
				idClient = parts[1].trim();
				tipoId = "RNC Cliente";
			}
		}
		
		ticketPanel.add(crearFilaDetalle(nombreLabel, pagoActual.getCliente().getNombre()));
		ticketPanel.add(crearFilaDetalle(tipoId, idClient));
		
		String conceptoLimpio = pagoActual.getConcepto().split("\\|")[0].trim();
		ticketPanel.add(crearFilaDetalle("Concepto", conceptoLimpio));
		ticketPanel.add(crearFilaDetalle("Método de Pago", pagoActual.getMetodoPago()));

		ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		JSeparator sep3 = new JSeparator();
		sep3.setForeground(new Color(230, 230, 230));
		sep3.setBackground(new Color(230, 230, 230));
		sep3.setMaximumSize(new Dimension(500, 2));
		sep3.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPanel.add(sep3);
		ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		if (isFiscal) {
			double subtotal = pagoActual.getMonto() / 1.18;
			double itbis = pagoActual.getMonto() - subtotal;
			ticketPanel.add(crearFilaDetalle("Subtotal", String.format("$%.2f", subtotal)));
			ticketPanel.add(crearFilaDetalle("ITBIS (18%)", String.format("$%.2f", itbis)));
		}
		ticketPanel.add(crearFilaDetalle("Total Facturado", String.format("$%.2f", pagoActual.getMonto())));

		ticketPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		
		JLabel lblGracias = new JLabel("¡Gracias por preferir a Altice!");
		lblGracias.setFont(new Font("Arial", Font.BOLD, 16));
		lblGracias.setForeground(new Color(150, 150, 150));
		lblGracias.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticketPanel.add(lblGracias);

		paddingPanel.add(ticketPanel, BorderLayout.CENTER);
		
		JScrollPane scrollFactura = new JScrollPane(paddingPanel);
		scrollFactura.setBorder(null);
		scrollFactura.getVerticalScrollBar().setUnitIncrement(16);
		scrollFactura.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		getContentPane().add(scrollFactura, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.setBackground(new Color(230, 235, 240));
		bottomPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

		RoundedButton btnCerrar = new RoundedButton("Cerrar Comprobante", 25);
		btnCerrar.setBackground(new Color(20, 20, 20)); 
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Arial", Font.BOLD, 16));
		btnCerrar.setPreferredSize(new Dimension(300, 55));
		btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCerrar.addActionListener(e -> dispose());
		bottomPanel.add(btnCerrar);

		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}

	private JPanel crearFilaDetalle(String label, String valor) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		panel.setMaximumSize(new Dimension(550, 35));
		
		JLabel lblIzq = new JLabel(label);
		lblIzq.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIzq.setForeground(new Color(120, 120, 120));
		
		JLabel lblDer = new JLabel(valor);
		lblDer.setFont(new Font("Arial", Font.BOLD, 15));
		lblDer.setForeground(new Color(40, 40, 40));
		lblDer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel.add(lblIzq, BorderLayout.WEST);
		panel.add(lblDer, BorderLayout.EAST);
		panel.setBorder(new EmptyBorder(5, 0, 5, 0));
		return panel;
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