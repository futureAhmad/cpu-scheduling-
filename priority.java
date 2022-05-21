import java.util.Scanner;
import java.io.*;
class cpu {
    String name;
    int burst_time,pr,waiting_time,complete_time,turnArount_time,arrival_time;


    cpu(String name,int pr,int burst_time,int complete_time, int waiting_time){
        this.name=name;
        this.pr=pr;
        this.burst_time=burst_time;
        this.waiting_time=waiting_time;
        this.complete_time=complete_time;
        this.turnArount_time=0;
        this.arrival_time=0;

    }
}
public class priority {
    priority() throws FileNotFoundException {
        Scanner input= new Scanner(System.in);
        System.out.print("write the path for the file: ");
        String read=input.next();
        File file=null;
        try {
             file = new File(read);
        }catch (Exception e){
            System.out.println("Error in open the file, the path not found !!");
        }
        Scanner scan = new Scanner(file);
        Scanner checkJob = new Scanner(file);

        int num_cpu = 0;
        String check;
        int i = 0;
        int line_num=0;


        check = checkJob.nextLine(); // first line in file
        // this while is to check how many jobs are in the file
        while (checkJob.hasNextLine()) {
            try {
                if ( (check.charAt(i) >=65 && check.charAt(i) <= 90)
                        ||
                        (check.charAt(i) >=97 && check.charAt(i) <= 122) ) {
                    num_cpu++;
                }
            } catch (Exception e) {
                check = checkJob.nextLine();
                continue;
            }
            check = checkJob.nextLine();
        }
        System.out.println("\nNumber of job in cpu is: (before start the job) > " + num_cpu);
        // end of check

        int length = 0, interrupt = 0;
        String convert, line;
        cpu[] job = new cpu[num_cpu]; // array of object, each index have name of job , burst time and priority

        initial_statue_job(job, num_cpu);

        int index = 0; // counter for array
        line = scan.nextLine(); // first line
        int number_of_line_in_file = 0;
        while (scan.hasNextLine()) {
            number_of_line_in_file++;
            if (index == 30) {
                System.out.println("no space the list is full");
                break;
            }
            length = line.length();
            cpu obj = new cpu("null", -1, -1,0,0); // make object of cpu ( number of object is same as number of num_cpu )

            if (number_of_line_in_file%2!=0) {
                obj.name = line;
                line = scan.nextLine(); // the line is name of job, go to next line which have the burst time and priority
                number_of_line_in_file++;
            }

            while (line.charAt(i) != ' ' && line.charAt(i) != ',') { // loop to find not number ( interrupt such , or space )
                interrupt++;
                i++;
            }
            convert = line.substring(0, interrupt);
            obj.burst_time = Integer.parseInt(convert);

            if (line.charAt(interrupt + 1) == ' ')
                interrupt++;
            convert = line.substring(interrupt + 1);
            obj.pr = Integer.parseInt(convert);
            i = 0;
            interrupt = 0;
            try {
                line = scan.nextLine();
            } catch (Exception e) {
                add(job, index, obj);
                break;
            }
            add(job, index, obj);
            index++;
        } // end of while
        System.out.println("Number of job in cpu is (after start the job) > " + job.length);
        System.out.println("The above should be match otherwise there is logical error !\n ");
        reverse(job);
        time(job);
        print2(job, job.length);
        gont_char(job);
        float wait_time = average_wait_time(job);
        System.out.println("average waiting time is : " + wait_time);
        float complete_time=average_complete_time(job);
        System.out.println("average turn around time is : " + complete_time);


    }


    public static void add(cpu[] job, int len, cpu obj) {
        if (len == 0)
            job[len] = obj;
        else {
            int max = obj.pr;
            int index = -1;

            for (int i = 0; i <= len; i++) {
                if (job[i].pr > max) {
                    index = i;
                }
            }
            for (int i = len; i > 0; --i) {
                if (i == index)
                    break;
                job[i] = job[i - 1];
            }
            index++;
            job[index] = obj;
        }

        //print(job,job.length);
    }

