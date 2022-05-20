import java.util.Scanner;

public class Customer {
    /*
     * Create customer class
     * - Allow user to create an account and apply for a tutor.
     * - Account setup will ask for necessary info. (@AshGampa see if you can figure
     * out how to get login to work in WIX)
     * - Find all tutors in DB that will work. (ONLY TUTORS. NOT ALL CUSTOMERS)
     * - Show tutors to person and let them select
     */
    String userName;
    int userID;
    boolean isTutor;
    boolean isLocked;
    boolean mathTrue;
    boolean scienceTrue;
    boolean englishTrue;
    boolean historyTrue;

    static int var = 0;

    public Customer(String userName) {
        this.userName = userName;
        isTutor = false;
    }

    public Customer(String userName, boolean isTutor) {
        this.userName = userName;
        this.isTutor = isTutor;
    }

    public void login() {
        System.out.println("Enter user name: \n");
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();

    }

    public String getuserName() {
        return userName;
    }

    public int getUserID() {
        return userID;
    }

    public boolean getisTutor() {
        return isTutor;
    }
    
    public boolean getmathTrue() {
        return mathTrue;
    }

    public boolean getscienceTrue() {
        return scienceTrue;
    }

    public boolean getenglishTrue() {
        return englishTrue;
    }

    public boolean gethistoryTrue() {
        return historyTrue;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setisTutor(boolean isTutor) {
        this.isTutor = isTutor;
    }

    public void setEnglishTrue(boolean englishTrue) {
        this.englishTrue = englishTrue;
    }
    
    public void setHistoryTrue(boolean historyTrue) {
        this.historyTrue = historyTrue;
    }

    public void setMathTrue(boolean mathTrue) {
        this.mathTrue = mathTrue;
    }

    public void setScienceTrue(boolean scienceTrue) {
        this.scienceTrue = scienceTrue;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setTutor(boolean isTutor) {
        this.isTutor = isTutor;
    }

}
