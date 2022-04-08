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
import com.mysql.cj.conf.ConnectionUrlParser;
import csulb.cecs323.model.*;
// import org.eclipse.persistence.jpa.jpql.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
   // Variable Declarations
   Scanner keyboard = new Scanner(System.in);
   Integer userChoice = -1, userChoice2, userYearFormed, count, userYearPublished, resultOfChoice;
   String userName, userEmail, userType, userHeadWriter, userAuthor, userTeam, userPhone, userISBN, userTitle, clearBuffer;
   Query query;

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
      authoringEntitiesList.add(new authoring_entities("tDymockp@gmail.com", "Writing Group", "The Dymock Poets",
              "Lascelles Abercrombie", 1915));
      authoringEntitiesList.add(new authoring_entities("tar@aol.com", "Writing Group", "The Algonquin Roundtable",
              "John Peter Toohey", 1919));
      authoringEntitiesList.add(new authoring_entities("soO@mail.me", "Writing Group", "Stratford-on-Odeon",
              "Sylvia Beach", 1919));
      authoringEntitiesList.add(new authoring_entities("the-factory@aol.com", "Writing Group", "The Factory",
              " Andy Warhol", 1962));
      authoringEntitiesList.add(new authoring_entities("N/A", "Writing Group", "The Socrates School",
              "Socrates", -400));
      authoringEntitiesList.add(new authoring_entities("the.february.house@yahoo.com", "Writing Group", "The February House",
              "W.H. Auden", 1940));
      authoringEntitiesList.add(new authoring_entities("stephen.king@gmail.com", "Individual Author", "Stephen King"));
      authoringEntitiesList.add(new authoring_entities("de@gmail.com", "Individual Author", "David Eddings"));
      authoringEntitiesList.add(new authoring_entities("leigh@gmail.com", "Individual Author", "Leigh Eddings"));
      authoringEntitiesList.add(new authoring_entities("ernestH@gmail.com", "Individual Author", "Ernest Hemingway"));
      authoringEntitiesList.add(new authoring_entities("jj@yahoo.com", "Individual Author", "James Joyce"));
      authoringEntitiesList.add(new authoring_entities("Ezra-Pound@yahoo.com", "Individual Author", "Ezra Pound"));
      authoringEntitiesList.add(new authoring_entities("Gertrude-Stein@mail.me", "Individual Author", "Gertrude Stein"));
      authoringEntitiesList.add(new authoring_entities("Fitzgerald@gmail.com", "Individual Author", "F. Scott Fitzgerald"));
      authoringEntitiesList.add(new authoring_entities("warhol@aol.com", "Individual Author", "Andy Warhol"));
      authoringEntitiesList.add(new authoring_entities("david.leigh@yahoo.com.com", "Ad Hoc Team", "David & Leigh Eddings",
              "David Eddings", 1990));
      authoringEntitiesList.add(new authoring_entities("harperlee@gmail.com", "Individual Author", "Harper Lee"));

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

      bookssList.add(new bookss("0345296370", 1983, "Pawn of Prophecy", authoringEntitiesList.get(12), publisherssList.get(0)));
      bookssList.add(new bookss("0345440781", 2001, "The Redemption of Althalus", authoringEntitiesList.get(19), publisherssList.get(0)));
      bookssList.add(new bookss("0684830426", 1991, "The Great Gatsby", authoringEntitiesList.get(18), publisherssList.get(2)));
      bookssList.add(new bookss("9781668002", 2022, "Fairy Tale", authoringEntitiesList.get(10), publisherssList.get(2)));
      bookssList.add(new bookss("0684803356", 1995, "For Whom the Bell Tolls", authoringEntitiesList.get(13), publisherssList.get(2)));
      bookssList.add(new bookss("0062561022", 2016, "Go Set a Watchman", authoringEntitiesList.get(20), publisherssList.get(0)));
      bookssList.add(new bookss("0060935464", 2002, "To Kill A Mockingbird", authoringEntitiesList.get(20), publisherssList.get(0)));
      bookssList.add(new bookss("0345300807", 1985, "castle of wizardry", authoringEntitiesList.get(12), publisherssList.get(0)));
      bookssList.add(new bookss("0345500938", 2007, "The Elenium: The Diamond Throne the Ruby Knight the Sapphire Rose", authoringEntitiesList.get(12), publisherssList.get(0)));
      bookssList.add(new bookss("1613823592", 1990, "Ulysses", authoringEntitiesList.get(14), publisherssList.get(3)));

      adHocTeamMembersList.add(new ad_hoc_teams_members(authoringEntitiesList.get(11), authoringEntitiesList.get(19)));
      adHocTeamMembersList.add(new ad_hoc_teams_members(authoringEntitiesList.get(12), authoringEntitiesList.get(19)));

      // Create the tables in the database.
      books.createEntity (authoringEntitiesList);
      books.createEntity (publisherssList);
      books.createEntity (bookssList);
      books.createEntity (adHocTeamMembersList);

      // Commit the changes so that the new data persists and is visible to other users.
      tx.commit();
      LOGGER.fine("End of Transaction");

      //Start of my new code
      books.mainMenu();

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

   public void welcomeMessage()   {
      System.out.println("\nWelcome to the Books JPA application.");
   }// End pf welcomeMessage member method

   public void mainMenu()  {
      //Method Variables
      userChoice =-1;

      do {
         //Display choices to the user
         welcomeMessage();
         System.out.println("1. Add a new object");
         System.out.println("2. List all the information about a specific Object:");
         System.out.println("3. Delete a Book");
                               // be sure to prompt for all the elements of a candidate key.
                              // Make sure that the book actually exists.
         System.out.println("4. Update a Book – Change the authoring entity for an existing book.");
         System.out.println("5. List the primary key of all the rows of");
                              // 1. Publishers
                              // 2. Books (show the title as well as the ISBN)
                              // 3. Authoring entities – and supply the type of authoring entity for each one as well.
         System.out.print("Please select the number of the option above: ");

         //Get the input from the user
         userChoice = keyboard.nextInt();

         // Process the user choice
         switch (userChoice)  {
            case 1:
               addObjectMenu();
               break;
            case 2:
               listInfoMenu();
               break;
            case 3:
               deleteBook();
               break;
            case 4:
               updateBook();
               break;
            case 5:
               listPrimaryKeys();
               break;
         }// End of the switch statement to process the choice

         if (userChoice < 1 || userChoice > 5)
         System.out.println("Invalid choice. Please select between 1 and 5");
      } while (userChoice < 1 || userChoice > 5);
   }// End of mainMenu member method

   public void addObjectMenu()   {
      //Method Variables
      userChoice =-1;

      do {
         // Display the choices
         System.out.println();
         System.out.println("Add Objects");
         System.out.println("1. Add a new Authoring Entity");
         System.out.println("2. Add a new Publisher");
         System.out.println("3. Add a new Book");
         System.out.print("Please select the number of the object to add above: ");

         //Get the input from the user
         userChoice = keyboard.nextInt();

         // Process the user choice
         switch (userChoice)  {
            case 1:
               addAuthoringEntitiesMenu();
               break;
            case 2:
               addPublisher();
               break;
            case 3:
               addBook();
               break;
         }// End of the switch statement to process the choice

         if (userChoice < 1 || userChoice > 3)
         System.out.println("Invalid choice. Please select between 1 and 3");
      } while (userChoice < 1 || userChoice > 3);
   }// End of addObjectMenu member method

   public void addAuthoringEntitiesMenu()   {
      //Method Variables
      userChoice =-1;

      do {
         // Display the choices
         System.out.println();
         System.out.println("Add Authoring Entity");
         System.out.println("1. Add a new Writing Group");
         System.out.println("2. Add a new Individual Author");
         System.out.println("3. Add a new Ad Hoc Team");
         System.out.println("4. Add an Individual Author to an existing Ad Hoc Team");
         System.out.print("Please select the number of the object to add above: ");

         //Get the input from the user
         userChoice = keyboard.nextInt();

         // Process the user choice
         switch (userChoice)  {
            case 1:
               addWritingGroup();
               break;
            case 2:
               addIndividualWriter();
               break;
            case 3:
               addAdHocTeam();
               break;
            case 4:
               addToTeam();
               break;
         }// End of the switch statement to process the choice

         if (userChoice < 1 || userChoice > 4)
         System.out.println("Invalid choice. Please select between 1 and 4");
      } while (userChoice < 1 || userChoice > 4);
   }// End of addObjectMenu member method

   public void addWritingGroup() {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<authoring_entities> aetmp = new ArrayList<>();
      //Clear the buffer
      clearBuffer = keyboard.nextLine();

      //Prompt the user for the columns of Writing Group
      System.out.println();
      System.out.println("Add Writing Group");
      System.out.print("Enter a name for the Writing Group: ");
      userName = keyboard.nextLine();
      System.out.print("Enter an email for the Writing Group: ");
      userEmail = keyboard.nextLine();
      userType = "Writing Group";
      System.out.print("Enter a year for when the Writing Group formed: ");
      userYearFormed = keyboard.nextInt();
      //Clear the buffer
      clearBuffer = keyboard.nextLine();
      System.out.print("Enter a head writer for the Writing Group: ");
      userHeadWriter = keyboard.nextLine();

      //Add the Weiring group to the database
      aetmp.add(new authoring_entities(userEmail, userType, userName, userHeadWriter, userYearFormed));
      books.createEntity(aetmp);
      tx.commit();
   }// End of addWritingGroup member method

   public void addIndividualWriter()   {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<authoring_entities> aetmp = new ArrayList<>();

      //Prompt the user for the columns of Individual Author
      // Clear the buffer
      clearBuffer = keyboard.nextLine();
      System.out.println();
      System.out.println("Add Individual Author");
      System.out.print("Enter a name for the Author: ");
      userName = keyboard.nextLine();
      System.out.print("Enter an email for the Author: ");
      userEmail = keyboard.nextLine();
      userType = "Individual Author";

      //Process user input into a row of an Individual Author
      aetmp.add(new authoring_entities(userEmail, userType, userName));
      books.createEntity(aetmp);
      tx.commit();
   }// End of addIndividualWriter member method

   public  void addAdHocTeam()   {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<authoring_entities> aetmp = new ArrayList<>();

      // Prompt the user for the columns of Writing Group
      // Clear the buffer
      clearBuffer = keyboard.nextLine();
      System.out.println();
      System.out.println("Add Ad Hoc Team");
      System.out.print("Enter a name for the Ad Hoc Team: ");
      userName = keyboard.nextLine();
      System.out.print("Enter an email for the Ad Hoc Team: ");
      userEmail = keyboard.nextLine();
      userType = "Ad Hoc Team";
      System.out.print("Enter a year for when the Ad Hoc Team formed: ");
      userYearFormed = keyboard.nextInt();

      // Add the Weiring group to the database
      aetmp.add(new authoring_entities(userEmail, userName, userType, userYearFormed));
      books.createEntity(aetmp);
      tx.commit();
   }// End of addIndividualWriter member method

   public  void addToTeam()   {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<authoring_entities> aeList, adHocTeamsList;
      List<ad_hoc_teams_members> adtmp = new ArrayList<>();

      List<String[]> aetmp;// = new ArrayList<>();
      List<String> ls;// = new ArrayList<>();
      List<Integer> intList;
      authoring_entities aeOne, aeTwo;

      // Display all possible authors
      System.out.println();
      System.out.println("Add To the Team");

      query = manager.createNamedQuery("selectAllIndividualAuthors");
      aeList = query.getResultList();

//      Query q = manager.createNativeQuery("SELECT EMAIL " +
//                                             "FROM AUTHORING_ENTITIES " +
//                                             "WHERE AUTHORING_ENTITY_TYPE = 'Individual Author' ");
//      ls = q.getResultList();

      // Let the user select the author to join the team
      for (int index = 0; index < aeList.size(); index++) {
         System.out.print(index + ". ");
         System.out.println("Author " + index + ": " + aeList.get(index).getName());
      }// End of the for loop

      System.out.print("Please select the index of an author to join the team: ");
      userChoice = keyboard.nextInt();
      userAuthor = aeList.get(userChoice).getEmail();
      //userAuthor = userAuthor.replaceAll("@", "\'@");

      // Display all teams
      query = manager.createNamedQuery("selectAllAdHocTeams");
      adHocTeamsList = query.getResultList();

//      q = manager.createNativeQuery("SELECT EMAIL " +
//                                       "FROM AUTHORING_ENTITIES " +
//                                       "WHERE AUTHORING_ENTITY_TYPE = 'Ad Hoc Team' ");
//      ls = q.getResultList();

      // Let the user select a team to join
      for (int index = 0; index < adHocTeamsList.size(); index++) {
         System.out.print("Team " + index + ": ");
         System.out.println(adHocTeamsList.get(index).getEmail());
      }// End of the for loop

      System.out.print("Please select the index of a team: ");
      userChoice2 = keyboard.nextInt();
//      userTeam = adHocTeamsList.get(userChoice).getEmail();

      // Get the author
//      q = manager.createNativeQuery("SELECT NAME " +
//                                       "FROM AUTHORING_ENTITIES " +
//                                       "WHERE EMAIL = '" + userAuthor + "'");
//      ls = q.getResultList();
//      userName = ls.get(0);
//      aeOne = new authoring_entities(userAuthor, "Individual Author", userName);
//      ls.clear();

//      aeOne = new authoring_entities(aetmp.get(0)[0], aetmp.get(0)[1], aetmp.get(0)[3]);
//      ls.clear();

      // Get the Team
//      q = manager.createNativeQuery("SELECT NAME " +
//                                       "FROM AUTHORING_ENTITIES " +
//                                       "WHERE EMAIL = '" + userTeam + "'");
//      ls = q.getResultList();
//      userName = ls.get(0);
//
//      q = manager.createNativeQuery("SELECT YEAR_FORMED " +
//                                       "FROM AUTHORING_ENTITIES " +
//                                       "WHERE EMAIL = '" + userTeam + "'");
//      intList = q.getResultList();
//      userYearFormed = intList.get(0);
//      aeTwo = new authoring_entities(userTeam, userName, "Ad Hoc Team", userYearFormed);



//      aeTwo = null;//new authoring_entities(aetmp.get(0), aetmp.get(1), aetmp.get(2));
//      ls.clear();

      // Add the author
      adtmp.add(new ad_hoc_teams_members(aeList.get(userChoice), adHocTeamsList.get(userChoice2)));
      books.createEntity(adtmp);
      tx.commit();
   }// End of addToTeam member method

   public void addPublisher() {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<publishers> pubTMP = new ArrayList<>();

      // Prompt the user for the columns of publisher
      System.out.println();
      // Clear the buffer
      clearBuffer = keyboard.nextLine();
      System.out.println("Add Publisher");
      System.out.print("Enter a name for the Publisher: ");
      userName = keyboard.nextLine();
      System.out.print("Enter an email for the Publisher: ");
      userEmail = keyboard.nextLine();
      System.out.print("Enter an phone number for the Publisher (XXX-XXX-XXXX): ");
      userPhone = keyboard.nextLine();


      // Add the publisher
      pubTMP.add(new publishers(userName, userEmail, userPhone));
      books.createEntity(pubTMP);
      tx.commit();
   }// End of addPublisher member method

   public void addBook() {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<Integer> countList;
      List<authoring_entities> authoringEntitiesList;
      List<publishers> publishersList;
      List<bookss> bookssList = new ArrayList<>();
      int isbnCount = 0;

      // Get the book info
      // Clear the buffer
      clearBuffer = keyboard.nextLine();
      System.out.println("Add a Book");
      System.out.println("Please enter the following about the book");
      System.out.print("The name of the book: ");
      userTitle = keyboard.nextLine();
      System.out.print("The 4-digit year that the book was published: ");
      userYearPublished = keyboard.nextInt();
      do {
         // Clear the buffer
         clearBuffer = keyboard.nextLine();
         System.out.print("The ISBN of the book (This value must be unique): ");
         userISBN = keyboard.nextLine();

         // Check that the ISBN is valid
//         query = manager.createNamedQuery("selectSpecificBook");
//         query.setParameter(1, userISBN);
         query = manager.createNamedQuery("selectAllFromBookss");
         bookssList = query.getResultList();

         isbnCount = 0;
         for (int index = 0; index < bookssList.size(); index++)  {
            if (bookssList.get(index).getIsbn().equals(userISBN))
               isbnCount++;
         }// End of loop to look for the given isbn

         bookssList.clear();

         if (isbnCount > 0)
            System.out.println("The ISBN must be unique. Please try again.");
         else
            System.out.println("Success! The ISBN is unique. You may continue");
      } while (isbnCount > 0);

      // Show a list of authors
      query = manager.createNamedQuery("selectAllAuthoringEntities");
      authoringEntitiesList = query.getResultList();

      for (int index = 0; index < authoringEntitiesList.size(); index++)   {
         System.out.println("Author " + index + ":\n" + authoringEntitiesList.get(index) + "\n");
      }// End of the loop to display the authoring entities

      // Prompt the user for the author
      System.out.print("Enter the index of the author of this book: ");
      userChoice = keyboard.nextInt();

      // Show a list of publishers
      query = manager.createNamedQuery("selectAllPublishers");
      publishersList = query.getResultList();

      for (int index = 0; index < publishersList.size(); index++)   {
         System.out.println("Publisher " + index + ":\n" + publishersList.get(index) + "\n");
      }// End of the loop to display the publisher's

      // Prompt the user for the publisher
      System.out.print("Enter the index of the Publisher of this book: ");
      userChoice2 = keyboard.nextInt();

      // Add the book to the database
      bookssList.add(new bookss(userISBN, userYearPublished, userTitle, authoringEntitiesList.get(userChoice), publishersList.get(userChoice2)));
      books.createEntity(bookssList);
      tx.commit();
   }// End of addBook member method

   public  void listInfoMenu()   {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<publishers> publishersList = new ArrayList<>();
      List<bookss> bookssList;// = new ArrayList<>();
      List<authoring_entities> wgList = new ArrayList<>();

      do {
         // Display the choices
         System.out.println();
         System.out.println("List Information");
         System.out.println("The following information is available to show");
         System.out.println("1. List information on the publishers");
         System.out.println("2. List information on the books");
         System.out.println("3. List information on the Writing Groups");
         System.out.print("Please enter the number of your choice: ");
         userChoice = keyboard.nextInt();

         //Process the choice
         switch (userChoice)  {
            case 1:  // Publishers
               query = manager.createNamedQuery("selectAllPublishers");
               publishersList = query.getResultList();

               for (int index = 0; index < publishersList.size(); index++)
                  System.out.println("Publisher " + index + ": \n" + publishersList.get(index) + "\n");
               break;
            case 2:  // Books
               query = manager.createNamedQuery("selectAllFromBookss");
               bookssList = query.getResultList();

               for (int index = 0; index < bookssList.size(); index++)
                  System.out.println("Book " + index + ": \n" + bookssList.get(index) + "\n");
               break;
            case 3:  // Writing Groups
               query = manager.createNamedQuery("selectAllWritingGroups");
               wgList = query.getResultList();

               for (int index = 0; index < wgList.size(); index++)
                  System.out.println("Writing Group  " + index + ": \n" + wgList.get(index) + "\n");
               break;
         }// End of the switch statement to process the choice

         if (userChoice < 1 || userChoice > 3)
         System.out.println("Invalid choice. Please select between 1 and 3");
      } while (userChoice < 1 || userChoice > 3);
   }// End of listInfoMenu member method

   public void deleteBook()   {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<bookss> bookssList, bookssCheckList;
      bookss bookToDelete;
      // Clear the buffer
      clearBuffer = keyboard.nextLine();

      // Show the user all the available books
      System.out.println();
      query = manager.createNamedQuery("selectAllFromBookss");
      bookssList = query.getResultList();

      System.out.println("Delete Book");
      System.out.println("Below is a list of all available books:\n");
      for (int index = 0; index < bookssList.size(); index++) {
         System.out.println("Book " + index + "\n" + bookssList.get(index) + "\n");
      }// End of the for loop that displays a list of books

      // Prompt the user for the ISBN of the book to delete
      System.out.print("Please select the ISBN of the book you want to delete: ");
      userISBN = keyboard.nextLine();

      // Check that the book with the given ISBN exists
      query = manager.createNamedQuery("selectSpecificBook");
      query.setParameter(1, userISBN);

      bookssList = query.getResultList();

      // If the book exists, delete it
      if (bookssList.size() != 0) {
//         query = manager.createNamedQuery("deleteSpecificBook");
//
//         // Commit the changes
//         query.setParameter(1, userISBN);
//         query.executeUpdate();
         for (int index = 0; index < bookssList.size(); index++)  {
            if (bookssList.get(index).getIsbn().equals(userISBN));
            bookToDelete = bookssList.get(index);

            manager.remove(bookToDelete);
         }// End of for loop to find the book to delete
         tx.commit();

         // Check that the book was deleted
         // Check that the book with the given ISBN was deleted
         query = manager.createNamedQuery("selectSpecificBook");
         query.setParameter(1, userISBN);
         bookssCheckList = query.getResultList();

         resultOfChoice = bookssCheckList.size();
         if (resultOfChoice == 0)
            System.out.println("Book deleted successfully!\n");

         // Show the updated list
         System.out.println("Below is the updated list of books: ");
         query = manager.createNamedQuery("selectAllFromBookss");
         bookssList = query.getResultList();

         for (int index = 0; index < bookssList.size(); index++)   {
            System.out.println("Book " + index + ":\n" + bookssList.get(index) + "\n");
         }// End of the loop to display the bookss
      }// End of the if statement to delete the book
   }// End of deleteBook member method

   public void updateBook  () {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<bookss> bookssList = null, toAdd = new ArrayList<>();
      List<authoring_entities> aeList;
      authoring_entities newAuthor;
      bookss pastBook = null, futureBook;
      boolean bookExists = false;
      bookss bookToDelete;

      String isbn, title, publisher, author;
      Integer yearPublished;
      List<String> isbnList, titleList, tempList;
      List<Integer> resultList, yearList, tempIntList;

      // Show the user all the available books
      System.out.println("\n");
      // Clear the buffer
      clearBuffer = keyboard.nextLine();
      System.out.println("Update Book");
      System.out.println("Below is a list of all available books:\n");
      query = manager.createNamedQuery("selectAllFromBookss");
      bookssList = query.getResultList();
      for (int index = 0; index < bookssList.size(); index++) {
         System.out.println("Book " + index + "\n" + bookssList.get(index) + "\n");
      }// End of the for loop that displays a list of books

      // Prompt the user for the ISBN of the book to update
      System.out.print("Please select the ISBN of the book you want to update: ");
      userISBN = keyboard.nextLine();

      // Show the user all the available authors
      System.out.println("Below is a list of all available authors:\n");
      query = manager.createNamedQuery("selectAllAuthoringEntities");
      aeList = query.getResultList();
      for (int index = 0; index < aeList.size(); index++)  {
         System.out.println("Author " + index + ":\n" + aeList.get(index) + "\n");
      }// End of the for loop that displays a list of authors
      System.out.print("Please select the index of the new author of this book: ");
      userChoice = keyboard.nextInt();
      newAuthor = aeList.get(userChoice);

      /// Check that the book with the given ISBN exists
      for (int index = 0; index < bookssList.size(); index++)  {
         if (bookssList.get(index).getIsbn().equals(userISBN)) {
            bookExists = true;
            pastBook = bookssList.get(index);
            pastBook.setAuthoringEntity(newAuthor);
            futureBook = pastBook;
            manager.remove(pastBook);
            if (toAdd != null && futureBook != null)  {
               toAdd.add(futureBook);
               books.createEntity(toAdd);
               tx.commit();
            }// End of the if statement to add the book
         }// End of if statement that runs iff the book is found
      }// End of loop to find the book with the given ISBN

      // Show the updated book info to the user
      System.out.println("The updated book:");
      query = manager.createNamedQuery("selectSpecificBook");
      query.setParameter(1, userISBN);
      bookssList = query.getResultList();

      // Details of the book
      System.out.println("New book details\n" + bookssList.get(0));
   }// End of updateBook member method

   public void listPrimaryKeys() {
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      // Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<String> primaryKeysList;// = new ArrayList<>();
      List<ConnectionUrlParser.Pair<String, String>> primaryKeyPlusList;// = new ArrayList<>();
      List<publishers> publisherList;
      List<bookss> bookssList;
      List<authoring_entities> aeList;

      do {
         System.out.println();
         System.out.println("List of the Primary Keys");
         System.out.println("1. Publishers");
         System.out.println("2. Boooks");
         System.out.println("3. Authoring Entities");
         System.out.print("Please select the number of the table you want to view Primary keys of: ");
         userChoice = keyboard.nextInt();

         // Process the choice
         switch (userChoice)  {
            case 1:  // Publisherd
               System.out.println("Primary Key of Publishers - Name");
               query = manager.createNamedQuery("selectAllPublishers");
               publisherList = query.getResultList();
               for (int index = 0; index < publisherList.size(); index++)  {
                  System.out.println("Record " + index + ": " + publisherList.get(index).getName());
               }// End of the for loop that displays a list of publishers
               break;
            case 2:  // Books (show the title as well as the ISBN)
               System.out.println("Primary Key of bools - ISBN + Title");
               query = manager.createNamedQuery("selectAllFromBookss");
               bookssList = query.getResultList();
               for (int index = 0; index < bookssList.size(); index++)  {
                  System.out.println("Record " + index + ":\nISBN: " +  bookssList.get(index).getIsbn() + "\nTitle: " + bookssList.get(index).getTitle() + "\n");
               }// End of the for loop that displays a list of books
               break;
            case 3:  // Authoring entities – and supply the type of authoring entity for each one as well.
               System.out.println("Primary Key of Authoring Entities - Email + Type");
               query = manager.createNamedQuery("selectAllAuthoringEntities");
               aeList = query.getResultList();
               for (int index = 0; index < aeList.size(); index++)  {
                  System.out.println("Record " + index + "\nEmail: " + aeList.get(index).getEmail() + "\nType: " +
                                       aeList.get(index).getAuthoring_entity_type() + "\n");
               }// End of the for loop that displays a list of authoring_entities
               break;
         }// End of the switch statement to process the choice

         if (userChoice < 1 || userChoice > 3)
            System.out.println("Invalid choice. Please select between 1 and 3");
      } while(userChoice < 1 || userChoice > 3);

   }// End of listPrimaryKeys member method
} // End of Books class
