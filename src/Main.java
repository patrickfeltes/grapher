public class Main {

    public static void main(String[] args) {
        Lexer lexer = new Lexer("3+4");
        System.out.println(lexer.expression());
    }

}
