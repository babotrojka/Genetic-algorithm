package hr.fer.mekorac.genetic;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentGeneticAlgorithm extends GeneticAlgorithm{

    private final int K = 3;

    private double MORTALITY;

    public TournamentGeneticAlgorithm(int POP_SIZE, double MUTATION, int ITERS, Path file, double MORTALITY) {
        super(POP_SIZE, MUTATION, ITERS, file);
        this.MORTALITY = MORTALITY;
    }

    @Override
    Beta run() {
        initBetas();

        for(int i = 0; i < ITERS; i++) {
            betas.forEach(b -> b.msq(params));

            betas.sort((b1, b2) -> Double.compare(b1.msq, b2.msq));
            //System.out.println("1st: " + betas.get(0).msq + ", 2nd " + betas.get(1).msq);
            if(best == null || betas.get(0).msq < best.msq) {
                best = betas.get(0);
                System.out.printf(
                        "ITER[%d]: New Best! b[0] = %f, b[1] = %f, b[2] = %f, b[3] = %f, b[4] = %f, msq is %f\n",
                        i, best.bs[0], best.bs[1],best.bs[2], best.bs[3], best.bs[4], best.msq
                );
            }

            List<Beta> childBetas = new ArrayList<>();
            List<Beta> removeBetas = new ArrayList<>();
            for(int j = 0; j < Math.round(MORTALITY * POP_SIZE); j++) {
                Random rand = new Random();
                List<Beta> selection = new ArrayList<>();
                for(int k = 0; k < K; k++) {
                    selection.add(betas.get(rand.nextInt(betas.size())));
                }
                selection.sort((b1, b2) -> Double.compare(b1.msq, b2.msq));
                Beta child = reproduction(selection.get(0), selection.get(1));
                mutation(child);
                childBetas.add(child);
                //betas.remove(selection.get(2));
                removeBetas.add(selection.get(2));
            }

            betas.removeAll(removeBetas);
            betas.addAll(childBetas);
        }

        System.out.println("Finished!");
        System.out.printf(
                "Best! b[0] = %f, b[1] = %f, b[2] = %f, b[3] = %f, b[4] = %f, msq is %f\n",
                best.bs[0], best.bs[1],best.bs[2], best.bs[3], best.bs[4], best.msq
        );
        return best;
    }

    @Override
    Beta selection() {
        return null;
    }
}
