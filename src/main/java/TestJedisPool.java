import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestJedisPool {

    final static String LOCALHOST = "127.0.0.1";
    final static int PORT = 6379;

    public static void main(String[] args) {

        JedisPool pool = new JedisPool(LOCALHOST, PORT);

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set("name", "Andy");
            System.out.println("Value received from Redis: " + jedis.get(" name"));
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