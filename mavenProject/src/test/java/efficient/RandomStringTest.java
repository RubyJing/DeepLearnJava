package efficient;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 对比几种随机生成String性能测试
 *
 * @author ZhouJing
 * @version 1.0
 * @date 2021/12/29 17:56
 */
@BenchmarkMode({Mode.Throughput})
@Warmup(iterations =3)
@Measurement(iterations = 5, time = 5)
@Threads(value = 8)
@Fork(value = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class RandomStringTest {
    public final static char[] str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RandomStringTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
    
    @Benchmark
    public void getRandomStringOne() {
        getRandomStringOne(16);
    }

    @Benchmark
    public void getRandomStringTwo() {
        getRandomStringTwo(16);
    }

    @Benchmark
    public void getRandomStringUtil() {
        RandomStringUtils.randomAlphanumeric(16);
    }

    public static String getRandomStringOne(int length) {
        ThreadLocalRandom random =ThreadLocalRandom.current();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str[number]);
        }
        return sb.toString();
    }

    /**
     * 随机生成大小写字母和数字
     *
     * @param length 字符位数
     * @return String
     */
    public static String getRandomStringTwo(int length) {

        StringBuilder val = new StringBuilder();
        ThreadLocalRandom random =ThreadLocalRandom.current();

        // 参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }
}
