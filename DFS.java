import java.util.*;

public class DFS {
    public static List<Node> SolveInDFS(Node initial) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visitedNodes = new HashSet<>();
        stack.push(initial);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current.isGoal()) {
                return reconstructPath(current);
            }

            if (!visitedNodes.contains(current)) {
                visitedNodes.add(current);
                for (Node neighbor : current.getNeighbors()) {
                    if (!visitedNodes.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return null; 
    }

    public static List<Node> reconstructPath(Node state) {
        List<Node> path = new ArrayList<>();
        while (state != null) {
            path.add(0, state);
            state = state.parent;
        }
        return path;
    }

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
}