package fr.mgs.model.event;

/**
 * Contains the different actions a store keeper can make.
 * 
 * @author Ismaël
 *
 */
public enum Action {

	CREATE("Création"), 
	UPDATE("Mise à jour"), 
	HIDE("Cache"), 
	SHOW("Affiche");

	private final String text;

	private Action(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
