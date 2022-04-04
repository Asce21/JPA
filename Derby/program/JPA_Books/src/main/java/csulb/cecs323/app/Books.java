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
   Integer userChoice = -1, userYearFormed, count, userYearPublished;
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
      bookssList.add(new bookss("0062561022", 2016, "Go Set a Watchman\n", authoringEntitiesList.get(20), publisherssList.get(0)));
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
      List<ad_hoc_teams_members> adtmp = new ArrayList<>();
      List<String[]> aetmp;// = new ArrayList<>();
      List<String> ls;// = new ArrayList<>();
      List<Integer> intList;
      authoring_entities aeOne, aeTwo;

      // Display all possible authors
      System.out.println();
      System.out.println("Add To the Team");
      Query q = manager.createNativeQuery("SELECT EMAIL " +
                                             "FROM AUTHORING_ENTITIES " +
                                             "WHERE AUTHORING_ENTITY_TYPE = 'Individual Author' ");
      ls = q.getResultList();

      // Let the user select the author to join the team
      for (int index = 0; index < ls.size(); index++) {
         System.out.print(index + ". ");
         System.out.println(ls.get(index));
      }// End of the for loop

      System.out.print("Please select the index of an author to join the team: ");
      userChoice = keyboard.nextInt();
      userAuthor = ls.get(userChoice);
      //userAuthor = userAuthor.replaceAll("@", "\'@");

      // Display all teams
      q = manager.createNativeQuery("SELECT EMAIL " +
                                       "FROM AUTHORING_ENTITIES " +
                                       "WHERE AUTHORING_ENTITY_TYPE = 'Ad Hoc Team' ");
      ls = q.getResultList();

      // Let the user select a team to join
      for (int index = 0; index < ls.size(); index++) {
         System.out.print(index + ". ");
         System.out.println(ls.get(index));
      }// End of the for loop

      System.out.print("Please select the index of a team: ");
      userChoice = keyboard.nextInt();
      userTeam = ls.get(userChoice);
      //userTeam = userTeam.replaceAll("@", "\'@");

      // Get the author
      q = manager.createNativeQuery("SELECT NAME " +
                                       "FROM AUTHORING_ENTITIES " +
                                       "WHERE EMAIL = '" + userAuthor + "'");
      ls = q.getResultList();
      userName = ls.get(0);
      aeOne = new authoring_entities(userAuthor, "Individual Author", userName);
      ls.clear();

//      aeOne = new authoring_entities(aetmp.get(0)[0], aetmp.get(0)[1], aetmp.get(0)[3]);
//      ls.clear();

      // Get the Team
      q = manager.createNativeQuery("SELECT NAME " +
                                       "FROM AUTHORING_ENTITIES " +
                                       "WHERE EMAIL = '" + userTeam + "'");
      ls = q.getResultList();
      userName = ls.get(0);

      q = manager.createNativeQuery("SELECT YEAR_FORMED " +
                                       "FROM AUTHORING_ENTITIES " +
                                       "WHERE EMAIL = '" + userTeam + "'");
      intList = q.getResultList();
      userYearFormed = intList.get(0);
      aeTwo = new authoring_entities(userTeam, userName, "Ad Hoc Team", userYearFormed);



