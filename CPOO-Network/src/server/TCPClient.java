package server;

import java.net.Socket;
//import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TCPClient {
	
	private static String readFile(String filename) throws IOException {
		String content = null;
		File file = new File(filename);
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return content;
	}

	public static void main(String[] args) throws IOException {

		String[] instructions;
		// byte[] data;

		Socket s = new Socket("localhost", 5001);
		System.out.println("Client: Connected to server.");

		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream out = new DataOutputStream(s.getOutputStream());

		out.writeInt(0);

		boolean connected = true;
		while (connected) {
			instructions = new BufferedReader(new InputStreamReader(System.in)).readLine().split(" ");

			if (instructions[0].equals("LIST")) {
				System.out.println("Client: LIST.");
				out.writeInt(1);
				if (in.readInt() == 101) {
					System.out.println("Client: LIST_RESP.");
				}
				int nbFiles = in.readInt();
				System.out.println("There are " + nbFiles + " text files on the server, which IDs are :");
				for (int i = 0; i < nbFiles; i++) {
					System.out.println("    " + in.readLong());
				}
			}
			
			if (instructions[0].equals("ADD")) {
				System.out.println("Client: ADD.");
				String txt = readFile(instructions[1]);
				out.writeInt(2);
				out.writeInt(txt.length());
				for (int i = 0; i < txt.length(); i++) {
					out.writeChar(txt.charAt(i));
				}
				if (in.readInt()==102) {
					System.out.println("Client: Received ADD_RESP");
				}
				System.out.println("Client: file id on server: " + in.readLong() + ".");
			}

			if (instructions[0].equals("REMOVE")) {
				out.writeInt(3);
				out.writeLong(Long.parseLong(instructions[1]));
				System.out.println("Client: REMOVE request sent.");
				if (in.readInt() == 103) {
					System.out.println("Client: REMOVE_RESP.");
				}
			}

			if (instructions[0].equals("GET")) {
				out.writeInt(4);
				System.out.println("Client: send GET.");
				out.writeLong(Long.parseLong(instructions[1]));
				if (in.readInt() == 104) {
					System.out.println("Client: receive GET_RESP.");
				}
				int nbChars = in.readInt();
				char[] str = new char[nbChars];

				for (int i = 0; i < nbChars; i++) {
					str[i] = in.readChar();
				}

				PrintWriter doc = new PrintWriter("received/" + instructions[1] + ".txt");
				doc.print(new String(str));
				doc.close();

				System.out.println("Client: received '" + new String(str) + "', and stored under file 'received/" + instructions[1] + ".txt'.");
			}
			
			if (instructions[0].equals("BYE")) {
				out.writeInt(90);
				if (in.readInt() == 190) {
					connected = false;
					System.out.println("Client: BYE_RESP received.");
				}
			}
		}

		s.close();
		System.out.println("Client: Connection terminated.");
	}
}
