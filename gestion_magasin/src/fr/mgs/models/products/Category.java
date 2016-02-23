package fr.mgs.models.products;

/**
 * This class describes a level 1 category. 
 * 
 * @author Isma�l
 *
 */
public enum Category {
	PAPER("Pap�terie"),
	PLASTIC("Plastique"),
	CULTURE_PLASTIC("Plastique de culture");
	
	private final String text;

	private Category(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
