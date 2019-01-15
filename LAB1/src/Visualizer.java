import javax.swing.*;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Visualizer extends JFrame {
    public Visualizer(Tree tree, String name) {
        super(name);

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            draw(graph, tree, parent, 400, 50, 80, 30);
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent component = new mxGraphComponent(graph);
        getContentPane().add(component);
    }

    private Object draw(mxGraph graph, Tree tree, Object parent, int left, int top, int len, int wid) {
        Object head = graph.insertVertex(parent, null, tree.node, left, top, len, wid);
        int cnt = 1;
        for (Tree child : tree.children) {
            Object edge = graph.insertEdge(parent, null, "", head,
                    draw(graph, child, parent,
                            (left - 60 * tree.children.size()) + cnt * 90, top + wid + 10,
                            len, wid));
            cnt++;
        }
        return head;
    }

    void run(Tree param_tree, String name) {
        JFrame frame = new Visualizer(param_tree, name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(frame.getWidth() + 40, frame.getHeight() + 40);
        frame.setVisible(true);
    }
}