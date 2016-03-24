package fr.mgs.toolbox;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import fr.mgs.model.order.Order;
import fr.mgs.model.user.Team;

/**
 * This is a tool used to sort a Map containing Team associated with Order
 * collection
 * 
 * @author Ibrahima
 *
 */
public class SortMap {
	Map<Team, Collection<Order>> treeMap = new TreeMap<Team, Collection<Order>>(new Comparator<Team>() {

		@Override
		public int compare(Team t1, Team t2) {
			return t1.getName().compareTo(t2.getName());
		}
	});

	public Map<Team, Collection<Order>> getTreeMap() {
		return treeMap;
	}
}