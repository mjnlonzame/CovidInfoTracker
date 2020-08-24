package com.magenic.exercise3;

import java.util.HashMap;
import java.util.Map;

public class CovidInformationService {
    private Map<String, CovidInformation> countryNameToCovidInfo = new HashMap<>();

    public CovidInformation add(CovidInformation covidInformation) {
        countryNameToCovidInfo.put(covidInformation.getCountry(), covidInformation);
        return covidInformation;
    }

    public CovidInformation update() {
        return null;
    }


    public void delete() {

    }

    public String getAllInfo() {
        StringBuilder output = new StringBuilder();
        String columnNames = "====================================\n" +
                "Country \t Cases \t Deaths \t Recoveries\n" + "====================================\n";

        output.append(columnNames).append("\n");
//        countryNameToCovidInfo.keySet().stream().map(key -> {
//            System.out.print(key);
//           return null;
//        }).collect(Collectors.);
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
}
