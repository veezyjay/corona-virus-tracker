package com.veezy.covidtracker.controller;

import com.veezy.covidtracker.model.LocationStats;
import com.veezy.covidtracker.service.CoronaVirusDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private CoronaVirusDataService coronaVirusDataService;

    public HomeController(CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        if (allStats.size() > 0) {
            int totalReportedCases = allStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
            int totalNewCases = allStats.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();
            model.addAttribute("locationStats", allStats);
            model.addAttribute("totalReportedCases", totalReportedCases);
            model.addAttribute("totalNewCases", totalNewCases);
        }
        return "home";
    }
}
