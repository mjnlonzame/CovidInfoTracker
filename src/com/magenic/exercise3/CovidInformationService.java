package com.magenic.exercise3;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CovidInformationService {
    private Map<String, CovidInformation> countryNameToCovidInfo;

    public CovidInformationService() {
        countryNameToCovidInfo = new HashMap<String, CovidInformation>();
    }

    public CovidInformation add(CovidInformation covidInformation) {
        countryNameToCovidInfo.put(covidInformation.getCountry(), covidInformation);
        return covidInformation;
    }

    public CovidInformation update() {
        return null;
    }

    public Map<String, CovidInformation> getCovidInfoList() {
        return countryNameToCovidInfo;
    }

    public void setCovidInfoList(HashMap<String, CovidInformation> countryNameToCovidInfo) {
        this.countryNameToCovidInfo = countryNameToCovidInfo;
    }

    public boolean delete(String countryName) {
        return countryNameToCovidInfo.keySet().removeIf(key -> key.equals(countryName));
    }

    public List<CovidInformation> getAllInfo() {
        return (List<CovidInformation>) countryNameToCovidInfo.values().stream().collect(Collectors.toList());
    }

    public void displayCovidInfoList(List<CovidInformation> covidInfoList) {
        if (getCovidInfoList().isEmpty()) {
            System.out.println("\nNo covid information found!\n");
        } else {
            StringJoiner join = new StringJoiner("			");

            System.out.println("==============================================================================================================");
            join.add("Country").add("Cases").add("Deaths").add("Recoveries");
            System.out.println(join.toString());
            System.out.println("==============================================================================================================");
            covidInfoList.forEach((e) -> {
                StringJoiner join2 = new StringJoiner("			");
                join2.add(e.getCountry()).add(String.valueOf(e.getCases())).add(String.valueOf(e.getDeaths())).add(String.valueOf(e.getRecoveries()));
                System.out.println(join2.toString());
            });
            System.out.println("\n");
        }
    }

    public List<CovidInformation> searchCovidInfo(String searchName, String searchValue) {
        return countryNameToCovidInfo
                .values()
                .stream()
                .filter(getPredicate(searchName, searchValue))
                .collect(Collectors.toList());
    }

    private Predicate<CovidInformation> getPredicate(String searchName, String searchValue) {
        if (searchName.equals("country"))
            return covidInfo -> covidInfo.getCountry().equals("PH");
        else if (searchName.equals("cases"))
            return covidInfo -> covidInfo.getCases() >= Integer.valueOf(searchValue);
        else if (searchName.equals("deaths"))
            return covidInfo -> covidInfo.getDeaths() >= Integer.valueOf(searchValue);
        else if (searchName.equals("recoveries"))
            return covidInfo -> covidInfo.getRecoveries() >= Integer.valueOf(searchValue);
        else
            return null;
    }
}
