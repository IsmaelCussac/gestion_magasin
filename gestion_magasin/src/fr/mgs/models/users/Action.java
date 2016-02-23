package fr.mgs.models.users;

/**
 * Contains the different actions a store keeper can do. 
 * @author Ismaël
 *
 */
public enum Action {
	
	CREATE(" a créé le produit "),
	UPDATE(" a mis à jour le produit "),
	DELETE(" a caché le produit "),
	INCREASING(" a augmenté la quantité du produit "),
	DECREASING(" a réduit la quantité du produit ");
	
	private final String text;

	private Action(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
