package hr.fer.mekorac.genetic;

import hr.fer.mekorac.genetic.reproduce.Reproduce;
import hr.fer.mekorac.genetic.reproduce.ReproduceAlgs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GeneticAlgorithm {
    protected int POP_SIZE;
    protected double MUTATION;
    protected int ITERS;

    protected double MSE_BOUND = 0.001;

    protected int BETAS_NUMBER = 5;
    protected double RANGE_MIN = -4;
    protected double RANGE_MAX = 4;


    protected List<Beta> betas = new ArrayList<>();
    protected Beta best;

    class Param {
        public double x;
        public double y;
        public double out;

        public Param(double x, double y, double out) {
            this.x = x;
            this.y = y;
            this.out = out;
        }
    }

    List<Param> params = new ArrayList<>();


    public GeneticAlgorithm(int POP_SIZE, double MUTATION, int ITERS, Path file) {
        this.POP_SIZE = POP_SIZE;
        this.MUTATION = MUTATION;
        this.ITERS = ITERS;

        for(int i = 0; i < POP_SIZE; i++) betas.add(new Beta(new Double[BETAS_NUMBER]));

        List<String> lines = null;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line: lines) {
            String[] splitted = line.split("\t");
            params.add(new Param(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2])));
        }
    }

    public GeneticAlgorithm(int POP_SIZE, double MUTATION, Path file) {
        this(POP_SIZE, MUTATION, 1000, file);
    }

    public GeneticAlgorithm(double MUTATION, int ITERS, Path file) {
        this(20, MUTATION, ITERS, file);
    }

    public GeneticAlgorithm(int POP_SIZE, int ITERS, Path file) {
        this(POP_SIZE, 0.01, ITERS, file);
    }

    public GeneticAlgorithm(int POP_SIZE, Path file) {
        this(POP_SIZE, 0.01, 1000, file);
    }

    public GeneticAlgorithm(double MUTATION, Path file) {
        this(20, MUTATION, 1000, file);
    }

    protected void initBetas() {
        Random rand = new Random();
        for(int i = 0; i < POP_SIZE; i++)
            for(int j = 0; j < BETAS_NUMBER; j++)
                betas.get(i).bs[j] = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * rand.nextDouble();
    }

    abstract Beta run();

    abstract Beta selection();

    protected Beta reproduction(Beta b1, Beta b2) {
        return ReproduceAlgs.getRandom().repr(b1, b2);
    }

    protected void mutation(Beta b) {
        Random rand = new Random();
        for(int i = 0; i < b.bs.length; i++)
            if(rand.nextDouble() < MUTATION) {
                //b.bs[i] = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * rand.nextDouble();
                //System.out.println(rand.nextGaussian());
                b.bs[i] += rand.nextGaussian();
            }
    }

}
