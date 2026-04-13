package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import Logico.Cliente;
import Logico.Plan;
import Logico.Altice;

public class RegCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdCliente;
	private JComboBox<String> cbxTipoCliente;
	private JTextField txtRnc;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JComboBox<String> cbxEstado;
	private JComboBox<String> cbxPlanes;
	private Cliente clienteActual;

	private JLabel lblCedula;
	private JLabel lblNombre;

	public RegCliente(Cliente cliente, boolean soloLectura) {
		this.clienteActual = cliente;
		if (clienteActual == null) {
			setTitle("Registrar Nuevo Cliente");
		} else if (soloLectura) {
			setTitle("Detalles del Cliente");
		} else {
			setTitle("Modificar Cliente");
		}
		
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 520, 650);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIdCliente = new JLabel("ID Cliente:");
		lblIdCliente.setBounds(30, 30, 120, 20);
		contentPanel.add(lblIdCliente);

		txtIdCliente = new JTextField();
		if (clienteActual == null) {
			txtIdCliente.setText(Altice.getInstance().generarIdCliente());
		} else {
			txtIdCliente.setText(clienteActual.getIdCliente());
		}
		txtIdCliente.setEditable(false);
		txtIdCliente.setBounds(160, 30, 300, 25);
		contentPanel.add(txtIdCliente);

		JLabel lblTipoCliente = new JLabel("Tipo Cliente:");
		lblTipoCliente.setBounds(30, 90, 120, 20);
		contentPanel.add(lblTipoCliente);

		cbxTipoCliente = new JComboBox<String>();
		cbxTipoCliente.addItem("Personal");
		cbxTipoCliente.addItem("Empresarial");
		cbxTipoCliente.setBounds(160, 90, 300, 25);
		contentPanel.add(cbxTipoCliente);

		JLabel lblRnc = new JLabel("RNC:");
		lblRnc.setBounds(30, 150, 120, 20);
		contentPanel.add(lblRnc);

		txtRnc = new JTextField();
		txtRnc.setBounds(160, 150, 300, 25);
		txtRnc.setEnabled(false);
		contentPanel.add(txtRnc);

		lblCedula = new JLabel("Cédula:");
		lblCedula.setBounds(30, 210, 120, 20);
		contentPanel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(160, 210, 300, 25);
		contentPanel.add(txtCedula);

		lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setBounds(30, 270, 120, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(160, 270, 300, 25);
		contentPanel.add(txtNombre);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(30, 330, 120, 20);
		contentPanel.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(160, 330, 300, 25);
		contentPanel.add(txtTelefono);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(30, 390, 120, 20);
		contentPanel.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(160, 390, 300, 25);
		contentPanel.add(txtDireccion);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(30, 450, 120, 20);
		contentPanel.add(lblEstado);

		cbxEstado = new JComboBox<String>();
		cbxEstado.addItem("Activo");
		cbxEstado.addItem("Inactivo");
		cbxEstado.addItem("Suspendido");
		cbxEstado.setBounds(160, 450, 300, 25);
		contentPanel.add(cbxEstado);

		JLabel lblPlan = new JLabel("Plan Inicial:");
		lblPlan.setBounds(30, 510, 120, 20);
		contentPanel.add(lblPlan);

		cbxPlanes = new JComboBox<String>();
		cbxPlanes.setBounds(160, 510, 300, 25);
		cargarPlanes();
		contentPanel.add(cbxPlanes);

		cbxTipoCliente.addActionListener(e -> {
			if (cbxTipoCliente.getSelectedItem().toString().equals("Empresarial")) {
				txtRnc.setEnabled(true);
				lblNombre.setText("Razón Social:");
				lblCedula.setText("Cédula Representante:");
			} else {
				txtRnc.setEnabled(false);
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
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		if (!soloLectura) {
			JButton btnRegistrar = new JButton(clienteActual == null ? "Registrar" : "Guardar Cambios");
			btnRegistrar.setMnemonic(KeyEvent.VK_R);
			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtDireccion.getText().isEmpty() || cbxPlanes.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					String id = txtIdCliente.getText();
					String tipoCliente = cbxTipoCliente.getSelectedItem().toString();
					String rnc = txtRnc.getText();
					String cedula = txtCedula.getText();
					String nombre = txtNombre.getText();
					String telefono = txtTelefono.getText();
					String direccion = txtDireccion.getText();
					String estado = cbxEstado.getSelectedItem().toString();
					String nombrePlan = ((String) cbxPlanes.getSelectedItem()).split(" - \\$")[0];

					if (clienteActual == null) {
						Cliente nuevoCliente = new Cliente(cedula, nombre, telefono, direccion, id, estado, null, tipoCliente, rnc);
						Altice.getInstance().registrarCliente(nuevoCliente);
						Altice.getInstance().asignarPlanACliente(id, nombrePlan);
						JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					} else {
						clienteActual.setTipoCliente(tipoCliente);
						clienteActual.setRnc(rnc);
						clienteActual.setCedula(cedula);
						clienteActual.setNombre(nombre);
						clienteActual.setTelefono(telefono);
						clienteActual.setDireccion(direccion);
						clienteActual.setEstado(estado);
						Altice.getInstance().asignarPlanACliente(id, nombrePlan);
						JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
					
					dispose();
				}
			});
			buttonPane.add(btnRegistrar);
			getRootPane().setDefaultButton(btnRegistrar);
		}

		JButton btnCancelar = new JButton(soloLectura ? "Cerrar" : "Cancelar");
		btnCancelar.setMnemonic(KeyEvent.VK_C);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if(!soloLectura) {
					txtCedula.requestFocus();
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
}