package com.magenic.exercise3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

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
                displayAllCovidInformation();
            } else if (optionNumber == 2) { // add new covid info
                addNewCovidInformation();
            } else if (optionNumber == 3) { // delete covid info
                deleteCovidInformation();
            } else if (optionNumber == 4) {// search info
                searchCovidInfo();
            } else if (optionNumber == 5) {// list all info from file
                listAllCovidInfoFromFile();
            }
        }
        System.out.println("Goodbye! ");
        sc.close();
    }

    private static void displayAllCovidInformation(){
        displayOptions();
        List<String> sortNames = List.of("country", "cases", "deaths", "recoveries");
        int searchOption = sc.nextInt();
        if (searchOption != -1) {
            String sortName = sortNames.get(searchOption - 1);
            List<CovidInformation> covidInformationList = covidInformationService.searchCovidInfo(null, null, sortName);
            covidInformationService.displayCovidInfoList(covidInformationList);
        }
    }

    private static void displayOptions() {
        System.out.println("\nChoose an option");
        System.out.println("[1] By Country");
        System.out.println("[2] By Cases");
        System.out.println("[3] By Deaths");
        System.out.println("[4] By Recoveries");
        System.out.println("[-1] Exit");
        System.out.print("\nEnter option type: ");
    }
    private static void displayCovidInformation() {
        System.out.println("\nChoose an Action to Perform");
        System.out.println("[1] List All Info");
        System.out.println("[2] Add new Covid Info");
        System.out.println("[3] Delete Covid Info");
        System.out.println("[4] Search for Covid Info");
        System.out.println("[5] List all info from file");
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

        System.out.print("Enter Date (MM/dd/yyyy): ");
        String date = sc.next();
        //LocalDate localDate = LocalDate.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        covidInformation.setDate(localDate);


        covidInformationService.add(covidInformation);
        System.out.println("\n" + covidInformation.format(covidInformation.getCountry(), covidInformation.getCases(), covidInformation.getDeaths(), covidInformation.getRecoveries(), covidInformation.getDate()));
    }

    private static void searchCovidInfo() {
        List<String> searchNames = List.of("country", "cases", "deaths", "recoveries");

        displayOptions();
        int searchOption = sc.nextInt();
        if (searchOption != -1) {
            String searchName = searchNames.get(searchOption - 1);
            System.out.print("\nEnter " + getSeachInfoPlaceholder(searchName) + ": ");
            String searchValue = sc.next();
            List<CovidInformation> covidInformationList = covidInformationService.searchCovidInfo(searchName, searchValue, null);
            covidInformationService.displayCovidInfoList(covidInformationList);
        }
        optionNumber = 0;
    }

    private static void listAllCovidInfoFromFile() {
        Path pth = Paths.get("C://");
        final int maxDepth = 10;
        Stream<Path> stream = null;
        try {
            stream = Files.find(pth,1,(path, basicFileAttributes) -> {
                File file = path.toFile();
                return !file.isDirectory() && file.getName().contains("_covid");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nCOVID files in the directory");
        stream.forEach(System.out::println);

        System.out.println("\nChoose file to view: ");
        String file = sc.next();

        System.out.println("\nDisplaying info from " + file);

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                reader.lines().forEach(System.out::println);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
