package com.magenic.exercise3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

public class CovidInformationService {
    private Map<String, CovidInformation> countryNameToCovidInfo;

    public CovidInformationService() {
        countryNameToCovidInfo = new HashMap<String, CovidInformation>();
    }

    public CovidInformation add(CovidInformation newCovidInformation) {
        Optional<CovidInformation> optionalCovidInformation = Optional.ofNullable(countryNameToCovidInfo.get(newCovidInformation.getCountry()));
        if (optionalCovidInformation.isPresent()) {
            CovidInformation currentCovidInfo = optionalCovidInformation.get();
            if (currentCovidInfo.getDate().compareTo(newCovidInformation.getDate()) == 0) {
                countryNameToCovidInfo.put(newCovidInformation.getCountry(), newCovidInformation);
            } else {
                LocalDate localDateTobeSaved = currentCovidInfo.getDate().compareTo(newCovidInformation.getDate()) > 0 ? currentCovidInfo.getDate() : newCovidInformation.getDate();
                currentCovidInfo.setCases(Math.addExact(currentCovidInfo.getCases(), newCovidInformation.getCases()));
                currentCovidInfo.setDeaths(Math.addExact(currentCovidInfo.getDeaths(), newCovidInformation.getDeaths()));
                currentCovidInfo.setRecoveries(Math.addExact(currentCovidInfo.getRecoveries(), newCovidInformation.getRecoveries()));
                currentCovidInfo.setDate(localDateTobeSaved);
                countryNameToCovidInfo.put(currentCovidInfo.getCountry(), currentCovidInfo);
            }
        } else {
            countryNameToCovidInfo.put(newCovidInformation.getCountry(), newCovidInformation);
        }

        return newCovidInformation;
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
        return countryNameToCovidInfo.values().stream().collect(Collectors.toList());
    }

    public void displayCovidInfoList(List<CovidInformation> covidInfoList) {
        if (getCovidInfoList().isEmpty()) {
            System.out.println("\nNo covid information found!\n");
        } else {
            StringJoiner join = new StringJoiner("		");

            System.out.println("==============================================================================================================");
            join.add("Country").add("Cases").add("Deaths").add("Recoveries").add("Date");
            System.out.println(join.toString());
            System.out.println("==============================================================================================================");
            covidInfoList.forEach((e) -> {
                StringJoiner join2 = new StringJoiner("			");
                join2.add(e.getCountry()).add(String.valueOf(e.getCases())).add(String.valueOf(e.getDeaths())).add(String.valueOf(e.getRecoveries())).add(String.valueOf(e.getDate()));
                System.out.println(join2.toString());
            });
            System.out.println("\n");
        }
    }

    public List<CovidInformation> searchCovidInfo(String searchName, String searchValue, String sort) {
        if (sort == null)
            sort = "country"; //default sorting
        return countryNameToCovidInfo
                .values()
                .stream()
                .filter(getPredicate(searchName, searchValue))
                .sorted(getKeyExtractor(sort))
                .collect(Collectors.toList());
    }

    private Predicate<CovidInformation> getPredicate(String searchName, String searchValue) {
        if (Objects.nonNull(searchName) && searchName.equals("country"))
            return covidInfo -> covidInfo.getCountry().equals(searchValue);
        else if (Objects.nonNull(searchName) && searchName != null && searchName.equals("cases"))
            return covidInfo -> covidInfo.getCases() >= Integer.valueOf(searchValue);
        else if (Objects.nonNull(searchName) && searchName.equals("deaths"))
            return covidInfo -> covidInfo.getDeaths() >= Integer.valueOf(searchValue);
        else if (Objects.nonNull(searchName) && searchName.equals("recoveries"))
            return covidInfo -> covidInfo.getRecoveries() >= Integer.valueOf(searchValue);
        else
            return Objects::nonNull;
    }

    private Comparator<CovidInformation> getKeyExtractor(String keyName) {
        Map<String, Comparator<CovidInformation>> map = new HashMap<>();
        map.put("country", Comparator.comparing(CovidInformation::getCountry));
        map.put("cases", Comparator.comparing(CovidInformation::getCases));
        map.put("deaths", Comparator.comparing(CovidInformation::getDeaths));
        map.put("recoveries", Comparator.comparing(CovidInformation::getRecoveries));
        return map.get(keyName);
    }
	
	public void saveCovidInfoList(List<CovidInformation> covidInformationList) {

		Base64.Encoder encoder = Base64.getEncoder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String filenameDate = LocalDate.now().format(format);
		String encodedFilenameDate = encoder.encodeToString(filenameDate.getBytes(StandardCharsets.UTF_8));
        String filenameCovid = "_covid";
        String encodedFilenameCovid = encoder.encodeToString(filenameCovid.getBytes(StandardCharsets.UTF_8));
        String encodedFileName = encodedFilenameDate + encodedFilenameCovid  + ".txt";
        System.out.println(System.getProperty("user.dir"));
        String covidFilesDir = System.getProperty("user.dir") + "\\covidFiles\\";
		try {

            BufferedWriter writer = new BufferedWriter(new FileWriter (covidFilesDir  + encodedFileName, true) );
            writer.write("================================================================================================\n");
            writer.write("Country" + "		" + "Cases" + "		" + "Deaths" + "		" + "Recoveries" + "	" + "Date" + "\n");
            writer.write("================================================================================================\n");
            covidInformationList.forEach((e) -> {
                StringJoiner strJoin = new StringJoiner("		");
                try {
                    writer.write (strJoin.add(e.getCountry()).add(String.valueOf(e.getCases())).add(String.valueOf(e.getDeaths())).add(String.valueOf(e.getRecoveries())).add(String.valueOf(e.getDate())).toString() + "\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            writer.close();
            System.out.println("Information is saved in " + encodedFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
		//String encodedCovidInfo = encoder.encodeToString(strJoin.toString().getBytes(StandardCharsets.UTF_8));
	}
}
