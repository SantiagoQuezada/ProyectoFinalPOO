package Visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {

    public Principal() {
        // Configuración básica de la ventana principal
        setTitle("Sistema de Gestión - Principal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 1. Crear el Header (Barra superior negra)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 15, 15)); // Color casi negro
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Márgenes internos

        // Logo a la izquierda
        JLabel lblLogo = new JLabel("<html><span style='font-size:16px;'><b>\u221E Altice</b></span></html>");
        lblLogo.setForeground(Color.WHITE);
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Usuario a la derecha
        JLabel lblUser = new JLabel("Hola, Juan Pérez | Admin   \u2699   \u23FB"); // Engranaje y botón de salida
        lblUser.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(lblUser, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // 2. Crear el Panel Central (Títulos y Tarjetas)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        
        // Espacio superior
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Título Principal
        JLabel lblTituloPrincipal = new JLabel("Panel de Control Principal - Altice");
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(lblTituloPrincipal);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Bienvenido al sistema interno. Gestiona las operaciones clave de la empresa.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.DARK_GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio
        centerPanel.add(lblSubtitulo);

        // Espacio antes de las tarjetas
        centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Panel para las Tarjetas (Usamos FlowLayout para que queden una al lado de la otra)
        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        cardsPanel.setBackground(Color.WHITE);

        // Crear las 3 tarjetas usando un método auxiliar
        JPanel cardPlanes = crearTarjeta(
                "\uD83D\uDCC8", // Icono (Emoji de gráfica)
                "PLANES", 
                "Gestionar paquetes, ofertas y<br>tarifas de servicios.", 
                "VER PLANES");
        
        JPanel cardEmpleados = crearTarjeta(
                "\uD83D\uDC65", // Icono (Emoji de personas)
                "LISTADO DE<br>EMPLEADOS", 
                "Base de datos del personal,<br>roles y perfiles.", 
                "VER EMPLEADOS");
        
        JPanel cardClientes = crearTarjeta(
                "\uD83D\uDCC1", // Icono (Emoji de carpeta/archivos)
                "LISTADO DE<br>CLIENTES", 
                "Consultar registros, servicios<br>activos y facturación.", 
                "VER CLIENTES");

        cardsPanel.add(cardPlanes);
        cardsPanel.add(cardEmpleados);
        cardsPanel.add(cardClientes);

        centerPanel.add(cardsPanel);
        
        add(centerPanel, BorderLayout.CENTER);

        // 3. Crear el Footer (Pie de página)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Sistema de Gestión Interna | Términos y Condiciones");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.GRAY);
        footerPanel.add(lblFooter);

        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Método auxiliar para crear las "Tarjetas" cuadradas del centro.
     * Esto ahorra escribir el mismo código tres veces.
     */
    private JPanel crearTarjeta(String icono, String titulo, String descripcion, String textoBoton) {
        // Panel principal de la tarjeta
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setPreferredSize(new Dimension(280, 320)); // Tamaño de la tarjeta
        tarjeta.setBackground(Color.WHITE);
        
        // Borde gris redondeado (simulado con LineBorder) y un poco de padding interno
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 2, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Icono (En el futuro puedes cambiar este JLabel para que use un ImageIcon real)
        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70)); // Fuente grande para el icono
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Título de la tarjeta
        JLabel lblTitulo = new JLabel("<html><div style='text-align: center;'>" + titulo + "</div></html>");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Descripción
        JLabel lblDesc = new JLabel("<html><div style='text-align: center;'>" + descripcion + "</div></html>");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDesc.setForeground(Color.DARK_GRAY);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Botón
        JButton boton = new JButton(textoBoton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(Color.BLACK);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false); // Quitar el borde punteado al hacer clic
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setMaximumSize(new Dimension(180, 40));

        // EVENTO DEL BOTÓN: Aquí simulamos la navegación a otra ventana
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textoBoton.equals("VER PLANES")) {
                    // Navegar a la ventana de Planes real
                    Planes ventanaPlanes = new Planes();
                    ventanaPlanes.setVisible(true);
                    dispose(); // Cierra la ventana principal actual
                } else if (textoBoton.equals("VER EMPLEADOS")) {
                    // Navegar a la ventana de Empleados
                    Empleados ventanaEmpleados = new Empleados();
                    ventanaEmpleados.setVisible(true);
                    dispose();
                } else if (textoBoton.equals("VER CLIENTES")) {
                    // Navegar a la ventana de Clientes
                    Clientes ventanaClientes = new Clientes();
                    ventanaClientes.setVisible(true);
                    dispose();
                } else {
                    abrirVentanaPrueba(textoBoton);
                }
            }
        });

        // Agregar elementos a la tarjeta con espacios entre ellos
        tarjeta.add(Box.createVerticalGlue()); // Empuja hacia el centro
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

    /**
     * Método para simular que abrimos una ventana nueva al presionar un botón
     */
    private void abrirVentanaPrueba(String nombreModulo) {
        // Creamos un JFrame temporal solo para demostrar la navegación
        JFrame ventanaSecundaria = new JFrame("Módulo: " + nombreModulo);
        ventanaSecundaria.setSize(600, 400);
        ventanaSecundaria.setLocationRelativeTo(this); // Centrar respecto a la ventana principal
        
        JLabel mensaje = new JLabel("Bienvenido al módulo de " + nombreModulo, SwingConstants.CENTER);
        mensaje.setFont(new Font("Arial", Font.BOLD, 20));
        ventanaSecundaria.add(mensaje);
        
        ventanaSecundaria.setVisible(true);
        
        // Si quisieras cerrar la ventana principal al abrir esta, usarías:
        // this.dispose(); 
    }

    // Método main para poder ejecutar y probar solo esta ventana
    public static void main(String[] args) {
        // Cambiar el "Look and Feel" para que se vea más moderno y menos como Windows 98
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar la aplicación
        SwingUtilities.invokeLater(() -> {
            Principal ventana = new Principal();
            ventana.setVisible(true);
        });
    }
}