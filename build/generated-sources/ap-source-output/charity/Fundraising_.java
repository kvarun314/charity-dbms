package charity;

import charity.Campaigns;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-17T14:31:58")
@StaticMetamodel(Fundraising.class)
public class Fundraising_ { 

    public static volatile SingularAttribute<Fundraising, String> dueDate;
    public static volatile SingularAttribute<Fundraising, Short> completePercent;
    public static volatile SingularAttribute<Fundraising, String> description;
    public static volatile SingularAttribute<Fundraising, Campaigns> campaign;
    public static volatile SingularAttribute<Fundraising, Long> id;
    public static volatile SingularAttribute<Fundraising, String> title;
    public static volatile SingularAttribute<Fundraising, String> priority;
    public static volatile SingularAttribute<Fundraising, String> assignedTo;
    public static volatile SingularAttribute<Fundraising, String> startDate;
    public static volatile SingularAttribute<Fundraising, String> status;

}