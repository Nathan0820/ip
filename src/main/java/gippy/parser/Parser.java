package gippy.parser;

/**
 * Parser class to process user input commands.
 */
public class Parser {
    /**
     * Processes the input string to extract the command.
     * @param input Input message from user
     * @return Command to be executed
     */
    public static String getCommand(String input) {
        return input.split(" ")[0].toLowerCase();
    }
}
