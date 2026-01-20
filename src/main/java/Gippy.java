import java.util.Scanner;

public class Gippy {
    public static void main(String[] args) {
        //initialize scanner and horizontal line
        Scanner scanner = new Scanner(System.in);
        String line = "    ____________________________________________________________";
        //greet
        System.out.println(line);
        System.out.println("     Hello! I'm Gippy");
        System.out.println("     What can I do for you?");
        System.out.println(line);

        //echo
        while (true){
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            else {
                System.out.println(line);
                System.out.println("    " + input);
                System.out.println(line);
            }
        }

        //exit
        System.out.println(line);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(line);
    }
}
