# Conexion a una base de datos distribuida

## Crear una archivo docker-compose.yml
```
version: '3.8'

services:
  cockroach1:
    image: cockroachdb/cockroach:latest
    container_name: crdb-node1
    hostname: crdb-node1  # Importante para el DNS interno
    ports:
      - "26257:26257"  # SQL (externo:26257 → interno:26257)
      - "8080:8080"    # UI
    volumes:
      - cockroach1:/cockroach/cockroach-data
    command: start-single-node --insecure --advertise-addr=crdb-node1

  cockroach2:
    image: cockroachdb/cockroach:latest
    container_name: crdb-node2
    hostname: crdb-node2
    ports:
      - "26258:26257"  # SQL (externo:26258 → interno:26257)
      - "8081:8080"    # UI
    volumes:
      - cockroach2:/cockroach/cockroach-data
    command: start --insecure --store=node2 --listen-addr=0.0.0.0:26257 --http-addr=0.0.0.0:8080 --join=crdb-node1:26257 --advertise-addr=crdb-node2

  cockroach3:
    image: cockroachdb/cockroach:latest
    container_name: crdb-node3
    hostname: crdb-node3
    ports:
      - "26259:26257"  # SQL (externo:26259 → interno:26257)
      - "8082:8080"    # UI
    volumes:
      - cockroach3:/cockroach/cockroach-data
    command: start --insecure --store=node3 --listen-addr=0.0.0.0:26257 --http-addr=0.0.0.0:8080 --join=crdb-node1:26257 --advertise-addr=crdb-node3

volumes:
  cockroach1:
  cockroach2:
  cockroach3:  # Corregí el typo (antes decía "cockroach3")
```

## Levantar el servicio
```
docker-compose up -d
```
## Crear las bases de datos en los nodos

### Primer Nodo
```
docker exec -it crdb-node1 ./cockroach sql --insecure
```

#### ejecutar el siguiente comando
```sql
CREATE DATABASE publicaciones_db;
CREATE USER admin WITH PASSWORD 'admin';
GRANT ALL ON DATABASE publicaciones_db TO admin;

CREATE DATABASE sincronizacion_db;
GRANT ALL ON DATABASE sincronizacion_db TO admin;
```
### Segundo Nodo
```
docker exec -it crdb-node2 ./cockroach sql --insecure
```
#### ejecutar el siguiente comando
```sql
CREATE DATABASE notificaciones_db;
CREATE USER admin WITH PASSWORD 'admin';
GRANT ALL ON DATABASE notificaciones_db TO admin;
```

### Tercer nodo
```
docker exec -it crdb-node3 ./cockroach sql --insecure
```
#### ejecutar el siguiente comando
```sql
CREATE DATABASE catalogo_db;
CREATE USER admin WITH PASSWORD 'admin';
GRANT ALL ON DATABASE catalogo_db TO admin;
```

## Configura cada microservicio para conectarse a su nodo correspondiente
### Publicaciones
```yaml
  datasource:
    #url: jdbc:postgresql://localhost:5432/db-publications
    #username: postgres
    #password: qwerty123
    url: jdbc:postgresql://localhost:26257/publicaciones_db?user=root&password=
    username: root
    password:
```
### Notificaciones
```yaml
  datasource:
    #url: jdbc:postgresql://localhost:5432/bd-notificaciones
    #username: postgres
    #password: qwerty123
    url: jdbc:postgresql://localhost:26257/notificaciones_db?user=root&password=
    username: root
    password:
```
