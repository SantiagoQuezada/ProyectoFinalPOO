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
import Logico.Empleado;
import Logico.Altice;
import Logico.Rol;
import Logico.Usuario;

public class RegEmpleado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdEmpleado;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtSalario;
	private JComboBox<String> cbxDepartamento;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JComboBox<Rol> cbxRol;

	public RegEmpleado() {
		setTitle("Registrar Nuevo Empleado");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 500, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIdEmpleado = new JLabel("ID Empleado:");
		lblIdEmpleado.setBounds(30, 30, 100, 20);
		contentPanel.add(lblIdEmpleado);

		txtIdEmpleado = new JTextField();
		txtIdEmpleado.setText("E-" + (Altice.getInstance().getEmpleados().size() + 1001));
		txtIdEmpleado.setEditable(false);
		txtIdEmpleado.setBounds(150, 30, 290, 25);
		contentPanel.add(txtIdEmpleado);

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setBounds(30, 80, 110, 20);
		contentPanel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(150, 80, 290, 25);
		contentPanel.add(txtCedula);

		txtCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) txtNombre.requestFocus();
			}
		});

		JLabel lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setBounds(30, 130, 120, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(150, 130, 290, 25);
		contentPanel.add(txtNombre);

		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) txtTelefono.requestFocus();
			}
		});

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(30, 180, 110, 20);
		contentPanel.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(150, 180, 290, 25);
		contentPanel.add(txtTelefono);

		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) txtDireccion.requestFocus();
			}
		});

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(30, 230, 110, 20);
		contentPanel.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(150, 230, 290, 25);
		contentPanel.add(txtDireccion);

		txtDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) cbxDepartamento.requestFocus();
			}
		});

		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setBounds(30, 280, 110, 20);
		contentPanel.add(lblDepartamento);

		cbxDepartamento = new JComboBox<String>();
		cbxDepartamento.addItem("Ventas");
		cbxDepartamento.addItem("Soporte Técnico");
		cbxDepartamento.addItem("Atención al Cliente");
		cbxDepartamento.addItem("Administración");
		cbxDepartamento.setBounds(150, 280, 290, 25);
		contentPanel.add(cbxDepartamento);

		JLabel lblSalario = new JLabel("Salario:");
		lblSalario.setBounds(30, 330, 110, 20);
		contentPanel.add(lblSalario);

		txtSalario = new JTextField();
		txtSalario.setBounds(150, 330, 290, 25);
		contentPanel.add(txtSalario);

		JLabel lblUsername = new JLabel("Usuario Sis:");
		lblUsername.setBounds(30, 380, 110, 20);
		contentPanel.add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(150, 380, 290, 25);
		contentPanel.add(txtUsername);

		JLabel lblPassword = new JLabel("Contraseña Sis:");
		lblPassword.setBounds(30, 430, 110, 20);
		contentPanel.add(lblPassword);

		txtPassword = new JTextField();
		txtPassword.setBounds(150, 430, 290, 25);
		contentPanel.add(txtPassword);

		JLabel lblRol = new JLabel("Rol de Sistema:");
		lblRol.setBounds(30, 480, 110, 20);
		contentPanel.add(lblRol);

		cbxRol = new JComboBox<Rol>();
		for (Rol rol : Rol.values()) {
			cbxRol.addItem(rol);
		}
		cbxRol.setBounds(150, 480, 290, 25);
		contentPanel.add(cbxRol);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Registrar Empleado");
		btnRegistrar.setMnemonic(KeyEvent.VK_R);
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
					String password = txtPassword.getText();
					Rol rol = (Rol) cbxRol.getSelectedItem();

					Usuario nuevoUsuario = new Usuario(username, password, rol);
					Empleado nuevoEmpleado = new Empleado(cedula, nombre, telefono, direccion, id, departamento, salario, nuevoUsuario);
					
					Altice.getInstance().registrarEmpleado(nuevoEmpleado);

					JOptionPane.showMessageDialog(null, "Empleado registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El salario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
				}
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
}