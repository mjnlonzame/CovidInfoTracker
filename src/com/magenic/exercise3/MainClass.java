package com.magenic.exercise3;

import java.util.*;

public class MainClass {
    private static Scanner sc = new Scanner(System.in);
    private static int optionNumber;
    private static CovidInformationService covidInformationService;

    public static void main(String[] args) {
        covidInformationService = new CovidInformationService();
        optionNumber = 0;

        while (optionNumber != -1) {
            displayCovidInformation();
            System.out.print("\nEnter action type: ");
            optionNumber = sc.nextInt();

            if (optionNumber == 1) { //display all info
                covidInformationService.displayCovidInfoList(covidInformationService.getAllInfo());
            } else if (optionNumber == 2) { // add new covid info
                addNewCovidInformation();
            } else if (optionNumber == 3) { // delete covid info
                deleteCovidInformation();
            } else if (optionNumber == 4) {// search info
                searchCovidInfo();
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

    private static void addNewCovidInformation() {

//        Map<String, String> countries = new HashMap<>();
//        for (String iso : Locale.getISOCountries()) {
//            Locale l = new Locale("", iso);
//            countries.put(l.getDisplayCountry(), iso);
//        }

        CovidInformation covidInformation = new CovidInformation();

        System.out.print("Enter Country Name: ");
        String country = sc.next();
        covidInformation.setCountry(country);

        System.out.print("Enter Number of cases: ");
        int cases = sc.nextInt();
        covidInformation.setCases(cases);

        System.out.print("Enter Number of deaths: ");
        int deaths = sc.nextInt();
        covidInformation.setDeaths(deaths);

        System.out.print("Enter Number of recoveries: ");
        int recoveries = sc.nextInt();
        covidInformation.setRecoveries(recoveries);

        covidInformationService.add(covidInformation);
        System.out.println("\n" + covidInformation.format(covidInformation.getCountry(), covidInformation.getCases(), covidInformation.getDeaths(), covidInformation.getRecoveries()));
    }

    private static void searchCovidInfo() {
        List<String> searchNames = List.of("country", "cases", "deaths", "recoveries");

        System.out.println("\nChoose an option");
        System.out.println("[1] By Country");
        System.out.println("[2] By Cases");
        System.out.println("[3] By Deaths");
        System.out.println("[4] By Recoveries");
        System.out.println("[-1] Exit");
        System.out.print("\nEnter option type: ");
        int searchOption = sc.nextInt();
        if (searchOption != -1) {
            String searchName = searchNames.get(searchOption - 1);
            System.out.print("\nEnter " + getSeachInfoPlaceholder(searchName) + ": ");
            String searchValue = sc.next();
            List<CovidInformation> covidInformationList = covidInformationService.searchCovidInfo(searchName, searchValue);
            covidInformationService.displayCovidInfoList(covidInformationList);
        }
        optionNumber = 0;
    }

    private static void deleteCovidInformation() {
        System.out.print("\nEnter Country Name: ");
        String countryName = sc.next();
        boolean removed = covidInformationService.delete(countryName);
        if (removed) {
            System.out.println(countryName + " CovidInformation has been deleted.\n");
            List<CovidInformation> covidInformationList = covidInformationService.getAllInfo();
            covidInformationService.displayCovidInfoList(covidInformationList);
        } else {
            System.out.println(countryName + " was not found in the records!");
        }
    }

    private static String getSeachInfoPlaceholder(String searchName) {
        return searchName == "country" ? "Country Name" : "Number of " + searchName + " (greater than equal)";
    }

}
