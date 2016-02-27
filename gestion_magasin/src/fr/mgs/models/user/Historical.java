package fr.mgs.models.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.mgs.models.product.Product;

/**
* This class describes a historical entity in database. It contains : 
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
@Entity(name = "historicals")
@Table(name = "historical_t")
public class Historical implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "historical_id")
	private int historicalId;

	@Column(name = "store_keeper_id", length = 10, nullable = false)
	private String storeKeeper;

	@Column(name = "product_name", length = 100, nullable = false)
	private String product;

	@Column(name = "action", nullable = false)
	private String action;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "resume", length = 250, nullable = true)
	private String resume;

	public Historical() {
	}

	public int getHistoricalId() {
		return historicalId;
	}

	public void setHistoricalId(int historicalId) {
		this.historicalId = historicalId;
	}

	public String getStoreKeeper() {
		return storeKeeper;
	}

	public void setStoreKeeper(String storeKeeper) {
		this.storeKeeper = storeKeeper;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
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

	public void setHistorical(String storeKeeper, Product product, Action action, Date date, String resume) {
		setStoreKeeper(storeKeeper);
		setProduct(product.getDesignation());
		setAction(action.toString());
		setDate(date);
		setResume(resume);
	}

	@Override
	public String toString() {
		return "Historical [historicalId=" + historicalId + ", storeKeeper=" + storeKeeper + ", product=" + product
				+ ", action=" + action + ", date=" + date + ", resume=" + resume + "]";
	}

}
