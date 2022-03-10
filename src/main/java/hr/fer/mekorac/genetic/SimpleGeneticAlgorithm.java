package hr.fer.mekorac.genetic;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleGeneticAlgorithm extends GeneticAlgorithm{
    private boolean ELITISM;
    private int elit_total = 1;

    public SimpleGeneticAlgorithm(int POP_SIZE, double MUTATION, int ITERS, Path file, boolean ELITISM) {
        super(POP_SIZE, MUTATION, ITERS, file);
        this.ELITISM = ELITISM;
    }

    public void setElit_total(int elit_total) {
        this.elit_total = elit_total;
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

            List<Beta> nextBetas = new ArrayList<>();
            if (ELITISM) for(int j = 0; j < elit_total; j++) nextBetas.add(betas.get(j));

            while(nextBetas.size() < POP_SIZE) {
                Beta b1 = selection();
                Beta b2 = null;
                do {
                    b2 = selection();
                } while (b2.equals(b1));

                Beta child = reproduction(b1, b2);
                mutation(child);

                nextBetas.add(child);
            }

            betas = nextBetas;

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
         double sum = betas.stream().mapToDouble(b -> 1 / b.msq).sum();

         double randPoint = new Random().nextDouble() * sum;
         double sumSoFar = 0;
         for(Beta b : betas) {
             if(randPoint <= sumSoFar + 1 / b.msq) {
                 return b;
             }
             sumSoFar += (1 / b.msq);
         }

         throw new RuntimeException("This cant happen!");
    }


}
