package fr.mgs.model.product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class describes a level 2 category entity in database. It contains : 
 * - a name 
 * - a level 1 category
 * 
 * @author Ismaël
 *
 */
@Entity(name = "subCategories")
@Table(name = "sub_category_t")
public class SubCategory implements Serializable {

	@Id
	@Column(name = "sub_category_name")
	private String name;

	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private Category category;

	@OneToMany(mappedBy = "subCategory", fetch = FetchType.EAGER, orphanRemoval = false)
	private Set<Product> products = new HashSet<Product>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public void setSubCategory(String name, Category category) {
		setName(name);
		setCategory(category);

	}

	@Override
	public String toString() {
		return "SubCategory [name=" + name + ", category=" + category + "]";
	}

}
