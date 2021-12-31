package efficient;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 对比几种随机生成String性能测试
 *
 * @author ZhouJing
 * @version 1.0
 * @date 2021/12/29 17:56
 */
@BenchmarkMode({Mode.Throughput})
@Warmup(iterations = 3)
@Measurement(iterations = 3, time = 5)
@Threads(value = 8)
@Fork(value = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class RandomTest {
    //    public final static char[] str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RandomTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void getRandomOne() {
        Random random = new Random();
        random.nextInt(62);
    }

    @Benchmark
    public void getRandomStringTwo() {
        Random random = new Random();
        random.nextInt(26);
    }
}
