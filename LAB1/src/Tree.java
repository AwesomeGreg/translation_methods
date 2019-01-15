import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {
    String node;

    List<Tree> children = new ArrayList<Tree>(0);

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }
}
