package fr.mgs.model.event;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.mgs.model.product.Product;

/**
 * This class describes a event monitor entity in database. It contains : 
 * - an id 
 * - a store keeper id 
 * - a product 
 * - an action 
 * - a date 
 * - a comment
 * 
 * @author Ismaël
 *
 */
@Entity(name = "events")
@Table(name = "event_t")
public class Event implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_id")
	private int eventId;

	@Column(name = "store_keeper_id", length = 25, nullable = false)
	private String storeKeeper;

	@Column(name = "product_id", nullable = false)
	private int productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "action", nullable = false)
	@Enumerated(EnumType.STRING)
	private Action action;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "resume", length = 250, nullable = true)
	private String resume;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getStoreKeeper() {
		return storeKeeper;
	}

	public void setStoreKeeper(String storeKeeper) {
		this.storeKeeper = storeKeeper;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public void setEvent(String storeKeeper, Product product, Action action, Date date, String resume) {
		setStoreKeeper(storeKeeper);
		setProductId(product.getProductId());
		setProductName(product.getDesignation());
		setAction(action);
		setDate(date);
		setResume(resume);
	}

	@Override
	public String toString() {
		return "Historical [eventId=" + eventId + ", storeKeeper=" + storeKeeper + ", product=" + productId
				+ ", action=" + action + ", date=" + date + ", resume=" + resume + "]";
	}

}
