package com.magenic.exercise3;

import java.time.LocalDate;

@FunctionalInterface
public interface CovidInformationInterface {
    public String format(String country, int cases, int deaths, int recoveries, LocalDate date);
}
