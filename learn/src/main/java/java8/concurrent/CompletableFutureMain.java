package java8.concurrent;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/2/7
 */
public class CompletableFutureMain {

    public static void main(String[] args) {
        List<String> groupIds = Lists.newArrayList();
        Map<String, Long> countMap = new HashMap<>();
        for(int i = 0; i < 10; i++) {
            String groupId = "group_id_" + i;
            groupIds.add(groupId);
            countMap.put(groupId, 0L);
        }


        List<CompletableFuture<Void>> futures = groupIds.stream().map(groupId -> {
            return CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countMap.put(groupId, System.currentTimeMillis());
                System.out.println("This is Group" + groupId);
            });
        }).collect(Collectors.toList());

        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("Over");
    }

}
