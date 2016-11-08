package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "DDM_CTLG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DDMCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long categoryId;
	private String categoryDesc;

	@Id
	@Column(name="CTLG_ID")
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="CTLG_DESC")
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
}
