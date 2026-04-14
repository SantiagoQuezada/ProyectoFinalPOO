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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Timer;
import java.awt.Component;
import java.util.Date;
import java.text.SimpleDateFormat;
import Logico.Pago;
import Logico.Cliente;
import Logico.Altice;

public class RegPago extends JDialog {

    private RoundedTextField txtIdPago;
    private RoundedTextField txtFecha;
    private RoundedTextField txtBuscarCliente;
    private JTable tablaClientes;
    private DefaultTableModel modeloTablaClientes;
    private RoundedTextField txtDeudaActiva;
    private RoundedComboBox<String> cbxComprobante;
    private RoundedTextField txtRncPersonal;
    private RoundedComboBox<String> cbxConcepto;
    private RoundedComboBox<String> cbxMetodo;
    private RoundedTextField txtMonto;
    private Pago pagoActual;
    private String idClienteSeleccionado = null;

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
        setSize(820, 800); // ← aumentado de 740 a 800
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        headerPanel.setBackground(new Color(15, 15, 15));
        headerPanel.setPreferredSize(new Dimension(820, 70));

        JLabel lblDialogTitle = new JLabel("Punto de Pago - Altice");
        lblDialogTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblDialogTitle.setForeground(Color.WHITE);
        headerPanel.add(lblDialogTitle);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(new Color(245, 247, 250));
        centerContainer.setBorder(new EmptyBorder(15, 25, 10, 25));

        RoundedPanel contentPanel = new RoundedPanel(25);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(null);
        centerContainer.add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(centerContainer, BorderLayout.CENTER);

        Font labelFont = new Font("Arial", Font.BOLD, 13);
        Color labelColor = new Color(120, 120, 120);

        int leftX = 30;
        int rightX = 410;
        int colWidth = 340;
        int fullWidth = 720;
        int fieldHeight = 35;

        JLabel lblIdPago = new JLabel("No. Transacción / Recibo");
        lblIdPago.setFont(labelFont);
        lblIdPago.setForeground(labelColor);
        lblIdPago.setBounds(leftX, 15, colWidth, 20);
        contentPanel.add(lblIdPago);

        txtIdPago = new RoundedTextField(15);
        txtIdPago.setFont(new Font("Arial", Font.BOLD, 14));
        txtIdPago.setText(Altice.getInstance().generarIdPago());
        txtIdPago.setEditable(false);
        txtIdPago.setBackground(new Color(240, 240, 240));
        txtIdPago.setBounds(leftX, 35, colWidth, fieldHeight);
        contentPanel.add(txtIdPago);

        JLabel lblFecha = new JLabel("Fecha y Hora Actual");
        lblFecha.setFont(labelFont);
        lblFecha.setForeground(labelColor);
        lblFecha.setBounds(rightX, 15, colWidth, 20);
        contentPanel.add(lblFecha);

        txtFecha = new RoundedTextField(15);
        txtFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        txtFecha.setEditable(false);
        txtFecha.setBackground(new Color(240, 240, 240));
        txtFecha.setBounds(rightX, 35, colWidth, fieldHeight);
        contentPanel.add(txtFecha);

