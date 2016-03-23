package fr.mgs.model.order;

/**
 * Contains the different status an order can have.
 * 
 * @author Ismaël
 *
 */
public enum OrderStatus {

	NOT_VALIDATED("Non validée"), 
	VALIDATED("Validée"), 
	IN_PROGRESS("En cours"), 
	SHORTAGE("Livrée avec reliquats"), 
	DELIVERED("Livrée");

	private final String text;

	private OrderStatus(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
