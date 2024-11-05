package Calendarapp1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarApp {
    private JFrame frame;
    private JLabel monthLabel;
    private JPanel calendarPanel;
    private int currentMonth;
    private int currentYear;
    private Map<String, String> reminders;

    public CalendarApp() {
        Calendar calendar = new GregorianCalendar();
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        reminders = new HashMap<>();

        frame = new JFrame("Interactive Calendar with Reminders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> changeMonth(-1));

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> changeMonth(1));

        monthLabel = new JLabel();
        updateMonthLabel();

        topPanel.add(prevButton);
        topPanel.add(monthLabel);
        topPanel.add(nextButton);

        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7));  // 7 columns for days of the week

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(calendarPanel, BorderLayout.CENTER);

        displayCalendar(currentMonth, currentYear);

        frame.setVisible(true);
    }

    private void updateMonthLabel() {
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        monthLabel.setText(months[currentMonth] + " " + currentYear);
    }

    private void changeMonth(int offset) {
        currentMonth += offset;

        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        } else if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }

        updateMonthLabel();
        displayCalendar(currentMonth, currentYear);
    }

    private void displayCalendar(int month, int year) {
        calendarPanel.removeAll();

        String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            calendarPanel.add(label);
        }

        Calendar calendar = new GregorianCalendar(year, month, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            String dateKey = String.format("%d-%02d-%02d", year, month + 1, day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            if (reminders.containsKey(dateKey)) {
                dayButton.setBackground(Color.YELLOW);
                dayButton.setToolTipText("Reminder: " + reminders.get(dateKey));
            }

            dayButton.addActionListener(e -> openReminderDialog(dateKey));

            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void openReminderDialog(String dateKey) {
        String existingReminder = reminders.getOrDefault(dateKey, "");

        String newReminder = JOptionPane.showInputDialog(
                frame,
                "Enter reminder for " + dateKey + ":",
                existingReminder
        );

        if (newReminder != null && !newReminder.trim().isEmpty()) {
            reminders.put(dateKey, newReminder);
        } else {
            reminders.remove(dateKey);
        }

        displayCalendar(currentMonth, currentYear);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarApp::new);
    }
}