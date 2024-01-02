package com.zkml.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: LiuBingFei
 * @Date: 2021-04-09 15:30
 * @Description: 基于 redis 生成分布式订单号(时间戳+分布式自增Id)
 */
@Component
public class RedisOrderIDGenerateUtils {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    private static RedisOrderIDGenerateUtils redisOrderIDGenerateUtils = new RedisOrderIDGenerateUtils();

    @PostConstruct
    public void init() {
        redisOrderIDGenerateUtils.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 使用 前缀prefix + 14位时间戳 + expansion位自增Id (位数不够自动填充0)
     *
     * @param prefix    redis-key前缀
     * @param stepSize  默认初始自增步长，例如自增步长为2，初始1，则依次为3,5,7...
     * @param expansion 默认初始自增步长
     * @return
     */
    public static String getOrderId(String prefix, Integer stepSize, Integer expansion) {
        // 生成14位的时间戳(每秒使用新的时间戳当key)
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //返回订单号
        String orderNo = "";
        try {
            //redis-key前缀
            if (Objects.isNull(prefix)) {
                prefix = "";
            }
            // 默认初始自增步长为1
            if (Objects.isNull(stepSize)) {
                stepSize = 1;
            }
            // 默认初始自增步长为1
            if (Objects.isNull(stepSize)) {
                stepSize = 1;
            }
            // 获得redis-key
            String newKey = prefix + ":" + timeStamp;
            // 获取自增值（时间戳+自定义key）
            Long increment = redisOrderIDGenerateUtils.stringRedisTemplate.opsForValue().increment(newKey, stepSize);
            // 设置时间戳生成的key的有效期为2秒
            redisOrderIDGenerateUtils.stringRedisTemplate.expire(newKey, 2, TimeUnit.SECONDS);
            // 获取订单号，时间戳 + 唯一自增Id( 6位数,不过前方补0)
            String autoIncrementId = String.format("%0" + expansion + "d", increment);
            orderNo = prefix + timeStamp + autoIncrementId;
            return orderNo;
        } catch (Exception e) {
            Random random = new Random();
            //14位时间戳到 + expansion位随机数
            for (int i = 0; i < expansion; i++) {
                timeStamp += (random.nextInt(10) + "");
            }
            orderNo = prefix + timeStamp;
            return orderNo;
        }
    }
}
