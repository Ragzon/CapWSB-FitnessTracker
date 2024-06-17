package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class TrainingServiceImpl implements TrainingProvider {

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    public List<Training> getTrainingsForUserInTimeRange(User user, Date startTime, Date endTime) {
        return trainingRepository.findByUserAndStartTimeBetween(user, startTime, endTime);
    }

    public String generateEmailContent(User user, List<Training> trainings) {
        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",\n\n");
        content.append("Here is your training summary for the past week:\n\n");

        for (Training training : trainings) {
            content.append("Training ID: ").append(training.getId()).append("\n");
            content.append("Activity: ").append(training.getActivityType().getDisplayName()).append("\n");
            content.append("Start Time: ").append(training.getStartTime()).append("\n");
            content.append("End Time: ").append(training.getEndTime()).append("\n");
            content.append("Distance: ").append(training.getDistance()).append(" km\n");
            content.append("Average Speed: ").append(training.getAverageSpeed()).append(" km/h\n\n");
        }

        content.append("Keep up the good work!\n");
        content.append("Best regards,\n");
        content.append("Your Fitness Tracker Team");

        return content.toString();
    }
}




