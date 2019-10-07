package kostyan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import static kostyan.DataManager.*;

public class Controller {


    private List<String> list;

    private List<String> str;

    @FXML
    private TextArea textField;

    @FXML
    private Button convert;

    @FXML
    private Button clear;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        convert.setDisable(true);
        clear.setDisable(true);
    }

    @FXML
    public void showSaveProjectDialog() { // show dialog to create new project

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("saveproject.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e) {
            System.out.println("No dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            String fileName = controller.readFileName();
            saveToFile(fileName, list);
        } else {
            System.out.println("Canceled");
        }
    }

    @FXML
    public void action(ActionEvent e) { //event listener

        if (e.getSource().equals(convert)) { //conservationist

            str = readString(textField.getText()); // read strings from text area into List
            textField.clear(); // clear text area
            list = sortList(str); // sorting List
            printToTextField(list, textField);// print to text area
            convert.setDisable(true); // disable button to convert
        }

        if (e.getSource().equals(clear)) { //clearing

            if (!textField.getText().isEmpty()) {
                textField.clear();
                convert.setDisable(true);
                clear.setDisable(true);
            }
        }
    }

    @FXML
    public void handleKeyReleased() {

        String text = textField.getText();
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
        convert.setDisable(disableButtons);
        clear.setDisable(disableButtons);
    }


    private void saveToFile(String filename, List<String> list) { // save project in filename

        Path path = Paths.get(filename + ".txt");

        try (BufferedWriter bw = Files.newBufferedWriter(path)){

            for (String str : list) {
                bw.write(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void print() {
        printer(textField);
    }

    @FXML
    public void printer(Node node) {

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null && job.showPrintDialog(node.getScene().getWindow())) { // main string to invoke dialog

            boolean printed = job.printPage(node);

            if (printed) {
                job.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        } else {
            System.out.println("Could not create a printer job.");
        }
    }
}
