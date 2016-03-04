package fr.mgs.model.monitor;

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
* This class describes a log monitor entity in database. It contains : 
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
@Entity(name = "logMonitors")
@Table(name = "log_monitor_t")
public class LogMonitor implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_monitor_id")
	private int logMonitorId;

	@Column(name = "store_keeper_id", length = 10, nullable = false)
	private String storeKeeper;

	@Column(name = "product_name", length = 100, nullable = false)
	private String product;

	@Column(name = "action", nullable = false)
	@Enumerated(EnumType.STRING)
	private Action action;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "resume", length = 250, nullable = true)
	private String resume;

	public LogMonitor() {
	}

	public int getLogMonitorId() {
		return logMonitorId;
	}

	public void setLogMonitorId(int logMonitorId) {
		this.logMonitorId = logMonitorId;
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

	public void setHistorical(String storeKeeper, Product product, Action action, Date date, String resume) {
		setStoreKeeper(storeKeeper);
		setProduct(product.getDesignation());
		setAction(action);
		setDate(date);
		setResume(resume);
	}

	@Override
	public String toString() {
		return "Historical [logMonitorId=" + logMonitorId + ", storeKeeper=" + storeKeeper + ", product=" + product
				+ ", action=" + action + ", date=" + date + ", resume=" + resume + "]";
	}

}
