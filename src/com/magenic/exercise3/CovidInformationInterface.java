package com.magenic.exercise3;

@FunctionalInterface
public interface CovidInformationInterface {
    public String format(String country, int cases, int deaths, int recoveries);
}
