package com.project.Coronavirustracker.Service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronaVirusDataService {

    public static String dataURL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    @PostConstruct
    public void fetchData() throws IOException, InterruptedException {

        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().
                uri(URI.create(dataURL))
                .build();
        HttpResponse<String> httpResponse=httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            String state=record.get("Province/State");
            System.out.println(state);
        }


    }
}
