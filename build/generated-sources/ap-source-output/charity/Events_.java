package charity;

import charity.Campaigns;
import charity.Donations;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-17T14:31:58")
@StaticMetamodel(Events.class)
public class Events_ { 

    public static volatile SingularAttribute<Events, String> owner;
    public static volatile SingularAttribute<Events, String> fundGoal;
    public static volatile CollectionAttribute<Events, Donations> donationsCollection;
    public static volatile SingularAttribute<Events, String> endDate;
    public static volatile SingularAttribute<Events, String> name;
    public static volatile SingularAttribute<Events, String> description;
    public static volatile SingularAttribute<Events, Long> id;
    public static volatile SingularAttribute<Events, String> startDate;
    public static volatile SingularAttribute<Events, Campaigns> relatedCampaign;
    public static volatile SingularAttribute<Events, String> status;

}