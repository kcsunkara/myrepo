package com.cognizant.ui.json;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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
