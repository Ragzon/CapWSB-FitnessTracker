package com.capgemini.wsb.fitnesstracker.statistics.internal;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping
    public Statistics createStatistic(@RequestBody Statistics statistic) {
        return statisticsService.createStatistic(statistic);
    }

    @PutMapping("/{id}")
    public Statistics updateStatistic(@PathVariable Long id, @RequestBody Statistics statistic) {
        return statisticsService.updateStatistic(id, statistic);
    }

    @GetMapping("/{id}")
    public Statistics getStatistic(@PathVariable Long id) {
        return statisticsService.getStatistics(id).orElseThrow(() -> new RuntimeException("Statistic not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteStatistic(@PathVariable Long id) {
        statisticsService.deleteStatistic(id);
    }

    @GetMapping("/calories-greater-than/{calories}")
    public List<Statistics> getStatisticsByCaloriesGreaterThan(@PathVariable int calories) {
        return statisticsService.getStatisticsByCaloriesGreaterThan(calories);
    }

    @GetMapping("/user/{userId}")
    public List<Statistics> getStatisticsByUserId(@PathVariable Long userId) {
        return statisticsService.getStatisticsByUserId(userId);
    }
}