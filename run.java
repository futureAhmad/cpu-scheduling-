import java.io.FileNotFoundException;
import java.util.Scanner;
public class run {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner read=new Scanner(System.in);
        boolean again=true;
        String x;
        while(again) {
            again=false;
            System.out.println("choose an algorithm please: ");
            System.out.println("---------------------------");
            System.out.println("Enter 1 for priority algorithm");
            System.out.println("Enter 2 for round algorithm");
            System.out.println("Enter 3 for SJTF algorithm");
            System.out.println("Enter 4 for exit");
            System.out.println("---------------------------");
            System.out.print("your choice is ? ");
            int choice=read.nextInt();
            if (choice == 1 || choice == 2 || choice == 3 || choice==4) {
                if (choice == 1) {
                    try {
                        priority temp = new priority();

                    } catch (Exception e) {
                        System.out.println("There an error in the excuting priority algorithm !");
                    }
                } else if (choice == 2) {
                    round temp = new round();
                } else if (choice==3){
                    SJTF temp = new SJTF();
                }else{
                    break;
                }
            } else {
                System.out.println("wrong number ! try again");
                again = true;
                System.out.print("your choice is ? ");
            }
//            System.out.print("do you want to try again? enter 1 for yes OR 2 for no: ");
//            x= read.next();
//            if (x.equalsIgnoreCase("1"))
//                again=true;
//            else
//                break;
        }
    }
}
