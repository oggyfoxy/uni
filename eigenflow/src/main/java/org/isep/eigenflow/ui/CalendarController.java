package org.isep.eigenflow.ui;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class CalendarController {

    @FXML
    private Text yearText;
    
    @FXML
    private Text monthText;

    @FXML
    private FlowPane calendarPane;

    private ZonedDateTime currentDate;
    private Map<LocalDate, String> deadlines;

    public CalendarController() {
        currentDate = ZonedDateTime.now();  // Start with the current date
        deadlines = new HashMap<>();
    }

    @FXML
    public void initialize() {
        updateHeader();
        updateCalendar();
    }

    @FXML
    private void goBackOneMonth() {
        currentDate = currentDate.minusMonths(1);
        updateHeader();
        updateCalendar();
    }

    @FXML
    private void goForwardOneMonth() {
        currentDate = currentDate.plusMonths(1);
        updateHeader();
        updateCalendar();
    }

    public void addDeadline(ZonedDateTime deadline, String description) {
        LocalDate date = deadline.toLocalDate();
        deadlines.put(date, description);
        updateCalendar();  // Refresh the calendar to show the new deadline
    }

    private void updateHeader() {
        yearText.setText(String.valueOf(currentDate.getYear()));
        monthText.setText(currentDate.getMonth().toString());
    }

    private void updateCalendar() {
        calendarPane.getChildren().clear();  // Clear the current view

        LocalDate firstOfMonth = currentDate.toLocalDate().withDayOfMonth(1);
        int daysInMonth = currentDate.toLocalDate().lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate currentDay = firstOfMonth.withDayOfMonth(i);
            Button dayButton = new Button(String.valueOf(i));

            // Highlight day if there's a deadline
            if (deadlines.containsKey(currentDay)) {
                dayButton.setStyle("-fx-background-color: red;");
            }

            dayButton.setOnAction(e -> {
                String deadlineDescription = deadlines.get(currentDay);
                if (deadlineDescription != null) {
                    System.out.println("Deadline for " + currentDay + ": " + deadlineDescription);
                }
            });

            calendarPane.getChildren().add(dayButton);
        }
    }
}
