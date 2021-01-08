Examen de ICC
====================
Antonio Daniel Martinez Gutierrez

Proyecto Chat
------------------------------

### Fecha de entrega: 14 de enero, 2021

Escribe un programa de chat de texto en la consola; el servidor debe escuchar en el puerto TCP 6666, y cuando un cliente se conecte, debe leer una línea de él (donde vendrá el identificador o nickname del usuario en el cliente), y mandar un mensaje a todos los otros clientes avisándoles que se ha conectado un nuevo cliente.
A partir de ese momento, el servidor espera por una línea con un mensaje del cliente, y cuando lo recibe, se lo manda a todos los otros clientes, prefijado con el identificador del cliente que mandó el mensaje.
El servidor debe usar la clase java.net.ServerSocket para escuchar por conexiones, y usar un hilo de ejecución (thread) para manejar la conexión de cada cliente.
No te preocupes en cómo terminar la ejecución del servidor o los clientes; abortar el programa con Control-C debe bastar. Sin embargo, todas las excepciones deben manejarse correctamente, y el servidor debe seguir corriendo correctamente cuando un cliente se desconecte de forma imprevista
