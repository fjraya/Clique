# Clique

Para ejecutar necesitamos crear un archivo de texto donde haya un usuario por linea, ejemplo:

user1
user2
user3


Teniendo maven instalado, para compilar el proyecto una vez clonado de git, tenemos que irnos al directorio raz y ejecutar:

mvn assembly:assembly

Esto creará un .jar autocontenido en el directorio "target". Lo podremos ejecutar con:

java -jar clique-1.0-SNAPSHOT-jar-with-dependencies.jar <filename>


Para ejecutar los tests, ir al directorio raíz del proyecto y ejecutar:

mvn test
