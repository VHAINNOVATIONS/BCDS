package main.java.gov.va.vba.persistence.dao;

import java.util.Date;

public class Claim {
	
	private int Vet_ID;
	private String Vet_Name;
	private String Office;
	private int Claim_ID;
	private Date Claim_Date;
	private Date CEST_Date;
	private String Model;
	private String Contentions;
	private Date Last_Mod_Date;
	
	public Claim(){}
	
	public Claim (int vetId, String vetName, String office, int claimId, Date claimDate, Date cestDate, String model, String contensions, Date lastModDate) {
		this.Vet_ID = vetId;
		this.Vet_Name = vetName;
		this.Office = office;
		this.Claim_Date = claimDate;
		this.Claim_ID = claimId;
		this.CEST_Date = cestDate;
		this.Model = model;
		this.Contentions = contensions;
		this.Last_Mod_Date = lastModDate;
	}
	
	public int getVetId() {
		return Vet_ID;
	}
	
	public void setVetId(int id) {
		this.Vet_ID = id;
	}
	
	public String getVetName() {
		return Vet_Name;
	}
	
	public void setVetName(String name) {
		this.Vet_Name = name;
	}
	
	public String getOffice() {
		return Office;
	}
	
	public void setOffice(String office) {
		this.Office = office;
	}
	
	public int getClaimId() {
		return Claim_ID;
	}
	
	public void setClaimId(int id) {
		this.Claim_ID = id;
	}
	
	public Date getClaimDate() {
		return Claim_Date;
	}
	
	public void setClaimDate(Date date) {
		this.Claim_Date = date;
	}
	
	public Date getCESTDate() {
		return CEST_Date;
	}
	
	public void setCESTDate(Date date) {
		this.CEST_Date = date;
	}
	
	public String getModel() {
		return Model;
	}
	
	public void setModel(String model) {
		this.Model = model;
	}
	
	public String getContentions() {
		return Contentions;
	}
	
	public void setContentions(String contensions) {
		this.Contentions = contensions;
	}
	
	public Date getLastModDate() {
		return Last_Mod_Date;
	}
	
	public void setLastModDate(Date date) {
		this.Last_Mod_Date = date;
	}
}