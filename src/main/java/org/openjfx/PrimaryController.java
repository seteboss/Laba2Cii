package org.openjfx;

import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import service.ART1;

public class PrimaryController {

    @FXML
    private TextField NNId;

    @FXML
    private TextField dId;

    @FXML
    private TextField pId;

    @FXML
    private TextField bId;

    @FXML
    private TextField statusId;

    @FXML
    private GridPane signsVectorsId;

    @FXML
    private GridPane prototypeVectorsId;

    @FXML
    private Button buttonId;

    @FXML
    void initialize() {
        ART1 art1 = new ART1();

        buttonId.setOnAction(actionEvent -> {
        try {
            statusId.clear();
            art1.setN(Integer.parseInt(NNId.getText()));
            art1.setP(Double.parseDouble(pId.getText()));
            art1.setD(Integer.parseInt(dId.getText()));
            art1.setBeta(Double.parseDouble(bId.getText()));
            art1.initSignsVector();
            art1.initPrototypeVectors();
            renderGridPaneSignsVectors(art1.getD());
            Integer[][] signsVector = art1.getSignsVectors();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < art1.getD(); j++) {
                    Label label = new Label(signsVector[i][j].toString());
                    GridPane.setHgrow(label, Priority.SOMETIMES);
                    GridPane.setVgrow(label, Priority.SOMETIMES);
                    signsVectorsId.add(label, j + 1, i + 1);
                }
            }
            art1.perform();
            renderGridPanePrototypeVectors(art1.getD());
            int[] clusterCof = art1.getClusterCof();
            int[] countPrintVectorInCluster = new int[art1.getN()];
            Arrays.fill(countPrintVectorInCluster, 0);
            Integer[] numberClusterForSignsVectors = art1.getNumberClusterForSignsVectors();
            for (int i = 0; i < 10; i++) {
                int numCluster = numberClusterForSignsVectors[i];
                int strIndex = clusterCof[numCluster] + countPrintVectorInCluster[numCluster]++;
                Label labelClusterNum = new Label(Integer.toString(numCluster));
                GridPane.setHgrow(labelClusterNum, Priority.SOMETIMES);
                GridPane.setVgrow(labelClusterNum, Priority.SOMETIMES);
                prototypeVectorsId.add(labelClusterNum, 0, strIndex + 1);
                for (int j = 0; j < art1.getD(); j++) {
                    Label label = new Label(signsVector[i][j].toString());
                    GridPane.setHgrow(label, Priority.SOMETIMES);
                    GridPane.setVgrow(label, Priority.SOMETIMES);
                    prototypeVectorsId.add(label, j + 1, strIndex + 1);
                }
                Label labelIndexVector = new Label(Integer.toString(i));
                GridPane.setHgrow(labelIndexVector, Priority.SOMETIMES);
                GridPane.setVgrow(labelIndexVector, Priority.SOMETIMES);
                prototypeVectorsId.add(labelIndexVector, art1.getD() + 1, strIndex + 1);
            }
            statusId.appendText("Выполнено");
        }catch (RuntimeException exception){
            statusId.appendText(exception.getMessage());
        }

        });

    }


    private void renderGridPaneSignsVectors(int d) {
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        signsVectorsId.setGridLinesVisible(false);
        signsVectorsId.getRowConstraints().clear();
        signsVectorsId.getColumnConstraints().clear();
        signsVectorsId.getChildren().clear();
        for (int i = 0; i < d + 1; i++) {
            signsVectorsId.getColumnConstraints()
                .add(new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, false));
        }
        for (int i = 0; i < 10 + 1; i++) {
            signsVectorsId.getRowConstraints()
                .add(new RowConstraints(-1, -1, -1, Priority.ALWAYS, VPos.CENTER, false));
        }
        signsVectorsId.setGridLinesVisible(true);
        for (int i = 1; i < d + 1; i++) {
            Label label = new Label(alphabet[i - 1]);
            GridPane.setHgrow(label, Priority.SOMETIMES);
            GridPane.setVgrow(label, Priority.SOMETIMES);
            signsVectorsId.add(label, i, 0);
        }
        for (int i = 0; i < 10; i++) {
            Label label = new Label(Integer.toString(i));
            GridPane.setHgrow(label, Priority.SOMETIMES);
            GridPane.setVgrow(label, Priority.SOMETIMES);
            signsVectorsId.add(label, 0, i + 1);
        }

    }

    private void renderGridPanePrototypeVectors(int d) {
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        prototypeVectorsId.setGridLinesVisible(false);
        prototypeVectorsId.getRowConstraints().clear();
        prototypeVectorsId.getColumnConstraints().clear();
        prototypeVectorsId.getChildren().clear();
        for (int i = 0; i < d + 2; i++) {
            prototypeVectorsId.getColumnConstraints()
                .add(new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, false));
        }
        for (int i = 0; i < 10; i++) {
            prototypeVectorsId.getRowConstraints()
                .add(new RowConstraints(-1, -1, -1, Priority.ALWAYS, VPos.CENTER, false));
        }
        prototypeVectorsId.setGridLinesVisible(true);
        for (int i = 1; i < d + 1; i++) {
            Label label = new Label(alphabet[i - 1]);
            GridPane.setHgrow(label, Priority.SOMETIMES);
            GridPane.setVgrow(label, Priority.SOMETIMES);
            prototypeVectorsId.add(label, i, 0);
        }
    }
}
