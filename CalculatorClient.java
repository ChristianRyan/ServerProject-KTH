import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class CalculatorClient {
	public static String HOSTADDRESS = "127.0.0.1";
	public static int DUMMYPORT = 6789;
	static Socket socket;
	public static String userInput = "";

	public static void main(String[] args) {
		try {
			if (args.length >= 2) {
				HOSTADDRESS = args[0];
				for (int i = 1; i < args.length; i++) {
					userInput += args[i];
				}
				socket = new Socket(args[0], DUMMYPORT);
				System.out.println("\nConnection with the server has been established. Please enter an algebraic expression:");
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out.println(userInput);
				System.out.println(in.readLine());
			}
			while (true) {
				socket = new Socket(HOSTADDRESS, DUMMYPORT);
				System.out.println("\nConnection with the server has been established. Please enter an algebraic expression:");

				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				userInput = stdIn.readLine();

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out.println(userInput);

				System.out.println(in.readLine());

			}
		} catch (ConnectException e) {
			System.out.println("Connection cannot be established. Server may be down.");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
