import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

public class ServerHelper extends Thread {
	private Socket socket;
	private static int query = 0;
	private String IP = "";

	public ServerHelper(Socket givenSocket) {
		socket = givenSocket;
		query++;
	}

	public void run() {
		// Print address + port
		PrintWriter out = null;
		System.out.println("Connection from : " + socket.getInetAddress().getHostAddress() + ':' + socket.getPort());
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			String inputLine = in.readLine();

			if (inputLine != null) {
				String result = Expression.Calculate(inputLine);
				int costForUser = Expression.giveCost();
				if (IP == socket.getInetAddress().getHostName()) {
					costForUser = costForUser * query;
				}
				if (result != null) {
					String text = ("Calculating: " + inputLine + ".\t The result is: " + result + ".\t Cost: "
							+ costForUser + " Ficitonal operations.");
					out.println(text);
					out.flush();
				}
			}

			IP = socket.getInetAddress().getHostAddress();
		} catch (NoSuchElementException | NumberFormatException e1) {
			out.print("Please enter an algebraic expression.\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
