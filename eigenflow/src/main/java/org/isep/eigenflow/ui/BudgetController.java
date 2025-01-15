package org.isep.eigenflow.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.isep.eigenflow.domain.Project;

import org.isep.eigenflow.repo.ProjectRepository;
import javafx.scene.control.TextField;
import org.isep.eigenflow.domain.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetController {
    @FXML private ComboBox<Project> projectSelector;
    @FXML private Label totalBudgetLabel;
    @FXML private Label spentLabel;
    @FXML private Label remainingLabel;
    @FXML private ProgressBar budgetProgress;
    @FXML private TableView<Expense> expensesTable;
    @FXML private PieChart expensesPieChart;

    private ProjectRepository projectRepo = new ProjectRepository();

    @FXML
    public void initialize() {
        setupProjectSelector();
        setupExpensesTable();
    }

    @FXML
    private void handleAddExpense() {
        Project selected = projectSelector.getValue();
        if (selected == null) {
            showAlert("Error", "Please select a project first");
            return;
        }

        Dialog<Expense> dialog = new Dialog<>();
        dialog.setTitle("Add Expense");
        dialog.setHeaderText("Enter expense details");

        // dialog content
        GridPane grid = new GridPane();
        TextField descriptionField = new TextField();
        TextField amountField = new TextField();
        ComboBox<String> categoryBox = new ComboBox<>(FXCollections.observableArrayList(
                "Equipment", "Software", "Labor", "Other"
        ));

        grid.add(new Label("Description:"), 0, 0);
        grid.add(descriptionField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Category:"), 0, 2);
        grid.add(categoryBox, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    return new Expense(
                            descriptionField.getText(),
                            amount,
                            categoryBox.getValue(),
                            LocalDate.now()
                    );
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid amount");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(expense -> {
            selected.addExpense(expense); 
            projectRepo.updateProject(selected); 
            loadExpenses(selected); 
        });


    }

    private void refreshBudgetView() {
        Project selected = projectSelector.getValue();
        if (selected == null) return;

        totalBudgetLabel.setText(String.format("$%.2f", selected.getBudget()));
        spentLabel.setText(String.format("$%.2f", selected.getSpentBudget()));
        remainingLabel.setText(String.format("$%.2f", selected.getRemainingBudget()));

        double progress = selected.getSpentBudget() / selected.getBudget();
        budgetProgress.setProgress(progress);
        budgetProgress.setStyle(progress > 0.9 ? "-fx-accent: red;" : "-fx-accent: green;");

        
        loadExpenses(selected);
    }

   
    private void setupProjectSelector() {
        projectSelector.setItems(FXCollections.observableArrayList(projectRepo.getAllProjects()));
        projectSelector.setConverter(new StringConverter<>() {
            @Override
            public String toString(Project project) {
                return project == null ? "" : project.getProjectName();
            }

            @Override
            public Project fromString(String string) {
                return null;
            }
        });
        projectSelector.setOnAction(e -> refreshBudgetView());
    }

    private void setupExpensesTable() {
       
        expensesTable.getColumns().get(0).setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        expensesTable.getColumns().get(1).setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        expensesTable.getColumns().get(2).setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );
        expensesTable.getColumns().get(3).setCellValueFactory(
                new PropertyValueFactory<>("category")
        );

        expensesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        expensesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        expensesTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("amount"));
        expensesTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("category"));

        
        TableColumn<Expense, Double> amountCol = (TableColumn<Expense, Double>) expensesTable.getColumns().get(2);
        amountCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", amount));
                }
            }
        });
    }


    private void loadExpenses(Project project) {
        List<Expense> expenses = projectRepo.getExpensesForProject(project.getId());
        expensesTable.setItems(FXCollections.observableArrayList(expenses));
        expensesTable.refresh(); 


       
        Map<String, Double> expensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::category,
                        Collectors.summingDouble(Expense::amount)
                ));

        expensesPieChart.setData(
                expensesByCategory.entrySet().stream()
                        .map(e -> new PieChart.Data(
                                e.getKey() + " ($" + String.format("%.2f", e.getValue()) + ")",
                                e.getValue()
                        ))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

