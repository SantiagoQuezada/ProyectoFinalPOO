package Visual;

import Logico.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Planes extends JFrame {

    public Planes() {
        // Configuración básica
        setTitle("Sistema de Gestión - Módulo de Planes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 1. Header (Igual a la principal, pero con botón de Volver)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 15, 15));
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Panel izquierdo del header (Botón volver + Logo)
        JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftHeaderPanel.setOpaque(false);
        
        JButton btnVolver = new JButton("\u25C0 Volver"); // Símbolo de flecha
        btnVolver.setBackground(Color.LIGHT_GRAY);
        btnVolver.setForeground(Color.BLACK); // Texto negro solicitado
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> {
            Principal principal = new Principal();
            principal.setVisible(true);
            dispose(); // Cierra esta ventana y vuelve al menú principal
        });

        JLabel lblLogo = new JLabel("<html><span style='font-size:16px;'><b>\u221E Altice</b></span></html>");
        lblLogo.setForeground(Color.WHITE);
        
        leftHeaderPanel.add(btnVolver);
        leftHeaderPanel.add(lblLogo);
        headerPanel.add(leftHeaderPanel, BorderLayout.WEST);

        // Usuario a la derecha
        JLabel lblUser = new JLabel("Hola, Juan Pérez | Admin   \u2699   \u23FB");
        lblUser.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(lblUser, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // 2. Panel Central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel lblTituloPrincipal = new JLabel("Gestión de Planes y Servicios");
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTituloPrincipal.setForeground(Color.BLACK); // Asegurando color negro
        lblTituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(lblTituloPrincipal);

        JLabel lblSubtitulo = new JLabel("Selecciona una categoría para asignar un plan a un cliente.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.BLACK); // Asegurando color negro
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(lblSubtitulo);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Tarjetas
        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        cardsPanel.setBackground(Color.WHITE);

        // Llenar Arrays leyendo dinámicamente desde el Sistema (Logico)
        String[] planesTripleplay = Empresa.getInstance().obtenerNombresPlanesPorCategoria("Combinado");
        String[] planesHogar = Empresa.getInstance().obtenerNombresPlanesPorCategoria("Hogar");
        String[] planesMoviles = Empresa.getInstance().obtenerNombresPlanesPorCategoria("Móvil");

        JPanel cardCombinados = crearTarjetaPlan("\uD83D\uDCFA", "PLANES<br>COMBINADOS", "Tripleplay", planesTripleplay);
        JPanel cardHogar = crearTarjetaPlan("\uD83C\uDFE0", "PLANES<br>HOGAR", "Internet Fibra", planesHogar);
        JPanel cardMoviles = crearTarjetaPlan("\uD83D\uDCF1", "PLANES<br>MÓVILES", "Pospago", planesMoviles);

        cardsPanel.add(cardCombinados);
        cardsPanel.add(cardHogar);
        cardsPanel.add(cardMoviles);

        centerPanel.add(cardsPanel);
        add(centerPanel, BorderLayout.CENTER);

        // 3. Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Módulo de Planes");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.BLACK); // Asegurando color negro
        footerPanel.add(lblFooter);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel crearTarjetaPlan(String icono, String titulo, String subtitulo, String[] planesDisponibles) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setPreferredSize(new Dimension(280, 320));
        tarjeta.setBackground(Color.WHITE);
        
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 2, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel("<html><div style='text-align: center; color: black;'>" + titulo + "</div></html>");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDesc = new JLabel(subtitulo);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDesc.setForeground(Color.BLACK); // Asegurando color negro
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton boton = new JButton("ASIGNAR PLAN");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(new Color(230, 230, 230)); // Fondo gris claro
        boton.setForeground(Color.BLACK); // Texto negro
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setMaximumSize(new Dimension(180, 40));
        boton.setBorder(new LineBorder(Color.GRAY, 1));

        // Evento para abrir el menú (ComboBox)
        boton.addActionListener(e -> mostrarMenuAsignacion(subtitulo, planesDisponibles));

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblIcono);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 10)));
        tarjeta.add(lblDesc);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 25)));
        tarjeta.add(boton);
        tarjeta.add(Box.createVerticalGlue());

        return tarjeta;
    }

    /**
     * Muestra una ventana emergente (Dialog) con los ComboBox para elegir el Cliente y el Plan
     */
    private void mostrarMenuAsignacion(String categoria, String[] planesDisponibles) {
        JDialog dialog = new JDialog(this, "Asignar - " + categoria, true); // true hace que sea modal (bloquea el fondo)
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.WHITE);

        // Panel del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 1, 10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Obtener la lista real de clientes de la base lógica
        String[] clientesRegistrados = Empresa.getInstance().obtenerNombresClientes();

        JComboBox<String> cbClientes = new JComboBox<>(clientesRegistrados);
        JComboBox<String> cbPlanes = new JComboBox<>(planesDisponibles);

        // Estilizar un poco los ComboBox
        cbClientes.setBackground(Color.WHITE);
        cbClientes.setForeground(Color.BLACK);
        cbPlanes.setBackground(Color.WHITE);
        cbPlanes.setForeground(Color.BLACK);

        JLabel lblSelCliente = new JLabel("1. Seleccionar Cliente:");
        lblSelCliente.setForeground(Color.BLACK);
        JLabel lblSelPlan = new JLabel("2. Seleccionar Plan " + categoria + ":");
        lblSelPlan.setForeground(Color.BLACK);

        panelFormulario.add(lblSelCliente);
        panelFormulario.add(cbClientes);
        panelFormulario.add(lblSelPlan);
        panelFormulario.add(cbPlanes);

        dialog.add(panelFormulario, BorderLayout.CENTER);

        // Panel para el botón de guardar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(245, 245, 245));
        panelBotones.setBorder(new EmptyBorder(10, 20, 10, 20));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dialog.dispose());

        JButton btnGuardar = new JButton("Confirmar Asignación");
        btnGuardar.setBackground(new Color(230, 230, 230)); // Gris claro para coincidir
        btnGuardar.setForeground(Color.BLACK); // Texto estrictamente negro
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(new LineBorder(Color.GRAY, 1));
        
        btnGuardar.addActionListener(e -> {
            // Lógica de validación
            if(cbClientes.getSelectedIndex() == 0 || cbPlanes.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(dialog, "Por favor, seleccione un cliente y un plan.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                String clienteSelec = (String) cbClientes.getSelectedItem();
                String planSelec = (String) cbPlanes.getSelectedItem();
                
                // Extraer el ID ("C-0010 - Juan Pérez" -> "C-0010") y asignar
                String idCliente = clienteSelec.split(" - ")[0];
                Empresa.getInstance().asignarPlanACliente(idCliente, planSelec);

                JOptionPane.showMessageDialog(dialog, "El plan ha sido asignado exitosamente al cliente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose(); // Cerrar el diálogo
            }
        });

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        dialog.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar el diálogo
        dialog.setVisible(true);
    }
}