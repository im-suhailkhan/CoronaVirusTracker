package com.project.Coronavirustracker.Controller;

import com.project.Coronavirustracker.Entity.LocationStats;
import com.project.Coronavirustracker.Service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;
    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats= coronaVirusDataService.getAllStats();
        model.addAttribute("locationStats",allStats);
        int totalCases=allStats.stream().mapToInt(stat->stat.getTotalCases()).sum();
        int totalNewCases=allStats.stream().mapToInt(stat->stat.getDeltaCases()).sum();
        model.addAttribute("totalCases",totalCases);
        model.addAttribute("totalNewCases",totalNewCases);
        return "home";
    }

}
