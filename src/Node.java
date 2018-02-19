import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    private int x,y;
    private HashSet<Node> children;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.children = new HashSet<>();
    }

    public Set<Node> getChildren(){ return children;}
    public int X(){return x;}
    public int Y(){return y;}
    public int value(){return x*y;}
    public void addChild(Node c){children.add(c);}
    public void addChildren(Set<Node> c){children.addAll(c);}

    @Override
    public boolean equals(Object o){
        return(o instanceof Node && ((Node) o).x==this.x && ((Node) o).y==this.y);
    }
    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
}