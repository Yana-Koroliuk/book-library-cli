package com.koroliuk.book_lib_cli;

import java.util.Scanner;

public class Controller {
    QueryManager queryManager;
    public Controller(QueryManager queryManager) {
        this.queryManager = queryManager;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            String[] inputs = input.split(" ");
            String command = inputs[0];
            try {
                if ("quit".equals(command) && inputs.length == 1) {
                    break;
                } else if ("find".equals(command) && inputs.length == 5) {
                    String request = inputs[3]; // todo: name of value
                    String name = inputs[4];
                    if ("author".equals(request)) {
                        System.out.println("Find by Author1");
                    } else if ("title".equals(request)) {
                        System.out.println("Find by Title1");
                    } else if ("category".equals(request)) {
                        System.out.println("Find by Category");
                    } else {
                        System.out.println("Please write again!");
                    }
                } else if ("take".equals(command) && inputs.length == 2) {
                    //todo: name and parameters
                    System.out.println("Take");
                } else if ("status".equals(command) && inputs.length == 2) {
                    System.out.println("Status");
                } else if ("create".equals(command) && inputs.length == 2) {
                    System.out.println("Create");
                } else {
                    System.out.println("Please write again!");
                }
            } catch (Exception e) {
                System.out.println("Please write again");
            }
        }
    }
}
