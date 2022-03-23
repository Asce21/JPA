package csulb.cecs323.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class publishers {

    //Member Variables
    @Id
    @Column(nullable = false, length = 80)
    private String name;

    //This variable serves as the link to books
//    @OneToMany(mappedBy = "bookPublisher")
//    List<books> ListOfBooks = new ArrayList<>();

    @Column(unique = true, nullable = false, length = 80)
    private String email;

    @Column(unique = true, nullable = false, length = 24)
    private String phone;

    //Constructors
    public publishers() {}
    public publishers(String initName, String initEmail, String initPhone) {
        this.name = initName;
        this.email = initEmail;
        this.phone = initPhone;
    }//End of the overloaded constructor

    //Getters & Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    //Other Methods
    @Override
    public String toString () {
        return "Publidher's name: " + this.getName() + "\n" +
                "Publidher's email: " + this.getEmail() + "\n" +
                "Publidher's phone number: " + this.phone;
    }//End of the toString method

}//End of class publishers
