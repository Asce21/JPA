package csulb.cecs323.model;

import javax.persistence.*;


@Table(uniqueConstraints = {@UniqueConstraint(name = "bookss_uk_01", columnNames = {"title", "publishers_name"}),
        @UniqueConstraint(name = "bookss_uk_02", columnNames = {"title", "author_entity_name"})})
@Entity
public class bookss {

    //Member Variables
    @Id
    @Column(nullable = false, length = 17)
    private String isbn;

    @Column(nullable = false, length = 80)
    private String title;

    @Column
    private int authoring_entity_name ;

    //This variable serves as the link to authoring_entities
    @ManyToOne
    @JoinColumn(name = "author_entity_name",referencedColumnName = "email", nullable = false)
    private authoring_entities authoringEntity;

    //This variable serves as the link to publishers
    @ManyToOne
    @JoinColumn(name = "publishers_name", referencedColumnName = "name", nullable = false)
    private publishers bookPublisher;

    //Constructors
    public bookss()  {}
    public bookss(String initIsbn, String initTitle, int initAuthoring_entity_name, authoring_entities initAuthoringEntity, publishers initBookPublisher) {
        this.isbn = initIsbn;
        this.title = initTitle;
        this.authoring_entity_name = initAuthoring_entity_name;
        this.authoringEntity = initAuthoringEntity;
        this.bookPublisher = initBookPublisher;
    }//End of the overloaded constructor

    //Getters & Setters
    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public int getAuthoring_entity_name() {return authoring_entity_name;}
    public void setAuthoring_entity_name(int authoring_entity_name) {this.authoring_entity_name = authoring_entity_name;}
    public authoring_entities getAuthoringEntity() {return authoringEntity;}
    public void setAuthoringEntity(authoring_entities authoringEntity) {this.authoringEntity = authoringEntity;}
    public publishers getBookPublisher() {return bookPublisher;}
    public void setBookPublisher(publishers bookPublisher) {this.bookPublisher = bookPublisher;}

    //Other Methods
    @Override
    public String toString () {
        return "ISBN: " + this.getIsbn() + "\n" +
                "Book Title: " + this.getTitle() + "\n" +
                "Authoring Entity Email: " + this.getAuthoringEntity().getEmail() + "\n" +
                "Publisher: " + this.getBookPublisher().getName();
    }//End of the toString method

}//End of class bookss
