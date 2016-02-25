package fr.mgs.models.products;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	
	private Category father;
}
