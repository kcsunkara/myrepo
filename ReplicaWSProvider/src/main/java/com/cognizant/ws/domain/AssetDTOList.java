package com.cognizant.ws.domain;

import java.util.List;

import javax.validation.Valid;

public class AssetDTOList {

	@Valid
	private List<AssetDTO> assets;

	
	public List<AssetDTO> getAssets() {
		return assets;
	}

	public void setAssets(List<AssetDTO> assets) {
		this.assets = assets;
	}
}
