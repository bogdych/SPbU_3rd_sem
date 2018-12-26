package jmh;


import org.openjdk.jmh.annotations.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import filter.*;

import javax.imageio.ImageIO;

@State(Scope.Thread)
public class Test {
    public static int HORIZONTAL_MODE = 0;
    public static int VERTIACAL_MODE = 1;

    @State(Scope.Thread)
    public static class HighRes {
        public Filter filter;
        @Setup
        public void setup () {
            BufferedImage src = null;
            try {
                src = ImageIO.read(new File("D:\\JMH\\Filter\\res\\highres.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            filter = new Filter(src);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 2)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_HorizontalMode_1Thread(HighRes img) {
        try {
            img.filter.getImage(1, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_HorizontalMode_2Threads(HighRes img) {
        try {
            img.filter.getImage(2, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_HorizontalMode_4Threads(HighRes img) {
        try {
            img.filter.getImage(4, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_HorizontalMode_8Threads(HighRes img) {
        try {
            img.filter.getImage(8, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_VerticalMode_1Thread(HighRes img) {
        try {
            img.filter.getImage(1, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_VerticalMode_2Threads(HighRes img) {
        try {
            img.filter.getImage(2, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_VerticalMode_4Threads(HighRes img) {
        try {
            img.filter.getImage(4, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void HR_VerticalMode_8Threads(HighRes img) {
        try {
            img.filter.getImage(8, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    @State(Scope.Thread)
    public static class MediumRes {
        public Filter filter;
        @Setup
        public void setup () {
            BufferedImage src = null;
            try {
                src = ImageIO.read(new File("D:\\JMH\\Filter\\res\\mediumres.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            filter = new Filter(src);
        }
    }



    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_HorizontalMode_1Thread(MediumRes img) {
        try {
            img.filter.getImage(1, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_HorizontalMode_2Threads(MediumRes img) {
        try {
            img.filter.getImage(2, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_HorizontalMode_4Threads(MediumRes img) {
        try {
            img.filter.getImage(4, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_HorizontalMode_8Threads(MediumRes img) {
        try {
            img.filter.getImage(8, HORIZONTAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_VerticalMode_1Thread(MediumRes img) {
        try {
            img.filter.getImage(1, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_VerticalMode_2Threads(MediumRes img) {
        try {
            img.filter.getImage(2, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_VerticalMode_4Threads(MediumRes img) {
        try {
            img.filter.getImage(4, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, batchSize = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Threads(1)
    public void MR_VerticalMode_8Threads(MediumRes img) {
        try {
            img.filter.getImage(8, VERTIACAL_MODE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}