package fr.mgs.models.orders;

/**
 * Contains the different status an order can have. 
 * 
 * @author Isma�l
 *
 */
public enum OrderStatus {
	
	NOT_VALIDATED("Non valid�e"),
	VALIDATED("Valid�e"),
	IN_PROGRESS("En cours"),
	SHORTAGE("Livr�e avec reliquats"),
	DELIVERED("Livr�e");
	
	private final String text;

	private OrderStatus(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
