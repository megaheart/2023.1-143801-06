package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ask user name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        // Print hello
        System.out.println("Hello " + name);
    }
}