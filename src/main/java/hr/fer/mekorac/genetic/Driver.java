package hr.fer.mekorac.genetic;

import java.nio.file.Path;

public class Driver {

    static Path dataset1 = Path.of("dataset1.txt");
    static Path dataset2 = Path.of("dataset2.txt");

    public static void main(String[] args) {
        int POP_SIZE = 30;
        double MUTATION = 0.11;
        int ITERS = 5000;

        GeneticAlgorithm alg = new SimpleGeneticAlgorithm(POP_SIZE, MUTATION, ITERS, dataset2, true);
        alg.run();

        double MORTALITY = 0.7;
        GeneticAlgorithm tAlg = new TournamentGeneticAlgorithm(POP_SIZE, MUTATION, ITERS, dataset1, MORTALITY);
        //tAlg.run();

    }
}
