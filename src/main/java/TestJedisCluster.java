import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import java.util.*;

import java.util.HashSet;

public class TestJedisCluster {
    final static String LOCALHOST = "127.0.0.1";
    final static int PORT = 5000;
    final static int NUM_CLUSTERS = 3;
    final static int NUM_REPLICATIONS = 2;

    public static void main(String[] args) {

        Set<HostAndPort> nodes = new HashSet<HostAndPort>();

        for (int i = 0; i < NUM_CLUSTERS * NUM_REPLICATIONS; i++) {
            nodes.add(new HostAndPort(LOCALHOST, PORT + i));
        }

        JedisCluster cluster = new JedisCluster(nodes);

        cluster.set("location", "Seattle");

        System.out.println("Value received from Redis: " + cluster.get("location"));

        cluster.close();
    }
}