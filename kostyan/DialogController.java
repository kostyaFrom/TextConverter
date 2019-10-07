package kostyan;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DialogController {

    @FXML
    private TextField saveField;

    public String readFileName() {

        String fileName = saveField.getText().trim();

        if (fileName.isEmpty()) {
            saveField.setText("Enter file name");
            return "";
        } else {
            return fileName;
        }
    }
}
