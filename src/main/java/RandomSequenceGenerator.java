import java.util.Random;

public class RandomSequenceGenerator {
     int seed;

     Random random;

     int maxNum;

    public RandomSequenceGenerator(int setSeed, int setMaxNum) {
        this.seed = setSeed;
        this.random = new Random(setSeed);
        this.maxNum = setMaxNum;
    }

    public int next(){
        return this.random.nextInt(this.maxNum);
    }
}
