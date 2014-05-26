package com.cognizant.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

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
