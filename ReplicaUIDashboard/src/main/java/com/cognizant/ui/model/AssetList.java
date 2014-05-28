package com.cognizant.ui.model;

import java.util.List;

//@XmlRootElement(name="assets")
 
public class AssetList {

	private List<Asset> asset;

	
	public List<Asset> getAsset() {
		return asset;
	}

	public void setAsset(List<Asset> asset) {
		this.asset = asset;
	}
}
