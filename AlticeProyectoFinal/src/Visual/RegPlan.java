package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import Logico.Plan;
import Logico.Altice;

public class RegPlan extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdPlan;
	private JComboBox<String> cbxCategoria;
	private JTextField txtNombre;
	private Plan planActual;

	public RegPlan(Plan plan, boolean soloLectura) {
		this.planActual = plan;
		if (planActual == null) {
			setTitle("Registrar Nuevo Plan");
		} else if (soloLectura) {
			setTitle("Detalles del Plan");
		} else {
			setTitle("Modificar Plan");
		}
		
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 480, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIdPlan = new JLabel("ID Plan:");
		lblIdPlan.setBounds(30, 30, 100, 20);
		contentPanel.add(lblIdPlan);

		txtIdPlan = new JTextField();
		if (planActual == null) {
			txtIdPlan.setText(Altice.getInstance().generarIdPlan());
		} else {
			txtIdPlan.setText(planActual.getIdPlan());
		}
		txtIdPlan.setEditable(false);
		txtIdPlan.setBounds(150, 30, 270, 25);
		contentPanel.add(txtIdPlan);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(30, 80, 110, 20);
		contentPanel.add(lblCategoria);

		cbxCategoria = new JComboBox<String>();
		cbxCategoria.addItem("Combinado");
		cbxCategoria.addItem("Hogar");
		cbxCategoria.addItem("Móvil");
		cbxCategoria.setBounds(150, 80, 270, 25);
		contentPanel.add(cbxCategoria);

		JLabel lblNombre = new JLabel("Nombre Plan:");
		lblNombre.setBounds(30, 130, 120, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(150, 130, 270, 25);
		contentPanel.add(txtNombre);

		if (planActual != null) {
			cbxCategoria.setSelectedItem(planActual.getCategoria());
			txtNombre.setText(planActual.getNombre());
		}

		if (soloLectura) {
			cbxCategoria.setEnabled(false);
			txtNombre.setEditable(false);
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		if (!soloLectura) {
			JButton btnRegistrar = new JButton(planActual == null ? "Registrar" : "Guardar Cambios");
			btnRegistrar.setMnemonic(KeyEvent.VK_R);
			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (txtNombre.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, indique un nombre para el plan.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					String id = txtIdPlan.getText();
					String categoria = cbxCategoria.getSelectedItem().toString();
					String nombre = txtNombre.getText();

					if (planActual == null) {
						Plan nuevoPlan = new Plan(id, categoria, nombre);
						Altice.getInstance().registrarPlan(nuevoPlan);
						JOptionPane.showMessageDialog(null, "Plan registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					} else {
						planActual.setCategoria(categoria);
						planActual.setNombre(nombre);
						JOptionPane.showMessageDialog(null, "Plan actualizado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
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
					txtNombre.requestFocus();
				}
			}
		});
	}
}