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
	private JTextField txtNombre;
	private JComboBox<String> cbxPlanes;

	public RegCliente() {
		setTitle("Registrar Nuevo Cliente");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblIdCliente = new JLabel("ID Cliente:");
		lblIdCliente.setBounds(30, 30, 100, 20);
		contentPanel.add(lblIdCliente);
		
		txtIdCliente = new JTextField();
		txtIdCliente.setText("C-" + (Empresa.getInstance().getClientes().size() + 100)); 
		txtIdCliente.setEditable(false);
		txtIdCliente.setBounds(140, 30, 250, 25);
		contentPanel.add(txtIdCliente);
		txtIdCliente.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setBounds(30, 80, 110, 20);
		contentPanel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(140, 80, 250, 25);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbxPlanes.requestFocus();
					cbxPlanes.showPopup();
				}
			}
		});
		
		JLabel lblPlan = new JLabel("Plan Inicial:");
		lblPlan.setBounds(30, 130, 100, 20);
		contentPanel.add(lblPlan);
		
		cbxPlanes = new JComboBox<String>();
		cbxPlanes.setBounds(140, 130, 250, 25);
		cargarPlanes();
		contentPanel.add(cbxPlanes);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setMnemonic(KeyEvent.VK_R);
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtNombre.getText().isEmpty() || cbxPlanes.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String id = txtIdCliente.getText();
				String nombre = txtNombre.getText();
				String nombrePlan = cbxPlanes.getSelectedItem().toString();
				
				Plan planSeleccionado = null;
				
				Cliente nuevoCliente = new Cliente(id, nombre, null);
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
				txtNombre.requestFocus();
			}
		});
	}

	private void cargarPlanes() {
		cbxPlanes.addItem("<Seleccione un plan>");
		
		for(String plan : Empresa.getInstance().obtenerNombresPlanesPorCategoria("Combinado")) {
			if(!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}
		
		for(String plan : Empresa.getInstance().obtenerNombresPlanesPorCategoria("Hogar")) {
			if(!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}
		
		for(String plan : Empresa.getInstance().obtenerNombresPlanesPorCategoria("Móvil")) {
			if(!plan.equals("Seleccione un plan...")) {
				cbxPlanes.addItem(plan);
			}
		}
	}
}