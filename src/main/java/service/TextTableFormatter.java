package service;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TextTableFormatter {
    private List<List<String>> cells;

    TextTableFormatter(List<List<String>> cells) {
        this.cells = cells;
    }

    private static String appendSpace(String s, int n) {
        StringBuilder builder = new StringBuilder(s);
        while (builder.length() < n) builder.append(" ");
        return builder.toString();
    }

    private final BiFunction<List<Integer>, List<Integer>, List<Integer>> maxByIndex =
            (acc, next) -> {
                for (int i = 0; i < next.size(); i++) {
                    int m = Math.max(next.get(i), i >= acc.size() ? 0 : acc.get(i));
                    if (i < acc.size()) acc.set(i, m);
                    else acc.add(m);
                }
                return acc;
            };

    @Override
    public String toString() {
        List<Integer> widthByColumn = cells.stream()
                .map(row -> row.stream().map(String::length).collect(Collectors.toList()))
                .reduce(new ArrayList<>(), maxByIndex::apply);
        return cells.stream().map(row -> IntStream.range(0, widthByColumn.size())
                .mapToObj(i -> {
                    String cell = i >= row.size() ? "" : row.get(i);
                    return i == widthByColumn.size() - 1 ? cell : appendSpace(cell, widthByColumn.get(i));
                })
                .collect(Collectors.joining("|"))
        ).collect(Collectors.joining("\n"));
    }
}
