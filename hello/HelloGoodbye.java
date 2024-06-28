public class HelloGoodbye {
    public static void main(String[] args) {
        String name1 = args[0];
        String name2 = args[1];
        System.out.printf("Hello %s and %s.\n", name1, name2);
        System.out.printf("Goodbye %s and %s.\n", name2, name1);
    }
}
