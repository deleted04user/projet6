package com.contactmanager;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.function.Function;

public class ContactManagerApp extends Application {
    private static final String[] GROUPS = {"Family", "Friends", "Work", "University", "Other"};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws SQLException {
        Database.initialize();
    }

    @Override
    public void start(Stage stage) {
        VBox contactsPanel = createContactsPanel();
        HBox.setHgrow(contactsPanel, Priority.ALWAYS);

        HBox layout = new HBox(20, createContactForm(), contactsPanel);
        layout.getStyleClass().add("app-layout");

        Scene scene = new Scene(layout, 1100, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Contact Manager");
        stage.setMinWidth(900);
        stage.setMinHeight(620);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createContactForm() {
        Label title = new Label("Contact Manager");
        title.getStyleClass().add("app-title");

        TextField name = new TextField();
        TextField phone = new TextField();
        TextField email = new TextField();
        ComboBox<String> group = new ComboBox<>();
        group.getItems().addAll(GROUPS);
        group.setPromptText("Select a group");

        VBox fields = new VBox(12,
                labeledField("Full name", name),
                labeledField("Phone number", phone),
                labeledField("Email (optional)", email),
                labeledField("Group", group));

        Button add = new Button("Add Contact");
        add.getStyleClass().add("primary-button");
        Button update = new Button("Update Contact");
        Button delete = new Button("Delete Contact");
        delete.getStyleClass().add("delete-button");
        Button clear = new Button("Clear Form");

        // Enable form actions when they are connected to their handlers.
        for (Button button : new Button[]{add, update, delete, clear}) {
            button.setDisable(true);
            button.setMaxWidth(Double.MAX_VALUE);
        }

        GridPane actions = new GridPane();
        actions.setHgap(8);
        actions.setVgap(8);
        // Give both button columns the same width.
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            actions.getColumnConstraints().add(column);
        }
        actions.addRow(0, add, update);
        actions.addRow(1, delete, clear);
        VBox panel = new VBox(24, title, fields, actions);
        panel.getStyleClass().addAll("panel", "form-panel");
        panel.setPrefWidth(312);
        panel.setMinWidth(312);
        return panel;
    }

    private VBox createContactsPanel() {
        Label title = new Label("Contacts");
        title.getStyleClass().add("section-title");

        TextField search = new TextField();
        search.setPromptText("Search by name or phone number");
        search.setAccessibleText("Search contacts by name or phone number");
        HBox.setHgrow(search, Priority.ALWAYS);

        ComboBox<String> groupFilter = new ComboBox<>();
        groupFilter.setAccessibleText("Filter by group");
        groupFilter.getItems().add("All Groups");
        groupFilter.getItems().addAll(GROUPS);
        groupFilter.getSelectionModel().selectFirst();
        groupFilter.setPrefWidth(144);

        TableView<Contact> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        table.setPlaceholder(new Label("No contacts to display"));
        table.getColumns().add(column("ID", 44, 56, Contact::getId));
        table.getColumns().add(column("Full Name", 98, 155, Contact::getName));
        table.getColumns().add(column("Phone Number", 124, 138, Contact::getPhone));
        table.getColumns().add(column("Email", 94, 176, Contact::getEmail));
        table.getColumns().add(column("Group", 75, 108, Contact::getGroupName));
        VBox.setVgrow(table, Priority.ALWAYS);

        Label total = new Label("Total contacts: 0");
        total.getStyleClass().add("contact-count");

        VBox panel = new VBox(16, title, new HBox(12, search, groupFilter), table, total);
        panel.getStyleClass().add("panel");
        return panel;
    }

    private VBox labeledField(String text, Control field) {
        Label label = new Label(text);
        label.setLabelFor(field);
        label.getStyleClass().add("field-label");
        field.setMaxWidth(Double.MAX_VALUE);
        return new VBox(6, label, field);
    }

    private <T> TableColumn<Contact, T> column(
            String heading, double minWidth, double prefWidth, Function<Contact, T> getValue) {
        TableColumn<Contact, T> column = new TableColumn<>(heading);
        column.setMinWidth(minWidth);
        column.setPrefWidth(prefWidth);
        column.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(getValue.apply(cell.getValue())));
        return column;
    }
}
