package zhengw.confmgr.utility;

import java.util.ArrayList;

public class ListUtility {

	public static <T> ArrayList<T> fromIterable(Iterable<T> iterable) {
		if (iterable == null)
			return null;
		
		ArrayList<T> list = new ArrayList<T>();
		for (T t : iterable) {
			list.add(t);
		}
		return list;
	}
}