//      aeTwo = null;//new authoring_entities(aetmp.get(0), aetmp.get(1), aetmp.get(2));
//      ls.clear();

      // Add the author
      adtmp.add(new ad_hoc_teams_members(aeOne, aeTwo));
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
      authoring_entities authoringEntity;
      publishers publisher;
      List<bookss> bookssList = new ArrayList<>();
      List<authoring_entities> authoringList;// = new ArrayList<>();
      List<publishers> publishersList;// = new ArrayList<>();
      List<String> namesList;
      List<Integer> intList;
      EntityTransaction tx = manager.getTransaction();
      tx.begin();

     do {
        // Clear the buffer
        clearBuffer = keyboard.nextLine();
        // Prompt the user for the columns of book (ISBN, title)
        System.out.println();
        System.out.println("Add Book");
        System.out.print("Please enter the ISBN of the book: ");
        userISBN = keyboard.nextLine();
        System.out.print("Please enter the 4 digit year that the book was publishe: ");
        userYearPublished = keyboard.nextInt();
        // Clear the buffer
        clearBuffer = keyboard.nextLine();
        System.out.print("Please enter the title of the book: ");
        userTitle = keyboard.nextLine();

        // Check that the ISBN is unique
        query = manager.createNativeQuery("SELECT COUNT(*) " +
                                             "FROM BOOKSS " +
                                             "WHERE ISBN = " + userISBN + " ");
        count = query.getFirstResult();

        if (count > 0)
           System.out.println("ISBN must be unique. Please enter a unique ISBN.\n");
        else
           System.out.println("The ISBN is unique! Please continue.\n");
     } while (count > 0);

      // Show the available authors to the user
      query = manager.createNativeQuery("SELECT NAME " +
                                           "FROM AUTHORING_ENTITIES " +
                                           "ORDER BY EMAIL ");

      namesList = query.getResultList();

      for (int index = 0; index < namesList.size(); index++)  {
         System.out.println(index + "." + namesList.get(index));
      }// End of the for loop to print the available author's email to the screen

      // Prompt the user for the author of the book
      System.out.print("Please select the number of the author for this book: ");
      userChoice = keyboard.nextInt();
      userName = namesList.get(userChoice);  // Get the name of the authoring entity

      // Get the email of the authoring entity
      query = manager.createNativeQuery("SELECT EMAIL " +
                                           "FROM AUTHORING_ENTITIES " +
                                           "ORDER BY EMAIL ");

      namesList = query.getResultList();
      userEmail = namesList.get(userChoice);

      // Get the type of the authoring entity
      query = manager.createNativeQuery("SELECT AUTHORING_ENTITY_TYPE " +
                                           "FROM AUTHORING_ENTITIES " +
                                           "ORDER BY EMAIL ");
      namesList = query.getResultList();
      userType = namesList.get(userChoice);

      // Get the head writer of the authoring entity
      query = manager.createNativeQuery("SELECT HEAD_WRITER " +
                                           "FROM AUTHORING_ENTITIES " +
                                           "ORDER BY EMAIL ");
      namesList = query.getResultList();
      userHeadWriter = namesList.get(userChoice);

      // Get the year formed of the authoring entity
      query = manager.createNativeQuery("SELECT YEAR_FORMED " +
                                           "FROM AUTHORING_ENTITIES " +
                                           "ORDER BY EMAIL ");
      intList = query.getResultList();
      userYearFormed = intList.get(userChoice);

      authoringEntity = new authoring_entities(userEmail, userType, userName, userHeadWriter, userYearFormed);

      // Show the available publisher to the user
      query = manager.createNativeQuery("SELECT NAME " +
                                           "FROM PUBLISHERS " +
                                           "ORDER BY NAME ");

      namesList = query.getResultList();

      for (int index = 0; index < namesList.size(); index++)  {
         System.out.println(index + "." + namesList.get(index));
      }// End of the for loop to print the available publishers to the screen

      // Prompt the user for the publisher of the book
      System.out.print("Please select the number of the publisher for this book: ");
      userChoice = keyboard.nextInt();
      userName = namesList.get(userChoice);

      query = manager.createNativeQuery("SELECT EMAIL " +
                                           "FROM PUBLISHERS " +
                                           "ORDER BY NAME ");
      namesList = query.getResultList();
      userEmail = namesList.get(userChoice);

      query = manager.createNativeQuery("SELECT PHONE " +
                                           "FROM PUBLISHERS " +
                                           "ORDER BY NAME ");
      namesList = query.getResultList();
      userPhone = namesList.get(userChoice);

      publisher = new publishers(userName, userEmail, userPhone);

      // Add the book to the database
      bookssList.add(new bookss(userISBN, userYearPublished, userTitle, authoringEntity, publisher));
      books.createEntity(bookssList);
      tx.commit();
   }// End of addBook member method

   public  void listInfoMenu()   {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      // Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<publishers> publishersList = new ArrayList<>();
      List<bookss> bookssList;// = new ArrayList<>();
      List<authoring_entities> wgList = new ArrayList<>();
      List<String> namesList = new ArrayList<>();
      List<String> emailsList, phonesList, isbnList, titleList, authorsList, hwList;
      List<Integer> yearList, yearFormedList;

      do {
         // Display the choices
         System.out.println();
         System.out.println("The following information is available to show");
         System.out.println("1. List information on the publishers");
         System.out.println("2. List information on the books");
         System.out.println("3. List information on the Writing Groups");
         System.out.print("Please enter the number of your choice: ");
         userChoice = keyboard.nextInt();

         //Process the choice
         switch (userChoice)  {
            case 1:  // Publishers
               query = manager.createNativeQuery("SELECT NAME " +
                                                   "FROM PUBLISHERS " +
                                                   "ORDER BY NAME ");
               namesList = query.getResultList();

               query = manager.createNativeQuery("SELECT EMAIL " +
                                                    "FROM PUBLISHERS " +
                                                    "ORDER BY NAME ");
               emailsList = query.getResultList();

               query = manager.createNativeQuery("SELECT PHONE " +
                                                    "FROM PUBLISHERS " +
                                                    "ORDER BY NAME ");
               phonesList = query.getResultList();

               for (int index = 0; index < namesList.size(); index++)
                  publishersList.add(new publishers(namesList.get(index), emailsList.get(index), phonesList.get(index)));

               for (int index = 0; index < publishersList.size(); index++)
                  System.out.println("Record " + index + ": \n" + publishersList.get(index));
               break;
            case 2:  // Books
               query = manager.createNativeQuery("SELECT ISBN " +
                                                   "FROM BOOKSS " +
                                                   "ORDER BY ISBN ");
               isbnList = query.getResultList();
               query = manager.createNativeQuery("SELECT YEAR_PUBLISHED " +
                                                   "FROM BOOKSS " +
                                                   "ORDER BY ISBN ");
               yearList = query.getResultList();
               query = manager.createNativeQuery("SELECT TITLE " +
                                                   "FROM BOOKSS " +
                                                   "ORDER BY ISBN ");
               titleList = query.getResultList();
               query = manager.createNativeQuery("SELECT PUBLISHERS_NAME " +
                                                   "FROM BOOKSS " +
                                                   "ORDER BY ISBN ");
               publishersList = query.getResultList();
               query = manager.createNativeQuery("SELECT ae.NAME " +
                                                   "FROM BOOKSS b INNER JOIN " +
                                                   "AUTHORING_ENTITIES ae on b.AUTHOR_ENTITY_NAME = ae.EMAIL " +
                                                   "ORDER BY b.ISBN ");
               authorsList = query.getResultList();

               for (int index = 0; index < isbnList.size(); index++)   {
                  System.out.println("Record " + index + ": ");
                  System.out.println("ISBN: " + isbnList.get(index));
                  System.out.println("Year Published: " + yearList.get(index));
                  System.out.println("Type: " + titleList.get(index));
                  System.out.println("Publisher: " + publishersList.get(index));
                  System.out.println("Author: " + authorsList.get(index));
                  System.out.println("");
               }// End of for loop to display each row
               break;
            case 3:  // Writing Groups
               query = manager.createNativeQuery("SELECT NAME " +
                                                    "FROM AUTHORING_ENTITIES " +
                                                    "WHERE AUTHORING_ENTITY_TYPE = 'Writing Group' " +
                                                    "ORDER BY EMAIL ");
               namesList = query.getResultList();
               query = manager.createNativeQuery("SELECT EMAIL " +
                                                   "FROM AUTHORING_ENTITIES " +
                                                   "WHERE AUTHORING_ENTITY_TYPE = 'Writing Group' " +
                                                   "ORDER BY EMAIL ");
               emailsList = query.getResultList();
               query = manager.createNativeQuery("SELECT HEAD_WRITER " +
                                                     "FROM AUTHORING_ENTITIES " +
                                                     "WHERE AUTHORING_ENTITY_TYPE = 'Writing Group' " +
                                                     "ORDER BY EMAIL ");
               hwList = query.getResultList();
               query = manager.createNativeQuery("SELECT YEAR_FORMED " +
                                                     "FROM AUTHORING_ENTITIES " +
                                                     "WHERE AUTHORING_ENTITY_TYPE = 'Writing Group' " +
                                                     "ORDER BY EMAIL ");
               yearFormedList = query.getResultList();
               for (int index = 0; index < emailsList.size(); index++)
                  wgList.add(new authoring_entities(emailsList.get(index), "Writing Group", namesList.get(index),
                          hwList.get(index), yearFormedList.get(index)));

               for (int index = 0; index < wgList.size(); index++)
                  System.out.println("Record " + index + ": \n" + wgList.get(index) + "\n");
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
      // Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<bookss> bookssList;// = new ArrayList<>();
      List<String> isbnList, titleList;

      // Show the user all the available books
      System.out.println();
      System.out.println("Delete Book");
      System.out.println("Below is a list of all available books:\n");

      query = manager.createNativeQuery("SELECT ISBN " +
                                            "FROM BOOKSS " +
                                            "ORDER BY ISBN ");
      isbnList = query.getResultList();
      query = manager.createNativeQuery("SELECT TITLE " +
                                            "FROM BOOKSS " +
                                            "ORDER BY ISBN ");
      titleList = query.getResultList();

      for (int index = 0; index < bookssList.size(); index++)  {
         System.out.println("Book " + index + ": " +
                 "ISBN: " + isbnList.get(index) + "\n" +
                 "Title: " + titleList.get(index));
      }// End of the for loop that displays a list of books

      // Prompt the user for the ISBN of the book to delete
      System.out.print("What is the ISBN of the book you want to delete: ");
      userISBN = keyboard.nextLine();

      // Check that the book exists
      query = manager.createNativeQuery("SELECT COUNT(*) " +
                                          "FROM BOOKSS " +
                                          "WHERE ISBN IS '" + userISBN + "';");
      userChoice = query.getFirstResult();

      // If the book exists, delete it
      if (userChoice != 0) {
         query = manager.createNativeQuery("DELETE FROM BOOKSS " +
                                              "WHERE ISBN IS '" + userISBN + "';");
      }// End of the if statement to delete the book

      // Commit the changes
      tx.commit();

      // Show the user the new books table
      query = manager.createNativeQuery("SELECT ISBN " +
                                          "FROM BOOKSS " +
                                          "ORDER BY ISBN ");
      isbnList = query.getResultList();
      query = manager.createNativeQuery("SELECT TITLE " +
                                           "FROM BOOKSS " +
                                           "ORDER BY ISBN ");
      titleList = query.getResultList();

      for (int index = 0; index < bookssList.size(); index++)  {
         System.out.println("Book " + index + ": " +
                 "ISBN: " + isbnList.get(index) + "\n" +
                 "Title: " + titleList.get(index));
      }// End of the for loop that displays a list of books
   }// End of deleteBook member method

   public void updateBook  () {
      //Method Variables
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Books");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of Books and store our new EntityManager as an instance variable.
      // Books books = new Books(manager);
      EntityTransaction tx = manager.getTransaction();
      tx.begin();
      List<bookss> bookssList;// = new ArrayList<>();
      List<authoring_entities> aeList;// = new ArrayList<>();
      authoring_entities newAuthor;

      // Show the user all the available books
      System.out.println();
      System.out.println("Update Book");
      System.out.println("Below is a list of all available books:\n");

      query = manager.createNativeQuery("SELECT * " +
              "FROM BOOKSS;");
      bookssList = query.getResultList();

      for (int index = 0; index < bookssList.size(); index++)  {
         System.out.println("Book " + index + ":\n" + bookssList.get(index));
      }// End of the for loop that displays a list of books

      // Prompt the user for the ISBN of the book to delete
      System.out.print("Please select the ISBN of the book you want to delete: ");
      userISBN = keyboard.nextLine();

      // Show the user all the available authors
      System.out.println("Below is a list of all available authors:\n");

      query = manager.createNativeQuery("SELECT * " +
                                           "FROM AUTHORING_ENTITIES;");
      aeList = query.getResultList();

      for (int index = 0; index < aeList.size(); index++)  {
         System.out.println("Author " + index + ":\n" + aeList.get(index));
      }// End of the for loop that displays a list of authors

      System.out.print("Please select the record # of the new author of this book: ");
      userChoice = keyboard.nextInt();
      newAuthor = aeList.get(userChoice);

      // Update the book
      query = manager.createNativeQuery("UPDATE BOOKSS " +
                                           "SET AUTHOR_ENTITY_NAME = " + newAuthor.getEmail() + " " +
                                           "WHERE ISBN = '" + userISBN + "';");

      // Commit the changes
      tx.commit();

      // Show the updated book info to the user
      System.out.println("The updated book:");
      query = manager.createNativeQuery("SELECT * " +
                                           "FROM BOOKSS " +
                                           "WHERE ISBN = '" + userISBN + "';");
      bookssList = query.getResultList();

      for (int index = 0; index < bookssList.size(); index++)  {
         System.out.println("Book " + index + ":\n" + bookssList.get(index));
      }// End of the for loop that displays a list of books
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
               query = manager.createNativeQuery("SELECT NAME " +
                                                    "FROM PUBLISHERS; ");
               primaryKeysList = query.getResultList();
               for (int index = 0; index < primaryKeysList.size(); index++)  {
                  System.out.println("Record " + index + ":\n" + primaryKeysList.get(index));
               }// End of the for loop that displays a list of publishers
               break;
            case 2:  // Books (show the title as well as the ISBN)
               query = manager.createNativeQuery("SELECT TITLE, ISBN " +
                                                    "FROM BOOKSS; ");
               primaryKeyPlusList = query.getResultList();
               for (int index = 0; index < primaryKeyPlusList.size(); index++)  {
                  System.out.println("Record " + index + ":\n" + primaryKeyPlusList.get(index));
               }// End of the for loop that displays a list of books
               break;
            case 3:  // Authoring entities – and supply the type of authoring entity for each one as well.
               query = manager.createNativeQuery("SELECT EMAIL, AUTHORING_ENTITY_TYPE " +
                                                    "FROM AUTHORING_ENTITIES; ");
               primaryKeyPlusList = query.getResultList();
               for (int index = 0; index < primaryKeyPlusList.size(); index++)  {
                  System.out.println("Record " + index + ":\n" + primaryKeyPlusList.get(index));
               }// End of the for loop that displays a list of authoring_entities
               break;
         }// End of the switch statement to process the choice

         if (userChoice < 1 || userChoice > 3)
            System.out.println("Invalid choice. Please select between 1 and 3");
      } while(userChoice < 1 || userChoice > 3);

   }// End of listPrimaryKeys member method
} // End of Books class
