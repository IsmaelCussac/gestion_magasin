package fr.mgs.toolbox;

public class BarCode {
	
	public static String getProductBarCode(String designation, String subCategory) {

		String barcode = "";

		// nombre de mots dans la désignation ( 2 chiffres )
		int nbword = designation.split(" ").length;
		if (nbword < 10)
			barcode += "0" + nbword;
		else
			barcode += nbword;

		// première lettre de la sous-catégorie produit ( 1 chiffre )
		char firstletter = subCategory.charAt(0);
		barcode += firstletter;

		// la somme des code ascii de la désignation + catégorie ( 5 chiffres )
		int ascii = 0;
		String ensembleRef = designation + subCategory;
		for (int i = 0; i < ensembleRef.length(); ++i) {
			ascii += (int) ensembleRef.charAt(i);
		}
		if (ascii < 100)
			barcode += "000" + ascii;
		else if (ascii < 1000)
			barcode += "00" + ascii;
		else if (ascii < 10000)
			barcode += "0" + ascii;
		else
			barcode += ascii;

		return barcode;
	}

}
