package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
	}

	private class Out implements Runnable {
                OutputStream output;

                public Out(OutputStream outputStream) {
                        output = outputStream;
                }

                @Override
                public void run() {
                        try {
                                int nextByteFromSystem = 0;
                                // Reads the stream from the System and puts the stream into the output
                                while ((nextByteFromSystem = System.in.read()) != -1) {
                                        output.write(nextByte);
                                        output.flush();
                                        System.out.flush();
                                }
                        } catch (IOException exception) {
                                System.out.println("Unexpected exception: "  + exception);
                        }
                }
        }

	private class In implements Runnable {
		InputStream input;

		public In(InputStream inputStream) {
			input = inputStream;
		}

		@Override
		public void run() {
			try {
				int nextByteFromSystem = 0;

      				// Reads the stream from the System and puts the stream into the output
      				while ((nextByteFromSystem = input.read()) != -1) {
      					System.out.write(nextByteFromSystem);
      				}
			} catch (IOException exception) {
				System.out.println("Unexpected exception: "  + exception);
			}
		}
	}
}
