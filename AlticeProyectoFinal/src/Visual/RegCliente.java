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
import Logico.Empresa;
import Logico.Plan;

public class RegCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdCliente;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JComboBox<String> cbxPlanes;

	public RegCliente() {
		setTitle("Registrar Nuevo Cliente");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 480, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIdCliente = new JLabel("ID Cliente:");
		lblIdCliente.setBounds(30, 30, 100, 20);
		contentPanel.add(lblIdCliente);

		txtIdCliente = new JTextField();
		txtIdCliente.setText(Empresa.getInstance().generarIdCliente());
		txtIdCliente.setEditable(false);
		txtIdCliente.setBounds(150, 30, 270, 25);
		contentPanel.add(txtIdCliente);
		txtIdCliente.setColumns(10);

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setBounds(30, 80, 110, 20);
		contentPanel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(150, 80, 270, 25);
		contentPanel.add(txtCedula);
		txtCedula.setColumns(10);

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
		txtNombre.setColumns(10);

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
		txtTelefono.setColumns(10);

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
		txtDireccion.setColumns(10);

		txtDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbxPlanes.requestFocus();
					cbxPlanes.showPopup();
				}
			}
		});

		JLabel lblPlan = new JLabel("Plan Inicial:");
		lblPlan.setBounds(30, 280, 100, 20);
		contentPanel.add(lblPlan);

		cbxPlanes = new JComboBox<String>();
		cbxPlanes.setBounds(150, 280, 270, 25);
		cargarPlanes();
		contentPanel.add(cbxPlanes);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Registrar");
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
				String nombrePlan = cbxPlanes.getSelectedItem().toString();

				Cliente nuevoCliente = new Cliente(id, cedula, nombre, telefono, direccion, null);
				Empresa.getInstance().registrarCliente(nuevoCliente);
				Empresa.getInstance().asignarPlanACliente(id, nombrePlan);

				JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		buttonPane.add(btnRegistrar);
		getRootPane().setDefaultButton(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setMnemonic(KeyEvent.VK_C);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				txtCedula.requestFocus();
			}
		});
	}

	private void cargarPlanes() {
		cbxPlanes.addItem("<Seleccione un plan>");

		for (String plan : Empresa.getInstance().obtenerNombresPlanesPorCategoria("Combinado")) {
			if (!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}

		for (String plan : Empresa.getInstance().obtenerNombresPlanesPorCategoria("Hogar")) {
			if (!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}

		for (String plan : Empresa.getInstance().obtenerNombresPlanesPorCategoria("Móvil")) {
			if (!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}
	}
}