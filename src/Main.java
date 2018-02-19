import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;

public class Main {

    static Map<Integer,Integer> dict = new HashMap<>();

    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String input = in.readLine();
        String[] temp = input.split(" ");

        int m = Integer.parseInt(temp[0]);
        int n = Integer.parseInt(temp[1]);
        int index = Integer.parseInt(temp[2]);

        int max = Math.max(m,n);
        int min = Math.min(m,n);

        Node node = new Node(1,1);
        int dir = 1;
        if(m*n - index < index) {
            node = new Node(m, n);
            dir = -1;
            index = m*n-index;
        }


        Instant before = Instant.now();

        index--;
        while(index>0){
            if(!(node.X()==node.Y()&&node.X()!=1))
                if(!(node.X()+dir > max))
                    node.addChild(new Node(node.X()+dir,node.Y()));
                if(!(node.Y()+dir > Math.min(min,node.X())))
                    node.addChild(new Node(node.X(), node.Y()+dir));

            Set<Node> children = node.getChildren();
            Node next = node.getChildren().stream()
                    .sorted(Comparator.comparing(Node::value))
                    .findFirst().get();
            children.remove(next);
            node = next;
            node.addChildren(children);
            if(node.X() == node.Y() || node.X() > min) index--;
            else index-=2;
        }
        System.out.println(node.value());


        Instant after = Instant.now();
        System.out.println(after.getEpochSecond() - before.getEpochSecond());

    }
//
//    static void recursivePath(Tree.Node<Integer> node, int max, int min){
//        if (node.getIndex() == 0)  System.out.println(node.value());
//        if(node.X() > max || node.Y() > Math.min(min,node.X())) return;
//        System.out.println("index: " + node.getIndex() + "\t"  + node.X() + " * " + node.Y() + " = " + node.value());
//
//        Tree.Node<Integer> left = new Tree.Node<>();
//        if(node.X() == node.Y() || node.X() > min) index--;
//        else index-=2;
//
//        if((x+1)*y < x*(y+1)){
//            recursivePath(max, min, index, x+1, y);
//            recursivePath(max, min, index, x, y+1);
//        } else {
//            recursivePath(max, min, index, x, y+1);
//            recursivePath(max, min, index, x+1, y);
//        }
//
//    }
//
//    static int getPosition(){
//        return dict.values().stream().mapToInt(Number::intValue).sum();
//    }
}


//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//public class Main {
//
//    public static void main(String[] args) throws Exception {
//        InputStreamReader reader = new InputStreamReader(System.in);
//        BufferedReader in = new BufferedReader(reader);
//        String input = in.readLine();
//        String [] temp = input.split(" ");
//
//        int m = Integer.parseInt(temp[0]);
//        int n = Integer.parseInt(temp[1]);
//        int index = Integer.parseInt(temp[2]);
//
//        int x = Math.min(m,n);
//        int pos = x*(x+1)/2;
//        int max = pos%x==0 ? (pos/x)*(pos/x) : ((pos-1)/x)*((pos-1)/x) - (pos-1);
//
//        int diffEnd = m*n - index;
//        int diffMid = index - pos;
//
//        if(index>pos) {
//            if (diffEnd < index)
//                countFromEnd(m,n,index);
//            else
//                countFromPos(m,n,index-pos);
//        } else {
//            countFromStart(m,n,index);
//        }
//    }
//
//    private static void countFromPos(int m, int n, int i) {
//        int x = Math.min(m,n);
//        for(int i = 1; i <= x; i++){
//            for(int j= x+1; j <= Math.max(m,n); j++){
//
//            }
//        }
//    }
//}