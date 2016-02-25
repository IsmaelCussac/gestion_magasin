package fr.mgs.models.users;

/**
 * Contains the different privileges a user can have.
 * 
 * @author Ismaël
 *
 */
public enum Privilege {

	CUSTOMER("Client"), 
	STORE_KEEPER("Magasinier"), 
	APP_ADMIN("Administrateur");

	private final String text;

	private Privilege(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
