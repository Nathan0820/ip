package gippy.parser;

public class Parser {
    /**
     * Processes the input message and return the command
     * @param input input message from user
     * @return command to be executed
     */
    public static String getCommand(String input) {
        return input.split(" ")[0].toLowerCase();
    }
}
