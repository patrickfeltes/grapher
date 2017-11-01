import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.println(new Lexer(input).expression());
        }
    }

}
