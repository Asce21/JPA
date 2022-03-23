/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A simple application to demonstrate how to persist an object in JPA.
 * <p>
 * This is for demonstration and educational purposes only.
 * </p>
 * <p>
 *     Originally provided by Dr. Alvaro Monge of CSULB, and subsequently modified by Dave Brown.
 * </p>
 */
public class Books {
   /**
    * You will likely need the entityManager in a great many functions throughout your application.
    * Rather than make this a global variable, we will make it an instance variable within the Books
    * class, and create an instance of Books in the main.
    */
   private EntityManager entityManager;

   /**
    * The Logger can easily be configured to log to a file, rather than, or in addition to, the console.
    * We use it because it is easy to control how much or how little logging gets done without having to
    * go through the application and comment out/uncomment code and run the risk of introducing a bug.
    * Here also, we want to make sure that the one Logger instance is readily available throughout the
    * application, without resorting to creating a global variable.
    */
   private static final Logger LOGGER = Logger.getLogger(Books.class.getName());

   /**
    * The constructor for the Books class.  All that it does is stash the provided EntityManager
    * for use later in the application.
    * @param manager    The EntityManager that we will use.
    */
   public Books(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);


      // Any changes to the database need to be done within a transaction.
      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();
      // List of owners that I want to persist.  I could just as easily done this with the seed-data.sql
      List <authoring_entities> authoringEntitiesList = new ArrayList<>();
      List <publishers> publisherssList = new ArrayList<>();
      List <bookss> bookssList = new ArrayList<>();
      List <ad_hoc_teams_members> adHocTeamMembersList = new ArrayList<>();

      // Load up my Lists with the Entities that I want to persist.  Note, this does not put them into the database.
      authoringEntitiesList.add(new authoring_entities("bg@gmail.com", "Writing Group", "Bloomsbury Group",
              "Virginia Wool", 1950));
      authoringEntitiesList.add(new authoring_entities("dill.pickle@gmail.com", "Writing Group", "Dill Pickle Club",
              "Sherwood Anderson", 1917));
      authoringEntitiesList.add(new authoring_entities("los-contemp@yahoo.com", "Writing Group", "Los Contemporáneos",
              " José Gorostiza", 1920));
      authoringEntitiesList.add(new authoring_entities("the.inlings@yahoo.com", "Writing Group", "The Inklings",
              "J. R. R. Tolkien", 1934));
      authoringEntitiesList.add(new authoring_entities("tDymockp@gmail.com", "Writing Group", "The Dymock Poets\n",
              "Lascelles Abercrombie", 1915));
      authoringEntitiesList.add(new authoring_entities("tar@aol.com", "Writing Group", "The Algonquin Roundtable\n",
              "John Peter Toohey", 1919));
      authoringEntitiesList.add(new authoring_entities("soO@mail.me", "Writing Group", "Stratford-on-Odeon",
              "Sylvia Beach", 1919));
      authoringEntitiesList.add(new authoring_entities("the-factory@aol.com", "Writing Group", "The Factory",
              " Andy Warhol", 1962));
      authoringEntitiesList.add(new authoring_entities("N/A", "Writing Group", "The Socrates School",
              "Socrates", -400));
      authoringEntitiesList.add(new authoring_entities("the.february.house@yahoo.com", "Writing Group", "The February House",
              "W.H. Auden", 1940));
      authoringEntitiesList.add(new authoring_entities("stephen.king@gmail.com", "Stephen King", 1976));
      authoringEntitiesList.add(new authoring_entities("de@gmail.com", "David Eddings", 1931));
      authoringEntitiesList.add(new authoring_entities("leigh@gmail.com", "Leigh Eddings", 1937));
      authoringEntitiesList.add(new authoring_entities("ernestH@gmail.com", "Ernest Hemingway", 1899));
      authoringEntitiesList.add(new authoring_entities("jj@yahoo.com", "James Joyce", 1882));
      authoringEntitiesList.add(new authoring_entities("Ezra-Pound@yahoo.com", "Ezra Pound", 1885));
      authoringEntitiesList.add(new authoring_entities("Gertrude-Stein@mail.me", "Gertrude Stein", 1874));
      authoringEntitiesList.add(new authoring_entities("Fitzgerald@gmail.com", "F. Scott Fitzgerald", 1896));
      authoringEntitiesList.add(new authoring_entities("warhol@aol.com", "Andy Warhol", 1928));
      authoringEntitiesList.add(new authoring_entities("david.leigh@yahoo.com.com", "Ad Hoc Team", "David & Leigh Eddings",
              "Virginia Wool", 1950));
      authoringEntitiesList.add(new authoring_entities("harperlee@gmail.com", "Harper Lee", 1926));

