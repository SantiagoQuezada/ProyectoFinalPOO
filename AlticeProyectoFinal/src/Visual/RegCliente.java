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
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JComboBox<String> cbxEstado;
	private JComboBox<String> cbxPlanes;
	private Cliente clienteActual;

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
		setBounds(100, 100, 480, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIdCliente = new JLabel("ID Cliente:");
		lblIdCliente.setBounds(30, 30, 100, 20);
		contentPanel.add(lblIdCliente);

		txtIdCliente = new JTextField();
		if (clienteActual == null) {
			txtIdCliente.setText(Altice.getInstance().generarIdCliente());
		} else {
			txtIdCliente.setText(clienteActual.getIdCliente());
		}
		txtIdCliente.setEditable(false);
		txtIdCliente.setBounds(150, 30, 270, 25);
		contentPanel.add(txtIdCliente);

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setBounds(30, 80, 110, 20);
		contentPanel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(150, 80, 270, 25);
		contentPanel.add(txtCedula);

		txtCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtNombre.requestFocus();
				}
			}
		});

		JLabel lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setBounds(30, 130, 120, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(150, 130, 270, 25);
		contentPanel.add(txtNombre);

		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtTelefono.requestFocus();
				}
			}
		});

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(30, 180, 110, 20);
		contentPanel.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(150, 180, 270, 25);
		contentPanel.add(txtTelefono);

		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtDireccion.requestFocus();
				}
			}
		});

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(30, 230, 110, 20);
		contentPanel.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(150, 230, 270, 25);
		contentPanel.add(txtDireccion);

		txtDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbxEstado.requestFocus();
					cbxEstado.showPopup();
				}
			}
		});

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(30, 280, 100, 20);
		contentPanel.add(lblEstado);

		cbxEstado = new JComboBox<String>();
		cbxEstado.addItem("Activo");
		cbxEstado.addItem("Inactivo");
		cbxEstado.addItem("Suspendido");
		cbxEstado.setBounds(150, 280, 270, 25);
		contentPanel.add(cbxEstado);

		JLabel lblPlan = new JLabel("Plan Inicial:");
		lblPlan.setBounds(30, 330, 100, 20);
		contentPanel.add(lblPlan);

		cbxPlanes = new JComboBox<String>();
		cbxPlanes.setBounds(150, 330, 270, 25);
		cargarPlanes();
		contentPanel.add(cbxPlanes);

		if (clienteActual != null) {
			txtCedula.setText(clienteActual.getCedula());
			txtNombre.setText(clienteActual.getNombre());
			txtTelefono.setText(clienteActual.getTelefono());
			txtDireccion.setText(clienteActual.getDireccion());
			cbxEstado.setSelectedItem(clienteActual.getEstado());
			
			if (clienteActual.getPlan() != null) {
				cbxPlanes.setSelectedItem(clienteActual.getPlan().getNombre());
			}
		}

		if (soloLectura) {
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
					String cedula = txtCedula.getText();
					String nombre = txtNombre.getText();
					String telefono = txtTelefono.getText();
					String direccion = txtDireccion.getText();
					String estado = cbxEstado.getSelectedItem().toString();
					String nombrePlan = cbxPlanes.getSelectedItem().toString();

					if (clienteActual == null) {
						Cliente nuevoCliente = new Cliente(cedula, nombre, telefono, direccion, id, estado, null);
						Altice.getInstance().registrarCliente(nuevoCliente);
						Altice.getInstance().asignarPlanACliente(id, nombrePlan);
						JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					} else {
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

		for (String plan : Altice.getInstance().obtenerNombresPlanesPorCategoria("Combinado")) {
			if (!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}

		for (String plan : Altice.getInstance().obtenerNombresPlanesPorCategoria("Hogar")) {
			if (!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}

		for (String plan : Altice.getInstance().obtenerNombresPlanesPorCategoria("Móvil")) {
			if (!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}
	}
}