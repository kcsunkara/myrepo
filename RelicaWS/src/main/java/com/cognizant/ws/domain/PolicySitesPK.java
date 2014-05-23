package com.cognizant.ws.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PolicySitesPK implements Serializable {
	
	@Column
	private Long policy_id;
	
	@Column
	private Long site_id;
	
	public PolicySitesPK() {
		
	}
	
	@Override
    public boolean equals(Object obj) {
		if(obj instanceof PolicySitesPK){
			PolicySitesPK policySitesPK = (PolicySitesPK) obj;
 
            if(!policySitesPK.getPolicy_id().equals(this.policy_id)){
                return false;
            }
 
            if(!policySitesPK.getSite_id().equals(this.getSite_id())){
                return false;
            }
            return true;
        }
		return false;
	}
	
	@Override
    public int hashCode() {
        return this.getPolicy_id().hashCode() + this.getSite_id().hashCode();
    }

	public Long getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(Long policy_id) {
		this.policy_id = policy_id;
	}

	public Long getSite_id() {
		return site_id;
	}

	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}

}
