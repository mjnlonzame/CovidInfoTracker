package com.magenic.exercise3;

import java.util.Date;

public class CovidInformation implements CovidInformationInterface {
    private String country;
    private int cases;
    private int deaths;
    private int recoveries;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecoveries() {
        return recoveries;
    }

    public void setRecoveries(int recoveries) {
        this.recoveries = recoveries;
    }

    @Override
    public String format(String country, int cases, int deaths, int recoveries) {
        StringBuilder output = new StringBuilder();
        output.append("Date: ").append(new Date().toString()).append("\n");
        output.append("Country: ").append(country).append("\n");
        output.append("Total Cases: ").append(cases).append("\n");
        output.append("Total Deaths: ").append(deaths).append("\n");
        output.append("Total Recoveries: ").append(recoveries).append("\n");
        return output.toString();
    }

    public void displayInfo() {
        System.out.println("\n==================================");
        System.out.println("Country Name: " + this.country);
        System.out.println("Cases: " + this.cases);
        System.out.println("Deaths: " + this.deaths);
        System.out.println("Recoveries: " + this.recoveries);
    }
}
