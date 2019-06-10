package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    static HashMap<String, String> columnChoices = new HashMap<>();

    public SearchController () {
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
    }

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    @RequestMapping(value = "/results")
    public String results(Model model,
                          @RequestParam String color, @RequestParam String searchType,
                          @RequestParam String searchTerm) {
        ArrayList<HashMap<String,String>> jobs;
        if (searchType.equals("all")) {
            jobs = JobData.findByValue(searchTerm);
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }
        model.addAttribute("heading", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("jobs", jobs);

        if (color.equals("yellow")) {
            model.addAttribute("color",color);
            return "colors";
        } else {
            return "search";
        }
    }
    // TODO #1 - Create handler to process search request and display results
    //2 params searchType searchTerm

}
