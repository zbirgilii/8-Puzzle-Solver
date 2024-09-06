import java.util.*;

public class Node {
    public int[][] board;
    public Node parent;
    public int row, column;
    private static final int[][] goal = {{1,2,3},{4,0,5},{6,7,8}};
    private static final int[][] movements = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public Node(int[][] board, Node parent) {
        this.board = board;
        this.parent = parent;
        findZero();
    }

    void findZero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    column = j;
                    return;
                }
            }
        }
    }

    public static int[][] generateRandomPuzzle() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Collections.shuffle(numbers);
        int[][] board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = numbers.get(i * 3 + j);
            }
        }
        return board;
    }

    public boolean isGoal() {
        return Arrays.deepEquals(board, goal);
    }

    public List<Node> getNeighbors() {
        List<Node> neighbors = new ArrayList<>();
        for (int[] move : movements) {
            int newRow = row + move[0];
            int newColumn = column + move[1];
            if (newRow >= 0 && newRow < 3 && newColumn >= 0 && newColumn < 3) {
                int[][] newBoard = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    newBoard[i] = board[i].clone();
                }
                newBoard[row][column] = newBoard[newRow][newColumn];
                newBoard[newRow][newColumn] = 0;
                neighbors.add(new Node(newBoard, this));
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node that = (Node) obj;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}