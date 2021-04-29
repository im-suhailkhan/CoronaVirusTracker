package com.project.Coronavirustracker.Service;

import com.project.Coronavirustracker.Entity.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service

public class CoronaVirusDataService {

    public static String dataURL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    List<LocationStats> allStats=new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public void setAllStats(List<LocationStats> allStats) {
        this.allStats = allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    //this help to schedule so that it run at specified interval
    //first star for sec then min,hour,days etc...
    public void fetchData() throws IOException, InterruptedException {
        List<LocationStats> newStats=new ArrayList<>();
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().
                uri(URI.create(dataURL))
                .build();
        HttpResponse<String> httpResponse=httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats=new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int currday=Integer.parseInt(record.get(record.size()-1));
            int prevday=Integer.parseInt(record.get(record.size()-2));
            locationStats.setTotalCases(currday);
            locationStats.setDeltaCases(currday-prevday);
//            System.out.println(locationStats);
            newStats.add(locationStats);
        }

        this.allStats=newStats;
    }
}
