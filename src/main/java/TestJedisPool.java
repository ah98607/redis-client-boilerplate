import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.*;

import java.util.HashMap;

public class TestJedisPool {

    final static String LOCALHOST = "127.0.0.1";
    final static int PORT = 6379;

    public static void main(String[] args) {

        JedisPool pool = new JedisPool(LOCALHOST, PORT);

        Jedis jedis = null;
        try {
            jedis = pool.getResource();

            // string type
            jedis.set("stringData", "hello");
            System.out.println("Value received from Redis: " + jedis.get(" stringData"));
            if (!jedis.exists("unspecifiedKey")) {
                System.out.println("Key not found");
            }

            // hash type
            Map<String, String> userData = new HashMap<String, String>();
            userData.put("country", "USA");
            userData.put("state", "Washington");
            userData.put("city", "Seattle");
            jedis.hset("hashData", userData);
            System.out.print("Keys in this hash ");
            for (String key : jedis.hkeys("hashData")) {
                System.out.print(key + " ");
            }
            System.out.print("Values in this hash ");
            for (String key : jedis.hvals("hashData")) {
                System.out.print(key + " ");
            }
            Map<String, String> outputHashData = jedis.hgetAll("hashData");
            for (Map.Entry<String, String> entry : outputHashData.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            jedis.hset("hashData", "city", "Camas");
            System.out.println("Changed city: " + jedis.hget("hashData", "city"));

            // list
            for (int i = 0; i < 5; i++) {
                jedis.lpush("listData", "listItem" + i);
            }
            for (int i = 0; i < 5; i++) {
                jedis.rpush("listData", "listItem" + String.valueOf(i + 5));
            }
            System.out.print("left pop: ");
            for (int i = 0; i < 5; i++) {
                System.out.print(jedis.lpop("listData") + " ");
            }
            System.out.print("\nright pop");
            for (int i = 0; i < 5; i++) {
                System.out.print(jedis.rpop("listData") + " ");
            }

            // set
            for (int i = 0; i < 5; i++) {
                jedis.sadd("setData", "setItem" + i);
            }
            System.out.print("\nset members: ");
            for (String setItem : jedis.smembers("setData")) {
                System.out.print(setItem + " ");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
            if (pool != null) {
                pool.close();
            }
        }
    }
}