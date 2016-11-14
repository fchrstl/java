package server;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import encryption.Atbash;
import encryption.Caesar;
import encryption.Keyword;
import encryption.Rottreize;
import encryption.Vigenere;

/**
 * Provides the static protected method <code>check(String[] ins)</code> used by
 * the TCPClient to verify if the instructions given through the line user
 * interface are correct, that is to say : correct
 * <ul>
 * <li>no syntax mistakes;
 * <li>no incorrect file paths given.
 * </ul>
 * A command <code>help</code> is provided to give in the console the
 * informations contained in the text file <code>commandHelp.txt</code> set at
 * the root of the application.
 * <p>
 * See {@link TCPClient} for the instruction syntax.
 * 
 * @author Félix CHRISTELLE
 * @author Benjamin GUIGNARD
 * 
 * @version 1.0
 * 
 * @see TCPClient
 * @see Atbash
 * @see Caesar
 * @see Keyword
 * @see Rottreize
 * @see Vigenere
 */

public class Instructions {

	/**
	 * Checks if a string contains only upper and lower case non accentuated
	 * letters, ie: ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.
	 * 
	 * @param s
	 *            the string to check
	 * @return true if it contains only letters
	 */
	private static boolean onlyLetters(String s) {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < s.length(); i++) {
			if (letters.indexOf(s.toUpperCase().charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gives the maximum arity for each command.
	 * 
	 * @param s
	 *            the name of the command
	 * @return its maximum arity
	 */
	private static int maxArguments(String s) {
		switch (s) {
		case "get":
			return 4;
		case "add":
			return 3;
		case "remove":
			return 1;
		}
		return 0;
	}

	/**
	 * Provides a string depicting the general form of use for each user
	 * commands.
	 * 
	 * @param s
	 *            the name of the command
	 * @return its form of use
	 */
	private static String use(String s) {
		switch (s) {
		case "add":
			return "'add filePath [cypherMethod] [cypherMethodKey]'";
		case "get":
			return "'get fileID filePath [cypherMethod] [cypherMethodKey]'";
		case "remove":
			return "'remove fileID'";
		}
		return "'" + s + "'";
	}

	/**
	 * Provides the arity for each cypher method.
	 * 
	 * @param cypher
	 *            the name of the cypher method
	 * @return its arity
	 */
	private static int nbCypherArgs(String cypher) {
		switch (cypher) {
		case "atbash":
			return 0;
		case "rottreize":
			return 0;
		}
		return 1;
	}

	/**
	 * Check if a sub-instruction regarding a cypher method is correctly
	 * written.
	 * 
	 * @param cypherMethod
	 *            the instructions
	 * @return true if the instruction is correct, false otherwise.
	 */
	private static boolean checkCypher(String[] cypherMethod) {

		if (!(cypherMethod.length - 1 > nbCypherArgs(cypherMethod[0]))) {
			System.out.println("Argument missing: the cypher method '" + cypherMethod[0] + "' expects a key.");
			return false;
		}

		if (!(cypherMethod.length - 1 < nbCypherArgs(cypherMethod[0]))) {
			System.out.println("Too many arguments: no key expected for '" + cypherMethod[0] + "'.");
			return false;
		}

		switch (cypherMethod[0]) {
		case "caesar":
			try {
				Integer.parseInt(cypherMethod[1]);
			} catch (NumberFormatException e) {
				System.out.println("The caesar cypher's key must be an integer.");
				return false;
			}
			break;
		case "keyword":
			if (!onlyLetters(cypherMethod[1])) {
				System.out.println(
						"Only non accentuated upper and lower case letters are allowed in the key for 'keyword'.");
				return false;
			}
			break;
		case "vigenere":
			if (!onlyLetters(cypherMethod[1])) {
				System.out.println(
						"Only non accentuated upper and lower case letters are allowed in the key for 'vigenere'.");
				return false;
			}
			break;
		}
		return true;
	}

	/**
	 * Checks case-by-case if the request instructions given by the user are
	 * correct, and display a message depicting the mistakes encountered, if
	 * any.
	 * 
	 * @param ins
	 *            the String[] format instructions
	 * @return true if the instructions are correct, false otherwise.
	 * @throws IOException
	 */
	protected static boolean check(String[] ins) throws IOException {

		String type = ins[0].toLowerCase();

		if (type.equals("")) {
			return true;
		}

		if (!(type.matches("(add|get|list|remove|bye|help)"))) {
			System.out.println("Unknown command '" + type + "'. Please type 'help' for more informations.");
			return false;
		}

		if (type.equals("help")) {
			String help = TCPClient.readFile("commandHelp.txt");
			System.out.println(help);
			return false;
		}

		int maxArg = maxArguments(type);
		if (ins.length > maxArg + 1) {
			if (maxArg == 0) {
				System.out.println("No arguments are expected for the command '" + type + "'.");
			} else if (maxArg == 1) {
				System.out.println("'" + type + "' expects only 1 argument. Usage: " + use(type));
			} else {
				System.out.println("'" + type + "' expects only " + maxArg + " arguments. Usage: " + use(type));
			}
			return false;
		}

		if (type.equals("add")) {
			if (ins.length < 2) {
				System.out.println("Missing path name of the file to add.");
				return false;
			}
			File f = new File(ins[1]);
			if (!f.exists()) {
				System.out.println("File '" + ins[1] + "' not found.");
				return false;
			}

			if (ins.length > 2) {
				if (ins[2].matches("(atbash|caesar|keyword|rottreize|vigenere)")) {
					return checkCypher(Arrays.copyOfRange(ins, 2, ins.length));
				} else {
					System.out.println(
							"Unknown cypher method '" + ins[2] + "'. Please type 'help' for more informations.");
					return false;
				}
			}
		}

		if (type.equals("remove")) {
			if (ins.length < 2) {
				System.out.println("Missing ID of the file to remove.");
				return false;
			}
		}

		if (type.equals("get")) {
			if (ins.length < 2) {
				System.out.println("Missing ID of the file to remove.");
				return false;
			}
			try {
				Long.parseLong(ins[1]);
			} catch (NumberFormatException e) {
				System.out.println("The file ID must be a positive integer.");
				return false;
			}
			if (ins.length < 3) {
				System.out.println("Missing path name for the file to retreive.");
				return false;
			}
			File f = new File(ins[2]);
			if (!f.exists()) {
				System.out.println("File '" + ins[1] + "' not found.");
				return false;
			}
			if (ins.length > 3) {
				if (ins[3].matches("(atbash|caesar|keyword|rottreize|vigenere)")) {
					return checkCypher(Arrays.copyOfRange(ins, 3, ins.length));
				} else {
					System.out.println(
							"Unknown cypher method '" + ins[3] + "'. Please type 'help' for more informations.");
					return false;
				}
			}
		}
		return true;
	}
}
