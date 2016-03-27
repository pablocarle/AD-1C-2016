package org.uade.ad.trucorepo.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RankingDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RankingItemDTO> items;
	
	public RankingDTO(List<RankingItemDTO> items) {
		super();
		this.items = items;
	}

	public List<RankingItemDTO> getItems() {
		return new ArrayList<>(items);
	}
}
