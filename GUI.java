import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GUI extends Application {

    private Node currentState;
    private GridPane puzzleGrid;
    private Label statusLabel;
    private Button nextStepButton;
    private List<Node> solution;
    private int currentStep;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("8-Puzzle Solver");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        
        puzzleGrid = new GridPane();
        puzzleGrid.setHgap(5);
        puzzleGrid.setVgap(5);

        
        Button newPuzzleButton = new Button("New Puzzle");
        ComboBox<String> algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll("BFS", "DFS");
        algorithmChoice.setValue("BFS");
        Button solveButton = new Button("Solve");
        nextStepButton = new Button("Next Step");
        nextStepButton.setDisable(true);

        HBox controlBox = new HBox(10, newPuzzleButton, algorithmChoice, solveButton, nextStepButton);

        
        statusLabel = new Label("Generate a new puzzle to begin.");

        
        root.getChildren().addAll(puzzleGrid, controlBox, statusLabel);

        
        newPuzzleButton.setOnAction(e -> generateNewPuzzle());
        solveButton.setOnAction(e -> solvePuzzle(algorithmChoice.getValue().equals("BFS")));
        nextStepButton.setOnAction(e -> showNextStep());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        generateNewPuzzle();
    }

    private void generateNewPuzzle() {
        currentState = new Node(Node.generateRandomPuzzle(), null);
        updatePuzzleDisplay();
        statusLabel.setText("New puzzle generated. Choose an algorithm and click Solve.");
        solution = null;
        nextStepButton.setDisable(true);
    }

    private void solvePuzzle(boolean useBFS) {
        if (currentState == null) {
            statusLabel.setText("Please generate a new puzzle first.");
            return;
        }

        solution = useBFS ? BFS.SolveInBFS(currentState) : DFS.SolveInDFS(currentState);
        
        if (solution != null) {
            statusLabel.setText("Solution found in " + (solution.size() - 1) + " moves. Click 'Next Step' to see the solution.");
            currentStep = 0;
            nextStepButton.setDisable(false);
        } else {
            statusLabel.setText("No solution found.");
        }
    }

    private void showNextStep() {
        if (solution != null && currentStep < solution.size()) {
            currentState = solution.get(currentStep);
            updatePuzzleDisplay();
            currentStep++;

            if (currentStep >= solution.size()) {
                statusLabel.setText("Solution complete!");
                nextStepButton.setDisable(true);
            } else {
                statusLabel.setText("Step " + currentStep + " of " + (solution.size() - 1));
            }
        }
    }

    private void updatePuzzleDisplay() {
        puzzleGrid.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = currentState.board[i][j];
                Button tile = new Button(value == 0 ? " " : String.valueOf(value));
                tile.setPrefSize(50, 50);
                puzzleGrid.add(tile, j, i);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}