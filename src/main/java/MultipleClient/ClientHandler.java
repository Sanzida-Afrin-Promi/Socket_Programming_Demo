package MultipleClient;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InetAddress clientAddress = clientSocket.getInetAddress();
            int clientPort = clientSocket.getPort();
            System.out.println("Handling client: " + clientAddress + ":" + clientPort);

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("Received from " + clientAddress + ":" + clientPort + ": " + inputLine);

                // Process the client's request (you can add your logic here)
                String r;
                Scanner scanner = new Scanner(System.in);
                r= scanner.nextLine();
                String response = "Server Replied: "+r;

                // Send the response back to the client
                writer.println(response);
            }

            // Close the client socket when done
            clientSocket.close();
            System.out.println("Client disconnected: " + clientAddress + ":" + clientPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
