package org.uade.ad.trucoserver;

import java.util.ArrayList;
import java.util.List;

import org.uade.ad.trucoserver.entities.HasDTO;

public final class DTOUtil {

	private DTOUtil() {
		super();
	}
	
	public static <T> List<T> getDTOs(List<? extends HasDTO<T>> beans, Class<T> clasz) {
		if (beans == null)
			return null;
		List<T> retList = new ArrayList<>(beans.size());
		for (HasDTO<T> elem : beans) {
			retList.add(elem.getDTO());
		}
		return retList;
	}
	
}
