package csulb.cecs323.model;
import javax.persistence.*;
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
    private Integer year_formed;

    //Constructors
    public authoring_entities(){}
    //Individual Author
    public authoring_entities(String initEmail, String initAuthoringEntityType, String initName)   {
        this.email = initEmail;
        this.authoring_entity_type = initAuthoringEntityType;
        this.name = initName;
        this.head_writer = null;
        this.year_formed = null;
    }//End of the overloaded constructor minus initial values for head_writer, year_formed
    //Ad Hoc Team
    public authoring_entities(String initEmail, String initName, String initAuthoringEntityType, Integer initYearFormed)   {
        this.email = initEmail;
        this.name = initName;
        this.authoring_entity_type = initAuthoringEntityType;
        this.head_writer = null;
        this.year_formed = initYearFormed;
    }//End of the overloaded constructor minus initial value for head_writer
    //Writing Group
    public authoring_entities(String initEmail, String initAuthoringEntityType, String initName, String initHeadWriter, Integer initYearFormed)   {
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
    public Integer getYear_formed() {return year_formed;}
    public void setYear_formed(Integer year_formed) {this.year_formed = year_formed;}

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
