package csulb.cecs323.model;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;


@Table(uniqueConstraints = {@UniqueConstraint(name = "bookss_uk_01", columnNames = {"title", "publishers_name"}),
        @UniqueConstraint(name = "bookss_uk_02", columnNames = {"title", "author_entity_name"})})
@Entity
@NamedNativeQueries( value = {
        @NamedNativeQuery(name = "selectAllFromBookss",
                          query = "SELECT * FROM BOOKSS ORDER BY ISBN ",
                          resultClass = bookss.class ),
        @NamedNativeQuery(name = "countSpecificBook",
                          query = "SELECT COUNT(*) FROM BOOKSS WHERE ISBN = ? ",
                          resultClass = bookss.class ),
        @NamedNativeQuery(name = "selectSpecificBook",
                          query = "SELECT * " +
                                  "FROM BOOKSS " +
                                  "WHERE ISBN = ? ",
                        resultClass = bookss.class),
        @NamedNativeQuery(name = "deleteSpecificBook",
                query = "DELETE FROM BOOKSS WHERE ISBN = ? ",
                resultClass = bookss.class),
//        @NamedNativeQuery(name = "deleteSpecificBook",
//                          query = "DELETE FROM BOOKSS WHERE ISBN = ? ",
//                          resultClass = bookss.class),
        @NamedNativeQuery(name = "deleteAllFromBookss",
                          query = "DELETE FROM BOOKSS"),
        @NamedNativeQuery(name = "updateBook",
                          query = "UPDATE BOOKSS " +
                                  "SET AUTHOR_ENTITY_NAME = ? " +
                                  "WHERE ISBN = ?")
    }// End of the individual NamedNativeQueries
)// End of the NamedNativeQueries
public class bookss {

    //Member Variables
    @Id
    @Column(nullable = false, length = 17)
    private String isbn;

    @Column(nullable = false)
    private Integer year_published;

    @Column(nullable = false, length = 80)
    private String title;

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
    public bookss(String initIsbn, Integer initYearPublished, String initTitle, authoring_entities initAuthoring_entity_name, publishers initBookPublisher) {
        this.isbn = initIsbn;
        this.year_published = initYearPublished;
        this.title = initTitle;
        this.authoringEntity = initAuthoring_entity_name;
        this.bookPublisher = initBookPublisher;
    }//End of the overloaded constructor

    //Getters & Setters
    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}
    public Integer getYearPublished() {return year_published;}
    public void setYearPublished(Integer newYearPublished) {this.year_published = newYearPublished;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public authoring_entities getAuthoringEntity() {return authoringEntity;}
    public void setAuthoringEntity(authoring_entities authoringEntity) {this.authoringEntity = authoringEntity;}
    public publishers getBookPublisher() {return bookPublisher;}
    public void setBookPublisher(publishers bookPublisher) {this.bookPublisher = bookPublisher;}

    //Other Methods
    @Override
    public String toString () {
        return "ISBN: " + this.getIsbn() + "\n" +
                "Year Published: " + this.getYearPublished() + "\n" +
                "Book Title: " + this.getTitle() + "\n" +
                "Authoring Entity Email: " + this.getAuthoringEntity().getEmail() + "\n" +
                "Publisher: " + this.getBookPublisher().getName();
    }//End of the toString method

}//End of class bookss
