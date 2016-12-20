package gov.va.vba.persistence.models.data;

import java.util.Date;

/**
 * Created by ProSphere User on 12/20/2016.
 */
public class DecisionDetails {

    private String decisionCode;
    private Date beginDate;
    private String percentNumber;

    public String getDecisionCode() {
        return decisionCode;
    }

    public void setDecisionCode(String decisionCode) {
        this.decisionCode = decisionCode;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getPercentNumber() {
        return percentNumber;
    }

    public void setPercentNumber(String percentNumber) {
        this.percentNumber = percentNumber;
    }

}
