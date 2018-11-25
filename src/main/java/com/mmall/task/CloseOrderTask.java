package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

//    // 版本一：没有分布式锁
//    @Scheduled(cron = "0 */1 * * * ?") // 每一分钟只需一次
//    public void closeOrderTaskV1() {
//        log.info("关闭订单定时任务启动");
//        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
//        iOrderService.closeOrder(hour);
//        log.info("关闭订单定时任务结束");
//    }

//    // 版本二：redis 分布式锁
//    @Scheduled(cron = "0 */1 * * * ?") // 每一分钟只需一次
//    public void closeOrderTaskV2() {
//        log.info("关闭订单定时任务启动");
//        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
//        String lockName = Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK;
//
//        // 步骤1：redis中setnx lockName
//        Long setnxResult = RedisShardedPoolUtil.setnx(lockName, String.valueOf(System.currentTimeMillis() + lockTimeout));
//        // 步骤2：如果成功，表示获取锁，执行定时任务逻辑
//        if (setnxResult != null && setnxResult == 1) {
//            // setnxResult == 1 表示set成功,获取到锁
//            closeOrder(lockName);
//
//        } else { // 步骤6：如果没有成功，表示获取锁失败，打印日志并返回
//            log.info("没有获取分布式锁{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
//        }
//
//        log.info("关闭订单定时任务结束");
//    }

    // 版本三：redis 分布式锁演进--双重防死锁
    @Scheduled(cron = "0 */1 * * * ?") // 每一分钟只需一次
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        String lockName = Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK;

        // 步骤1：redis中setnx lockName
        Long setnxResult = RedisShardedPoolUtil.setnx(lockName, String.valueOf(System.currentTimeMillis() + lockTimeout));
        // 步骤2：如果成功，表示获取锁，执行定时任务逻辑
        if (setnxResult != null && setnxResult == 1) {
            // setnxResult == 1 表示set成功,获取到锁
            closeOrder(lockName);

        } else { // 步骤6：如果没有成功，查看 lockName 是否已失效
            String lockValue = RedisShardedPoolUtil.get(lockName);
            // 步骤7：如果当前时间 大于 lockName 的value， 表示lockName已失效
            if (lockValue != null && System.currentTimeMillis() > Long.parseLong(lockValue)) {
                // 步骤8：给 lockName set新值，并返回旧值
                String getSetValue = RedisShardedPoolUtil.getset(lockName, String.valueOf(System.currentTimeMillis() + lockTimeout));
                // 步骤9：如果没有返回旧值，表示 lockName 已经不存在，当前线程可以获取锁
                // 如果返回的旧值等于 lockValue，表示在执行步骤7以后，没有其他线程修改 lockName的值，当前线程可以获取锁
                if (getSetValue == null || (getSetValue != null && StringUtils.equals(lockValue, getSetValue))) {
                    // 步骤10：执行业务逻辑
                    closeOrder(lockName);
                } else {
                    // 步骤11：获取锁失败，打印日志并返回
                    log.info("没有获取分布式锁{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                // 步骤11：获取锁失败，打印日志并返回
                log.info("没有获取分布式锁{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }

        log.info("关闭订单定时任务结束");
    }

    private void closeOrder(String lockName) {
        // 步骤3：设置key的有效期，防止死锁
        RedisShardedPoolUtil.expire(lockName, 50);
        log.info("获取{}，ThreadName：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        // 步骤4：执行业务逻辑
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        // 步骤5：业务逻辑执行完毕，删除 lockName
        RedisShardedPoolUtil.del(lockName);
        log.info("释放{}，ThreadName：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("==================================================");
    }
}
