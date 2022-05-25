import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    String organization;
    String userPassword;
    String userSubjects;
    boolean isAdmin;

    boolean isTutor;
    boolean isLocked;
    boolean wantsHelp;
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

    public Customer(String userName, int userID, String organization,
                     String userPassword,boolean isAdmin, String subjects) {
        this.userName = userName;
        this.userID = userID;
        this.organization = organization;
        this.isAdmin =isAdmin;
        this.userPassword = userPassword;

        String[] tempStuff = subjects.split(",");

        while(tempStuff.length<0){

        if(subjects.toLowerCase().equals("history")) {
            this.historyTrue = true;
        }
        if(subjects.toLowerCase().equals("english")) {
            this.englishTrue = true;
        }
        if(subjects.toLowerCase().equals("math")) {
            this.mathTrue = true;
        }
        if(subjects.toLowerCase().equals("science")) {
            this.scienceTrue = true;
        }

        

        int a = 0;

        while (scienceTrue==true || mathTrue==true || englishTrue==true || historyTrue== true && a==0){
            if(subjects.toLowerCase().equals("history") && a==0) {
                userSubjects+="history";
                a++;
            }
            if(subjects.toLowerCase().equals("english") && a==0) {
                userSubjects+="english";
                a++;
            }
            if(subjects.toLowerCase().equals("math") && a==0) {
                userSubjects+="math";
                a++;
            }
            if(subjects.toLowerCase().equals("science") && a==0) {
                userSubjects+="science";
                a++;
            }
        }
        while (scienceTrue==true || mathTrue==true || englishTrue==true || historyTrue== true && a==1){
            if(tempStuff[1].equals("history")) {
                userSubjects+=",history";
                a++;
            }
            if(tempStuff[1].equals("english")) {
                userSubjects+=",english";
                a++;
            }
            if(tempStuff[1].equals("math")) {
                userSubjects+=",math";
                a++;
            }
            if(tempStuff[1].equals("science")) {
                userSubjects+=",science";
                a++;
            }
        }
        while (scienceTrue==true || mathTrue==true || englishTrue==true || historyTrue== true && a==2){
            if(tempStuff[2].equals("history")) {
                userSubjects+=",history";
                a++;
            }
            if(tempStuff[2].equals("english")) {
                userSubjects+=",english";
                a++;
            }
            if(tempStuff[2].equals("math")) {
                userSubjects+=",math";
                a++;
            }
            if(tempStuff[2].equals("science")) {
                userSubjects+=",science";
                a++;
            }
        }
        while (scienceTrue==true || mathTrue==true || englishTrue==true || historyTrue== true && a==3){
            if(tempStuff[3].equals("history")) {
                userSubjects+=",history";
                a++;
            }
            if(tempStuff[3].equals("english")) {
                userSubjects+=",english";
                a++;
            }
            if(tempStuff[3].equals("math")) {
                userSubjects+=",math";
                a++;
            }
            if(tempStuff[3].equals("science")) {
                userSubjects+=",science";
                a++;
            }
        }
    }
    csvWriter(userID, organization, userName, userPassword, isAdmin, subjects);
}

    public void csvWriter(int userID, String organization, String userName, String password, boolean Admin, String Subjects){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("AllRegisteredUsers.csv"));
            writer.write(userID+","+organization+","+userName+","+password+","+Admin+","+Subjects+"\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error reading from database files");
            e.printStackTrace();
        }
    }

    //Incomplete
    public void login() {
        System.out.println("Enter user name: \n");
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();

    }


    //This too is incomplete
    public String userWantsHelp(boolean mathTrue, boolean scienceTrue, boolean englishTrue, boolean historyTrue){
        if (mathTrue || scienceTrue || englishTrue || historyTrue == true){
            return "something lol"; 
        }
        return "not complete";
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
