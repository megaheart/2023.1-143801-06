package com.megaheart;

import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name;
        name = scanner.nextLine();
        System.out.println("Hello " + name);
    }
}
