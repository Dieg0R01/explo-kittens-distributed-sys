</head><body><article id="15138a47-4a51-8044-9fe5-c51dc92ed600" class="page sans"><header><h1 class="page-title">Manejo de hilos</h1><p class="page-description"></p></header><div class="page-body"><table id="15138a47-4a51-8096-be06-f2a05f4d4692" class="simple-table"><tbody><tr id="15138a47-4a51-807b-984b-d0aef79638f2"><td id="\BXy" class="">Aspecto</td><td id="KL;t" class=""><code>Executors.newCachedThreadPool</code></td><td id="@WG~" class="" style="width:261px"><code>Executors.newSingleThreadExecutor</code></td></tr><tr id="15138a47-4a51-807d-86dd-eb8d5c99ebf5"><td id="\BXy" class=""><strong>Ejecución paralela</strong></td><td id="KL;t" class="">Mejor paralelismo entre todas las tareas.</td><td id="@WG~" class="" style="width:261px">Aislada por cliente, menos paralelismo.</td></tr><tr id="15138a47-4a51-8043-b45e-c8309be9793a"><td id="\BXy" class=""><strong>Secuencialidad</strong></td><td id="KL;t" class="">No garantizada entre tareas del cliente.</td><td id="@WG~" class="" style="width:261px">Garantizada para tareas del cliente.</td></tr><tr id="15138a47-4a51-80d0-a5ae-d81f9f0ca875"><td id="\BXy" class=""><strong>Uso de recursos</strong></td><td id="KL;t" class="">Más eficiente (pool compartido).</td><td id="@WG~" class="" style="width:261px">Más costoso (un pool por cliente).</td></tr><tr id="15138a47-4a51-8090-9c4b-f52d865abf50"><td id="\BXy" class=""><strong>Escalabilidad</strong></td><td id="KL;t" class="">Mejor para muchos clientes/salas.</td><td id="@WG~" class="" style="width:261px">Escalabilidad limitada por memoria.</td></tr><tr id="15138a47-4a51-801f-bba6-e5e517b83c68"><td id="\BXy" class=""><strong>Aislamiento</strong></td><td id="KL;t" class="">Baja, comparte el pool global.</td><td id="@WG~" class="" style="width:261px">Alta, un pool privado.</td></tr></tbody></table><p id="15138a47-4a51-8050-9374-d2d4ea5043a0" class=""><br/>Combinar <code><strong>CachedThreadPool</strong></code> y <code><strong>SingleThreadExecutor</strong></code> permite escalabilidad global y control local en el servidor, maximizando eficiencia y orden. Mientras que el primero maneja múltiples clientes concurrentemente, el segundo garantiza secuencialidad en tareas críticas por cliente o sala. Esta integración asegura un manejo óptimo de recursos y un flujo robusto en aplicaciones de alto rendimiento. Es una solución flexible para escalar sin comprometer la precisión.<br/><br/><br/></p><script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js" integrity="sha512-7Z9J3l1+EYfeaPKcGXu3MS/7T+w19WtKQY/n+xzmw4hZhJ9tyYmcUS+4QqAlzhicE5LAfMQSF3iFTK9bQdTxXg==" crossorigin="anonymous" referrerPolicy="no-referrer"></script><pre id="15138a47-4a51-806e-b4d8-fbca512c2cb2" class="code"><code class="language-Java">public class ServerNet {
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
}</code></pre><script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js" integrity="sha512-7Z9J3l1+EYfeaPKcGXu3MS/7T+w19WtKQY/n+xzmw4hZhJ9tyYmcUS+4QqAlzhicE5LAfMQSF3iFTK9bQdTxXg==" crossorigin="anonymous" referrerPolicy="no-referrer"></script><pre id="15138a47-4a51-80df-8263-ea5e450e6a53" class="code"><code class="language-Java">public class ClientHandler implements Runnable {
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
}</code></pre></div></article><span class="sans" style="font-size:14px;padding-top:2em"></span></body></html>
