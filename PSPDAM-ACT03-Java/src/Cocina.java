import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Cocina {
    public static void main(String[] args) {
        List<Pedido> pedidos = new LinkedList<>();
        Object lock = new Object();
        File logFile = new File("log_pedidos.txt");
        Random rand = new Random();

        // Crear archivo log o avisar si ya existe
        try {
            if (logFile.createNewFile()) {
                System.out.println("Archivo log_pedidos.txt creado correctamente.");
            } else {
                System.out.println("Archivo log_pedidos.txt ya existe. Se añadirán nuevos registros.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lista grande de platos
        String[] todosPlatos = {
            "Paella", "Hamburguesa", "Pizza", "Ensalada", "Sushi", "Tacos", "Spaghetti", "Lasaña", 
            "Bocadillo de chopped", "Empanada", "Gazpacho", "Croquetas", "Pollo Asado", "Patatas Bravas", 
            "Pescado Frito", "Ceviche", "Ramen", "Curry", "Falafel", "Quiche", "Sopa de Verduras", 
            "Chili con Carne", "Tortilla Española", "Bruschetta", "Arroz con Pollo", "Burrito",
            "Dumplings", "Kebab", "Paella Negra", "Canelones"
        };

        // Crear 6 pedidos
        int id = 1;
        for (int i = 0; i < 6; i++) {
            StringBuilder nombrePedido = new StringBuilder();
            // Simular tiempo de llegada del pedido
            try {
                Thread.sleep(rand.nextInt(500) + 500); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           //Escoge aleatoriamente el siguiente plato de toda la lista
           int index = rand.nextInt(todosPlatos.length);
           nombrePedido.append(todosPlatos[index]);
           pedidos.add(new Pedido(id, nombrePedido.toString()));
           id++;
        }

        // Crear cocineros
        Cocinero c1 = new Cocinero("Cocinero 1", pedidos, lock, logFile);
        Cocinero c2 = new Cocinero("Cocinero 2", pedidos, lock, logFile);
        Cocinero c3 = new Cocinero("Cocinero 3", pedidos, lock, logFile);

        // Iniciar hilos
        c1.start();
        c2.start();
        c3.start();

        // Esperar a que terminen todos los cocineros
        try {
            c1.join();
            c2.join();
            c3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Todos los pedidos han sido procesados.");
    }
}
