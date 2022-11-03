package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013; 
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		// Create a socket to make a connection to the client from the server
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

		// Create a client input reader/director and run it on a thread
		while (true) {
			Socket socket = serverSocket.accept();
			ClientConnection connection = new ClientConnection(socket);
			Thread clientThread = new Thread(connection);
			clientThread.start();
		}
	}
	
	// Set up the Client Connection class, which is essentially a client steam reader/director
	public static class ClientConnection implements Runnable {
		private Socket clientCommunicationSocket;

		// Set the stream from the client taken as an argument to a variable
		public ClientConnection(Socket socket) {
			clientCommunicationSocket = socket;
		}

		@Override
		public void run() {
			try {
				// Grab the input stream connected to the output of the client communication socket
				InputStream input = clientCommunicationSocket.getInputStream();
      				// Grab the output stream connected to the input of the client communication socket
      				OutputStream output = clientCommunicationSocket.getOutputStream();

      				// Create variable to track the bytes being redirected
      				int nextByteFromSystem = 0;

      				// Reads the stream from the System and puts the stream into the output
      				while ((nextByteFromSystem = input.read()) != -1) {
        			output.write(nextByteFromSystem);
				output.flush();
      				}

				// Shut down the client communications output socket
      				clientCommunicationSocket.shutdownOutput();
			} catch (IOException exception) {
				System.out.println("Unexpected exception: "  + exception);
			}
		}
	}
}
