package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException, InterruptedException {
		// Create a socket to make a connection to the server from the client
		Socket socket = new Socket("localhost", PORT_NUMBER);

		// Grab the input stream connected to the input of the server communication socket
		InputStream socketInputStream = socket.getInputStream();
		// Grab the output stream connected to the output of the server communication socket
		OutputStream socketOutputStream = socket.getOutputStream();
	
		// Create a new Input Reader for the input stream
		In InputReader = new In(socketInputStream);
		// Create a new Output Reader for the output stream
		Out OutputReader = new Out(socketOutputStream);

		// Create threads for the input and output so that they may run concurrently
		Thread input = new Thread(InputReader);
		Thread output = new Thread(OutputReader);

		// Start running on the threads concurrently
		input.start();
		output.start();

		// Wait for the output thread to die, and shutdown the output stream afterwards
		output.join();
		socket.shutdownOutput();

		// Wait for the input thread to die, and shutdown the input stream afterwards
		input.join();
		socket.shutdownInput();

		// Close the connection with the server
		socket.close();
	}

	// Set up the Out class, which is essentially an output stream reader/director
	private class Out implements Runnable {
                OutputStream output;
	
		// Set the Output Stream taken as an argument to a variable
                public Out(OutputStream outputStream) {
                        output = outputStream;
                }

                @Override
                public void run() {
                        try {
				// Create a variable to track the bytes being redirected
                                int nextByteFromSystem = 0;
                                // Reads the stream from the System and puts the stream into the output
                                while ((nextByteFromSystem = System.in.read()) != -1) {
                                        output.write(nextByteFromSystem);
                                        output.flush();
                                        System.out.flush();
                                }
                        } catch (IOException exception) {
                                System.out.println("Unexpected exception: "  + exception);
                        }
                }
        }
	
	// Set up the In class, which is essentailly an input stream reader/director
	private class In implements Runnable {
		InputStream input;

		// Set the Inputstream taken as an argument to a variable
		public In(InputStream inputStream) {
			input = inputStream;
		}

		@Override
		public void run() {
			try {
				// Create variable to track the bytes being redirected
				int nextByteFromSystem = 0;

      				// Reads the stream from the Input and outputs the stream into the system output
      				while ((nextByteFromSystem = input.read()) != -1) {
      					System.out.write(nextByteFromSystem);
					System.out.flush();
      				}
			} catch (IOException exception) {
				System.out.println("Unexpected exception: "  + exception);
			}
		}
	}
}
