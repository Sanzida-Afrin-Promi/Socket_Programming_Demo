package MessageShareBetweenClients;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter writer;
    private String clientName;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Prompt the client for their name
            sendMessage("Server: Please enter your name:");
            clientName = reader.readLine();

            // Register the client with the server
            Server.registerClient(clientName, this);

            // Continue handling messages
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("Received from client " + clientName + ": " + inputLine);

                // Check if the message is a private message to the server
                if (inputLine.startsWith("@server")) {
                    String[] parts = inputLine.split(" ", 3);
                    if (parts.length >= 3) {
                        String recipientName = parts[1];
                        String message = parts[2];
                        Server.sendPrivateMessage(clientName, recipientName, message);
                    }
                } else {
                    // Broadcast the received message to all clients except the sender
                    Server.broadcastMessage(clientName + ": " + inputLine, this);
                }
            }

            // Unregister the client when done
            Server.unregisterClient(clientName);
            clientSocket.close();
            System.out.println("Client disconnected: " + clientName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send a message to this client
    public void sendMessage(String message) {
        writer.println(message);
    }
}
