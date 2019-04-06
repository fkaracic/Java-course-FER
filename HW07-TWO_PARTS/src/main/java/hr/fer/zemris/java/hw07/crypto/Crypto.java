package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.zemris.java.hw07.util.Util;

/**
 * Program {@code Crypto} allows the user to encrypt/decrypt given file using
 * the AES crypto-algorithm and the 128-bit encryption key or calculate and
 * check the SHA-256 file digest. User must enter one of the keywords (checksha,
 * encrypt or decrypt) and the arguments needed. If the keyword is checksha,
 * argument with the file name is required. If the keyword is encrypt or
 * decrypt, two arguments are required: name of the file to be
 * encrypted/decrypted and name of the file in which encrypted/decrypted file
 * will be written.
 * 
 * @author Filip Karacic
 *
 */
public class Crypto {

	/**
	 * Method called when program starts.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		if (args.length < 2 || args.length > 3) {
			System.out.println("Invalid number of arguments.");
			return;
		}

		String firstArgument = args[0].toLowerCase();

		if (firstArgument.equals("checksha")) {
			checkSha(args);
		}

		else if (firstArgument.equals("encrypt") || firstArgument.equals("decrypt")) {
			crypting(firstArgument, args);
		}

		else {
			System.out.println("Invalid keyword. Must be: checksha, encrypt or decrypt.");
		}
	}

	/**
	 * Validates 'checksha' command arguments and checks if the given sha digest
	 * matches to the sha digest of the file.
	 * 
	 * @param args
	 *            command line argument given to the main method
	 */
	private static void checkSha(String[] args) {
		if (args.length != 2) {
			System.out.println("With the keyword 'checksha' only file nam must be provided.");
			return;
		}

		try {
			if (shaMatches(args[1])) {
				System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
			} else {
				System.out
						.println("Digesting completed. Digest of " + args[1] + " does not match the expected digest.");
			}
		} catch (IOException e) {
			System.out.println("Error reading file " + args[1]);
		}
	}

	/**
	 * Checks if sha-digest matches the sha-digest from the file.
	 * 
	 * @param fileName
	 *            name of the file
	 * @return <code>true</code> if sha digests matches
	 * @throws IOException
	 *             if error occur while reading file
	 */
	private static boolean shaMatches(String fileName) throws IOException {
		try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(fileName)))) {

			try (Scanner scanner = new Scanner(System.in)) {
				System.out.print("Please provide expected sha-256 digest for " + fileName + "\n> ");
				String digest = scanner.nextLine();

				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				while (true) {
					byte[] buff = new byte[4096];

					int r = inputStream.read(buff);
					if (r < 1)
						break;

					messageDigest.update(buff, 0, r);
				}

				String computedDigest = Util.byteToHex(messageDigest.digest());
				return computedDigest.equals(digest);

			} catch (NoSuchAlgorithmException e1) {
			}

		}

		return false;
	}

	/**
	 * Validate arguments for the encrypt and decrypt commands and performs
	 * crypting.
	 * 
	 * @param firstArgument
	 *            command name
	 * @param args
	 *            command line arguments given to the main method
	 */
	private static void crypting(String firstArgument, String[] args) {
		if (args.length != 3) {
			System.out.println("With the keyword 'encrypt' and 'decrypt' two arguments (files) must be provided.");
			return;
		}

		try {
			crypt(firstArgument, args[1], args[2]);
			System.out.println((firstArgument.equals("encrypt") ? "En" : "De") + "cryption Completed. "
					+ "Generated file " + args[2] + " based on file " + args[1] + ".");
		} catch (IOException e) {
			System.out.println("Error crypting file " + args[1] + " to file " + args[2]
					+ ". Given arguments must be existing file to crypt and file in which cryption will be written.");
		} catch (InvalidKeyException e) {
			System.out.println("Invalid key was entered. Please enter valid keys as described in program.");
		}
	}

	/**
	 * Allows user to enter password and initialization vector for encryption and
	 * then if they are valid encrypts or decrypts the given original file to the
	 * crypted file.
	 * 
	 * @param operation
	 *            decryption or encryption
	 * @param originalFile
	 *            original file
	 * @param cryptedFile
	 *            resulting file
	 * @throws InvalidKeyException
	 *             if the key entered is not valid (32 hex-digits)
	 * @throws IOException
	 *             if error occur while reading or writing files
	 * 
	 */
	private static void crypt(String operation, String originalFile, String cryptedFile)
			throws InvalidKeyException, IOException {

		try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(originalFile)));
				OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(cryptedFile)))) {

			try (Scanner scanner = new Scanner(System.in)) {
				System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
				String keyText = scanner.nextLine();

				System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
				String ivText = scanner.nextLine();

				boolean encrypt = operation.equals("encrypt") ? true : false;

				executeCryption(inputStream, outputStream, encrypt, keyText, ivText);
			}
		}
	}

	/**
	 * Writes crypted content of the original file to the given target file.
	 * 
	 * @param is
	 *            input stream of the original file
	 * @param os
	 *            output stream of the target file
	 * @param encrypt
	 *            <code>true</code> if method is encryption, <code>false</code> if
	 *            decryption
	 * @param key
	 *            entered password
	 * @param iv
	 *            initialization vector
	 * @throws InvalidKeyException
	 *             if the key is not valid (32-hex digit)
	 */
	private static void executeCryption(InputStream is, OutputStream os, boolean encrypt, String key, String iv)
			throws InvalidKeyException {

		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(key), "AES");

			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(iv));
			Cipher cipher;

			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			while (true) {
				byte[] buff = new byte[4096];

				int r = is.read(buff);
				if (r < 1)
					break;

				byte[] cipherText = cipher.update(buff, 0, r);

				os.write(cipherText);
			}

			os.write(cipher.doFinal());
		} catch (IllegalArgumentException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new InvalidKeyException(e);
		} catch (Exception e) {
		}

	}
}
