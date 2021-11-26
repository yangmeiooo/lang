package test;

import com.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RLockTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testRLock() throws InterruptedException {
        new Thread(this::testLockOne).start();
        new Thread(this::testLockTwo).start();

        TimeUnit.SECONDS.sleep(200);
    }

    public void testLockOne(){
        try {
            RLock lock = redissonClient.getLock("bravo1988_distributed_lock");
            log.info("testLockOne尝试加锁...");
            lock.lock();
            log.info("testLockOne加锁成功...");
            log.info("testLockOne业务开始...");
            TimeUnit.SECONDS.sleep(50);
            log.info("testLockOne业务结束...");
            lock.unlock();
            log.info("testLockOne解锁成功...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testLockTwo()  {
        try {
            RLock lock = redissonClient.getLock("bravo1988_distributed_lock");
            log.info("testLockTwo尝试加锁...");
            lock.lock();
            log.info("testLockTwo加锁成功...");
            log.info("testLockTwo业务开始...");
            TimeUnit.SECONDS.sleep(50);
            log.info("testLockTwo业务结束...");
            lock.unlock();
            log.info("testLockTwo解锁成功...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
