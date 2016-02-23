package fr.mgs.models.users;

/**
 * Contains the different actions a store keeper can do. 
 * @author Isma�l
 *
 */
public enum Action {
	
	CREATE(" a cr�� le produit "),
	UPDATE(" a mis � jour le produit "),
	DELETE(" a cach� le produit "),
	INCREASING(" a augment� la quantit� du produit "),
	DECREASING(" a r�duit la quantit� du produit ");
	
	private final String text;

	private Action(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
