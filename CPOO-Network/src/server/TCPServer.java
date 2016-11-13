package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.String;
import java.io.PrintWriter;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;
import java.io.File;
import java.io.FileReader;

public class TCPServer {

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

	private static ArrayList<Long> listIds() {
		ArrayList<Long> ids = new ArrayList<Long>();
		File directory = new File("files");
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				String nameFile = file.getName();
				ids.add(Long.parseLong(nameFile.substring(0, nameFile.length() - 4)));
			}
		}
		return ids;
	}

	private static Long generateId(ArrayList<Long> ids) {
		boolean generate = true;
		Long id = Math.abs(new Random().nextLong());
		while (generate) {
			if (!(ids.contains(id))) {
				generate = false;
			}
		}
		return id;
	}

	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(5001);
		System.out.println("Server: Initiated.");

		while (true) {
			Socket s = ss.accept();
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			System.out.println("Server: Connection made with " + s.getInetAddress() + ".");
			out.writeInt(101);
			
			boolean clientConnected = true;
			while (clientConnected) {
				int type = in.readInt();

				if (type == 1) {
					System.out.println("Server: receive LIST.");
					ArrayList<Long> ids = listIds();
					out.writeInt(101);
					out.writeInt(ids.size());
					for (Long id : ids) {
						out.writeLong(id);
					}
				}

				if (type == 3) {
					System.out.println("Server: receive REMOVE.");
					long id = in.readLong();
					System.out.println("Server: removing file 'files/" + id + ".txt'.");
					new File("files/" + id + ".txt").delete();
					out.writeInt(103);
					System.out.println("Server: send REMOVE_RESP.");
				}

				if (type == 2) {
					System.out.println("Server: receive ADD.");
					int nbChars = in.readInt();
					char[] str = new char[nbChars];

					for (int i = 0; i < nbChars; i++) {
						str[i] = in.readChar();
					}
					ArrayList<Long> ids = listIds();

					Long id = generateId(ids);
					PrintWriter doc = new PrintWriter("files/" + id + ".txt");
					doc.print(new String(str));
					doc.close();

					System.out.println("Server: file '" + new String(str) + "'.");
					
					out.writeInt(102);
					out.writeLong(id);
				}

				if (type == 4) {
					System.out.println("Server: receive GET.");
					Long id = in.readLong();
					String txt = readFile("files/" + id + ".txt");
					out.writeInt(104);
					out.writeInt(txt.length());
					for (int i = 0; i < txt.length(); i++) {
						out.writeChar(txt.charAt(i));
					}
				}

				if (type == 90) {
					out.writeInt(190);
					System.out.println("Server: send BYE_RESP.");
					clientConnected = false;
				}
			}
			s.close();
		}
	}
}
