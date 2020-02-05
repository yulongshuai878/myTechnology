package test;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-02-03 14:56
 **/
public class Test2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        DefaultNode defaultNode = new DefaultNode();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                String uuid = UUID.randomUUID().toString();
                Node node = new Node();
                node.setNodeValue(uuid);
                node.setNodeName(uuid);
                defaultNode.addChild(node);
            });
            t.setPriority(10);
            executorService.execute(t);
        }
        executorService.shutdown();
        System.out.println(defaultNode.getChildList().size());
    }
}
