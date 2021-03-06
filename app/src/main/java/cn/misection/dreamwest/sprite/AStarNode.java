package cn.misection.dreamwest.sprite; /**
 * @author soulnew@soulnew.com
 */

import java.util.LinkedList;
import java.util.List;

public class AStarNode {

    public int x, y;
    String name;
    int costFromStart;
    int estimatedCostToGoal;
    AStarNode pathParent;
    List neighbors;
    /**
     * Creates a new instance of AStarNode
     */
    public AStarNode(String name, int x, int y) {
        neighbors = new LinkedList();
        this.x = x;
        this.y = y;
        this.name = name;
    }
    public AStarNode(int x, int y) {
        neighbors = new LinkedList();
        this.x = x;
        this.y = y;
        this.name = "(" + x + "," + y + ")";
    }

    public String toString() {
        return name;
    }

    public int getEstimatedCost(AStarNode node) { //
        int dx = this.x - node.x;
        int dy = this.y - node.y;
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    public List getNeighbors() {
        return neighbors;
    }

    public int getCost(AStarNode node) {
        int dx = this.x - node.x;
        int dy = this.y - node.y;
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AStarNode) {
            AStarNode node1 = (AStarNode) obj;
            return this.x == node1.x && this.y == node1.y;
        }
        return super.equals(obj);
    }
}
