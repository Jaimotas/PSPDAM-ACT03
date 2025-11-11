import threading
import time
import random

class Cocinero(threading.Thread):
    def __init__(self, nombre, pedidos, lock, log_file): 
        super().__init__()
        self.nombre = nombre
        self.pedidos = pedidos
        self.lock = lock
        self.log_file = log_file
        self.rand = random.Random()
    
    def escribir_log(self, mensaje):
        # Escribir en el archivo log de manera segura
        with open(self.log_file, "a", encoding="utf-8") as f:
            f.write(mensaje + "\n")
            f.flush()

    def run(self):
        while True:
            # Obtener pedido
            with self.lock:
                if not self.pedidos:
                    return  # no hay más pedidos
                pedido = self.pedidos.pop(0)
                msg = f"{self.nombre} ha recogido el pedido {pedido.id_pedido}: {pedido.nombre_plato}"
                print(msg)
                self.escribir_log(msg)

            # Simular tiempo de preparación (fuera del lock)
            time.sleep(self.rand.uniform(0.5, 1.5))
            
            msg = f"{self.nombre} empieza a preparar el pedido {pedido.id_pedido}: {pedido.nombre_plato}"
            with self.lock:
                print(msg)
                self.escribir_log(msg)

            # Simular cocinado
            time.sleep(self.rand.uniform(1.0, 3.0))

            msg = f"{self.nombre} ha terminado el pedido {pedido.id_pedido}: {pedido.nombre_plato}"
            with self.lock:
                print(msg)
                self.escribir_log(msg)

            # Pausa antes del siguiente pedido
            time.sleep(self.rand.uniform(0.3, 0.7))
