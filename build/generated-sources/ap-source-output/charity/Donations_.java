package charity;

import charity.Campaigns;
import charity.Events;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-17T14:31:58")
@StaticMetamodel(Donations.class)
public class Donations_ { 

    public static volatile SingularAttribute<Donations, String> owner;
    public static volatile SingularAttribute<Donations, Long> amount;
    public static volatile SingularAttribute<Donations, String> contributor;
    public static volatile SingularAttribute<Donations, String> paid;
    public static volatile SingularAttribute<Donations, String> paymentMethod;
    public static volatile SingularAttribute<Donations, Campaigns> campaign;
    public static volatile SingularAttribute<Donations, Long> id;
    public static volatile SingularAttribute<Donations, String> paymentDate;
    public static volatile SingularAttribute<Donations, Events> event;
    public static volatile SingularAttribute<Donations, String> pledgeDate;

}