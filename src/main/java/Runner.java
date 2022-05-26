import GUIStuff.ScholarlyFrame;
import VAC.Scholarly;

public class Runner {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    new ScholarlyFrame();
    
    

        /**
         * TODO:
         * Necessary Stuff
         * - Create Admin Class
         * - Allow robot to communicate and send applications to reviewers
         * - Create GUI/Website tab for applying for tutors.
         * - Account creation will ask for all the user background in tutoring
         * - Link to DB. Create user logins for customers.
         * 
         * - Create customer class
         * - Allow user to create an account and apply for a tutor.
         * - Account setup will ask for necessary info. (@AshGampa see if you can figure
         * out how to get login to work in WIX)
         * - Find all tutors in DB that will work. (ONLY TUTORS. NOT ALL CUSTOMERS)
         * - Show tutors to person and let them select
         * 
         * Later Stuff
         * - Set up payment methods
         * - Set up discord server
         * - Create discord bot for moderation and suggestions
         * 
         * This is a lot of stuff but I think with three people working on it, we should
         * be able to finish this in time. At least fot he soft launch.
         * 
         * Agenda:
         *  - ✅ Set up MongoDB
         *  - Set up Registration and Login (WITH GUI)
         *  - Create Scroll Panel with all the Tutors
         *  - Create Tutor sign up
         *  - Create description box
         *  - Create command box
         * Soft Launch Happens
         *  - Make the Wix Site for downloading and collecting payment for software (lifetime for now. will change to monthly once we figure out how)
         *  - Create the Discord Server.
         *  - Create the Discord Bot for server moderation.
         * Final Presentation (if we get investors)
         *  - **We choose if we want to continue this project and see if it goes anywhere**.
         */

  }

  public static void Welcome() {
    System.out.println("Welcome to SCHOLARLY Student Resource");
    System.out.println("Press (A) for Admin");
    System.out.println("Press (C) for Customer login");

  }
}