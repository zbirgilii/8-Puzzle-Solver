import java.util.*;

public class BFS {
    public static List<Node> SolveInBFS(Node initial) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visitedNode = new HashSet<>();
        queue.offer(initial);
        visitedNode.add(initial);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.isGoal()) {
                return reconstructPath(current);
            }

            for (Node neighbor : current.getNeighbors()) {
                if (!visitedNode.contains(neighbor)) {
                    queue.offer(neighbor);
                    visitedNode.add(neighbor);
                }
            }
        }
        return null; // No solution found
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