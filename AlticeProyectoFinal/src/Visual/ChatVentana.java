package Visual;

import Logico.Empleado;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatVentana extends JFrame {

    private Empleado empleadoLocal;
    private JTextArea areaMensajes;
    private JTextField txtMensaje;
    private JButton btnEnviar;
    
   
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public ChatVentana(Empleado empleado) {
        this.empleadoLocal = empleado;
        
        setTitle("Chat de Empleados - Altice");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(10, 10, 10));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitulo = new JLabel("Chat Interno de Empleados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.WEST);
        
        JLabel lblUsuario = new JLabel((empleado != null ? empleado.getNombre() : "Usuario"));
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setForeground(new Color(200, 200, 200));
        headerPanel.add(lblUsuario, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);

       
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(new Font("Arial", Font.PLAIN, 15));
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);
        areaMensajes.setBackground(Color.WHITE);
        areaMensajes.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setBackground(new Color(245, 247, 250));
        bottomPanel.setBorder(new EmptyBorder(0, 15, 15, 15));

        txtMensaje = new JTextField();
        txtMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMensaje.setPreferredSize(new Dimension(300, 45));
        
        
        txtMensaje.addActionListener(e -> enviarMensaje());

        btnEnviar = new JButton("Enviar");
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEnviar.setBackground(new Color(0, 102, 204));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setPreferredSize(new Dimension(100, 45));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnviar.addActionListener(e -> enviarMensaje());

        bottomPanel.add(txtMensaje, BorderLayout.CENTER);
        bottomPanel.add(btnEnviar, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

       
        conectarAlServidor();
    }

    private void conectarAlServidor() {
        try {
            
            socket = new Socket("localhost", 9090);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            
            new Thread(() -> {
                try {
                    String mensajeServidor;
                    while ((mensajeServidor = in.readLine()) != null) {
                        areaMensajes.append(mensajeServidor + "\n");
                        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength()); // Auto-scroll
                    }
                } catch (Exception e) {
                    areaMensajes.append("\n[Conexión con el servidor perdida]\n");
                }
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se encontró un servidor de chat activo en la red.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            btnEnviar.setEnabled(false);
            txtMensaje.setEnabled(false);
        }
    }

    private void enviarMensaje() {
        String texto = txtMensaje.getText().trim();
        if (!texto.isEmpty() && out != null) {
            String nombre = empleadoLocal != null ? empleadoLocal.getNombre() : "Desconocido";
           
            out.println("[" + nombre + "]: " + texto);
            txtMensaje.setText("");
        }
    }
}