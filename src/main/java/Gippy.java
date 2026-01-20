import java.util.ArrayList;
import java.util.Scanner;

public class Gippy {
    public static void main(String[] args) {
        //initialize variables
        Scanner scanner = new Scanner(System.in);
        String line = "    ____________________________________________________________";
        ArrayList<String> tasks = new ArrayList<>();
        //greet
        System.out.println(line);
        System.out.println("     Hello! I'm Gippy");
        System.out.println("     What can I do for you?");
        System.out.println(line);

        //store task in arraylist and list them out
        while (true){
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("    " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println(line);
            } else {
                tasks.add(input);
                System.out.println(line);
                System.out.println("    added: " + input);
                System.out.println(line);
            }
        }

        //exit
        System.out.println(line);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(line);
    }
}
