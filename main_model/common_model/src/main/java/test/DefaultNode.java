package test;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-02-03 14:20
 **/
public class DefaultNode {
    /**
     * The list of all child nodes.
     */
    private volatile Set<Node> childList = new HashSet<>();


    /**
     * Add child node to current node.
     *
     * @param node valid child node
     */
    public void addChild(Node node) {
        if (node == null) {
            //    RecordLog.warn("Trying to add null child to node <{0}>, ignored");
            return;
        }
        if (!childList.contains(node)) {
            synchronized (this) {
                if (!childList.contains(node)) {
                    Set<Node> newSet = new HashSet<>(childList.size() + 1);
                    newSet.addAll(childList);
                    newSet.add(node);
                    childList = newSet;
                }
            }
            //  RecordLog.info("Add child <{0}> to node <{1}>", ((DefaultNode)node).id.getName(), id.getName());
        }
    }
    /**
     * Reset the child node list.
     */
    public void removeChildList() {
        this.childList = new HashSet<>();
    }

    public Set<Node> getChildList() {
        return childList;
    }

}
