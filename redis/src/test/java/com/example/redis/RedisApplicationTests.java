package com.example.redis;

import com.example.redis.config.RedisLockService;
import com.example.redis.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLockService redisLockService;

    @Test
    public void test() throws Exception {
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(uuid, uuid, 60, TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.opsForValue().get(uuid));
    }

    @Test
    public void stringTest() throws Exception {
        // 设置值，默认不过期
        stringRedisTemplate.opsForValue().set("userName", "张三");

        // 获取值
        String value = stringRedisTemplate.opsForValue().get("userName");
        System.out.println("获取userName对应的值：" + value);

        // 设置值并且设置2秒过期时间，过期之后自动删除
        stringRedisTemplate.opsForValue().set("email", "123@123.com", 2, TimeUnit.SECONDS);
        Thread.sleep(1000);
        System.out.println("获取email过期时间（单位秒）：" + stringRedisTemplate.getExpire("email"));
        System.out.println("获取email对应的值：" + stringRedisTemplate.opsForValue().get("email"));
        Thread.sleep(1000);
        System.out.println("获取email对应的值：" + stringRedisTemplate.opsForValue().get("email"));

        // 删除key
        Boolean result = stringRedisTemplate.delete("userName");
        System.out.println("删除userName结果：" + result);
    }

    @Test
    public void objectTest() throws Exception {
        // 设置对象值，并且2秒自动过期
        ValueOperations<String, UserVo> operations = redisTemplate.opsForValue();
        UserVo user = new UserVo("aa@126.com", "张三");
        operations.set("user", user, 2, TimeUnit.SECONDS);

        //获取对象值
        UserVo userVo = operations.get("user");
        System.out.println(userVo.toString());
        System.out.println("获取user过期时间（单位秒）：" + redisTemplate.getExpire("user"));


        //删除key
        Boolean deleteValue = redisTemplate.delete("user");
        System.out.println("删除userName结果：" + deleteValue);
    }


    //list
    @Test
    public void ListTest() throws Exception {
        // 向列表中添加数据
        ListOperations<String, UserVo> operations = redisTemplate.opsForList();
        // 往List左侧插入一个元素
        operations.leftPush("userList", new UserVo("aa@126.com", "张三"));
        operations.leftPush("userList", new UserVo("bb@126.com", "里斯"));
        //往 List 右侧插入一个元素
        operations.rightPush("userList", new UserVo("cc@126.com", "王五"));
        operations.rightPush("userList", new UserVo("dd@126.com", "赵六"));
        // 获取List 大小
        Long size = operations.size("userList");
        System.out.println("获取列表总数：" + size);
        //遍历整个List
        List<UserVo> allUserVo1 = operations.range("userList", 0, size);
        System.out.println("遍历列表所有数据：" + allUserVo1.toString());
        //遍历整个List，-1表示倒数第一个即最后一个
        List<UserVo> allUserVo2 = operations.range("userList", 0, -1);
        System.out.println("遍历列表所有数据：" + allUserVo2.toString());
        //从 List 左侧取出第一个元素，并移除
        Object userVo1 = operations.leftPop("userList", 200, TimeUnit.MILLISECONDS);
        System.out.println("从左侧取出第一个元素并移除：" + userVo1.toString());
        //从 List 右侧取出第一个元素，并移除
        Object userVo2 = operations.rightPop("userList", 200, TimeUnit.MILLISECONDS);
        System.out.println("从右侧取出第一个元素并移除：" + userVo2.toString());

    }


    //hash
    @Test
    public void hashTest() throws Exception {
        // 向hash中添加数据
        HashOperations<String, String, Integer> operations = redisTemplate.opsForHash();
        //Hash 中新增元素。
        operations.put("score", "张三", 2);
        operations.put("score", "里斯", 1);
        operations.put("score", "王五", 3);
        operations.put("score", "赵六", 4);

        Boolean hasKey = operations.hasKey("score", "张三");
        System.out.println("检查是否存在【score】【张三】：" + hasKey);
        Integer value = operations.get("score", "张三");
        System.out.println("获取【score】【张三】的值：" + value);
        Set<String> keys = operations.keys("score");
        System.out.println("获取hash表【score】所有的key集合：" + keys);
        List<Integer> values = operations.values("score");
        System.out.println("获取hash表【score】所有的value集合：" + values);
        Map<String, Integer> map = operations.entries("score");
        System.out.println("获取hash表【score】下的map数据：" + map);
        Long delete = operations.delete("score", "里斯");
        System.out.println("删除【score】中key为【里斯】的数据：" + delete);
        Boolean result = redisTemplate.delete("score");
        System.out.println("删除整个key：" + result);

    }


    //集合
    @Test
    public void setTest() throws Exception {
        // 向集合中添加数据
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        //向集合中添加元素,set元素具有唯一性
        operations.add("city", "北京", "上海", "广州", "深圳", "武汉");
        Long size = operations.size("city");
        System.out.println("获取集合总数：" + size);
        //判断是否是集合中的元素
        Boolean isMember = operations.isMember("city", "广州");
        System.out.println("检查集合中是否存在指定元素：" + isMember);
        Set<String> cityNames = operations.members("city");
        System.out.println("获取集合所有元素：" + (cityNames));
        Long remove = operations.remove("city", "广州");
        System.out.println("删除指定元素结果：" + remove);
        //移除并返回集合中的一个随机元素
        String cityName = operations.pop("city");
        System.out.println("移除并返回集合中的一个随机元素：" + cityName);
    }

    //有序集合
    @Test
    public void zsetTest() throws Exception {
        // 向有序集合中添加数据
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        //向有序集合中添加元素,set元素具有唯一性
        operations.add("cityName", "北京", 100);
        operations.add("cityName", "上海", 95);
        operations.add("cityName", "广州", 75);
        operations.add("cityName", "深圳", 85);
        operations.add("cityName", "武汉", 70);

        //获取变量指定区间的元素。0, -1表示全部
        Set<String> ranges = operations.range("cityName", 0, -1);
        System.out.println("获取有序集合所有元素：" + ranges);
        Set<String> byScores = operations.rangeByScore("cityName", 85, 100);
        System.out.println("获取有序集合所有元素（按分数从小到大）：" + byScores);
        Long zCard = operations.zCard("cityName");
        System.out.println("获取有序集合成员数: " + zCard);
        Long remove = operations.remove("cityName", "武汉");
        System.out.println("删除某个成员数结果: " + remove);

    }

    @Test
    public void lockTest(){

        //分布式加锁，5秒自动过期
        boolean lock = redisLockService.tryLock("LOCK", "test", Duration.ofSeconds(10));
        System.out.println("加锁结果1：" +  lock);
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean unlock = redisLockService.releaseLock("LOCK", "test");
        System.out.println("解锁结果1：" +  unlock);
    }
    @Test
    public void lockTest2(){

        //分布式加锁，5秒自动过期
        boolean lock = redisLockService.tryLock("LOCK", "test", Duration.ofSeconds(10));
        System.out.println("加锁结果2：" +  lock);
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        boolean unlock = redisLockService.releaseLock("LOCK", "test");
        System.out.println("解锁结果2：" +  unlock);
    }
}
