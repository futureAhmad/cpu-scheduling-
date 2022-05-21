import java.io.*;
import java.util.Scanner;

    public class SJTF {

        SJTF() throws FileNotFoundException {
            // TODO Auto-generated method stub
            Scanner inputt= new Scanner(System.in);
            System.out.print("write the path for the file: ");
            String read=inputt.next();
            File file=null;
            Scanner sc=null;
            try {
                file = new File(read);
                sc = new Scanner(file);
            } catch (Exception e) {
                System.out.println("Error in open the file, the path not found !!");
            }
            Scanner input = new Scanner(System.in);
            if (sc!=null) {
                int n = 30;
                int pid[] = new int[n];
                int arr_time[] = new int[n];
                int burst[] = new int[n];

                String s = "";
                while (sc.hasNextLine()) {
                    s += sc.nextLine();
                }


                int count = 0;
                int n_process = 0;
                while (count < s.length()) {
                    pid[n_process] = s.charAt(count);
                    count += 2;
                    arr_time[n_process] = s.charAt(count);
                    count += 2;
                    burst[n_process] = s.charAt(count);
                    if (count == s.length())
                        break;
                    count++;
                    n_process++;
                }


                for (int i = 0; i < n_process; i++) {
                    pid[i] = Character.getNumericValue(pid[i]);
                    arr_time[i] = Character.getNumericValue(arr_time[i]);
                    burst[i] = Character.getNumericValue(burst[i]);

                }

                int rem_time[] = new int[n_process];
                int w_time[] = new int[n_process];
                int t_time[] = new int[n_process];
                int is_completed[] = new int[n_process];
                int c_time[] = new int[n_process];
                for (int i = 0; i < n_process; i++) {
                    rem_time[i] = burst[i];
                    w_time[i] = 0;
                    is_completed[i] = 0;
                }

                int completed = 0;
                int current = 0;
                int total_w = 0, total_c = 0;
                System.out.print("0");
                while (completed != n_process) {
                    int index = -1;
                    int min = 10000;
                    for (int i = 0; i < n_process; i++) {
                        if (arr_time[i] <= current && is_completed[i] == 0) {
                            if (rem_time[i] < min) {
                                min = rem_time[i];
                                index = i;
                            }

                            if (rem_time[i] == min) {
                                if (arr_time[i] < arr_time[index]) {
                                    min = rem_time[i];
                                    index = i;
                                }
                            }
                        }
                    }

                    if (index != -1) {
                        rem_time[index] -= 1;
                        current++;
                        System.out.print(" | job" + pid[index] + " | ");
                        System.out.print(current);
                        if (rem_time[index] == 0) {

                            c_time[index] = current;
                            t_time[index] = c_time[index] - arr_time[index];
                            w_time[index] = t_time[index] - burst[index];
                            total_w += w_time[index];
                            total_c += c_time[index];
                            is_completed[index] = 1;
                            completed++;

                        }
                    } else {
                        current++;
                        System.out.print(" | Cpu idle | ");
                        System.out.print(current);
                    }

                }

                int min_arrival_time = 10000000;
                int max_completion_time = -1;
                for (int i = 0; i < n_process; i++) {
                    min_arrival_time = Math.min(min_arrival_time, arr_time[i]);
                    max_completion_time = Math.max(max_completion_time, c_time[i]);
                }

                System.out.println();
                System.out.println("---------------------------------------------------------------------------------------------------------");
                System.out.println("process\t\tarrive time\t\tburst time\t\twaiting time\t\tcompletion time");
                for (int i = 0; i < n_process; i++) {
                    System.out.println("  " + pid[i] + "\t\t\t\t" + arr_time[i] + "\t\t\t\t" + burst[i] + "\t\t\t\t" + w_time[i] + "\t\t\t\t\t" + c_time[i]);
                }

                System.out.println("---------------------------------------------------------------------------------------------------------");

                System.out.println("Average wait time = " + (float) total_w / n_process);
                System.out.println("Average completion time = " + (float) total_c / n_process);


            }

        }

    }


