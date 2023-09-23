package MultipleClient;
import java.io.*;
        import java.net.*;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12375;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create a new client handler thread and submit it to the thread pool
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