    public static void sort(cpu[] job, int len) {
        System.out.println("Before sorting by burst time and priority");
        print(job, job.length);
        int end = job[job.length - 1].burst_time;
        int start = job[job.length - 1].pr;
        for (int i = job.length - 2; i >= 0; --i) {
            if (job[job.length - 1].pr == job[i].pr && job[i].burst_time < job[job.length - 1].burst_time) {
                cpu temp = job[i];
                job[i] = job[job.length - 1];
                job[job.length - 1] = temp;
            } else if (job[job.length - 1].pr != job[i].pr)
                break;
        }
        end = job[job.length - 1].burst_time;

        int current = job.length - 2;
        int min = job[job.length - 2].burst_time;
        for (int i = job.length - 2; i >= 0; --i) {
            current = i;
            min = job[i].burst_time;
            for (int j = i; j >= 0; --j) {
                if (job[i].pr >= start && job[i].pr <= end && min > job[j].burst_time) {
                    current = j;
                    min = job[j].burst_time;
                }
            }
            cpu temp = job[i];
            job[i] = job[current];
            job[current] = temp; }
        System.out.println("After sorting by burst time and priority");


    }

    public static void print(cpu[] job, int index) {
        System.out.println("******************************************");
        for (int j = 0; j < index; j++) {
            System.out.println("job " + (j) + " is >>>>\t\tname is: " + job[j].name +
                    "\t\t burst time is: " + job[j].burst_time +
                    "\t\t priority is: " + job[j].pr+"\t waiting time is: " + job[j].waiting_time+
                    "\t\t complete is: " + job[j].complete_time);

        }
        System.out.println("******************************************");
    }
    public static void print2(cpu[] job, int index){
        System.out.println("Name\tburst time\tpriority\twaiting time\tcomplete time");
        for (int j = 0; j < index; j++){
            System.out.println(job[j].name+"\t\t"
                    +job[j].burst_time+"\t\t   "
                    +job[j].pr+"\t\t    "+job[j].waiting_time+"\t\t\t\t"+job[j].complete_time);
        }
    }

    public static void reverse(cpu[] job) {
        int right = job.length - 1;
        for (int left = 0; left < job.length; ++left) {
            if (right == left || (left - 1 == right)) // odd || even
                break;
            cpu temp = job[left];
            job[left] = job[right];
            job[right] = temp;
            right--;
        }
    }

    public static void initial_statue_job(cpu[] job, int num_cpu) {
        for (int k = 0; k < num_cpu; k++) {
            cpu temp = new cpu("null", 0, 0,0,0);
            job[k] = temp;
        }
    }

    public static float average_wait_time(cpu[] job) {
        float sum = 0;
        for (int i = 0; i < job.length; ++i) {
            sum += job[i].waiting_time;
        }
        return sum==0?sum:sum/ job.length;
    }

    public static float average_complete_time(cpu[] job) {
        float sum = 0;
        for (int i = 0; i < job.length; ++i) {
            sum += job[i].complete_time;
        }
        return sum==0?sum:sum/ job.length;
    }
    public static float average_turnAround_time(cpu[] job) {
        float sum = 0;
        for (int i = 0; i < job.length; ++i) {
            sum += job[i].turnArount_time;
        }
        return sum==0?sum:sum/ job.length;
    }

    public static void time(cpu[]job){
        int wait=0;
        job[0].complete_time=job[0].burst_time;
        for (int i=1; i< job.length;++i){
            for (int j=i-1; j>=0;--j){
                wait+=job[j].burst_time;
            }
            job[i].complete_time=wait+job[i].burst_time;
            job[i].turnArount_time=job[i].complete_time-job[i].arrival_time;

            job[i].waiting_time=wait;
            wait=0;
        }
    }

    public static void gont_char(cpu[] job){
        int empty=gont_char_size(job);
        for (int i=0; i< empty;++i)
            System.out.print("-");
        System.out.println();
        int space=0;

        for (int i=0; i< job.length;++i){
            space= job[i].burst_time%2==0?job[i].burst_time/2:(job[i].burst_time/2)+1;
            //space=job[i].complete_time;

            System.out.print("|");
            for (int j=0; j< space;++j)
                System.out.print(" ");

            System.out.print(job[i].name);

            for (int j=0; j< space;++j)
                System.out.print(" ");
        }
        System.out.println("|");
        for (int i=0; i< empty;++i)
            System.out.print("-");
        System.out.println();

    }
    public static int gont_char_size(cpu[] job){
        int space=0;
        int num_of_space=1;
        for (int i=0; i< job.length;++i){
            num_of_space++;
            space= job[i].burst_time%2==0?job[i].burst_time/2:job[i].burst_time/2+1;
            for (int j=0; j< space;++j)
                num_of_space++;
            num_of_space+=job[i].name.length();
            for (int j=0; j< space;++j)
                num_of_space++;
        }
        return num_of_space;
    }

}



