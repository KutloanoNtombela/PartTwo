/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.login;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Login {
    //Declarations

    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;
    private ArrayList<Task> tasks = new ArrayList<>();
    private int totalHours = 0;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;

        boolean hasUpperCase = false, hasNumber = false, hasSpecialChar = false;
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpperCase = true;
            else if (Character.isDigit(ch)) hasNumber = true;
            else if (!Character.isLetterOrDigit(ch)) hasSpecialChar = true;
        }
        return hasUpperCase && hasNumber && hasSpecialChar;
    }

    public String registerUser(String username, String password, String firstName, String lastName) {
        if (checkUserName(username) && checkPasswordComplexity(password)) {
            this.registeredUsername = username;
            this.registeredPassword = password;
            this.firstName = firstName;
            this.lastName = lastName;
            return "Username and password successfully captured.";
        } else {
            if (!checkUserName(username)) {
                return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than 5 characters.";
            }
            if (!checkPasswordComplexity(password)) {
                return "Password is not correctly formatted; it must contain at least 8 characters, a capital letter, a number, and a special character.";
            }
            return "Registration failed due to incorrect username or password format.";
        }
    }

    public boolean loginUser(String username, String password) {
        return username.equals(registeredUsername) && password.equals(registeredPassword);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
//Adding tasks
    public void addTasks() {
        int numTasks = Integer.parseInt(JOptionPane.showInputDialog("How many tasks would you like to add?"));

        for (int i = 0; i < numTasks; i++) {
            String taskName = JOptionPane.showInputDialog("Enter Task Name:");

            String taskDescription = JOptionPane.showInputDialog("Enter Task Description (max 50 characters):");
            while (!checkTaskDescription(taskDescription)) {
                taskDescription = JOptionPane.showInputDialog("Please enter a task description of less than 50 characters.");
            }

            String developerName = JOptionPane.showInputDialog("Enter Developer Name:");
            int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter Task Duration (hours):"));

            String[] statusOptions = {"To Do", "Doing", "Done"};
            String taskStatus = (String) JOptionPane.showInputDialog(null, "Select Task Status:", "Task Status",
                    JOptionPane.QUESTION_MESSAGE, null, statusOptions, statusOptions[0]);

            String taskID = createTaskID(taskName, i, developerName);
            Task task = new Task(taskName, i, taskDescription, developerName, taskDuration, taskID, taskStatus);
            tasks.add(task);
            totalHours += taskDuration;

            JOptionPane.showMessageDialog(null, task.printTaskDetails(), "Task Details", JOptionPane.INFORMATION_MESSAGE);
        }

        JOptionPane.showMessageDialog(null, "Total task hours: " + totalHours, "Total Hours", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean checkTaskDescription(String description) {
        return description.length() <= 50;
    }

    public String createTaskID(String taskName, int taskNumber, String developerName) {
        return taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" +
                developerName.substring(developerName.length() - 3).toUpperCase();
    }

    public static void main(String[] args) {
        Login login = new Login();
//Declarations
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        String username = JOptionPane.showInputDialog("Enter username (must contain an underscore and be no more than 5 characters):");
        String password = JOptionPane.showInputDialog("Enter password (must be at least 8 characters, contain a capital letter, a number, and a special character):");

        String registrationResult = login.registerUser(username, password, firstName, lastName);
        JOptionPane.showMessageDialog(null, registrationResult);

        if (registrationResult.equals("Username and password successfully captured.")) {
            String loginUsername = JOptionPane.showInputDialog("Enter username:");
            String loginPassword = JOptionPane.showInputDialog("Enter password:");
            JOptionPane.showMessageDialog(null, login.returnLoginStatus(loginUsername, loginPassword));

            if (login.loginUser(loginUsername, loginPassword)) {
                boolean exit = false;
                while (!exit) {
                    String[] options = {"Add tasks", "Show report", "Quit"};
                    int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Menu",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                    switch (choice) {
                        case 0:
                            login.addTasks();
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, "Coming Soon.", "Report", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 2:
                            exit = true;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid option. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
