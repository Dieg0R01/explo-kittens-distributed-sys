# **Elementos concurrentes del proyecto y funcionamiento**
### **1. Uso de `ExecutorService` para manejo de hilos**

El código utiliza pools de hilos para manejar múltiples conexiones y tareas concurrentes. Ejemplo:

```java
// Pool global para gestionar múltiples clientes
ExecutorService pool = Executors.newCachedThreadPool(); // Maneja dinámicamente el número de hilos

// Pool local para tareas secuenciales
ExecutorService gameExecutor = Executors.newSingleThreadExecutor(); // Ejecuta tareas de forma secuencial

// Ejecución de tareas
pool.execute(new ClientHandler(client)); // Manejo de un cliente en un hilo separado
gameExecutor.submit(() -> processMessage(message, out)); // Procesamiento de mensajes dentro de una sala
```

### **2. Sincronización con `RoomManager`**

El `RoomManager` centraliza la gestión de salas, permitiendo operaciones concurrentes controladas por hilos:

```java
// Crear una sala
String roomId = roomManager.createRoom(maxPlayers, playerHandled);

// Unirse a una sala
boolean isJoined = roomManager.joinRoom(roomId, playerHandled, out);

// Obtener información de una sala
Room room = roomManager.getRoom(currentRoomId);
```

### **3. Ejecución concurrente de sesiones de juego**

Cuando un juego comienza, una nueva sesión se ejecuta en un hilo independiente:

```java
// Iniciar sesión de juego en una sala
GameSession gameSession = new GameSession(room);
gameExecutor.execute(gameSession); // Ejecutar la sesión de juego en un hilo dedicado
```

### **4. Manejo de flujo de entrada/salida de clientes**

Cada cliente tiene su propio flujo de entrada/salida gestionado de manera concurrente:

```java
// Configurar flujos de comunicación
ObjectInputStream in = new ObjectInputStream(client.getInputStream());
PrintStream out = new PrintStream(client.getOutputStream(), true);

// Leer mensajes en un bucle
String message;
while ((message = (String) in.readObject()) != null) {
		// Procesar mensajes en tareas independientes
    gameExecutor.submit(() -> processMessage(message, out)); 
}

```

### **5. Limpieza de recursos**

Se asegura que los recursos de red y los hilos se cierren correctamente:

```java
// Cierre de conexión del cliente
try {
    client.close();
    gameExecutor.shutdown(); // Detener el pool de hilos
} catch (IOException e) {
    e.printStackTrace();
}

// Cierre del pool global en el servidor
pool.shutdown();
```

### **6. Comunicación cliente-servidor**

Se utilizan protocolos de mensajes simples para coordinar acciones entre el cliente y el servidor (`CREATE`, `JOIN`, `EXIT`, `START`), lo que permite operaciones concurrentes sin conflictos.

### Conclusión

Estos elementos concurrentes aseguran que múltiples clientes puedan interactuar con el servidor de forma eficiente y que las tareas específicas, como el inicio de un juego, se ejecuten de manera ordenada sin interferir con otras operaciones.

</head><body><article id="15138a47-4a51-8044-9fe5-c51dc92ed600" class="page sans"><header><h1 class="page-title">Manejo de hilos</h1><p class="page-description"></p></header><div class="page-body"><table id="15138a47-4a51-8096-be06-f2a05f4d4692" class="simple-table"><tbody><tr id="15138a47-4a51-807b-984b-d0aef79638f2"><td id="\BXy" class="">Aspecto</td><td id="KL;t" class=""><code>Executors.newCachedThreadPool</code></td><td id="@WG~" class="" style="width:261px"><code>Executors.newSingleThreadExecutor</code></td></tr><tr id="15138a47-4a51-807d-86dd-eb8d5c99ebf5"><td id="\BXy" class=""><strong>Ejecución paralela</strong></td><td id="KL;t" class="">Mejor paralelismo entre todas las tareas.</td><td id="@WG~" class="" style="width:261px">Aislada por cliente, menos paralelismo.</td></tr><tr id="15138a47-4a51-8043-b45e-c8309be9793a"><td id="\BXy" class=""><strong>Secuencialidad</strong></td><td id="KL;t" class="">No garantizada entre tareas del cliente.</td><td id="@WG~" class="" style="width:261px">Garantizada para tareas del cliente.</td></tr><tr id="15138a47-4a51-80d0-a5ae-d81f9f0ca875"><td id="\BXy" class=""><strong>Uso de recursos</strong></td><td id="KL;t" class="">Más eficiente (pool compartido).</td><td id="@WG~" class="" style="width:261px">Más costoso (un pool por cliente).</td></tr><tr id="15138a47-4a51-8090-9c4b-f52d865abf50"><td id="\BXy" class=""><strong>Escalabilidad</strong></td><td id="KL;t" class="">Mejor para muchos clientes/salas.</td><td id="@WG~" class="" style="width:261px">Escalabilidad limitada por memoria.</td></tr><tr id="15138a47-4a51-801f-bba6-e5e517b83c68"><td id="\BXy" class=""><strong>Aislamiento</strong></td><td id="KL;t" class="">Baja, comparte el pool global.</td><td id="@WG~" class="" style="width:261px">Alta, un pool privado.</td></tr></tbody></table></body></html>

