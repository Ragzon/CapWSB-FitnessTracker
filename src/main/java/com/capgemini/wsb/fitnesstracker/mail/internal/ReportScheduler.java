package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class ReportScheduler {

    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Scheduled(cron = "0 0 0 1/14 * ?") // cron
//    @Scheduled(cron = "0 * * * * *") // cron every minute for test
    public void sendBiWeeklyReports() {

        List<User> users = userRepository.findAll();


        Calendar calendar = Calendar.getInstance();
        Date endTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        Date startTime = calendar.getTime();

        //test data start
//        System.out.println("This is a simple log message");
//
//        // Clear existing data (if any)
//        trainingRepository.deleteAll();
//        userRepository.deleteAll();
//
//        // Mock users
//        User user1 = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
//        User user2 = new User("Jane", "Doe", LocalDate.of(1992, 2, 2), "jane.doe@example.com");
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//
//        // Mock trainings
//        Calendar calendar = Calendar.getInstance();
//        Date endTime = calendar.getTime();
//        calendar.add(Calendar.DAY_OF_YEAR, -7);
//        Date startTime = calendar.getTime();
//
//        Training training1 = new Training(user1, startTime, endTime, ActivityType.RUNNING, 10.0, 6.0);
//        Training training2 = new Training(user1, startTime, endTime, ActivityType.CYCLING, 20.0, 15.0);
//        Training training3 = new Training(user2, startTime, endTime, ActivityType.WALKING, 5.0, 4.0);
//
//        trainingRepository.save(training1);
//        trainingRepository.save(training2);
//        trainingRepository.save(training3);
//
//        // Fetch users and generate reports
//        List<User> users = userRepository.findAll();
        //test data end

        for (User user : users) {
            List<Training> trainings = trainingService.getTrainingsForUserInTimeRange(user, startTime, endTime);
            String content = trainingService.generateEmailContent(user, trainings);
            EmailDto email = new EmailDto(user.getEmail(), "Training Summary", content);
            emailSender.send(email);
        }
    }
}
