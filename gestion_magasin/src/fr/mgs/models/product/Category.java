package fr.mgs.models.product;

/**
 * This class describes a level 1 category. 
 * 
 * @author Ismaël
 *
 */
public enum Category {
	PAPER("Papeterie"),
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