        Timer timer = new Timer(1000, e -> {
            txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date()));
        });
        timer.start();

        JLabel lblBuscarCliente = new JLabel("Búsqueda Rápida de Cliente (Nombre, Cédula o RNC)");
        lblBuscarCliente.setFont(labelFont);
        lblBuscarCliente.setForeground(labelColor);
        lblBuscarCliente.setBounds(leftX, 80, fullWidth, 20);
        contentPanel.add(lblBuscarCliente);

        txtBuscarCliente = new RoundedTextField(15);
        txtBuscarCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscarCliente.setBounds(leftX, 100, fullWidth, fieldHeight);
        contentPanel.add(txtBuscarCliente);

        String[] columnas = {"ID", "Identificación", "Nombre del Cliente", "Deuda"};
        modeloTablaClientes = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaClientes = new JTable(modeloTablaClientes);
        tablaClientes.setFillsViewportHeight(true);
        tablaClientes.setRowHeight(35);
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setGridColor(new Color(230, 230, 230));
        tablaClientes.setShowVerticalLines(false);
        tablaClientes.setSelectionBackground(new Color(0, 102, 204));
        tablaClientes.setSelectionForeground(Color.WHITE);

        JTableHeader header = tablaClientes.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(15, 15, 15));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 13));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(new EmptyBorder(5, 5, 5, 5));
                return label;
            }
        });
        header.setPreferredSize(new Dimension(100, 35));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                ((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));

                String idCli = table.getValueAt(row, 0).toString();
                Cliente clienteDeTabla = Altice.getInstance().getClienteById(idCli);
                float deuda = clienteDeTabla != null ? clienteDeTabla.getDeudaActiva() : 0;

                if (isSelected) {
                    c.setBackground(new Color(0, 102, 204));
                    c.setForeground(Color.WHITE);
                    c.setFont(new Font("Arial", Font.BOLD, 14));
                } else {
                    if (deuda > 0) {
                        c.setBackground(new Color(255, 240, 240));
                        c.setForeground(new Color(220, 40, 40));
                        c.setFont(new Font("Arial", Font.BOLD, 14));
                    } else {
                        if (row % 2 == 0) c.setBackground(new Color(250, 250, 250));
                        else c.setBackground(new Color(240, 240, 240));
                        c.setForeground(new Color(15, 15, 15));
                        c.setFont(new Font("Arial", Font.PLAIN, 14));
                    }
                }
                return c;
            }
        };

        for (int i = 0; i < tablaClientes.getColumnCount(); i++) {
            tablaClientes.getColumnModel().getColumn(i).setCellRenderer(tableRenderer);
        }

        tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(140);
        tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(280);
        tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPaneClientes = new JScrollPane(tablaClientes);
        scrollPaneClientes.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneClientes.getViewport().setBackground(Color.WHITE);

        RoundedPanel tableWrapper = new RoundedPanel(20);
        tableWrapper.setLayout(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(new EmptyBorder(5, 5, 5, 5));
        tableWrapper.setBounds(leftX, 145, fullWidth, 150);
        tableWrapper.add(scrollPaneClientes, BorderLayout.CENTER);
        contentPanel.add(tableWrapper);

        cargarClientesTabla("");

        txtBuscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarClientesTabla(txtBuscarCliente.getText().toLowerCase());
            }
        });

        JLabel lblDeudaActiva = new JLabel("Deuda Activa en el Sistema");
        lblDeudaActiva.setFont(labelFont);
        lblDeudaActiva.setForeground(new Color(200, 50, 50));
        lblDeudaActiva.setBounds(leftX, 305, colWidth, 20);
        contentPanel.add(lblDeudaActiva);

        txtDeudaActiva = new RoundedTextField(15);
        txtDeudaActiva.setFont(new Font("Arial", Font.BOLD, 15));
        txtDeudaActiva.setForeground(new Color(200, 50, 50));
        txtDeudaActiva.setBackground(new Color(255, 235, 235));
        txtDeudaActiva.setEditable(false);
        txtDeudaActiva.setBounds(leftX, 325, colWidth, fieldHeight);
        contentPanel.add(txtDeudaActiva);

        JLabel lblComprobante = new JLabel("Tipo de Comprobante");
        lblComprobante.setFont(labelFont);
        lblComprobante.setForeground(labelColor);
        lblComprobante.setBounds(rightX, 305, colWidth, 20);
        contentPanel.add(lblComprobante);

        cbxComprobante = new RoundedComboBox<String>(15);
        cbxComprobante.addItem("Normal (Consumidor Final)");
        cbxComprobante.addItem("Comprobante Fiscal (Empresarial)");
        cbxComprobante.setBounds(rightX, 325, colWidth, fieldHeight);
        contentPanel.add(cbxComprobante);

        JLabel lblRncPersonal = new JLabel("RNC / Cédula para Facturar");
        lblRncPersonal.setFont(labelFont);
        lblRncPersonal.setForeground(labelColor);
        lblRncPersonal.setBounds(leftX, 370, colWidth, 20);
        contentPanel.add(lblRncPersonal);

        txtRncPersonal = new RoundedTextField(15);
        txtRncPersonal.setFont(new Font("Arial", Font.PLAIN, 14));
        txtRncPersonal.setBounds(leftX, 390, colWidth, fieldHeight);
        txtRncPersonal.setEnabled(false);
        txtRncPersonal.setBackground(new Color(240, 240, 240));
        aplicarFormatoRNC(txtRncPersonal);
        contentPanel.add(txtRncPersonal);

        cbxComprobante.addActionListener(e -> {
            boolean isFiscal = cbxComprobante.getSelectedIndex() == 1;
            txtRncPersonal.setEnabled(isFiscal);
            if (!isFiscal) {
                txtRncPersonal.setText("");
                txtRncPersonal.setBackground(new Color(240, 240, 240));
            } else {
                txtRncPersonal.setBackground(Color.WHITE);
                if (idClienteSeleccionado != null) {
                    Cliente c = Altice.getInstance().getClienteById(idClienteSeleccionado);
                    if (c != null) {
                        txtRncPersonal.setText(c.getTipoCliente().equals("Empresarial") ? c.getRnc() : c.getCedula());
                    }
                }
            }
        });

        JLabel lblConcepto = new JLabel("Concepto del Cobro");
        lblConcepto.setFont(labelFont);
        lblConcepto.setForeground(labelColor);
        lblConcepto.setBounds(rightX, 370, colWidth, 20);
        contentPanel.add(lblConcepto);

        cbxConcepto = new RoundedComboBox<String>(15);
        cbxConcepto.addItem("Mensualidad");
        cbxConcepto.addItem("Instalación");
        cbxConcepto.addItem("Compra de Equipo");
        cbxConcepto.addItem("Otros");
        cbxConcepto.setBounds(rightX, 390, colWidth, fieldHeight);
        contentPanel.add(cbxConcepto);

        JLabel lblMetodo = new JLabel("Método de Pago Autorizado");
        lblMetodo.setFont(labelFont);
        lblMetodo.setForeground(labelColor);
        lblMetodo.setBounds(leftX, 435, fullWidth, 20);
        contentPanel.add(lblMetodo);

        cbxMetodo = new RoundedComboBox<String>(15);
        cbxMetodo.addItem("Efectivo");
        cbxMetodo.addItem("Tarjeta de Crédito / Débito");
        cbxMetodo.addItem("Transferencia Bancaria");
        cbxMetodo.setBounds(leftX, 455, fullWidth, fieldHeight);
        contentPanel.add(cbxMetodo);

        JLabel lblMonto = new JLabel("MONTO TOTAL A PAGAR (RD$)");
        lblMonto.setFont(new Font("Arial", Font.BOLD, 16));
        lblMonto.setForeground(new Color(0, 102, 204));
        lblMonto.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonto.setBounds(leftX, 505, fullWidth, 20); // ← bajado 5px
        contentPanel.add(lblMonto);

        txtMonto = new RoundedTextField(20);
        txtMonto.setFont(new Font("Arial", Font.BOLD, 42));
        txtMonto.setForeground(new Color(0, 150, 50));
        txtMonto.setHorizontalAlignment(JTextField.CENTER);
        txtMonto.setBounds(leftX, 530, fullWidth, 70); // ← alto aumentado de 60 a 70
        txtMonto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
                if (c == '.' && txtMonto.getText().contains(".")) {
                    e.consume();
                }
            }
        });
        contentPanel.add(txtMonto);

        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarDatosCliente();
                actualizarMonto();
            }
        });

        cbxConcepto.addActionListener(e -> actualizarMonto());

        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPane.setBackground(new Color(245, 247, 250));
        buttonPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        RoundedButton btnCancelar = new RoundedButton("Cancelar", 20);
        btnCancelar.setBackground(new Color(220, 53, 69));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(130, 45));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btnCancelar.setBackground(new Color(180, 40, 50)); }
            @Override
            public void mouseExited(MouseEvent e) { btnCancelar.setBackground(new Color(220, 53, 69)); }
        });
        btnCancelar.addActionListener(e -> dispose());

        RoundedButton btnRegistrar = new RoundedButton("Procesar Factura", 20);
        btnRegistrar.setBackground(new Color(0, 150, 50));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 15));
        btnRegistrar.setPreferredSize(new Dimension(190, 45));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 130, 40)); }
            @Override
            public void mouseExited(MouseEvent e) { btnRegistrar.setBackground(new Color(0, 150, 50)); }
        });

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaClientes.getSelectedRow();
                if (selectedRow < 0 || txtMonto.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe buscar y seleccionar un cliente de la tabla, y digitar el monto a pagar.", "Atención", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String rncIngresado = txtRncPersonal.getText().trim();
                if (cbxComprobante.getSelectedIndex() == 1) {
                    if (rncIngresado.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un RNC/Cédula válido para emitir el Comprobante Fiscal.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String rncLimpio = rncIngresado.replaceAll("-", "");
                    if (rncLimpio.length() != 9 && rncLimpio.length() != 11) {
                        JOptionPane.showMessageDialog(null, "RNC/Cédula '" + rncIngresado + "' no válido.\nUn RNC debe tener 9 dígitos y una Cédula 11 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                float monto = 0;
                try {
                    monto = Float.parseFloat(txtMonto.getText().trim());
                    if (monto <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El monto ingresado '" + txtMonto.getText() + "' no es válido.\nSolo se permiten números mayores a cero.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String idPago = txtIdPago.getText();
                String idCliente = (String) modeloTablaClientes.getValueAt(selectedRow, 0);
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

                if (text.length() <= 9) {
                    if (text.length() > 1 && text.length() <= 3) {
                        formatted = text.substring(0, 1) + "-" + text.substring(1);
                    } else if (text.length() > 3 && text.length() <= 8) {
                        formatted = text.substring(0, 1) + "-" + text.substring(1, 3) + "-" + text.substring(3);
                    } else if (text.length() > 8) {
                        formatted = text.substring(0, 1) + "-" + text.substring(1, 3) + "-" + text.substring(3, 8) + "-" + text.substring(8, Math.min(text.length(), 9));
                    } else {
                        formatted = text;
                    }
                } else {
                    if (text.length() > 3 && text.length() <= 10) {
                        formatted = text.substring(0, 3) + "-" + text.substring(3);
                    } else if (text.length() > 10) {
                        formatted = text.substring(0, 3) + "-" + text.substring(3, 10) + "-" + text.substring(10, Math.min(text.length(), 11));
                    }
                }

                if (!formatted.equals(textField.getText())) {
                    textField.setText(formatted);
                }
            }
        });
    }

    private void actualizarMonto() {
        if (cbxConcepto.getSelectedItem() != null && cbxConcepto.getSelectedItem().toString().equals("Mensualidad")) {
            int selectedRow = tablaClientes.getSelectedRow();
            if (selectedRow >= 0) {
                String idCliente = (String) modeloTablaClientes.getValueAt(selectedRow, 0);
                Cliente c = Altice.getInstance().getClienteById(idCliente);
                if (c != null && c.getPlan() != null) {
                    txtMonto.setText(String.valueOf(c.getPlan().getPrecio()));
                    txtMonto.setEditable(true);
                } else {
                    txtMonto.setText("");
                    JOptionPane.showMessageDialog(this, "El cliente seleccionado no tiene un plan asignado para cobrarle mensualidad.", "Atención", JOptionPane.WARNING_MESSAGE);
                    cbxConcepto.setSelectedIndex(3);
                }
            } else {
                txtMonto.setText("");
            }
        } else {
            txtMonto.setText("");
        }
    }

    private void actualizarDatosCliente() {
        int selectedRow = tablaClientes.getSelectedRow();
        if (selectedRow >= 0) {
            String idCliente = (String) modeloTablaClientes.getValueAt(selectedRow, 0);
            idClienteSeleccionado = idCliente;
            Cliente c = Altice.getInstance().getClienteById(idCliente);

            if (c != null) {
                txtDeudaActiva.setText("RD$ " + String.format("%.2f", c.getDeudaActiva()));

                // ← refrescar columna Deuda en la tabla con el valor real del objeto
                modeloTablaClientes.setValueAt("$" + String.format("%.2f", c.getDeudaActiva()), selectedRow, 3);

                if (c.getTipoCliente().equals("Empresarial")) {
                    cbxComprobante.setSelectedIndex(1);
                    txtRncPersonal.setText(c.getRnc() != null ? c.getRnc() : "");
                } else {
                    cbxComprobante.setSelectedIndex(0);
                    txtRncPersonal.setText("");
                }
            }
        } else {
            txtDeudaActiva.setText("");
            idClienteSeleccionado = null;
        }
    }

    private void cargarClientesTabla(String busqueda) {
        modeloTablaClientes.setRowCount(0);
        int selectIndexToRestore = -1;

        for (int i = 0; i < Altice.getInstance().getClientes().size(); i++) {
            Cliente c = Altice.getInstance().getClientes().get(i);
            if (c.getEstado().equals("Activo")) {
                String identificacion = c.getTipoCliente().equals("Empresarial") ? c.getRnc() : c.getCedula();
                if (identificacion == null) identificacion = "";

                if (c.getNombre().toLowerCase().contains(busqueda) ||
                    c.getIdCliente().toLowerCase().contains(busqueda) ||
                    identificacion.toLowerCase().contains(busqueda)) {

                    Object[] fila = new Object[4];
                    fila[0] = c.getIdCliente();
                    fila[1] = identificacion;
                    fila[2] = c.getNombre();
                    fila[3] = "$" + String.format("%.2f", c.getDeudaActiva()); // ← valor real
                    modeloTablaClientes.addRow(fila);

                    if (idClienteSeleccionado != null && c.getIdCliente().equals(idClienteSeleccionado)) {
                        selectIndexToRestore = modeloTablaClientes.getRowCount() - 1;
                    }
                }
            }
        }

        if (selectIndexToRestore != -1) {
            tablaClientes.setRowSelectionInterval(selectIndexToRestore, selectIndexToRestore);
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

        TicketPanel ticketPanel = new TicketPanel(25);
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

        ticketPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        ticketPanel.add(lblTotalAmount);
        ticketPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        ticketPanel.add(new DashedSeparator());
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
        ticketPanel.add(new DashedSeparator());
        ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        String idClient = pagoActual.getCliente().getTipoCliente().equals("Empresarial")
                ? pagoActual.getCliente().getRnc()
                : pagoActual.getCliente().getCedula();
        String tipoId = pagoActual.getCliente().getTipoCliente().equals("Empresarial") ? "RNC Cliente" : "Cédula";
        String nombreLabel = isFiscal ? "Razón Social" : "Cliente";

        if (isFiscal && pagoActual.getConcepto().contains("RNC:")) {
            String[] parts = pagoActual.getConcepto().split("RNC:");
            if (parts.length > 1) {
                idClient = parts[1].trim();
                tipoId = "RNC/Cédula";
            }
        }

        ticketPanel.add(crearFilaDetalle(nombreLabel, pagoActual.getCliente().getNombre()));
        ticketPanel.add(crearFilaDetalle(tipoId, idClient));

        String conceptoLimpio = pagoActual.getConcepto().split("\\|")[0].trim();
        ticketPanel.add(crearFilaDetalle("Concepto", conceptoLimpio));

        String tipoPlan = "N/A";
        String planAsignado = "Sin Plan Asignado";
        if (pagoActual.getCliente() != null && pagoActual.getCliente().getPlan() != null) {
            tipoPlan = pagoActual.getCliente().getPlan().getCategoria();
            planAsignado = pagoActual.getCliente().getPlan().getNombre();
        }
        ticketPanel.add(crearFilaDetalle("Tipo de Plan", tipoPlan));
        ticketPanel.add(crearFilaDetalle("Plan Asignado", planAsignado));
        ticketPanel.add(crearFilaDetalle("Método de Pago", pagoActual.getMetodoPago()));

        ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        ticketPanel.add(new DashedSeparator());
        ticketPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        if (isFiscal) {
            double subtotal = pagoActual.getMonto() / 1.18;
            double itbis = pagoActual.getMonto() - subtotal;
            ticketPanel.add(crearFilaDetalle("Subtotal", String.format("$%.2f", subtotal)));
            ticketPanel.add(crearFilaDetalle("ITBIS (18%)", String.format("$%.2f", itbis)));
        }
        ticketPanel.add(crearFilaDetalle("Total Facturado", String.format("$%.2f", pagoActual.getMonto())));

        ticketPanel.add(Box.createRigidArea(new Dimension(0, 50)));

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
        btnCerrar.setBackground(new Color(220, 53, 69));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnCerrar.setPreferredSize(new Dimension(300, 55));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btnCerrar.setBackground(new Color(180, 40, 50)); }
            @Override
            public void mouseExited(MouseEvent e) { btnCerrar.setBackground(new Color(220, 53, 69)); }
        });
        btnCerrar.addActionListener(e -> dispose());
        bottomPanel.add(btnCerrar);

        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel crearFilaDetalle(String label, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setOpaque(false);
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

    class TicketPanel extends RoundedPanel {
        public TicketPanel(int radius) { super(radius); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            g2.setColor(new Color(0, 150, 50, 20));
            g2.rotate(-Math.PI / 5, getWidth() / 2.0, getHeight() / 2.0);
            g2.drawString("PAGADO", getWidth() / 4, getHeight() / 2);
            g2.dispose();
        }
    }

    class DashedSeparator extends JSeparator {
        public DashedSeparator() {
            setMaximumSize(new Dimension(550, 5));
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new Color(200, 200, 200));
            float[] dash = {5.0f, 5.0f};
            g2.setStroke(new java.awt.BasicStroke(1.5f, java.awt.BasicStroke.CAP_BUTT,
                    java.awt.BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            g2.dispose();
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
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
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