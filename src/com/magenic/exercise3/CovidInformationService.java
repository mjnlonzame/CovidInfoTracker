package com.magenic.exercise3;

import java.util.*;
import java.util.logging.Logger;

public class CovidInformationService {
    private HashMap<String, CovidInformation> countryNameToCovidInfo;

    public CovidInformationService(){
        countryNameToCovidInfo = new HashMap<String,CovidInformation>();
    }

    public CovidInformation add(CovidInformation covidInformation) {
        countryNameToCovidInfo.put(covidInformation.getCountry(), covidInformation);
        return covidInformation;
    }

    public CovidInformation update() {
        return null;
    }

    public HashMap<String,CovidInformation> getCovidInfoList(){
        return countryNameToCovidInfo;
    }

    public void setCovidInfoList(HashMap<String,CovidInformation> countryNameToCovidInfo ){
        this.countryNameToCovidInfo = countryNameToCovidInfo;
    }


    public boolean delete(String countryName) {
        return countryNameToCovidInfo.keySet().removeIf(key -> key.equals(countryName));
    }

    public String getAllInfo() {
        StringBuilder output = new StringBuilder();
        String columnNames = "====================================\n" +
                "Country \t Cases \t Deaths \t Recoveries\n" + "====================================\n";

        output.append(columnNames).append("\n");

        countryNameToCovidInfo.forEach((k, v) -> {
            output.append(v.getCountry());
            output.append("\t");
            output.append(v.getCases());
            output.append("\t");
            output.append(v.getDeaths());
            output.append("\t");
            output.append(v.getRecoveries());
            output.append("\t");
        });
        return output.toString();
    }

    public CovidInformation searchCovidInfo(String country){
        Collection<CovidInformation> covidInfoList = getCovidInfoList().values();
        ArrayList<CovidInformation> tempCovidList = new ArrayList<CovidInformation>(covidInfoList);
        CovidInformation covidInfo = null;

        Optional<CovidInformation> optCovidInfo = tempCovidList.stream().filter(cvdInfo -> cvdInfo.getCountry().equals(country)).findFirst();

        if(optCovidInfo.isPresent()){
            covidInfo = optCovidInfo.get();

            System.out.print(getAllInfo());
            System.out.println("\n");

        } else {
            System.out.println("\nCountry Not Found!\n");
        }

        return covidInfo;
    }
}
