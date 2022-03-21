package csulb.cecs323.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class authoring_entities {

    //Member Variables
    @Id
    @Column(nullable = false, length = 30)
    private String email;

    //This variable serves as the link to books
    @OneToMany(mappedBy = "authoringEntity")
    List<bookss> ListOfBookss = new ArrayList<>();

    @Column(length = 31)
    private String authoring_entity_type;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(length = 80)
    private String head_writer;

    @Column
    private int year_formed;

    //Constructors
    public authoring_entities(){}
    public authoring_entities(String initEmail, String initName, String initHeadWriter, int initYearFormed)   {
        this.email = initEmail;
        this.name = initName;
        this.head_writer = initHeadWriter;
        this.year_formed = initYearFormed;
    }//End of the constructor minus initial value for authoring_entity_type
    //    public authoring_entities(String initEmail, String initAuthoringEntityType, String initName, int initYearFormed)   {
//        this.email = initEmail;
//        this.authoring_entity_type = initAuthoringEntityType;
//        this.name = initName;
//        this.year_formed = initYearFormed;
//    }//End of the constructor minus initial value for head_writer
    public authoring_entities(String initEmail, String initName, int initYearFormed)   {
        this.email = initEmail;
        this.name = initName;
        this.year_formed = initYearFormed;
    }//End of the constructor minus initial value for authoring_entity_type, head_writer
    public authoring_entities(String initEmail, String initAuthoringEntityType, String initName, String initHeadWriter, int initYearFormed)   {
        this.email = initEmail;
        this.authoring_entity_type = initAuthoringEntityType;
        this.name = initName;
        this.head_writer = initHeadWriter;
        this.year_formed = initYearFormed;
    }//End of the overloaded constructor

    //Getters & Setters
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAuthoring_entity_type() {return authoring_entity_type;}
    public void setAuthoring_entity_type(String authoring_entity_type) {this.authoring_entity_type = authoring_entity_type;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getHead_writer() {return head_writer;}
    public void setHead_writer(String head_writer) {this.head_writer = head_writer;}
    public int getYear_formed() {return year_formed;}
    public void setYear_formed(int year_formed) {this.year_formed = year_formed;}

    //Other Methods
    @Override
    public String toString () {
        return "Name: " + this.getName() + "\n" +
                "Email: " + this.getEmail() + "\n" +
                "Type: " + this.getAuthoring_entity_type() + "\n" +
                "Head Writer: " + this.getHead_writer() + "\n" +
                "Year Formed: " + this.getYear_formed();
    }//End of the toString method

} // End of class AUTHORING_ENTITIES
