package server;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import encryption.*;
import message.*;
import java.util.Date;

/**
 * Provides an executable TCP client in console user interface to send and
 * retrieve possibly cyphered text document with a server that works under the
 * following requirements:
 * 
 * The available console commands and their usage are presented below:
 * <p>
 * <ul>
 * <li><b>'add filePath [cypherMethod] [cypherMethodKey]'</b>
 * <p>
 * Send the 'filePath' .txt format text document to the server.
 * <p>
 * <i>OPTIONAL</i>: 'cypherMethod [cypherMethodKey]': the optional method to
 * encrypt the document and its key. The five available cypher methods and their
 * respective keys are :
 * <ul>
 * <li>'atbash': no key
 * <li>'caesar': integer key
 * <li>'keyword': upper or lower case non accentuated letter key
 * <li>'rottreize': no key
 * <li>'vigenere': upper or lower case non accentuated letter key
 * </ul>
 * <p>
 * <li><b>'bye'</b>
 * <p>
 * Disconnect from the server and/or exit the application.
 * <li><b>'get fileID filePath [cypherMethod] [cypherMethodKey]'</b>
 * <p>
 * Requests the server to retrieve a .txt format text document stored under ID
 * 'fileID' and copy it at the path 'filePath'.
 * <p>
 * <i>OPTIONAL</i>: 'cypherMethod [cypherMethodKey]': see 'add'.
 * <li><b>'help'</b>
 * <p>
 * Displays the a list of all available methods.
 * <li><b>'list'</b>
 * <p>
 * List all file ID currently stored on the server.
 * <li><b>'remove fileID'</b>
 * <p>
 * Send the server a request to delete the file with ID 'fileID'.
 * </ul>
 * 
 * The precise processing of each cypher method are explained in their
 * respective class.
 * 
 * @author Félix CHRISTELLE
 * @author Benjamin GUIGNARD
 * 
 * @version 1.0
 * 
 * @see Atbash
 * @see Caesar
 * @see Keyword
 * @see Rottreize
 * @see Vigenere
 */

public class TCPClient {

	/**
	 * Cyphers a message with the requested cypher method.
	 * 
	 * @param msg
	 *            the message to be cyphered
	 * @param method
	 *            the cypher method
	 * @param key
	 *            the cypher method key (by default "")
	 * @return the cyphered message
	 */
	private static String cypher(String msg, String method, String key) {
		switch (method) {
		case "atbash":
			return new Atbash().cypher(new Messaged(msg), "").getMessage();
		case "caesar":
			return new Caesar().cypher(new Messaged(msg), key).getMessage();
		case "keyword":
			return new Keyword().cypher(new Messaged(msg), key).getMessage();
		case "rottreize":
			return new Rottreize().cypher(new Messaged(msg), "").getMessage();
		case "vigenere":
			return new Vigenere().cypher(new Messaged(msg), key).getMessage();
		}
		return "";
	}

	/**
	 * Decyphers a message with the requested cypher method.
	 * 
	 * @param msg
	 *            the cyphered message to be decyphered
	 * @param method
	 *            the cypher method
	 * @param key
	 *            the cypher method key (by default "")
	 * @return the decyphered message
	 */
	private static String decypher(String msg, String method, String key) {
		switch (method) {
		case "atbash":
			return new Atbash().decypher(new Messagec(msg), "").getMessage();
		case "caesar":
			return new Caesar().decypher(new Messagec(msg), key).getMessage();
		case "keyword":
			return new Keyword().decypher(new Messagec(msg), key).getMessage();
		case "rottreize":
			return new Rottreize().decypher(new Messagec(msg), "").getMessage();
		case "vigenere":
			return new Vigenere().decypher(new Messagec(msg), key).getMessage();
		}
		return "";
	}

	/**
	 * Waits for the server to send a specific response. If this response is
	 * received, a corresponding message is print. If however the server send
	 * the message 199, then the following error message sent by the server is
	 * raised as an IOException.
	 * 
	 * @param in
	 *            the InputStream from the server
	 * @param resp
	 *            the requested response message from the server
	 * @param msg
	 *            the message corresponding to the requested response
	 * @throws IOException
	 */
	private static void wait(DataInputStream in, int resp, String msg) throws IOException {

		Long d = new Date().getTime();
		
		boolean waiting = true;
		while (waiting) {
			int type = in.readInt();
			
			if (type == resp) {
				System.out.println(msg);
				waiting = false;
			}

			if (type == 199) {
				int nbChars = in.readInt();
				char[] str = new char[nbChars];

				for (int i = 0; i < nbChars; i++) {
					str[i] = in.readChar();
				}
				throw new IOException(new String(str));
			}
			
			if ((new Date().getTime()) - d > 10000) {
				throw new IOException("Connection timeout: no response from the server.");
			}
		}
	}

