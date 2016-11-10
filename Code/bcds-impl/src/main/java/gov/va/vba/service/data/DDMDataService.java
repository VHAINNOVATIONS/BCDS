package gov.va.vba.service.data;

import gov.va.vba.domain.Claim;
import gov.va.vba.persistence.entity.DDMModelPattern;
import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.repository.DDMModelPatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
 
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Service
public class DDMDataService extends AbsDataService<gov.va.vba.persistence.entity.DDMModelPattern, DDMModelPattern> {
    private static SessionFactory factory;

    public static void main(String[] args)  {
    	try{
    		factory = new Configuration().configure().buildSessionFactory();
    	} catch (Throwable ex) {
    		System.err.println("Failed to create sessionFactory object." + ex);
    		throw new ExceptionInInitializerError(ex);
    	}
    }

    // DDMDataService results = new DDMDataService();

    //@Autowired
    DDMModelPatternRepository ddmModelPatternRepository;
    
    //@Autowired
    ModelRatingResults modelRatingResults;
    
    public DDMDataService() {
        this.setClasses(gov.va.vba.persistence.entity.DDMModelPattern.class, DDMModelPattern.class);
    }

    public List<DDMModelPattern> matchPatternId(String modelType, Long claimantAge, Long claimCount, Long contentionCount, Long priorCDD, Long CDDAge) {
        List<gov.va.vba.persistence.entity.DDMModelPattern> result = ddmModelPatternRepository.findPatternId(modelType, claimantAge, claimCount, contentionCount, priorCDD, CDDAge);
        List<DDMModelPattern> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public void recordPattern(Long processId, Long patternId) {
        Session session = factory.openSession();
        Transaction tx = null;

        try{
           tx = session.beginTransaction();
           ModelRatingResults modelRatingResults =  (ModelRatingResults)session.get(ModelRatingResults.class, processId); 
           modelRatingResults.setPatternId(patternId);
           session.update(modelRatingResults);
           tx.commit();
        } catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        } finally {
           session.close(); 
        }
     }
}