      publisherssList.add(new publishers("Spectrum Literary Agency, Inc.", "general@spectrumliteraryagency.com", "212-362-4323"));
      publisherssList.add(new publishers("Random House Publishing Group", "main@randomhousebooks.com", "800-726-0600"));
      publisherssList.add(new publishers("scribner", "me@scribner.com", "212-751-2600"));
      publisherssList.add(new publishers("Simon & Brown", "N/A", "N/A"));
      publisherssList.add(new publishers("Hachette Book Group", "intake@hachettebookgroup.com", "212-546-7861"));
      publisherssList.add(new publishers("HarperCollins", "mail@harpercollins.com", "800-242-7737"));
      publisherssList.add(new publishers("Macmillan Publishers", "press.inquiries@macmillan.com", "505-438-6497"));
      publisherssList.add(new publishers("Penguin-Ransom House", "generalqueries@penguinrandomhouse.sg", "800-733-3000"));
      publisherssList.add(new publishers("Simon & Schuster", "inquiries@simonandschuster.com", "212-698-7000"));
      publisherssList.add(new publishers("TCK Publishing", "tck@tck-publishing.com", "164-543-4545"));

      bookssList.add(new bookss("0345296370", "Pawn of Prophecy", authoringEntitiesList.get(12), publisherssList.get(0)));
      bookssList.add(new bookss("0345440781", "The Redemption of Althalus", authoringEntitiesList.get(19), publisherssList.get(0)));
      bookssList.add(new bookss("0684830426", "The Great Gatsby", authoringEntitiesList.get(18), publisherssList.get(2)));
      bookssList.add(new bookss("9781668002179", "Fairy Tale", authoringEntitiesList.get(10), publisherssList.get(2)));
      bookssList.add(new bookss("", "For Whom the Bell Tolls", authoringEntitiesList.get(13), publisherssList.get(2)));
      bookssList.add(new bookss("0486447162", "0060935464", authoringEntitiesList.get(17), publisherssList.get(0)));
      bookssList.add(new bookss("0060935464", "To Kill A Mockingbird", authoringEntitiesList.get(20), publisherssList.get(0)));
      bookssList.add(new bookss("0345300807", "castle of wizardry", authoringEntitiesList.get(12), publisherssList.get(0)));
      bookssList.add(new bookss("0345500938", "Random House Publishing Group", authoringEntitiesList.get(12), publisherssList.get(0)));
      bookssList.add(new bookss("1613823592", "Ulysses", authoringEntitiesList.get(14), publisherssList.get(3)));

      adHocTeamMembersList.add(new ad_hoc_teams_members(authoringEntitiesList.get(11), authoringEntitiesList.get(19)));
      adHocTeamMembersList.add(new ad_hoc_teams_members(authoringEntitiesList.get(12), authoringEntitiesList.get(19)));

      // Create the list of owners in the database.
      books.createEntity (authoringEntitiesList);
      books.createEntity (publisherssList);
      books.createEntity (bookssList);
      books.createEntity (adHocTeamMembersList);

      // Commit the changes so that the new data persists and is visible to other users.
      tx.commit();
      LOGGER.fine("End of Transaction");

   } // End of the main method

   /**
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the Books entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

//      // The auto generated ID (if present) is not passed in to the constructor since JPA will
//      // generate a value.  So the previous for loop will not show a value for the ID.  But
//      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
//      for (E next : entities) {
//         LOGGER.info("Persisted object after flush (non-null id): " + next);
//      }
   } // End of createEntity member method


} // End of Books class
