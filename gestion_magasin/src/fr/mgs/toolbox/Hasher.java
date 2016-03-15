package fr.mgs.toolbox;

import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class used to hash a string, implementation of PasswordEncoder to be used by
 * spring security
 * 
 * @author ismael
 *
 */
public class Hasher implements PasswordEncoder {

	/**
	 * Hash a string in SHA-256
	 * 
	 * @param stringToHash
	 * @return
	 */
	public static String hash(String stringToHash) {
		String salt1 = "gestion";
		String salt2 = "magasin";
		byte[] hash = null;
		String result;

		try {
			MessageDigest digester = MessageDigest.getInstance("SHA-256");
			hash = digester.digest(salt1.concat(stringToHash).concat(salt2).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = toHex(hash);
		return result;
	}

	/**
	 * Convert a byte array into a string in hexadecimal
	 * 
	 * @param input byte array
	 * @return hexadecimal string of input
	 */
	public static String toHex(byte[] input) {
		StringBuffer buf = new StringBuffer();
		for (byte b : input)
			buf.append(String.format("%02x", b));
		return buf.toString();
	}

	/**
	 * Overrided method to encode a CharSequence using hash method
	 */
	@Override
	public String encode(CharSequence stringToHash) {
		return hash(stringToHash.toString());
	}

	/**
	 * Overrided method used for the password checking
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword.toString()).equals(encodedPassword);
	}


}
