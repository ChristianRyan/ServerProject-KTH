import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class CalculatorServer {
	static ServerSocket server;

	public static void main(String[] args) throws SocketException {
		int portNumber = 6789;
		try {
			server = new ServerSocket(portNumber);
			while (true) {
				ServerHelper r = new ServerHelper(server.accept());
				r.start();
			}
		} catch (IOException e) {
			System.out.println("Error at input/output handling.");
			System.exit(1);
		} finally {
			try {
				server.close();
				System.out.println("The server has been shut down.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
