package hr.fer.mekorac.genetic.reproduce;

import hr.fer.mekorac.genetic.Beta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ReproduceAlgs {
    static Reproduce discRecomb = (ba, bb) -> {
        Double[] newbs = new Double[ba.bs.length];
        for (int i = 0; i < ba.bs.length; i++)
            newbs[i] = new Random().nextDouble() >= 0.5 ? bb.bs[i] : ba.bs[i];
        return new Beta(newbs);
    };

    static Reproduce aritmRecomb = (ba, bb) -> {
        Double[] newbs = new Double[ba.bs.length];
        int limit = new Random().nextInt(ba.bs.length);
        for (int i = 0; i < ba.bs.length; i++) {
            if(i < limit) newbs[i] = ba.bs[i];
            else newbs[i] = (ba.bs[i] + bb.bs[i]) / 2;
        }
        return new Beta(newbs);
    };

    static Reproduce wholeAritm = (ba, bb) -> {
        Double[] newbs = new Double[ba.bs.length];
        for (int i = 0; i < ba.bs.length; i++) {
            newbs[i] = (ba.bs[i] + bb.bs[i]) / 2;
        }
        return new Beta(newbs);
    };

    public static Reproduce getRandom() {
        List<Reproduce> reps = new ArrayList<>(Arrays.asList(discRecomb, aritmRecomb, wholeAritm));
        //return reps.get(new Random().nextInt(reps.size()));
        return reps.get(2);
    }
}
