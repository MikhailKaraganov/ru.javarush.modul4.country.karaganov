package ru.jru.karaganov.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import org.hibernate.Session;
import ru.jru.connection.DBConnection;
import ru.jru.dao.CityDAO;
import ru.jru.entity.City;
import ru.jru.redis.Client;
import ru.jru.redis.Transformer;
import java.util.List;

import static java.util.Objects.nonNull;

public class DBPerformanceComparator {
    static final DBConnection dbConnection = new DBConnection();
    static final Session currentSession = dbConnection.getCurrentSession();
    static final CityDAO cityDAO = new CityDAO(City.class, currentSession.getSessionFactory());
    static final Client client = new Client();
    static final RedisClient redisClient = client.prepareRedisClient();
    static final ObjectMapper mapper = new ObjectMapper();
    static final Transformer transformator = new Transformer();

    private static void shutdown() {

        if (nonNull(currentSession)) {
            currentSession.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }

    public static void main(String[] args) {
//        List<City> all = cityDAO.findAll();
//        transformator.pushToRedis(transformator.transformData(all), redisClient, mapper);
        List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

        long startRedis = System.currentTimeMillis();
        client.testRedisData(ids, redisClient, mapper);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        dbConnection.testMysqlData(ids, cityDAO);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        shutdown();
    }

}

