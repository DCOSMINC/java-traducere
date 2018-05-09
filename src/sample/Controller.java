package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;


public class Controller {
    @FXML
    private TableView<Translation> tableView;

    private int count = 3;

    @FXML
    private void initialize() {
        restoreState();
        tableView.getSelectionModel().cellSelectionEnabledProperty().set(true);
        tableView.setOnKeyPressed(e -> {
            if(e.getCode().isLetterKey() || e.getCode().isDigitKey()) {
                editFocusedCell();
                e.consume();
            }
        });
    }

    @FXML
    private void addNewTag() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Add new Translation");

        Label label = new Label("Enter values!");
        Button button = new Button("Add");

        List<Label> labelList= new ArrayList<>();
        List<TextField> textFieldList = new ArrayList<>();

        for(int i = 0; i < tableView.getColumns().size(); i++) {
            Object column = tableView.getColumns().get(i);
            labelList.add(new Label(((TableColumn) column).getText()));
            textFieldList.add(new TextField());
        }

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(8);
        gridPane.add(label, 1, 0);
        for(int i = 0; i < tableView.getColumns().size(); i++) {
            gridPane.add(labelList.get(i), 1, i + 1);
            gridPane.add(textFieldList.get(i), 2, i + 1);
        }

        List<String> list = new ArrayList<>();
        for(int i = 0; i < tableView.getColumns().size(); i++) {
            list.add(textFieldList.get(i).getText());
        }

        button.setOnAction(e-> {
            tableView.getItems().add(new Translation(textFieldList.get(0).getText(),textFieldList.get(1).getText(), textFieldList.get(2).getText()));
            popup.close();
        });
        gridPane.add(button, 2,0);

        Scene scene = new Scene(gridPane, 480, 240);
        popup.setScene(scene);
        popup.showAndWait();


    }

    @FXML
    private void addNewLanguageColumn() {
        Stage getNameOfNewColumn = new Stage();
        getNameOfNewColumn.initModality(Modality.APPLICATION_MODAL);
        getNameOfNewColumn.setTitle("New Language");

        Label label = new Label("Enter the title of the new column!");

        TextField textField = new TextField();
        textField.setMaxWidth(200);

        Button button = new Button("Add Column");
        button.setOnAction(e-> {
            if(!textField.getText().isEmpty()) {
                TableColumn<Translation, String> column = new TableColumn();
                column.setText(textField.getText());
                column.setCellValueFactory(new PropertyValueFactory("traducere" + count++));
                column.setPrefWidth(150);
                tableView.getColumns().add(column);
                getNameOfNewColumn.close();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, textField, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 200);
        getNameOfNewColumn.setScene(scene);
        getNameOfNewColumn.showAndWait();

    }

    @FXML
    private void saveProject() {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("save_state.txt")))) {
//            bufferedWriter.write(String.valueOf(tableView.getColumns().size()));
//            bufferedWriter.newLine();
            for(Object row : tableView.getItems()) {
                for(TableColumn column : tableView.getColumns()) {
                    bufferedWriter.write((String)column.getCellObservableValue(row).getValue());
                    bufferedWriter.newLine();
                }
            }
        } catch(IOException e) {
            e.getMessage();
        }
    }

    private void restoreState() {
        try(Scanner scanner = new Scanner(new FileReader(new File("save_state.txt")))) {
//            int length = scanner.nextInt();
//            List<String> translationParList = new ArrayList<>();
            while(scanner.hasNext()) {
                String param1 = scanner.nextLine();
                String param2 = scanner.nextLine();
                String param3 = scanner.nextLine();
                tableView.getItems().add(new Translation(param1, param2, param3));
            }

        } catch(IOException e) {
            e.getMessage();
        }
    }
    @FXML
    private void exportToFile() {
        for(Translation translation : tableView.getItems()) {
            WriteXmlToFile.writeInputToFileInRomanian(translation.getTagName(), translation.getTranslation1());
            WriteXmlToFile.writeInputToFileInEnglish(translation.getTagName(), translation.getTranslation2());
        }
    }

    private void editFocusedCell() {
        tableView.setEditable(true);
        for(TableColumn column : tableView.getColumns()) {
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Translation, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Translation, String> event) {
                    if("Nume Tag".equals(event.getTableColumn().getText())) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setTagName(event.getNewValue());
                    } else if ("Traducere Ro".equals(event.getTableColumn().getText())) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setTranslation1(event.getNewValue());
                    } else if ("Traducere En".equals(event.getTableColumn().getText())) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setTranslation2(event.getNewValue());
                    }
                    event.consume();
                }
            });
        }
    }

}
