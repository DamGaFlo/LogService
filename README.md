##  AREP - MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER Y  AWS

## Introducción
#### Descripción 
En este practica podremos ver como implementar servicios y generar sus imágenes para desplegarlos en docker y adicional a esto publicarlos en un servicio de AWS.
#### Resumen
Para realizar este proyecto contamos con dos servicios implementados en spark estos son [logservice](https://github.com/DamGaFlo/LogService) que interactúa con nuestra base de datos publica un servicio para interactuar por esta desde http y [RoundRobin](https://github.com/DamGaFlo/RoundRobin) que es un balanceador de cargar que nos permite distribuir las peticiones a logservice, adicional a esto los dos cuentan con un cliente js para interactuar con estos.

#### Arquitectura
Contamos con:
- una base de datos en mongo
- tres instancias de el servicio que trata los datos en la base de datos
- un servicio que usa estos servicios y actúa como balanceador de carga
- un cliente en JS
Estos tres primeros elementos estarán desplegados en AWS ECS y cliente JS en el browser.

![localhost](/img/arquitectura.png)

## Intruciones de uso local

#### Pre requisitos

* [GIT](https://git-scm.com/book/es/v2/Inicio---Sobre-el-Control-de-Versiones-Instalación-de-Git)
* [JAVA 8](https://www.java.com/es/download/)
* [MAVEN](https://maven.apache.org)
* [DOCKER](https://www.docker.com/)
* [DOCKER COMPOSE](https://docs.docker.com/compose/install/)
* [MONGODB](www.mongodb.com)
**NOTA:** MongoDB puede trabajarlo con su imagen oficial publicada en dockerHub sin necesidad de descargar este.


#### Complicación

Genere copia local del repositorio puede ejecutar la siguiente línea en la consola de comandos.

    git clone https://github.com/DamGaFlo/RoundRobin
    git clone https://github.com/DamGaFlo/LogService

Primero es necesario usar el siguiente comando en estos proyectos:
```
mvn clean install
```
Para ejecutar el proyecto usamos Maven en el directorio raiz de cada proyecto proyecto  usando el siguiente comando.
```
mvn package
mvn compile
```

## Despliegue local

####  Creación de imágenes
Para esto usaremos los archivos Dockerfile creados y ubicados en el directorio raíz, usaremos los siguientes comando en la raíz respectiva.
```
docker build --tag roundrobin .
docker build --tag logservice .
docker pull mongo
```
#### Creacion de contenedores
```
docker run -d -p 4567:6000 --network lognet --name rr roundrobin
docker run -d -p 34000:6000 --network lognet --name logserv logservice
docker run -d -p 27017:27017 --network lognet --name db mongo
```


#### Configuracion base de datos
Entraremos a la consola del contenedor de mongo usando
```
docker exec -it db bash
```
Una vez en la consola ejecutamos mongo para poder crear una base de datos y una coleccion
```
mongo
```
Ya dentro de la linea de comando de mongo escribimos.

```
use data
db.createCollection("strings")
```
#### Prueba local

Aca podremos ver la respuesta del objeto JSON desde el local 

![localhost](/img/local.png)

## AWS y Docker

para esto debemos crear una maquina virtual en AWS en este caso usamos el servicio EC2 una vez creada debemos prepararla descargando Docker.
Para obtener las imágenes las bajamos desde dockerHub en este caso usaremos el comando

```
docker pull damgaflo/logservice
docker pull damgaflo/roundrobin
docker pull mongo
``` 

ya con las imagenes creamos la red con

```
docker network create lognet
```
y podremos ejecutar los contenedores como lo indicamos en la parte del [local](#despliegue-local)

#### Pruebas AWS

Inpunt del Cliente js desplegado
![input](/img/input.png)

respuesta donde vemos las cadenas ingresadas

![aws](/img/labAws.png)


## Autor
[Johan Damian Garrido Florez](https://github.com/DamGaFlo)
## Derechos de Autor
**©** _Johan Damian Garrido Florez, Escuela Colombiana de Ingeniería Julio Garavito._
## Licencia
Licencia bajo  GNU General Public License