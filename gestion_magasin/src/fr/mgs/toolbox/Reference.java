package fr.mgs.toolbox;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Generate a random reference for product id
 * 
 * @author Ismael
 *
 */
public class Reference {

	public static int generateRandomInt() {
		int ref;

		SecureRandom random = new SecureRandom();

		byte[] a = new byte[5];
		random.nextBytes(a);
		byte seed[] = random.generateSeed(20);
		BigInteger b = new BigInteger(1, seed);

		String s = String.valueOf(b);
		s = s.substring(0, 7);
		ref = Integer.parseInt(s);

		return ref;
	}
}
