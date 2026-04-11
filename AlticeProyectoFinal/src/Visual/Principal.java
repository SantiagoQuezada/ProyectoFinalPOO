package Visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {

    public Principal() {
        setTitle("Sistema de Gestión - Principal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 15, 15));
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblLogo = new JLabel("<html><span style='font-size:16px;'><b>\u221E Altice</b></span></html>");
        lblLogo.setForeground(Color.WHITE);
        headerPanel.add(lblLogo, BorderLayout.WEST);

        JLabel lblUser = new JLabel("Hola, Juan Pérez | Admin   \u2699   \u23FB");
        lblUser.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(lblUser, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel lblTituloPrincipal = new JLabel("Panel de Control Principal - Altice");
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(lblTituloPrincipal);

        JLabel lblSubtitulo = new JLabel("Bienvenido al sistema interno. Gestiona las operaciones clave de la empresa.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.DARK_GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(lblSubtitulo);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        cardsPanel.setBackground(Color.WHITE);

        JPanel cardPlanes = crearTarjeta(
                "\uD83D\uDCC8", 
                "PLANES", 
                "Gestionar paquetes, ofertas y<br>tarifas de servicios.", 
                "VER PLANES");
        
        JPanel cardEmpleados = crearTarjeta(
                "\uD83D\uDC65", 
                "LISTADO DE<br>EMPLEADOS", 
                "Base de datos del personal,<br>roles y perfiles.", 
                "VER EMPLEADOS");
        
        JPanel cardClientes = crearTarjeta(
                "\uD83D\uDCC1", 
                "LISTADO DE<br>CLIENTES", 
                "Consultar registros, servicios<br>activos y facturación.", 
                "VER CLIENTES");

        cardsPanel.add(cardPlanes);
        cardsPanel.add(cardEmpleados);
        cardsPanel.add(cardClientes);

        centerPanel.add(cardsPanel);
        
        add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Sistema de Gestión Interna | Términos y Condiciones");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.GRAY);
        footerPanel.add(lblFooter);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel crearTarjeta(String icono, String titulo, String descripcion, String textoBoton) {
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
        
        JLabel lblTitulo = new JLabel("<html><div style='text-align: center;'>" + titulo + "</div></html>");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDesc = new JLabel("<html><div style='text-align: center;'>" + descripcion + "</div></html>");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDesc.setForeground(Color.DARK_GRAY);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton boton = new JButton(textoBoton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(Color.BLACK);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setMaximumSize(new Dimension(180, 40));

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textoBoton.equals("VER PLANES")) {
                    Planes ventanaPlanes = new Planes();
                    ventanaPlanes.setVisible(true);
                    dispose();
                } else if (textoBoton.equals("VER EMPLEADOS")) {
                    Empleados ventanaEmpleados = new Empleados();
                    ventanaEmpleados.setVisible(true);
                    dispose();
                } else if (textoBoton.equals("VER CLIENTES")) {
                    Clientes ventanaClientes = new Clientes();
                    ventanaClientes.setVisible(true);
                    dispose();
                } else {
                    abrirVentanaPrueba(textoBoton);
                }
            }
        });

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblIcono);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
        tarjeta.add(lblDesc);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 25)));
        tarjeta.add(boton);
        tarjeta.add(Box.createVerticalGlue());

        return tarjeta;
    }

    private void abrirVentanaPrueba(String nombreModulo) {
        JFrame ventanaSecundaria = new JFrame("Módulo: " + nombreModulo);
        ventanaSecundaria.setSize(600, 400);
        ventanaSecundaria.setLocationRelativeTo(this);
        
        JLabel mensaje = new JLabel("Bienvenido al módulo de " + nombreModulo, SwingConstants.CENTER);
        mensaje.setFont(new Font("Arial", Font.BOLD, 20));
        ventanaSecundaria.add(mensaje);
        
        ventanaSecundaria.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Principal ventana = new Principal();
            ventana.setVisible(true);
        });
    }
}