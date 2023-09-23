package MessageShareBetweenClients;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client3{
    private static final String SERVER_IP = "127.0.0.1"; // Change this to the server's IP address
    private static final int SERVER_PORT = 12345; // Change this to the server's port number

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to the server");

            final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Read user's name

            String clientName = new Scanner(System.in).nextLine();

            // Start a thread to listen for messages from the server
            Thread messageListener = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String serverMessage;
                        while ((serverMessage = reader.readLine()) != null) {
                            System.out.println(serverMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            messageListener.start();

            // Send user's messages to the server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String message = scanner.nextLine();
                writer.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
