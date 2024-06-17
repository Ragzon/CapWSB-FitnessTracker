package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService implements StatisticsProvider {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public Optional<Statistics> getStatistics(Long statisticsId) {
        return statisticsRepository.findById(statisticsId);
    }

    public Statistics createStatistic(Statistics statistic) {
        return statisticsRepository.save(statistic);
    }

    public Statistics updateStatistic(Long id, Statistics updatedStatistic) {
        return statisticsRepository.findById(id).map(statistic -> {
            statistic.setTotalTrainings(updatedStatistic.getTotalTrainings());
            statistic.setTotalDistance(updatedStatistic.getTotalDistance());
            statistic.setTotalCaloriesBurned(updatedStatistic.getTotalCaloriesBurned());
            return statisticsRepository.save(statistic);
        }).orElseThrow(() -> new RuntimeException("Statistic not found"));
    }

    public void deleteStatistic(Long id) {
        statisticsRepository.deleteById(id);
    }

    public List<Statistics> getStatisticsByCaloriesGreaterThan(int calories) {
        return statisticsRepository.findByTotalCaloriesBurnedGreaterThan(calories);
    }

    public List<Statistics> getStatisticsByUserId(Long userId) {
        return statisticsRepository.findByUserId(userId);
    }
}
