Practica Final ASR: Javier Caminos y Rodrigo Lopez de Toledo
Health Score Calculator

Funcionamiento: La aplicación calcula la valoración de tu salud haciendo uso del microservicio "Risk Engine". Hace uso de los parametros tanto de carácteristicas sorporales como puede ser la altura o el peso, como de la edad y habitos saludables o no, como pueden ser el descanso y el hecho de que el cliente sea fumador o no. Además de Risk Engine, la aplicacion usa otros tres microservicios. El primero de ellos es la base de datos de Cloudant. Esta se rellena con el nombre del cliente que se introduce en el primer campo del formulario. Este nombre insertado aparece para cada cliente cuando se muestran los resultados. Si se quiere ver el historial de clientes aue han usado la herramienta, se le pulsa al enlace "listar" pzra poder verlos.
El segundo microservicio que usa es el de traducción, este traduce la respuesta de Risk Engine al español para mostrarla como se muestra en la aplicación.
El tercer microservicio es el de Risk Engine ya explicado, y, por último, se usa el microservicio TextToSpeech, ayudandose de una clase Speak que se hace uso de el cuando se pulsa el link "Instrucciones de Uso".

Uso:
	1. Si quiere ver la lista de clientes que ha usado el microservicio, pulse el link "Listar". Para volver a la página inicial, hacer click en el enlace atras.
	2. Si quiere calcular su valoración de salud, rellene los campos en las unidades que se indican y pulse el botón "Check My Health". (Transferencia de Actividades Físicas debe ser un numero enterp mayor que 0). Para que la app funcione, todos los campos tienen que estar rellenados (de forma correcta como indica cada campo) antes de pulsar el botón. Los resultados se muestran debajo del formulario en la misma página.
	3. Si quiere escuchar las intrucciones pulse el link "Instrucciones de uso". Para volver a la página principal, usar el botón de atrás del navegador
	
