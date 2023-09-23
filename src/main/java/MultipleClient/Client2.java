package MultipleClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2 {
    private static final String SERVER_IP = "127.0.0.1"; // Change this to the server's IP address
    private static final int SERVER_PORT = 12345; // Change this to the server's port number

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to the server");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Read user input and send it to the server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Client2: Enter a message (or type 'exit' to quit): ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                writer.println("Client2: " + message);

                // Receive and display the server's response
                String serverResponse = reader.readLine();
                System.out.println(serverResponse);
            }

            // Close the socket when done
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
