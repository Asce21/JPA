package csulb.cecs323.model;
import javax.persistence.*;
import java.net.Authenticator;

@Entity
@IdClass(ad_hoc_teams_members_id.class)
public class ad_hoc_teams_members {

    //Member Variables
    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "email", nullable = false)
//    @Column(name = "individual_authors_email")
    private authoring_entities individual_authors_email;

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "email", nullable = false)
//    @Column(name = "ad_hoc_teams_email")
    private authoring_entities ad_hoc_teams_email;

    //Constructors
    public ad_hoc_teams_members() {}
    public ad_hoc_teams_members(authoring_entities initIndividualAuthorsEmail, authoring_entities initAdHocTeamsEmail) {
        this.individual_authors_email = initIndividualAuthorsEmail;
        this.ad_hoc_teams_email = initAdHocTeamsEmail;
    }//End of the overloaded constructor

    //Getters & Setters
    public authoring_entities getIndividualAuthorsEmail() {return individual_authors_email;}
    public void setIndividualAuthorsEmail(authoring_entities newIndividualAuthorsEmail) {this.individual_authors_email = newIndividualAuthorsEmail;}
    public authoring_entities getAdHocTeamsEmail() {return ad_hoc_teams_email;}
    public void setAdHocTeamsEmail(authoring_entities newAdHocTeamsEmail) {this.ad_hoc_teams_email = newAdHocTeamsEmail;}

    //Other Methods
    @Override
    public String toString() {
        return "Individual Email: "+ this.getIndividualAuthorsEmail() + "\n" +
                "Ad Hoc Team's Email: " + this.getAdHocTeamsEmail();
    }//End of the toString method
}//End of class ad_hoc_teams_members
