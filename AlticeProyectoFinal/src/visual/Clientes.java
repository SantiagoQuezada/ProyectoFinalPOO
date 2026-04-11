package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Clientes extends JFrame {

    public Clientes() {
        setTitle("Sistema de Gestión - Módulo de Clientes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 1. Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 15, 15));
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftHeaderPanel.setOpaque(false);
        
        JButton btnVolver = new JButton("\u25C0 Volver");
        btnVolver.setBackground(Color.LIGHT_GRAY);
        btnVolver.setForeground(Color.BLACK); // Texto negro
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> {
            Principal principal = new Principal();
            principal.setVisible(true);
            dispose();
        });

        JLabel lblLogo = new JLabel("<html><span style='font-size:16px;'><b>\u221E Altice</b></span></html>");
        lblLogo.setForeground(Color.WHITE); 
        
        leftHeaderPanel.add(btnVolver);
        leftHeaderPanel.add(lblLogo);
        headerPanel.add(leftHeaderPanel, BorderLayout.WEST);

        JLabel lblUser = new JLabel("Hola, Juan Pérez | Admin   \u2699   \u23FB");
        lblUser.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(lblUser, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // 2. Panel Central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Título del módulo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(Color.WHITE);
        
        JLabel lblTituloPrincipal = new JLabel("Listado de Clientes Activos");
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTituloPrincipal.setForeground(Color.BLACK); // Texto negro
        lblTituloPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Consulta y gestión de clientes y sus planes contratados.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.BLACK); // Texto negro
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelTitulo.add(lblTituloPrincipal);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelTitulo.add(lblSubtitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 20)));

        centerPanel.add(panelTitulo, BorderLayout.NORTH);

        // Tabla de Clientes
        String[] columnas = {"ID Cliente", "Nombre Completo", "Plan Contratado"};
        Object[][] datos = {
            {"C-0010", "Juan Pérez", "Tripleplay Básico"},
            {"C-0011", "María Gómez", "Internet Fibra 300Mbps"},
            {"C-0012", "Pedro Sánchez", "Pospago Ilimitado 5G"},
            {"C-0013", "Laura Guzmán", "Tripleplay Premium"},
            {"C-0014", "Roberto Díaz", "Pospago Familiar 3 Líneas"}
        };

        DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);
        JTable tablaClientes = new JTable(modeloTabla);
        tablaClientes.setRowHeight(30);
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaClientes.setForeground(Color.BLACK); // Texto negro en celdas
        tablaClientes.setGridColor(Color.LIGHT_GRAY);
        tablaClientes.setSelectionBackground(new Color(220, 220, 220));
        tablaClientes.setSelectionForeground(Color.BLACK); // Texto negro al seleccionar

        // Estilo del encabezado
        JTableHeader header = tablaClientes.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK); // Texto negro en encabezado

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel CRUD (Botones abajo)
        JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        crudPanel.setBackground(Color.WHITE);

        JButton btnCrear = crearBotonCRUD("Registrar Cliente");
        JButton btnLeer = crearBotonCRUD("Ver Facturación");
        JButton btnActualizar = crearBotonCRUD("Modificar Plan");
        JButton btnEliminar = crearBotonCRUD("Dar de Baja");

        crudPanel.add(btnCrear);
        crudPanel.add(btnLeer);
        crudPanel.add(btnActualizar);
        crudPanel.add(btnEliminar);

        centerPanel.add(crudPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // 3. Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        JLabel lblFooter = new JLabel("Altice \u00A9 2024 | Módulo de Clientes");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.BLACK); // Texto negro
        footerPanel.add(lblFooter);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton crearBotonCRUD(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(230, 230, 230)); // Fondo gris claro
        boton.setForeground(Color.BLACK); // TEXTO ESTRICTAMENTE NEGRO
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(160, 40));
        boton.setBorder(new LineBorder(Color.GRAY, 1));
        return boton;
    }
    
}