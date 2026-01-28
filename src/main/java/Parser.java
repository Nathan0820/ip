public class Parser {
    public static String getCommand(String input) {
        return input.split(" ")[0].toLowerCase();
    }
}
