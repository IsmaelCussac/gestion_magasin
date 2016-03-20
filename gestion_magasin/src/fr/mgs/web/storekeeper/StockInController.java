package fr.mgs.web.storekeeper;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "stockInCtl")
@SessionScoped
public class StockInController {

	@ManagedProperty("#{ListProducts}")
	ListProductController listProducts;

	private String initScan;

	@PostConstruct
	private void init() {

	}

	public void saveProducts() {

	}

	public void initScan() {

	}

	public void scan() {

	}

	public void resetScan() {

	}

	public ListProductController getListProducts() {
		return listProducts;
	}

	public void setListProducts(ListProductController listProducts) {
		this.listProducts = listProducts;
	}

	
	public String getInitScan() {
		return initScan;
	}
	
	public void setInitScan(String initScan) {
		this.initScan = initScan;
	}
}
