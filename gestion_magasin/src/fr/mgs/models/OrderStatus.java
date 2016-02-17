package fr.mgs.models;

/**
 * Contains the different status an order can have. 
 * 
 * @author Ismaël
 *
 */
public enum OrderStatus {
	
	NOT_VALIDATED,
	VALIDATED,
	IN_PROGRESS,
	READY,
	SHORTAGE,
	DELIVERED
}