	/**
	 * Import a .txt text file into a string.
	 * 
	 * @param path
	 *            the path of the file
	 * @return the content of the file
	 * @throws IOException
	 */
	protected static String readFile(String path) throws IOException {
		String content = null;
		File file = new File(path);
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			// would happen only if the file is not made readable, as the
			// existence of its path is checked by another method in the TCP
			// client
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return content;
	}

	/**
	 * The core function of the TCP client. It connects to the server and then
	 * listens to the user commands through the console until it is stopped.
	 * <p>
	 * The communications with the server are only made as execution of the
	 * commands.
	 * 
	 * @param args
	 *            the IP address of the host and the port to connect to (for
	 *            instance <code>localhost 6789</code>)
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// establishment of the connection

		Socket s = new Socket(args[0], Integer.parseInt(args[1]));
		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream out = new DataOutputStream(s.getOutputStream());

		out.writeInt(0);
		wait(in, 100, "HELLO_RESP");
		// the exception is not caught here if the server throws it

		String host = s.getInetAddress().toString();
		int port = s.getPort();

		System.out.println("Connected to server " + s.getInetAddress() + ":" + port + ".");
		
		// connection now established
		// user command listening loop

		boolean bye = false;
		while (!bye) {

			String[] instructions = new BufferedReader(new InputStreamReader(System.in)).readLine().split(" ");

			// verification of the instruction validity
			if (Instructions.check(instructions)) {
				// the following try ... catch block expands all over the while
				// loop. It enables to catch exceptions raised by the server.
				try {
					String type = instructions[0].toUpperCase();

					// command 'bye'
					if (type.equals("BYE")) {
						out.writeInt(90);
						wait(in, 190, "BYE_RESP");
						bye = true;
					}

					// command 'remove'
					if (type.equals("REMOVE")) {
						out.writeInt(3);
						out.writeLong(Long.parseLong(instructions[1]));

						wait(in, 103, "REMOVE_RESP");
					}

					// command 'list'
					if (type.equals("LIST")) {
						out.writeInt(1);
						wait(in, 101, "LIST_REP");
						int nbFiles = in.readInt();
						if (nbFiles == 0) {
							System.out.println("There are no available files on the server.");
						} else {
							System.out.println(
									"There are " + nbFiles + " available files on the server, which IDs are :");
							for (int i = 0; i < nbFiles; i++) {
								System.out.println("    " + in.readLong());
							}
						}
					}

					// command 'add'
					if (type.equals("ADD")) {
						String txt = readFile(instructions[1]);
						out.writeInt(2);
						out.writeInt(txt.length());

						if (instructions.length > 2) {
							if (instructions.length > 3) {
								txt = cypher(txt, instructions[2], instructions[3]);
							} else {
								txt = cypher(txt, instructions[2], "");
							}
						}

						for (int i = 0; i < txt.length(); i++) {
							out.writeChar(txt.charAt(i));
						}

						wait(in, 102, "ADD_RESP");

						System.out.println("File stored on the server under the ID: " + in.readLong() + ".");
					}

					// command 'get'
					if (type.equals("GET")) {
						out.writeInt(4);
						out.writeLong(Long.parseLong(instructions[1]));

						wait(in, 104, "GET_RESP");

						int nbChars = in.readInt();
						char[] str = new char[nbChars];

						for (int i = 0; i < nbChars; i++) {
							str[i] = in.readChar();
						}

						String txt = new String(str);
						if (instructions.length > 3) {
							if (instructions.length > 4) {
								txt = decypher(txt, instructions[3], instructions[4]);
							} else {
								txt = decypher(txt, instructions[3], "");
							}
						}

						try {
							PrintWriter doc = new PrintWriter(instructions[2]);
							doc.print(txt);
							doc.close();
							System.out.println("File received stored on path '" + instructions[2] + "'.");
						} catch (IOException e) {
							System.out.println(
									"File correctly received but unable to create it on the given path.\nPlease make sure that the directories on the path "
											+ instructions[2] + "' exist.");
						}
					}
				} catch (IOException e) {
					System.out.println("ERROR_RESP\nException from the server: " + e.toString() + ".");
				}
			}
		}

		s.close();
		System.out.println("Disconnected from server " + host + ":" + port + ".");
	}
}