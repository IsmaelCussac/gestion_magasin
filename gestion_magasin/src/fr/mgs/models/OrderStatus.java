package fr.mgs.models;

/**
 * Contains the different status an order can have. 
 * 
 * @author Isma�l
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
