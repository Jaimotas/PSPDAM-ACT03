
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Cocinero extends Thread {
    private String nombre;
    private List<Pedido> pedidos;
    private final Object lock;
    private File logFile;
    private Random rand = new Random();

    public Cocinero(String nombre, List<Pedido> pedidos, Object lock, File logFile) {
        this.nombre = nombre;
        this.pedidos = pedidos;
        this.lock = lock;
        this.logFile = logFile;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido = null;

            // Bloque sincronizado: coger un pedido de la lista
            synchronized (lock) {
                if (!pedidos.isEmpty()) {
                    pedido = pedidos.remove(0);

                    // Pedido recogido
                    String msgRecogido = nombre + " ha recogido el pedido " + pedido.getId() + ": " + pedido.getNombrePlato();
                    System.out.println(msgRecogido);
                    escribirLog(msgRecogido);
                } else {
                    break; // No quedan pedidos
                }
            }

            try {
                // Simular tiempo de preparación inicial
                Thread.sleep(rand.nextInt(500) + 500); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Comenzar preparación
            String msgInicio = nombre + " empieza a preparar el pedido " + pedido.getId() + ": " + pedido.getNombrePlato();
            System.out.println(msgInicio);
            escribirLog(msgInicio);

            // Simular preparación
            try {
                Thread.sleep(rand.nextInt(2000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Finalizar preparación
            String msgFin = nombre + " ha terminado el pedido " + pedido.getId() + ": " + pedido.getNombrePlato();
            System.out.println(msgFin);
            escribirLog(msgFin);

            try {
                // Simular tiempo de limpieza/entrega
                Thread.sleep(rand.nextInt(300) + 200); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Método auxiliar para escribir en log
    private void escribirLog(String mensaje) {
        synchronized (lock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(mensaje);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
