# Clique

Para ejecutar necesitamos crear un archivo de texto donde haya un usuario por linea, ejemplo:

user1

user2

user3


Teniendo maven instalado, para compilar el proyecto una vez clonado de git, tenemos que irnos al directorio raíz y ejecutar:


mvn assembly:assembly



Esto creará un .jar autocontenido en el directorio "target". Lo podremos ejecutar con:

java -jar clique-1.0-SNAPSHOT-jar-with-dependencies.jar <filename>


Para ejecutar los tests, ir al directorio raíz del proyecto y ejecutar:

mvn test


# Notas

1. Hay muchos problemas por el Rate Limit de las apis de twitter y github. 

2. He utilizado el framework Akka que implementa el modelo de actores para solucionar el problema de forma paralela/concurrente

3.- Hay una muestra de test unitarios para que os hagais una idea de mi manejo. 
