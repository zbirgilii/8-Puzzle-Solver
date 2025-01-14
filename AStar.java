import java.util.*;

public class AStar {
    public List<Node> solveInAStar(Node startNode) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        Map<Node, Integer> gScores = new HashMap<>();
        Set<Node> closedSet = new HashSet<>();
        
        openSet.add(startNode);
        gScores.put(startNode, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.isGoal()) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Node neighbor : current.getNeighbors()) {
                if (closedSet.contains(neighbor)) continue;

                int tentative_g = gScores.get(current) + 1;

                if (tentative_g < gScores.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    neighbor.parent = current;
                    gScores.put(neighbor, tentative_g);
                    neighbor.setG(tentative_g);
                    neighbor.setF(tentative_g + neighbor.getH());

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return null; // No solution found
    }

    private List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    public static boolean isSolvable(int[][] board) {
        int inversions = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (board[i/3][i%3] != 0 && board[j/3][j%3] != 0 && 
                    board[i/3][i%3] > board[j/3][j%3]) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }
}