Combinar <code><strong>CachedThreadPool</strong></code> y <code><strong>SingleThreadExecutor</strong></code> permite escalabilidad global y control local en el servidor, maximizando eficiencia y orden. Mientras que el primero maneja múltiples clientes concurrentemente, el segundo garantiza secuencialidad en tareas críticas por cliente o sala. Esta integración asegura un manejo óptimo de recursos y un flujo robusto en aplicaciones de alto rendimiento. Es una solución flexible para escalar sin comprometer la precisión.
    
```java
public class ServerNet {
private static final int PORT = 55555;
private final ExecutorService pool; // Pool global para manejar clientes

    public ServerNet() {
        pool = Executors.newCachedThreadPool(); // Manejo dinámico de threads
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println(&quot;Servidor iniciado en el puerto: &quot; + PORT);

            while (true) {
                Socket client = serverSocket.accept(); // Acepta conexiones
                System.out.println(&quot;Nuevo cliente conectado: &quot; + client.getInetAddress());
                pool.execute(new ClientHandler(client)); // Maneja cada cliente en el pool global
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown(); // Libera recursos al cerrar el servidor
        }
    }

    public static void main(String[] args) {
        ServerNet server = new ServerNet();
        server.start();
    }
}
```
```java
public class ClientHandler implements Runnable {
    private final Socket client;
    private final ExecutorService clientExecutor; // Pool dedicado para este cliente

    public ClientHandler(Socket client) {
        this.client = client;
        this.clientExecutor = Executors.newSingleThreadExecutor(); // Garantiza secuencialidad
    }

    @Override
    public void run() {
        System.out.println(&quot;Manejando cliente: &quot; + client.getInetAddress());
        try (Scanner in = new Scanner(client.getInputStream());
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

            out.println(&quot;Bienvenido al servidor! Escribe algo:&quot;);
            while (in.hasNextLine()) {
                String message = in.nextLine();
                // Procesar cada mensaje en el executor dedicado al cliente
                clientExecutor.submit(() -&gt; processMessage(message, out));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientExecutor.shutdown(); // Libera recursos del cliente
        }
    }

    private void processMessage(String message, PrintWriter out) {
        System.out.println(&quot;Procesando mensaje del cliente: &quot; + message);
        out.println(&quot;Mensaje recibido: &quot; + message.toUpperCase());
    }
}
```
</head><body><article id="15138a47-4a51-8044-9fe5-c51dc92ed600" class="page sans"><header><h1 class="page-title">Explicación de la no finalización del proyecto</h1><p class="page-description"></p></header>

El proyecto de Exploding Kittens a la par que divertido, nos pareció de primeras un proyecto asequible en un mes. Pero para empezar, iniciamos el proyecto de diseño, con su diagrama de casos de uso, con los diagramas de actividades, queriendo tener claro cuáles iban a ser los pasos a dar una vez implementemos el juego.
Sin darnos cuenta de que, tardaríamos demasiado tiempo implementando el modelo de dominio. Tanto tiempo, que para cuando nos pusimos a implementar la conexión enter cliente y servidor, en específico que varios clientes, en forma de jugador, se unieran en una room para poder comenzar una partida, nos dieron las mil intentando realizar las conexiones cliente - servidor. Nos hemos quedado a las puertas de poder realizar la conexión jugador - servidor cuando una partida ya ha sido iniciado.
Sin pasar por alto que queríamos realizar la GUI, pero no hemos tenido tiempo suficiente. 
Hemos aprendido mucho realizando este proyecto, pero hemos tardado mucho más tiempo de lo esperado al realizar implementaciones absurdas como tipos de datos que para esta asignatura no son significantes, pero eran obviamente necesarias.
