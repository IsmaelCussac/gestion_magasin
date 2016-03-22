package fr.mgs.web.storekeeper;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(value = "barCodeItemConverter", forClass = BarCodeItem.class)
public class BarCodeItemConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		Object ret = null;
		if (component instanceof PickList) {
			Object dualList = ((PickList) component).getValue();
			DualListModel dl = (DualListModel) dualList;
			for (Object o : dl.getSource()) {
				String id = "" + ((BarCodeItem) o).getProductId();
				if (value.equals(id)) {
					ret = o;
					break;
				}
			}
			if (ret == null)
				for (Object o : dl.getTarget()) {
					String id = "" + ((BarCodeItem) o).getProductId();
					if (value.equals(id)) {
						ret = o;
						break;
					}
				}
		}
		return ret;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component, Object value) {
		String str = "";
		if (value instanceof BarCodeItem) {
			str = "" + ((BarCodeItem) value).getProductId();
		}
		return str;
	}
}
