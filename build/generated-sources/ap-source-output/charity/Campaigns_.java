package charity;

import charity.Donations;
import charity.Events;
import charity.Fundraising;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-17T14:31:58")
@StaticMetamodel(Campaigns.class)
public class Campaigns_ { 

    public static volatile SingularAttribute<Campaigns, String> owner;
    public static volatile SingularAttribute<Campaigns, String> fundGoal;
    public static volatile CollectionAttribute<Campaigns, Donations> donationsCollection;
    public static volatile CollectionAttribute<Campaigns, Fundraising> fundraisingCollection;
    public static volatile SingularAttribute<Campaigns, String> name;
    public static volatile CollectionAttribute<Campaigns, Events> eventsCollection;
    public static volatile SingularAttribute<Campaigns, Long> id;
    public static volatile SingularAttribute<Campaigns, String> launchDate;
    public static volatile SingularAttribute<Campaigns, String> deadline;
    public static volatile SingularAttribute<Campaigns, String> status;

}