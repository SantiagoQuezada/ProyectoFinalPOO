package Logico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ChatServidor {
    
    
    private static final int PUERTO = 9090;
    
   
    private static HashSet<PrintWriter> escritores = new HashSet<>();

    public static void iniciarServidor() {
        new Thread(() -> {
            try (ServerSocket listener = new ServerSocket(PUERTO)) {
                System.out.println("Servidor de Chat iniciado en el puerto " + PUERTO);
                while (true) {
                    new ManejadorCliente(listener.accept()).start();
                }
            } catch (IOException e) {
               
                System.out.println("Servidor de Chat ya activo en esta máquina.");
            }
        }).start();
    }

    
    private static class ManejadorCliente extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                
                synchronized (escritores) {
                    escritores.add(out);
                }

                String mensaje;
               
                while ((mensaje = in.readLine()) != null) {
                  
                    synchronized (escritores) {
                        for (PrintWriter writer : escritores) {
                            writer.println(mensaje);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Un empleado se ha desconectado del chat.");
            } finally {
                
                if (out != null) {
                    synchronized (escritores) {
                        escritores.remove(out);
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}