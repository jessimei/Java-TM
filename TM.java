/* Jessica Garcia
   CSC131
   2/4/2018       */

import java.io.*;
import java.time.*;
import java.util.*;

public class TM {
    static LocalTime ts = LocalTime.now();
    static LocalDate dt = LocalDate.now();
    //static LinkedList<String> task = new LinkedList<String>();
    static class TaskLog {
        private String name;
        private String cmd;
        private LocalDate date;
        private LocalTime time;

        public TaskLog(LocalDate date, LocalTime time, String name, String cmd) {
            this.name = name;
            this.cmd = cmd;
            this.date = date;
            this.time = time;
        }

        public String toString() {
            return date + " " + time + " " + name + " " + cmd ;
        }
    }

    public static void main(String args[]) throws IOException {
        LinkedList<String> task = new LinkedList<String>();
        TaskLog entry;
        String cmd = args[0];
        String data;// = args[1];
        //String desc = args[2];
        //String s = args[3];
        switch (cmd) {
            case "start":
                data = args[1];

                entry = new TaskLog(dt, ts, data, cmd); TM.write(entry.toString(),true);
                break;
            case "stop":
                data = args[1];

                entry = new TaskLog(dt, ts, data, cmd); TM.write(entry.toString(),true);
                break;
            case "summary":
                if(args.length == 1){
                    summarize();
                }
                else {
                    data = args[1];
                    summary(data, task);
                }
                break;
            case "describe":
                data = args[1];
                String desc = args[2];
                if(args.length == 4){
                    String s = args[3];
                    String f = (dt + " " + ts + " " + data + " " + cmd + " " + desc + " " + s);
                    TM.write(f, true);
                }else{
                    String d = (dt + " " + ts + " " + data + " " + cmd + " " + desc);
                    TM.write(d, true);
                    write(d, true);
                }
                break;
            case "size":
                data = args[1];
                String s = args[2];
                String size = (dt + " " + ts + " " + data + " " + cmd + " " + s);
                TM.write(size, true);
                break;
            default:
                intro();
                break;
        }
    }

    static void intro() { //instructions for usage
        System.out.print("Type start<task name>, to start the task\n" +
                "Type stop<task name>, to stop the task\n" +
                "Type summarize<task name>, for a summary of the task\n" +
                "Type describe<task name><\"description\">, for a description of the task.\n" +
                "Type summary, for a the entire log summary.\n" + 
		"Type size<size> to set the size of the project.\n");
    }

    static void summary(String data, LinkedList<String> task) throws IOException {
        LinkedList<String> sum = new LinkedList<String>(); // linked list to hold each line separately in their own string
        BufferedReader br = new BufferedReader(new FileReader("log.txt")); //caveofprogramming.com
        String line = null;
        try {

            while ((line = br.readLine()) != null) {
                sum.add(line); //add line to linked list
            }
        } finally {
            br.close(); // closes the buffer
        }
        String d = data; //stackoverflow
        for (String s : sum) { //the new for each loop
            if (s.contains(d)) { //checks if the task word is in the string
                int i = sum.indexOf(s); // gets the line number of the string
                System.out.println(sum.get(i)); // prints the string

            }
        }
    }

    static void summarize() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("log.txt")); //caveofprogramming.com
        String line = null;
        try {

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            br.close();
        }

    }

    static void size(String data, String cmd, String desc, String s) throws IOException{
        ArrayList<String> description = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("log.txt")); //caveofprogramming.com
        String line = null;
        try {

            while ((line = br.readLine()) != null) {
                description.add(line);
            }
        } finally {
            br.close();
        }

        String d = (data + " " + cmd);
        String dp = (desc + " " + s);
        for (String st : description) {
            if (st.contains(d)) {
                int i = description.indexOf(st);
                description.set(i, (st + " " + dp));
            }
        }
        for(String st : description){
            write(st, true);
        }

    }
    public static LinkedList<TaskLog> readFile(String TaskLog) throws IOException { //https://www.reddit.com/r/javaexamples/comments/344kch/reading_and_parsing_data_from_a_file/
        LinkedList<TaskLog> tasklog = new LinkedList<>();

        BufferedReader br = new BufferedReader(new FileReader("log.txt"));
        try {
            String fileRead = br.readLine();
            while (fileRead != null) {
                StringTokenizer st = new StringTokenizer(fileRead);
                //int tokens = st.countTokens();
                LocalDate tmpdt = LocalDate.parse(st.nextToken());
                LocalTime tmptime = LocalTime.parse(st.nextToken());
                String tmpname = st.nextToken();
                String tmpcmd = st.nextToken();
            /*if(tmpcmd == "start"){
               task.add(tmptime);
            }         */
                TaskLog tmp = new TaskLog(tmpdt, tmptime, tmpname, tmpcmd);
                tasklog.add(tmp);
                fileRead = br.readLine();
            }
        } finally {
            br.close();
        }
        return tasklog;
    }

    public static void write(String output, boolean append) throws IOException {

        BufferedWriter br = new BufferedWriter(new FileWriter("log.txt", append));
        try {
            br.write(output);

            br.newLine();
        } finally {
            br.close();
        }



/*
   class Duration{
    private LocalDateTime start, stop;

    public void duration(LocalDateTime start, LocalDateTime stop){
       this.start = start;
       this.stop = stop;

    }
   }*/
    }
}
