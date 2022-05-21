import java.io.*;
import java.util.Scanner;

    public class round {

        round() throws FileNotFoundException {
            // TODO Auto-generated method stub
            Scanner inputt = new Scanner(System.in);
            System.out.print("write the path for the file: ");
            String read = inputt.next();
            File file = null;
            Scanner sc = null;
            try {
                file = new File(read);
                sc = new Scanner(file);
            } catch (Exception e) {
                System.out.println("Error in open the file, the path not found !!");
            }

            Scanner input = new Scanner(System.in);
            if (sc != null) {
                int n = 30;
                String job[] = new String[n];
                String burst1[] = new String[n];

                int count = 0;
                while (sc.hasNextLine()) {
                    job[count] = sc.nextLine();
                    burst1[count] = sc.nextLine();
                    //priority1[count] = sc.nextLine();
                    count++;
                }

                int burst_time[] = new int[30];

                for (int i = 0; i < count; i++)
                    burst_time[i] = Integer.parseInt(burst1[i]);

                int rem_time[] = new int[count];
                int w_time[] = new int[count];
                int c_time[] = new int[count];
                int arrive_time[] = new int[count];
                for (int i = 0; i < count; i++) {
                    rem_time[i] = burst_time[i];
                    w_time[i] = 0;
                }

                System.out.print("Enter the quantom number: ");
                int quantum = input.nextInt();


                int rp = count;
                int i = 0;
                int current = 0;

                System.out.print("0");
                w_time[0] = 0;
                while (rp != 0) {

                    if (rem_time[i] > quantum) {
                        rem_time[i] = rem_time[i] - quantum;
                        System.out.print(" | " + job[i] + " | ");
                        current += quantum;
                        System.out.print(current);

                    } else if (rem_time[i] <= quantum && rem_time[i] > 0) {
                        current += rem_time[i];
                        rem_time[i] = rem_time[i] - rem_time[i];
                        w_time[i] = (current - arrive_time[i]) - burst_time[i];
                        System.out.print(" | " + job[i] + " | ");
                        rp--;
                        System.out.print(current);


                    }
                    i++;
                    if (i == count)
                        i = 0;
                }
                System.out.println();
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("process\t\tburst time\t\twaiting time\tcompletion time");
                int total_wait = 0;
                int total_comp = 0;
                for (int j = 0; j < count; j++) {
                    c_time[j] = burst_time[j] + w_time[j] + arrive_time[j];

                    System.out.println(job[j] + "\t\t\t" + burst_time[j] + "\t\t\t\t" + w_time[j] + "\t\t\t\t" + c_time[j]);
                    total_wait += w_time[j];
                    total_comp += c_time[j];
                }

                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("Average wait time = " + (float) total_wait / count);
                System.out.println("Average completion time = " + (float) total_comp / count);

                sc.close();
                input.close();

            }
        }

    }

