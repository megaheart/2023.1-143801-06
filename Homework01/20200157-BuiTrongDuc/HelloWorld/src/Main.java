import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        String name_string = scanner.nextLine();
        System.out.println("Hello " + name_string + "!");
    }
}