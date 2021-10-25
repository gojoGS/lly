package main.util.random;

import main.model.AnswerId;

import java.util.*;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class RandomUtil {
    private static final Random random = new Random();

    public static double[] generateNRandomDoublesWhichSumToOne(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        double[] values = new double[n];
        double sum = 0;
        for (int i = 0; i < n; ++i) {
            values[i] = Math.abs(random.nextGaussian());
            sum += values[i];
        }
        for (int i = 0; i < n; ++i) {
            values[i] /= sum;
        }
        return values;
    }

    public static Map<AnswerId, Double> generateRandomAnswerFrequency(AnswerId correctId) {
        var mapping  = new HashMap<AnswerId, Double>();
        var keys = AnswerId.values();
        var frequencies = generateNRandomDoublesWhichSumToOne(4);

        if(Arrays.stream(frequencies).max().isEmpty()) {
            System.out.println("Hiba lépett fel random generálás során");
            exit(3);
        }

        Double max = Arrays.stream(frequencies).max().getAsDouble();
        mapping.put(correctId, max);

        var wrongKeys = Arrays.stream(keys)
                .filter(Predicate.not(correctId::equals))
                .toArray(AnswerId[]::new);
        var wrongFrequencies = Arrays.stream(frequencies)
                .boxed()
                .collect(Collectors.toList());

        wrongFrequencies.remove(max);

        for (int i = 0; i < wrongKeys.length; ++i) {
            mapping.put(wrongKeys[i], wrongFrequencies.get(i));
        }

        return mapping;

    }
}
