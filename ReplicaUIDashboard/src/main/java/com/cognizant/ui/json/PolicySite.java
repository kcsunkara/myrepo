package com.cognizant.ui.json;

public class PolicySite {
	private int number_copies;
	private CompositeID compositId;
	private Site site;

	public int getNumber_copies() {
		return number_copies;
	}

	public void setNumber_copies(int number_copies) {
		this.number_copies = number_copies;
	}

	public CompositeID getCompositId() {
		return compositId;
	}

	public void setCompositId(CompositeID compositId) {
		this.compositId = compositId;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Override
	public String toString() {
		String policySite = "number_copies:::" + getNumber_copies()
				+ "\n compositId:::"
				+ getCompositId().toString() + "\n Site::" + getSite().toString();
		return policySite;
	}

}
