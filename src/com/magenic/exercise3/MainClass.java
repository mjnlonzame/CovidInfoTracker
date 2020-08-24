package com.magenic.exercise3;

import java.util.Scanner;

public class MainClass {
    private static Scanner sc = new Scanner(System.in);
    private static int optionNumber;


    public static void main(String[] args) {

        CovidInformationService covidInformationService = new CovidInformationService();


        while (optionNumber != -1) {
            displayCovidInformation();
            System.out.print("\nEnter action type: ");
            optionNumber = sc.nextInt();

            if (optionNumber == 1) { //display all info
                System.out.println(covidInformationService.getAllInfo());
            } else if (optionNumber == 2) { // add new covid info
                CovidInformation covidInformation = new CovidInformation();
                covidInformation.setCountry("PH");
                covidInformation.setCases(11000);
                covidInformation.setDeaths(200);
                covidInformation.setRecoveries(500);
                covidInformationService.add(covidInformation);
                System.out.println(covidInformation.format("PH", 1000, 500, 200));
            } else if (optionNumber == 3) { // delete covid info
                System.out.print("\nEnter Country Name: ");
                String countryName = sc.next();
                boolean removed = covidInformationService.delete(countryName);
                if(removed){
                    System.out.println(countryName + " CovidInformation has been deleted.\n");
                    System.out.println(covidInformationService.getAllInfo());
                } else {
                    System.out.println(countryName + " was not found in the records!");
                }
            } else if (optionNumber == 4) {// search info

            }
        }
        System.out.println("Goodbye! ");
        sc.close();


    }


    private static void displayCovidInformation() {
        System.out.println("\nChoose an Action to Perform");
        System.out.println("[1] List All Info");
        System.out.println("[2] Add new Covid Info");
        System.out.println("[3] Delete Covid Info");
        System.out.println("[4] Search for Covid Info");
        System.out.println("[-1] Exit\n");
    }


}
