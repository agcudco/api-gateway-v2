from locust import HttpUser, task, between
import random

class MyUser(HttpUser):
    host = "http://localhost:8000"

    wait_time = between(0.5, 1.5)

    @task
    def crear_autor(self):
        # Generar un payload aleatorio para el autor
        payload = {
            "nombre": f"Autor {random.randint(1, 1000)}",
            "apellido": f"Apellido {random.randint(1, 1000)}",
            "email": f"autor{random.randint(1, 999)}_apellido{random.randint(1, 999)}@ejemplo.com",
            "orcid": f"000-{random.randint(1, 1000)}-{random.randint(1, 1000)}",
            "nacionalidad": "Ecuatoriana",
            "telefono": "+593987654321",
            "institucion": "Universidad Técnica Nacional"
        }
        
        # Realizar la solicitud POST para crear un autor
        self.client.post("/autor", json=payload)