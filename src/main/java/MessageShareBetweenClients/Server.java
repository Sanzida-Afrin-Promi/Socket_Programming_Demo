package MessageShareBetweenClients;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Server {
    private static final int PORT = 12346;
    private static List<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private static ConcurrentMap<String, ClientHandler> clientMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create a new client handler thread and submit it to the thread pool
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                threadPool.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast a message to all connected clients
    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            // Send the message to all clients except the sender
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    // Send a private message from one client to another through the server
    public static void sendPrivateMessage(String senderName, String recipientName, String message) {
        ClientHandler recipient = clientMap.get(recipientName);
        if (recipient != null) {
            recipient.sendMessage(senderName + " (private): " + message);
        }
    }

    // Register a client with a unique name
    public static void registerClient(String clientName, ClientHandler clientHandler) {
        clientMap.put(clientName, clientHandler);
    }

    // Unregister a client
    public static void unregisterClient(String clientName) {
        clientMap.remove(clientName);
    }
}
