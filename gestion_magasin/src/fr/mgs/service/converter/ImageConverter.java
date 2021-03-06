package fr.mgs.service.converter;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.mgs.business.ProductManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.product.Product;

/**
 * Get a product id in the external context, find the picture and convert it to StreamedContent
 * 
 * @author Ismaël
 *
 */
@ManagedBean(name = "imageCvt")
@ApplicationScoped
public class ImageConverter {

	private ProductManager productManager;

	@PostConstruct
	public void init() {
		productManager = new ProductManager();
		productManager.init(DataSource.LOCAL);
	}

	/**
	 * Return the image of the product
	 * 
	 * @return the picture of the product
	 * @throws NumberFormatException
	 *             Exception format
	 * @throws SQLException
	 *             exception SQL
	 */
	public StreamedContent getImage() throws NumberFormatException, SQLException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String id = context.getExternalContext().getRequestParameterMap().get("image");
			Product product = productManager.findProduct(Integer.parseInt(id));
			return new DefaultStreamedContent(new ByteArrayInputStream(product.getPicture()));
		}
	}
}
