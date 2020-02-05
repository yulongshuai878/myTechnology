package test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-02-03 14:06
 **/
public class Test1 {
    public static void main(String[] args) throws Exception{
        DefaultNode defaultNode = new DefaultNode();
        for(int i = 0 ; i < 10000;i++) {
           Thread thread = new Thread(()->{
                Node node = new Node();
                node.setNodeName(UUID.randomUUID().toString());
                node.setNodeValue(UUID.randomUUID().toString());
                defaultNode.addChild(node);
            });
            thread.start();

        }
        Thread.sleep(10000);
        Set<Node> set = defaultNode.getChildList();
        System.out.println(set.size());
        Set<Node> set1 = new HashSet<>();
        for(int i = 0 ; i < 10000;i++) {
            Thread thread = new Thread(()->{
                Node node = new Node();
                node.setNodeName(UUID.randomUUID().toString());
                node.setNodeValue(UUID.randomUUID().toString());
                set1.add(node);
            });
            thread.start();
        }
        Thread.sleep(10000);
        System.out.println(set1.size());
    }
}
