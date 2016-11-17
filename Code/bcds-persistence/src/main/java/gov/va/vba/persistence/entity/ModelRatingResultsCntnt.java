package gov.va.vba.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "MODEL_RATING_RESULTS_CNTNT", schema = "BCDSS")
public class ModelRatingResultsCntnt implements java.io.Serializable {

    private ModelRatingResultsCntntId id;
    private BigDecimal count;
    private ModelRatingResults modelRatingResults;
    private DDMContention contention;

    @EmbeddedId
    public ModelRatingResultsCntntId getId() {
        return this.id;
    }

    public void setId(ModelRatingResultsCntntId id) {
        this.id = id;
    }

    @Column(name = "COUNT")
    public BigDecimal getCount() {
        return this.count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @MapsId("processId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID", insertable = false, updatable = false)
    public ModelRatingResults getModelRatingResults() {
        return this.modelRatingResults;
    }

    public void setModelRatingResults(ModelRatingResults modelRatingResults) {
        this.modelRatingResults = modelRatingResults;
    }

    @MapsId("cntntId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNTNT_ID", insertable = false, updatable = false)
    public DDMContention getContention() {
        return contention;
    }

    public void setContention(DDMContention contention) {
        this.contention = contention;
    }

}
