import hr.fer.mekorac.genetic.Beta;
import hr.fer.mekorac.genetic.GeneticAlgorithm;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FuncTest {

    Path dataset1 = Path.of("dataset1.txt");
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

    @Test
    public void funcTest() {
        Beta b = new Beta(new Double[] {0.3716098375679594, -0.12266472245630115, 3.5162387906120154, 1.3177075521853587, -1.3261765840444857});

        List<String> lines = null;
        try {
            lines = Files.readAllLines(dataset1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Param> params = new ArrayList<>();
        for(String line: lines) {
            String[] splitted = line.split("\t");
            params.add(new Param(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2])));
        }

        params.forEach(p -> System.out.println("Calculated: " + b.func(p.x, p.y) + ", real: " + p.out));

    }
}
