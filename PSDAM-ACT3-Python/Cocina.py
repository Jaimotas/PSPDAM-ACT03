import os
import time
import random
import threading
from Pedido import Pedido
from Cocinero import Cocinero

def main():
    pedidos = []
    lock = threading.Lock() # Lock para sincronización de acceso a pedidos y log
    base_path = os.path.dirname(os.path.abspath(__file__)) 
    log_file = os.path.join(base_path, "log_pedidos.txt") 
    rand = random.Random()

    # Crear archivo log o avisar si ya existe
    if not os.path.exists(log_file):
        with open(log_file, "w", encoding="utf-8") as f: 
            pass
        print("Archivo log_pedidos.txt creado correctamente.")
    else:
        print("Archivo log_pedidos.txt ya existe. Se añadirán nuevos registros.")

    # Lista grande de platos
    todos_platos = [
        "Paella", "Hamburguesa", "Pizza", "Ensalada", "Sushi", "Tacos", "Spaghetti", "Lasaña",
        "Bocadillo", "Empanada", "Gazpacho", "Croquetas", "Pollo Asado", "Patatas Bravas",
        "Pescado Frito", "Ceviche", "Ramen", "Curry", "Falafel", "Quiche", "Sopa de Verduras",
        "Chili con Carne", "Tortilla Española", "Arroz con Pollo", "Burrito",
        "Dumplings", "Kebab", "Paella Negra", "Canelones"
    ]

    # Crear 6 pedidos
    id_pedido = 1
    for _ in range(6):
        time.sleep(rand.uniform(0.5, 1.0))  # tiempo de llegada
        nombre_pedido = random.choice(todos_platos)
        pedidos.append(Pedido(id_pedido, nombre_pedido))
        id_pedido += 1

    # Crear cocineros
    c1 = Cocinero("Cocinero 1", pedidos, lock, log_file)
    c2 = Cocinero("Cocinero 2", pedidos, lock, log_file)
    c3 = Cocinero("Cocinero 3", pedidos, lock, log_file)

    # Iniciar hilos
    c1.start()
    c2.start()
    c3.start()

    # Esperar a que terminen todos
    c1.join()
    c2.join()
    c3.join()

    print("Todos los pedidos han sido procesados.")

if __name__ == "__main__":
    main()
