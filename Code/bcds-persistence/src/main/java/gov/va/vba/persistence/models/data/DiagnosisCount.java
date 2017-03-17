package gov.va.vba.persistence.models.data;

/**
 * Created by ProSphere User on 12/16/2016.
 */
public class DiagnosisCount {

    private String decisionCode;
    private int count;

    public DiagnosisCount() {}

    public DiagnosisCount(String decisionCode, int count) {
        this.decisionCode = decisionCode;
        this.count = count;
    }

    public String getDecisionCode() {
        return decisionCode;
    }

    public void setDecisionCode(String decisionCode) {
        this.decisionCode = decisionCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
