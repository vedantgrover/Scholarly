public class Customer {
    /*
        Create customer class
         * - Allow user to create an account and apply for a tutor.
         * - Account setup will ask for necessary info. (@AshGampa see if you can figure
         * out how to get login to work in WIX)
         * - Find all tutors in DB that will work. (ONLY TUTORS. NOT ALL CUSTOMERS)
         * - Show tutors to person and let them select
    */
    String userName; 
    int userID;
    boolean isTutor;

    static int var = 0;

    public Customer(String userName, boolean isTutor){
        this.userName = userName;
        userID = var++;
        this.isTutor = isTutor;
    }
}
