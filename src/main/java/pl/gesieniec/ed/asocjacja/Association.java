package pl.gesieniec.ed.asocjacja;

import com.github.dakusui.combinatoradix.Combinator;
import pl.gesieniec.ed.common.FileReader;
import pl.gesieniec.ed.common.Mappers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Association {

    private static final String ASSOCIATION_FILE_PATH = "C:/studia/eksploracja_danych/src/main/resources/asocjacja.txt";
    private static final double SUPPORT = 0.012;
    private static final int NO_OFTEN_SETS = 4;

    public static void main(String[] args) {

        final List<Cart> carts = FileReader.readFile(ASSOCIATION_FILE_PATH, Mappers.mapToCart);

        final Map<List<Item>, Long> occurrenceMap =
                Objects.requireNonNull(carts)
                       .stream()
                       .map(Cart::getCart)
                       .flatMap(Collection::stream)
                       .collect(Collectors.groupingBy(Collections::singletonList, Collectors.counting()));


        final Map<List<Item>, Long> supportFilteredOccurrenceMap = removeUnsupported.apply(occurrenceMap, carts);


        final List<Item> flatMapItems = supportFilteredOccurrenceMap.keySet()
                                                                    .stream()
                                                                    .flatMap(Collection::stream)
                                                                    .collect(Collectors.toList());

        for (int i = 2; i <= NO_OFTEN_SETS; i++) {
            final List<List<Item>> allCombinationsOfN = getAllCombinations.apply(flatMapItems, i);
            final Map<List<Item>, Long> supportFilteredAllCombinationsOfNOccurrences = allOccurrencesToSupportedFilteredCombinations.apply(allCombinationsOfN, carts);
            System.out.println("--------------// " + i + " //--------------");
            supportFilteredAllCombinationsOfNOccurrences.forEach((e, v) -> System.out.println(e + " : " + v));
        }
    }

    private static BiPredicate<Map.Entry<List<Item>, Long>, List<Cart>> hasSupport =
            (entry, carts) -> (entry.getValue() / (double) carts.size()) > SUPPORT;

    private static BiFunction<List<Item>, List<Cart>, Long> countOccurrences =
            (items, carts) -> carts.stream()
                                   .filter(e -> e.cart.containsAll(items))
                                   .count();

    private static BiFunction<Map<List<Item>, Long>, List<Cart>, Map<List<Item>, Long>> removeUnsupported =
            (occurrenceMap, carts) -> occurrenceMap.entrySet()
                                                   .stream()
                                                   .filter(e -> hasSupport.test(e, carts))
                                                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static BiFunction<List<List<Item>>, List<Cart>, Map<List<Item>, Long>> allOccurrencesToSupportedFilteredCombinations =
            (allCombinationsOfN, carts) -> {

                final Map<List<Item>, Long> allCombinationsOfNOccurrences = allCombinationsOfN
                        .stream()
                        .collect(Collectors.toMap(k -> k, e -> countOccurrences.apply(e, carts)));

                return removeUnsupported.apply(allCombinationsOfNOccurrences, carts);
            };

    private static BiFunction<List<Item>, Integer, List<List<Item>>> getAllCombinations =
            (itemList, noCombinations) -> {
                Combinator<Item> lists = new Combinator<>(itemList, noCombinations);
                List<List<Item>> combinations = new ArrayList<>();
                lists.forEach(combinations::add);
                return combinations;
            };
}
