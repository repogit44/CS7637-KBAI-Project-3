package ravensproject.solver;

import java.util.*;
import java.util.stream.Collectors;

public class AdvancedSolver {

    private final String name;
    private final Map<String, VisualFigure> matrixFigures;
    private final Map<String, VisualFigure> answerFigures;

    private Map<String, FigureSeries> figureSeriesMap;

    private Map<String, Integer> resultSet;

    public AdvancedSolver(String name, Map<String, VisualFigure> matrixFigures, Map<String, VisualFigure> answerFigures) {

        this.name = name;
        this.matrixFigures = matrixFigures;
        this.answerFigures = answerFigures;
    }

    public int Solve() {

        GenerateFigureSeries();
        InitializeResultSet();

        Set<String> horizontalAnswers = TestHorizontalRelationship();
        AddToResultSet(horizontalAnswers);

        Set<String> verticalAnswers = TestVerticalRelationship();
        AddToResultSet(verticalAnswers);

        Set<String> rowSetAnswers = TestRowSetRelationship();
        AddToResultSet(rowSetAnswers);

        Set<String> colSetAnswers = TestColSetRelationship();
        AddToResultSet(colSetAnswers);

        Set<String> partialHorizontalRelationshipAnswers = TestPartialHorizontalRelationship();
        AddToResultSet(partialHorizontalRelationshipAnswers);

        Set<String> partialVerticalRelationshipAnswers = TestPartialVerticalRelationship();
        AddToResultSet(partialVerticalRelationshipAnswers);

        Set<String> partialHorizontalSetRelationshipAnswers = TestPartialHorizontalSetRelationship();
        AddToResultSet(partialHorizontalSetRelationshipAnswers);

        Set<String> partialVerticalSetRelationshipAnswers = TestPartialVerticalSetRelationship();
        AddToResultSet(partialVerticalSetRelationshipAnswers);

        Set<String> horizontalSetRelationshipFuzzyAnswers = TestHorizontalSetRelationshipFuzzy();
        AddToResultSet(horizontalSetRelationshipFuzzyAnswers);

        Set<String> horizontalAdditiveRelationshipAnswers = TestHorizontalAdditiveRelationship();
        AddToResultSet(horizontalAdditiveRelationshipAnswers);

        Set<String> verticalAdditiveRelationshipAnswers = TestVerticalAdditiveRelationship();
        AddToResultSet(verticalAdditiveRelationshipAnswers);

        Set<String> horizontalXORRelationshipAnswers = TestHorizontalXORRelationship();
        AddToResultSet(horizontalXORRelationshipAnswers);

        Set<String> verticalXORRelationshipAnswers = TestVerticalXORRelationship();
        AddToResultSet(verticalXORRelationshipAnswers);

        Set<String> horizontalIntersectionRelationshipAnswers = TestHorizontalIntersectionRelationship();
        AddToResultSet(horizontalIntersectionRelationshipAnswers);

        Set<String> verticalIntersectionRelationshipAnswers = TestVerticalIntersectionRelationship();
        AddToResultSet(verticalIntersectionRelationshipAnswers);

        Set<String> horizontalDifferenceWithLeftShiftAnswers = TestHorizontalDifferenceWithLeftShift();
        AddToResultSet(horizontalDifferenceWithLeftShiftAnswers);

        Set<String> verticalDifferenceWithLeftShiftAnswers = TestVerticalDifferenceWithLeftShift();
        AddToResultSet(verticalDifferenceWithLeftShiftAnswers);

        Set<String> horizontalDifferenceWithRightShiftAnswers = TestHorizontalDifferenceWithRightShift();
        AddToResultSet(horizontalDifferenceWithRightShiftAnswers);

        Set<String> verticalDifferenceWithRightShiftAnswers = TestVerticalDifferenceWithRightShift();
        AddToResultSet(verticalDifferenceWithRightShiftAnswers);

        Set<String> horizontalDifferenceWithTopShiftAnswers = TestHorizontalDifferenceWithTopShift();
        AddToResultSet(horizontalDifferenceWithTopShiftAnswers);

        Set<String> verticalDifferenceWithTopShiftAnswers = TestVerticalDifferenceWithTopShift();
        AddToResultSet(verticalDifferenceWithTopShiftAnswers);

        Set<String> horizontalDifferenceWithBottomShiftAnswers = TestHorizontalDifferenceWithBottomShift();
        AddToResultSet(horizontalDifferenceWithBottomShiftAnswers);

        Set<String> verticalDifferenceWithBottomShiftAnswers = TestVerticalDifferenceWithBottomShift();
        AddToResultSet(verticalDifferenceWithBottomShiftAnswers);

        Set<String> horizontalObjectXORRelationshipAnswers = TestHorizontalObjectXORRelationship();
        AddToResultSet(horizontalObjectXORRelationshipAnswers);

        Set<String> verticalObjectXORRelationshipAnswers = TestVerticalObjectXORRelationship();
        AddToResultSet(verticalObjectXORRelationshipAnswers);

        Set<String> hollowContainsHorizontalRelationshipAnswers = TestSolidHollowContainsHorizontalRelationship();
        AddToResultSet(hollowContainsHorizontalRelationshipAnswers);

        Set<String> hollowContainsVerticalRelationshipAnswers = TestSolidHollowContainsVerticalRelationship();
        AddToResultSet(hollowContainsVerticalRelationshipAnswers);

        Set<String> solidHollowSurroundsUnknownHorizontalRelationshipAnswers = TestSolidHollowSurroundsUnknownHorizontalRelationship();
        AddToResultSet(solidHollowSurroundsUnknownHorizontalRelationshipAnswers);

        Set<String> solidHollowSurroundsUnknownVerticalRelationshipAnswers = TestSolidHollowSurroundsUnknownVerticalRelationship();
        AddToResultSet(solidHollowSurroundsUnknownVerticalRelationshipAnswers);

        Set<String> nonHorizontalPartialRelationshipAnswers = TestNonHorizontalPartialRelationship();
        AddToResultSet(nonHorizontalPartialRelationshipAnswers);

        Set<String> nonVerticalPartialRelationshipAnswers = TestNonVerticalPartialRelationship();
        AddToResultSet(nonVerticalPartialRelationshipAnswers);

        Set<String> horizontalNumericalRelationshipAnswersWithVerticalWeighting = TestHorizontalNumericalRelationshipWithVerticalWeighting();
        AddToResultSet(horizontalNumericalRelationshipAnswersWithVerticalWeighting);

        Set<String> verticalNumericalRelationshipAnswersWithVerticalWeighting = TestVerticalNumericalRelationshipWithVerticalWeighting();
        AddToResultSet(verticalNumericalRelationshipAnswersWithVerticalWeighting);

        Set<String> horizontalNumericalRelationshipAnswersWithHorizontalWeighting = TestHorizontalNumericalRelationshipWithHorizontalWeighting();
        AddToResultSet(horizontalNumericalRelationshipAnswersWithHorizontalWeighting);

        Set<String> verticalNumericalRelationshipAnswersWithHorizontalWeighting = TestVerticalNumericalRelationshipWithHorizontalWeighting();
        AddToResultSet(verticalNumericalRelationshipAnswersWithHorizontalWeighting);

        Set<String> horizontalKnownRelationships = TestHorizontalKnownRelationships();
        AddToResultSet(horizontalKnownRelationships);

        Set<String> verticalKnownRelationships = TestVerticalKnownRelationships();
        AddToResultSet(verticalKnownRelationships);

        int maxResponses = 0;
        Set<String> bestResponses = new HashSet<>();
        for(Map.Entry<String, Integer> result : resultSet.entrySet()) {
            Integer value = result.getValue();
            if(value > maxResponses) {
                maxResponses = value;
                bestResponses = new HashSet<>();
            }

            if(value == maxResponses && maxResponses != 0) {
                bestResponses.add(result.getKey());
            }
        }

        if(bestResponses.size() == 1) {
            System.out.println(name + " - " + maxResponses + " votes");
            return Integer.parseInt(bestResponses.stream().findFirst().get());
        } else if(bestResponses.size() > 1 && bestResponses.size() < 5) {
            System.out.println(name + " - " + bestResponses.size() + " Answers with " + maxResponses + " votes");
            try {
                Random r = new Random();
                int guess = r.nextInt(bestResponses.size());
                return Integer.parseInt(bestResponses.stream().skip(guess).findFirst().get());
            }
            catch (Exception e) {
                //e.printStackTrace();
            }
        } else {
            System.out.println(name + " Nil response");
        }

        return -1;
    }

    private void InitializeResultSet() {
        resultSet = new HashMap<>();
        for (Map.Entry<String, VisualFigure> figure : answerFigures.entrySet()) {
            resultSet.put(figure.getKey(), 0);
        }
    }

    private void AddToResultSet(Set<String> testAnswers) {
        if(testAnswers.size() != 8) {
            for (String answer : testAnswers) {
                resultSet.compute(answer, (k, v) -> v == null ? 1 : v + 1);
            }
        }
    }

    private void GenerateFigureSeries() {
        figureSeriesMap = new HashMap<>();
        figureSeriesMap.put("Row1", new FigureSeries(matrixFigures.get("A"), matrixFigures.get("B"), matrixFigures.get("C")));
        figureSeriesMap.put("Row2", new FigureSeries(matrixFigures.get("D"), matrixFigures.get("E"), matrixFigures.get("F")));
        figureSeriesMap.put("Col1", new FigureSeries(matrixFigures.get("A"), matrixFigures.get("D"), matrixFigures.get("G")));
        figureSeriesMap.put("Col2", new FigureSeries(matrixFigures.get("B"), matrixFigures.get("E"), matrixFigures.get("H")));

        for (Map.Entry<String, VisualFigure> figure : answerFigures.entrySet()) {
            figureSeriesMap.put("RowAns" + figure.getKey(), new FigureSeries(matrixFigures.get("G"), matrixFigures.get("H"), figure.getValue()));
            figureSeriesMap.put("ColAns" + figure.getKey(), new FigureSeries(matrixFigures.get("C"), matrixFigures.get("F"), figure.getValue()));
        }

    }

    private Set<String> TestHorizontalRelationship() {
        return TestHorizontalRelationship(false);
    }

    private Set<String> TestHorizontalRelationship(boolean useEdited) {

        boolean row1HasEqualDarkRatio = figureSeriesMap.get("Row1").hasEqualDarkRatio(useEdited);
        boolean row2HasEqualDarkRatio = figureSeriesMap.get("Row2").hasEqualDarkRatio(useEdited);

        if (row1HasEqualDarkRatio && row2HasEqualDarkRatio) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> f.getValue().hasEqualDarkRatio(useEdited))
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalRelationship() {
        return TestVerticalRelationship(false);
    }

    private Set<String> TestVerticalRelationship(boolean useEdited) {

        boolean col1HasEqualDarkRatio = figureSeriesMap.get("Col1").hasEqualDarkRatio(useEdited);
        boolean col2HasEqualDarkRatio = figureSeriesMap.get("Col2").hasEqualDarkRatio(useEdited);

        if (col1HasEqualDarkRatio && col2HasEqualDarkRatio) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> f.getValue().hasEqualDarkRatio(useEdited))
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestRowSetRelationship() {

        boolean row1 = figureSeriesMap.get("Row1").hasMatchingFigures(figureSeriesMap.get("Row2"));
        if (row1) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> figureSeriesMap.get("Row2").hasMatchingFigures(f.getValue()))
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    private Set<String> TestColSetRelationship() {
        boolean col1 = figureSeriesMap.get("Col1").hasMatchingFigures(figureSeriesMap.get("Col2"));
        if (col1) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> figureSeriesMap.get("Col2").hasMatchingFigures(f.getValue()))
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    private Set<String> TestPartialHorizontalRelationship() {
        figureSeriesMap.get("Row1").removePartialMatchingFigures();
        figureSeriesMap.get("Row2").removePartialMatchingFigures();

        Set<String> answerSet = new HashSet<>();

        for (Map.Entry<String, FigureSeries> rowSeriesEntry : figureSeriesMap.entrySet()) {
            if (rowSeriesEntry.getKey().startsWith("RowAns")) {
                String rowAnsNumber = rowSeriesEntry.getValue().getFigure3().getName();

                rowSeriesEntry.getValue().removePartialMatchingFigures();

                boolean col1HasEqualDarkRatio = figureSeriesMap.get("Col1").hasEqualDarkRatio(true);
                boolean col2HasEqualDarkRatio = figureSeriesMap.get("Col2").hasEqualDarkRatio(true);

                if (col1HasEqualDarkRatio && col2HasEqualDarkRatio) {
                    answerSet.addAll(figureSeriesMap.entrySet().stream()
                            .filter(f -> f.getKey().equals("ColAns" + rowAnsNumber))
                            .filter(f -> f.getValue().hasEqualDarkRatio(true))
                            .map(f -> f.getValue().getFigure3().getName())
                            .collect(Collectors.toSet()));
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestPartialVerticalRelationship() {
        figureSeriesMap.get("Col1").removePartialMatchingFigures();
        figureSeriesMap.get("Col2").removePartialMatchingFigures();

        Set<String> answerSet = new HashSet<>();

        for (Map.Entry<String, FigureSeries> colSeriesEntry : figureSeriesMap.entrySet()) {
            if (colSeriesEntry.getKey().startsWith("ColAns")) {
                String colAnsNumber = colSeriesEntry.getValue().getFigure3().getName();

                colSeriesEntry.getValue().removePartialMatchingFigures();

                boolean row1HasEqualDarkRatio = figureSeriesMap.get("Row1").hasEqualDarkRatio(true);
                boolean row2HasEqualDarkRatio = figureSeriesMap.get("Row2").hasEqualDarkRatio(true);

                if (row1HasEqualDarkRatio && row2HasEqualDarkRatio) {
                    answerSet.addAll(figureSeriesMap.entrySet().stream()
                            .filter(f -> f.getKey().equals("RowAns" + colAnsNumber))
                            .filter(f -> f.getValue().hasEqualDarkRatio(true))
                            .map(f -> f.getValue().getFigure3().getName())
                            .collect(Collectors.toSet()));
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestPartialHorizontalSetRelationship() {
        figureSeriesMap.get("Row1").removePartialMatchingFigures();
        figureSeriesMap.get("Row2").removePartialMatchingFigures();

        Set<String> answerSet = new HashSet<>();

        boolean row1 = figureSeriesMap.get("Row1").hasMatchingFigures(figureSeriesMap.get("Row2"), true);
        if (row1) {
            for (Map.Entry<String, FigureSeries> rowSeriesEntry : figureSeriesMap.entrySet()) {
                if (rowSeriesEntry.getKey().startsWith("RowAns")) {
                    rowSeriesEntry.getValue().removePartialMatchingFigures();

                    if (figureSeriesMap.get("Row2").hasMatchingFigures(rowSeriesEntry.getValue(), true)) {
                        answerSet.add(rowSeriesEntry.getValue().getFigure3().getName());
                    }
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestPartialVerticalSetRelationship() {
        figureSeriesMap.get("Col1").removePartialMatchingFigures();
        figureSeriesMap.get("Col2").removePartialMatchingFigures();

        Set<String> answerSet = new HashSet<>();

        boolean col1 = figureSeriesMap.get("Col1").hasMatchingFigures(figureSeriesMap.get("Col2"), true);
        if (col1) {
            for (Map.Entry<String, FigureSeries> colSeriesEntry : figureSeriesMap.entrySet()) {
                if (colSeriesEntry.getKey().startsWith("ColAns")) {
                    colSeriesEntry.getValue().removePartialMatchingFigures();

                    if (figureSeriesMap.get("Col2").hasMatchingFigures(colSeriesEntry.getValue(), true)) {
                        answerSet.add(colSeriesEntry.getValue().getFigure3().getName());
                    }
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestHorizontalSetRelationshipFuzzy() {

        Set<String> answerSet = new HashSet<>();

        for (Map.Entry<String, FigureSeries> rowSeriesEntry : figureSeriesMap.entrySet()) {
            if (rowSeriesEntry.getKey().startsWith("RowAns")) {
                FigureSeries answerSeries = rowSeriesEntry.getValue();
                figureSeriesMap.get("Row1").removePartialMatchingFigures(figureSeriesMap.get("Row2"), answerSeries);
                if (TestObjectCountAnswer(figureSeriesMap.get("Row1"), figureSeriesMap.get("Row2"), answerSeries)) {
                    answerSet.add(answerSeries.getFigure3().getName());
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestHorizontalAdditiveRelationship() {

        Set<String> row1HasOrMatch = figureSeriesMap.get("Row1").hasOrMatch();
        Set<String> row2HasOrMatch = figureSeriesMap.get("Row2").hasOrMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasOrMatch, row2HasOrMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasOrMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalAdditiveRelationship() {

        Set<String> row1HasOrMatch = figureSeriesMap.get("Col1").hasOrMatch();
        Set<String> row2HasOrMatch = figureSeriesMap.get("Col2").hasOrMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasOrMatch, row2HasOrMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasOrMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestHorizontalXORRelationship() {

        Set<String> row1HasXORMatch = figureSeriesMap.get("Row1").hasXORMatch();
        Set<String> row2HasXORMatch = figureSeriesMap.get("Row2").hasXORMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasXORMatch, row2HasXORMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasXORMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalXORRelationship() {

        Set<String> row1HasXORMatch = figureSeriesMap.get("Col1").hasXORMatch();
        Set<String> row2HasXORMatch = figureSeriesMap.get("Col2").hasXORMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasXORMatch, row2HasXORMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasXORMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestHorizontalIntersectionRelationship() {

        Set<String> row1HasAndMatch = figureSeriesMap.get("Row1").hasAndMatch();
        Set<String> row2HasAndMatch = figureSeriesMap.get("Row2").hasAndMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasAndMatch, row2HasAndMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasAndMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalIntersectionRelationship() {

        Set<String> row1HasAndMatch = figureSeriesMap.get("Col1").hasAndMatch();
        Set<String> row2HasAndMatch = figureSeriesMap.get("Col2").hasAndMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasAndMatch, row2HasAndMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasAndMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestHorizontalDifferenceWithLeftShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Row1").hasDifferenceMatch(FigureShift.LEFT);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Row2").hasDifferenceMatch(FigureShift.LEFT);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.LEFT);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.LEFT)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalDifferenceWithLeftShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Col1").hasDifferenceMatch(FigureShift.LEFT);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Col2").hasDifferenceMatch(FigureShift.LEFT);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.LEFT);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.LEFT)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestHorizontalDifferenceWithRightShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Row1").hasDifferenceMatch(FigureShift.RIGHT);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Row2").hasDifferenceMatch(FigureShift.RIGHT);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.RIGHT);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.RIGHT)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalDifferenceWithRightShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Col1").hasDifferenceMatch(FigureShift.RIGHT);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Col2").hasDifferenceMatch(FigureShift.RIGHT);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.RIGHT);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.RIGHT)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestHorizontalDifferenceWithTopShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Row1").hasDifferenceMatch(FigureShift.TOP);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Row2").hasDifferenceMatch(FigureShift.TOP);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.TOP);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.TOP)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalDifferenceWithTopShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Col1").hasDifferenceMatch(FigureShift.TOP);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Col2").hasDifferenceMatch(FigureShift.TOP);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.TOP);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.TOP)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestHorizontalDifferenceWithBottomShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Row1").hasDifferenceMatch(FigureShift.BOTTOM);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Row2").hasDifferenceMatch(FigureShift.BOTTOM);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.BOTTOM);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.BOTTOM)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalDifferenceWithBottomShift() {
        Set<String> row1HasDifferenceMatch = figureSeriesMap.get("Col1").hasDifferenceMatch(FigureShift.BOTTOM);
        Set<String> row2HasDifferenceMatch = figureSeriesMap.get("Col2").hasDifferenceMatch(FigureShift.BOTTOM);

        Set<String> intersection = FigureCompare.Intersection(row1HasDifferenceMatch, row2HasDifferenceMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> {
                        f.getValue().shift(FigureShift.BOTTOM);
                        return FigureCompare.Intersection(intersection, f.getValue().hasDifferenceMatch(FigureShift.BOTTOM)).size() > 0;
                    })
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestHorizontalObjectXORRelationship() {
        Set<String> row1HasXORObjectMatch = figureSeriesMap.get("Row1").hasObjectXORMatch();
        Set<String> row2HasXORObjectMatch = figureSeriesMap.get("Row2").hasObjectXORMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasXORObjectMatch, row2HasXORObjectMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasObjectXORMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private Set<String> TestVerticalObjectXORRelationship() {
        Set<String> row1HasXORObjectMatch = figureSeriesMap.get("Col1").hasObjectXORMatch();
        Set<String> row2HasXORObjectMatch = figureSeriesMap.get("Col2").hasObjectXORMatch();

        Set<String> intersection = FigureCompare.Intersection(row1HasXORObjectMatch, row2HasXORObjectMatch);

        if (intersection.size() > 0) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("ColAns"))
                    .filter(f -> FigureCompare.Intersection(intersection, f.getValue().hasObjectXORMatch()).size() > 0)
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    private boolean TestObjectCountAnswer(FigureSeries row1, FigureSeries row2, FigureSeries answerSeries) {
        int aSize = row1.getFigure1ObjectCount();
        int bSize = row1.getFigure2ObjectCount();
        int cSize = row1.getFigure3ObjectCount();
        int dSize = row2.getFigure1ObjectCount();
        int eSize = row2.getFigure2ObjectCount();
        int fSize = row2.getFigure3ObjectCount();
        int gSize = answerSeries.getFigure1ObjectCount();
        int hSize = answerSeries.getFigure2ObjectCount();
        int answerFigureObjectCount = answerSeries.getFigure3ObjectCount();

        boolean row1Same = aSize == bSize && bSize == cSize;
        boolean row2Same = dSize == eSize && eSize == fSize;
        boolean row3Same = gSize == hSize;

        boolean col1Same = aSize == dSize && dSize == gSize;
        boolean col2Same = bSize == eSize && eSize == hSize;
        boolean col3Same = cSize == fSize;

        if (row1Same && row2Same && row3Same && col1Same && col2Same && col3Same) {
            // same count in every cell
            return answerFigureObjectCount == aSize;
        } else if (row1Same && row2Same && row3Same) {
            // same count across rows
            return answerFigureObjectCount == gSize;
        } else if (col1Same && col2Same && col3Same) {
            // same count across cols
            return answerFigureObjectCount == cSize;
        }

        HashMap<Integer, Integer> row1Map = new HashMap<>();
        row1Map.compute(aSize, (k, v) -> v == null ? 1 : v + 1);
        row1Map.compute(bSize, (k, v) -> v == null ? 1 : v + 1);
        row1Map.compute(cSize, (k, v) -> v == null ? 1 : v + 1);

        HashMap<Integer, Integer> row2Map = new HashMap<>();
        row2Map.compute(dSize, (k, v) -> v == null ? 1 : v + 1);
        row2Map.compute(eSize, (k, v) -> v == null ? 1 : v + 1);
        row2Map.compute(fSize, (k, v) -> v == null ? 1 : v + 1);

        HashMap<Integer, Integer> row3Map = new HashMap<>();
        row3Map.compute(gSize, (k, v) -> v == null ? 1 : v + 1);
        row3Map.compute(hSize, (k, v) -> v == null ? 1 : v + 1);

        Set<Integer> row12Intersect = FigureCompare.Intersection(row1Map.keySet(), row2Map.keySet());
        if (row1Map.size() == 3 && row2Map.size() == 3) {
            if (row12Intersect.size() == 3) {
                Set<Integer> difference = FigureCompare.Difference(row12Intersect, row3Map.keySet());
                Integer missingSize = difference.stream().findFirst().get();
                return answerFigureObjectCount == missingSize;
            } else {
                // System.out.println(name + " - Non-3 Intersect");
            }
        } else if (row1Map.size() == 2 && row2Map.size() == 2) {
            if (row12Intersect.size() == 2) {
                Set<Integer> difference = FigureCompare.Difference(row12Intersect, row3Map.keySet());
                if (difference.size() == 0) {
                    Optional<Map.Entry<Integer, Integer>> entry = row1Map.entrySet().stream()
                            .filter(s -> s.getValue() == 2)
                            .findFirst();
                    if (entry.isPresent()) {
                        Integer missingSize = entry.get().getKey();
                        return answerFigureObjectCount == missingSize;
                    } else {
                        // System.out.println(name + " - Entry not present");
                    }
                } else {
                    Integer missingSize = difference.stream().findFirst().get();
                    return answerFigureObjectCount == missingSize;
                }
            } else {
                // System.out.println(name + " - Non-2 Intersect");
            }
        } else {
            // System.out.println(name + " - Something else");
        }


        return false;
    }

    private Set<String> TestSolidHollowContainsHorizontalRelationship() {
        FigureSeries row1 = figureSeriesMap.get("Row1");
        FigureSeries row2 = figureSeriesMap.get("Row2");

        ArrayList<FigureClassification> row1Classifications = row1.calculateFigureSeriesClassification();
        ArrayList<FigureClassification> row2Classifications = row2.calculateFigureSeriesClassification();

        Set<String> answerSet = new HashSet<>();

        if (!row1Classifications.contains(FigureClassification.UNKNOWN) && !row2Classifications.contains(FigureClassification.UNKNOWN)) {

            Set<FigureClassification> row1Set = new HashSet<>();
            row1Set.addAll(row1Classifications);

            Set<FigureClassification> row2Set = new HashSet<>();
            row2Set.addAll(row2Classifications);

            if (row1Set.size() == 3 && row2Set.size() == 3) {

                for (Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                    if (figureSeriesEntry.getKey().startsWith("RowAns")) {
                        ArrayList<FigureClassification> ansClassifications = figureSeriesEntry.getValue().calculateFigureSeriesClassification();
                        if (!ansClassifications.contains(FigureClassification.UNKNOWN)) {

                            Set<FigureClassification> all = new HashSet<>();
                            all.addAll(ansClassifications);

                            if (all.size() == 3) {
                                Set<FigureClassification> first2 = new HashSet<>();
                                first2.add(ansClassifications.get(0));
                                first2.add(ansClassifications.get(1));

                                Set<FigureClassification> difference = FigureCompare.Difference(row1Set, first2);
                                if (difference.size() == 1) {
                                    boolean row12Match = row1.hasAtLeastOneMatchingFigure(row2);
                                    boolean row13Match = row1.hasAtLeastOneMatchingFigure(figureSeriesEntry.getValue());
                                    boolean row23Match = row2.hasAtLeastOneMatchingFigure(figureSeriesEntry.getValue());

                                    if (!row12Match && !row13Match && !row23Match) {
                                        answerSet.add(figureSeriesEntry.getValue().getFigure3().getName());
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        return answerSet;
    }

    private Set<String> TestSolidHollowContainsVerticalRelationship() {
        FigureSeries row1 = figureSeriesMap.get("Col1");
        FigureSeries row2 = figureSeriesMap.get("Col2");

        ArrayList<FigureClassification> row1Classifications = row1.calculateFigureSeriesClassification();
        ArrayList<FigureClassification> row2Classifications = row2.calculateFigureSeriesClassification();

        Set<String> answerSet = new HashSet<>();

        if (!row1Classifications.contains(FigureClassification.UNKNOWN) && !row2Classifications.contains(FigureClassification.UNKNOWN)) {

            Set<FigureClassification> row1Set = new HashSet<>();
            row1Set.addAll(row1Classifications);

            Set<FigureClassification> row2Set = new HashSet<>();
            row2Set.addAll(row2Classifications);

            if (row1Set.size() == 3 && row2Set.size() == 3) {

                for (Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                    if (figureSeriesEntry.getKey().startsWith("ColAns")) {
                        ArrayList<FigureClassification> ansClassifications = figureSeriesEntry.getValue().calculateFigureSeriesClassification();
                        if (!ansClassifications.contains(FigureClassification.UNKNOWN)) {

                            Set<FigureClassification> all = new HashSet<>();
                            all.addAll(ansClassifications);

                            if (all.size() == 3) {
                                Set<FigureClassification> first2 = new HashSet<>();
                                first2.add(ansClassifications.get(0));
                                first2.add(ansClassifications.get(1));

                                Set<FigureClassification> difference = FigureCompare.Difference(row1Set, first2);
                                if (difference.size() == 1) {
                                    boolean row12Match = row1.hasAtLeastOneMatchingFigure(row2);
                                    boolean row13Match = row1.hasAtLeastOneMatchingFigure(figureSeriesEntry.getValue());
                                    boolean row23Match = row2.hasAtLeastOneMatchingFigure(figureSeriesEntry.getValue());

                                    if (!row12Match && !row13Match && !row23Match) {
                                        answerSet.add(figureSeriesEntry.getValue().getFigure3().getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestSolidHollowSurroundsUnknownHorizontalRelationship() {

        FigureSeries row1 = figureSeriesMap.get("Row1");
        FigureSeries row2 = figureSeriesMap.get("Row2");

        ArrayList<FigureClassification> row1Classifications = row1.calculateFigureSeriesClassification();
        ArrayList<FigureClassification> row2Classifications = row2.calculateFigureSeriesClassification();

        Set<String> answerSet = new HashSet<>();

        if (row1Classifications.contains(FigureClassification.UNKNOWN) && row2Classifications.contains(FigureClassification.UNKNOWN)) {

            Set<FigureClassification> row1Set = new HashSet<>();
            row1Set.addAll(row1Classifications);

            Set<FigureClassification> row2Set = new HashSet<>();
            row2Set.addAll(row2Classifications);

            if (row1Set.size() == 3 && row2Set.size() == 3) {
                Set<FigureClassification> classificationIntersection = FigureCompare.Intersection(row1Set, row2Set);
                if(classificationIntersection.size() == 3) {

                    for (Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                        if (figureSeriesEntry.getKey().startsWith("RowAns")) {

                            ArrayList<FigureClassification> figureClassifications = figureSeriesEntry.getValue().calculateFigureSeriesClassification();
                            Set<FigureClassification> row3Set = new HashSet<>();
                            row3Set.addAll(figureClassifications);

                            Set<FigureClassification> intersection = FigureCompare.Intersection(row1Set, row3Set);
                            if(intersection.size() == 3) {
                                answerSet.add(figureSeriesEntry.getValue().getFigure3().getName());
                            }
                        }
                    }
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestNonVerticalPartialRelationship() {
        figureSeriesMap.get("Col1").removePartialMatchingFigures();
        figureSeriesMap.get("Col2").removePartialMatchingFigures();

        boolean col1HasEqualDarkRatio = figureSeriesMap.get("Col1").hasDuplicateFigures(true);
        boolean col2HasEqualDarkRatio = figureSeriesMap.get("Col2").hasDuplicateFigures(true);

        Set<String> answerSet = new HashSet<>();
        if (!col1HasEqualDarkRatio && !col2HasEqualDarkRatio) {
            for(Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                if(figureSeriesEntry.getKey().startsWith("ColAns")) {

                    figureSeriesEntry.getValue().removePartialMatchingFigures();
                    if(!figureSeriesEntry.getValue().hasDuplicateFigures(true)){
                        answerSet.add(figureSeriesEntry.getValue().getFigure3().getName());
                    }
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestNonHorizontalPartialRelationship() {
        figureSeriesMap.get("Row1").removePartialMatchingFigures();
        figureSeriesMap.get("Row2").removePartialMatchingFigures();

        boolean col1HasEqualDarkRatio = figureSeriesMap.get("Row1").hasDuplicateFigures(true);
        boolean col2HasEqualDarkRatio = figureSeriesMap.get("Row2").hasDuplicateFigures(true);

        Set<String> answerSet = new HashSet<>();
        if (!col1HasEqualDarkRatio && !col2HasEqualDarkRatio) {
            for(Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                if(figureSeriesEntry.getKey().startsWith("RowAns")) {

                    figureSeriesEntry.getValue().removePartialMatchingFigures();
                    if(!figureSeriesEntry.getValue().hasDuplicateFigures(true)){
                        answerSet.add(figureSeriesEntry.getValue().getFigure3().getName());
                    }
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestSolidHollowSurroundsUnknownVerticalRelationship() {

        FigureSeries row1 = figureSeriesMap.get("Col1");
        FigureSeries row2 = figureSeriesMap.get("Col2");

        ArrayList<FigureClassification> row1Classifications = row1.calculateFigureSeriesClassification();
        ArrayList<FigureClassification> row2Classifications = row2.calculateFigureSeriesClassification();

        Set<String> answerSet = new HashSet<>();

        if (row1Classifications.contains(FigureClassification.UNKNOWN) && row2Classifications.contains(FigureClassification.UNKNOWN)) {

            Set<FigureClassification> row1Set = new HashSet<>();
            row1Set.addAll(row1Classifications);

            Set<FigureClassification> row2Set = new HashSet<>();
            row2Set.addAll(row2Classifications);

            if (row1Set.size() == 3 && row2Set.size() == 3) {
                Set<FigureClassification> classificationIntersection = FigureCompare.Intersection(row1Set, row2Set);
                if(classificationIntersection.size() == 3) {

                    for (Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                        if (figureSeriesEntry.getKey().startsWith("ColAns")) {

                            ArrayList<FigureClassification> figureClassifications = figureSeriesEntry.getValue().calculateFigureSeriesClassification();
                            Set<FigureClassification> row3Set = new HashSet<>();
                            row3Set.addAll(figureClassifications);

                            Set<FigureClassification> intersection = FigureCompare.Intersection(row1Set, row3Set);
                            if(intersection.size() == 3) {
                                answerSet.add(figureSeriesEntry.getValue().getFigure3().getName());
                            }
                        }
                    }
                }
            }
        }

        return answerSet;
    }

    private Set<String> TestHorizontalNumericalRelationshipWithVerticalWeighting() {
        FigureSeries row1 = figureSeriesMap.get("Row1");
        FigureSeries row2 = figureSeriesMap.get("Row2");

        row1.classifyFigures();
        row2.classifyFigures();

        Set<String> answerSet = new HashSet<>();

        try {
            int R1sum1 = sumVerticalFigureShapes(row1.getFigure1());
            int R1sum2 = sumVerticalFigureShapes(row1.getFigure2());
            int R1sum3 = sumVerticalFigureShapes(row1.getFigure3());

            boolean r1Sum = R1sum1 + R1sum2 == R1sum3;
            boolean r1Diff = R1sum1 - R1sum2 == R1sum3;

            int R2sum1 = sumVerticalFigureShapes(row2.getFigure1());
            int R2sum2 = sumVerticalFigureShapes(row2.getFigure2());
            int R2sum3 = sumVerticalFigureShapes(row2.getFigure3());

            boolean r2Sum = R2sum1 + R2sum2 == R2sum3;
            boolean r2Diff = R2sum1 - R2sum2 == R2sum3;

            if((r1Sum && r2Sum) || (r1Diff && r2Diff)) {
                for(Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                    if(figureSeriesEntry.getKey().startsWith("RowAns")) {

                        FigureSeries row3 = figureSeriesEntry.getValue();
                        row3.classifyFigures();

                        try {
                            int R3sum1 = sumVerticalFigureShapes(row3.getFigure1());
                            int R3sum2 = sumVerticalFigureShapes(row3.getFigure2());
                            int R3sum3 = sumVerticalFigureShapes(row3.getFigure3());

                            boolean r3Sum = R3sum1 + R3sum2 == R3sum3;
                            boolean r3Diff = R3sum1 - R3sum2 == R3sum3;

                            if (((r1Sum && r2Sum && r3Sum) || (r1Diff && r2Diff && r3Diff)) && row3.getFigure3ObjectCount() == 1) {
                                answerSet.add(row3.getFigure3().getName());
                            }
                        } catch (Exception i) {}
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return answerSet;
    }

    private Set<String> TestVerticalNumericalRelationshipWithVerticalWeighting() {
        FigureSeries row1 = figureSeriesMap.get("Col1");
        FigureSeries row2 = figureSeriesMap.get("Col2");

        row1.classifyFigures();
        row2.classifyFigures();

        Set<String> answerSet = new HashSet<>();

        try {
            int R1sum1 = sumVerticalFigureShapes(row1.getFigure1());
            int R1sum2 = sumVerticalFigureShapes(row1.getFigure2());
            int R1sum3 = sumVerticalFigureShapes(row1.getFigure3());

            boolean r1Sum = R1sum1 + R1sum2 == R1sum3;
            boolean r1Diff = R1sum1 - R1sum2 == R1sum3;

            int R2sum1 = sumVerticalFigureShapes(row2.getFigure1());
            int R2sum2 = sumVerticalFigureShapes(row2.getFigure2());
            int R2sum3 = sumVerticalFigureShapes(row2.getFigure3());

            boolean r2Sum = R2sum1 + R2sum2 == R2sum3;
            boolean r2Diff = R2sum1 - R2sum2 == R2sum3;

            if((r1Sum && r2Sum) || (r1Diff && r2Diff)) {
                for(Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                    if(figureSeriesEntry.getKey().startsWith("ColAns")) {

                        FigureSeries row3 = figureSeriesEntry.getValue();
                        row3.classifyFigures();

                        try {
                            int R3sum1 = sumVerticalFigureShapes(row3.getFigure1());
                            int R3sum2 = sumVerticalFigureShapes(row3.getFigure2());
                            int R3sum3 = sumVerticalFigureShapes(row3.getFigure3());

                            boolean r3Sum = R3sum1 + R3sum2 == R3sum3;
                            boolean r3Diff = R3sum1 - R3sum2 == R3sum3;

                            if (((r1Sum && r2Sum && r3Sum) || (r1Diff && r2Diff && r3Diff)) && row3.getFigure3ObjectCount() == 1) {
                                answerSet.add(row3.getFigure3().getName());
                            }
                        } catch (Exception i) {}
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return answerSet;
    }

    private Set<String> TestHorizontalNumericalRelationshipWithHorizontalWeighting() {
        FigureSeries row1 = figureSeriesMap.get("Row1");
        FigureSeries row2 = figureSeriesMap.get("Row2");

        row1.classifyFigures();
        row2.classifyFigures();

        Set<String> answerSet = new HashSet<>();

        try {
            int R1sum1 = sumHorizontalFigureShapes(row1.getFigure1());
            int R1sum2 = sumHorizontalFigureShapes(row1.getFigure2());
            int R1sum3 = sumHorizontalFigureShapes(row1.getFigure3());

            boolean r1Sum = R1sum1 + R1sum2 == R1sum3;
            boolean r1Diff = R1sum1 - R1sum2 == R1sum3;

            int R2sum1 = sumHorizontalFigureShapes(row2.getFigure1());
            int R2sum2 = sumHorizontalFigureShapes(row2.getFigure2());
            int R2sum3 = sumHorizontalFigureShapes(row2.getFigure3());

            boolean r2Sum = R2sum1 + R2sum2 == R2sum3;
            boolean r2Diff = R2sum1 - R2sum2 == R2sum3;

            if((r1Sum && r2Sum) || (r1Diff && r2Diff)) {
                for(Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                    if(figureSeriesEntry.getKey().startsWith("RowAns")) {

                        FigureSeries row3 = figureSeriesEntry.getValue();
                        row3.classifyFigures();

                        try {
                            int R3sum1 = sumHorizontalFigureShapes(row3.getFigure1());
                            int R3sum2 = sumHorizontalFigureShapes(row3.getFigure2());
                            int R3sum3 = sumHorizontalFigureShapes(row3.getFigure3());

                            boolean r3Sum = R3sum1 + R3sum2 == R3sum3;
                            boolean r3Diff = R3sum1 - R3sum2 == R3sum3;

                            if (((r1Sum && r2Sum && r3Sum) || (r1Diff && r2Diff && r3Diff)) && row3.getFigure3ObjectCount() == 1) {
                                answerSet.add(row3.getFigure3().getName());
                            }
                        } catch (Exception i) {}
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return answerSet;
    }

    private Set<String> TestVerticalNumericalRelationshipWithHorizontalWeighting() {
        FigureSeries row1 = figureSeriesMap.get("Col1");
        FigureSeries row2 = figureSeriesMap.get("Col2");

        row1.classifyFigures();
        row2.classifyFigures();

        Set<String> answerSet = new HashSet<>();

        try {
            int R1sum1 = sumHorizontalFigureShapes(row1.getFigure1());
            int R1sum2 = sumHorizontalFigureShapes(row1.getFigure2());
            int R1sum3 = sumHorizontalFigureShapes(row1.getFigure3());

            boolean r1Sum = R1sum1 + R1sum2 == R1sum3;
            boolean r1Diff = R1sum1 - R1sum2 == R1sum3;

            int R2sum1 = sumHorizontalFigureShapes(row2.getFigure1());
            int R2sum2 = sumHorizontalFigureShapes(row2.getFigure2());
            int R2sum3 = sumHorizontalFigureShapes(row2.getFigure3());

            boolean r2Sum = R2sum1 + R2sum2 == R2sum3;
            boolean r2Diff = R2sum1 - R2sum2 == R2sum3;

            if((r1Sum && r2Sum) || (r1Diff && r2Diff)) {
                for(Map.Entry<String, FigureSeries> figureSeriesEntry : figureSeriesMap.entrySet()) {
                    if(figureSeriesEntry.getKey().startsWith("ColAns")) {

                        FigureSeries row3 = figureSeriesEntry.getValue();
                        row3.classifyFigures();

                        try {
                            int R3sum1 = sumHorizontalFigureShapes(row3.getFigure1());
                            int R3sum2 = sumHorizontalFigureShapes(row3.getFigure2());
                            int R3sum3 = sumHorizontalFigureShapes(row3.getFigure3());

                            boolean r3Sum = R3sum1 + R3sum2 == R3sum3;
                            boolean r3Diff = R3sum1 - R3sum2 == R3sum3;

                            if (((r1Sum && r2Sum && r3Sum) || (r1Diff && r2Diff && r3Diff)) && row3.getFigure3ObjectCount() == 1) {
                                answerSet.add(row3.getFigure3().getName());
                            }
                        } catch (Exception i) {}
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return answerSet;
    }

    private Set<String> TestHorizontalHollowSolidSetRelationship() {
        FigureSeries row1 = figureSeriesMap.get("Row1");
        FigureSeries row2 = figureSeriesMap.get("Row2");

        Set<String> answerSet = new HashSet<>();

        Map<String, Set<Pixel>> r1f1H = row1.getFigure1().getPixelSetMapForClassification(FigureClassification.HOLLOW);
        Map<String, Set<Pixel>> r1f1S = row1.getFigure1().getPixelSetMapForClassification(FigureClassification.SOLID);

        Map<String, Set<Pixel>> r1f2H = row1.getFigure2().getPixelSetMapForClassification(FigureClassification.HOLLOW);
        Map<String, Set<Pixel>> r1f2S = row1.getFigure2().getPixelSetMapForClassification(FigureClassification.SOLID);

        Map<String, Set<Pixel>> r1f3H = row1.getFigure3().getPixelSetMapForClassification(FigureClassification.HOLLOW);
        Map<String, Set<Pixel>> r1f3S = row1.getFigure3().getPixelSetMapForClassification(FigureClassification.SOLID);

        if(r1f1H.size() == r1f1S.size() && r1f2H.size() == r1f2S.size() && r1f3H.size() == r1f3S.size()
                && r1f1H.size() == r1f2H.size() && r1f2H.size() == r1f3H.size()) {

        }

        boolean row1HasEqualDarkRatio = figureSeriesMap.get("Row1").hasEqualDarkRatio(false);
        boolean row2HasEqualDarkRatio = figureSeriesMap.get("Row2").hasEqualDarkRatio(false);

        if (row1HasEqualDarkRatio && row2HasEqualDarkRatio) {
            return figureSeriesMap.entrySet().stream()
                    .filter(f -> f.getKey().startsWith("RowAns"))
                    .filter(f -> f.getValue().hasEqualDarkRatio(false))
                    .map(f -> f.getValue().getFigure3().getName())
                    .collect(Collectors.toSet());
        }

        return answerSet;
    }

    private Set<String> TestHorizontalKnownRelationships() {
        FigureSeries row1 = figureSeriesMap.get("Row1");

        return figureSeriesMap.entrySet().stream()
                .filter(f -> f.getKey().startsWith("RowAns"))
                .filter(f -> row1.testMatchingSeries(f.getValue()))
                .map(f -> f.getValue().getFigure3().getName())
                .collect(Collectors.toSet());
    }

    private Set<String> TestVerticalKnownRelationships() {
        FigureSeries row1 = figureSeriesMap.get("Col1");

        return figureSeriesMap.entrySet().stream()
                .filter(f -> f.getKey().startsWith("ColAns"))
                .filter(f -> row1.testMatchingSeries(f.getValue()))
                .map(f -> f.getValue().getFigure3().getName())
                .collect(Collectors.toSet());
    }

    private int sumVerticalFigureShapes(VisualFigure figure) throws Exception {
        int total = 0;
        for(ObjectProperties properties : figure.getObjectPropertiesMap().values()) {
            if(properties.verticalObjectShape == ObjectShape.TOP_WEIGHTED) {
                total += -1;
            } else if(properties.verticalObjectShape == ObjectShape.BOTTOM_WEIGHTED) {
                total += 1;
            } else {
                throw new Exception("Unknown shape");
            }
        }

        return total;
    }

    private int sumHorizontalFigureShapes(VisualFigure figure) throws Exception {
        int total = 0;
        for(ObjectProperties properties : figure.getObjectPropertiesMap().values()) {
            if(properties.horizontalObjectShape == ObjectShape.LEFT_WEIGHTED) {
                total += -1;
            } else if(properties.horizontalObjectShape == ObjectShape.RIGHT_WEIGHTED) {
                total += 1;
            } else {
                throw new Exception("Unknown shape");
            }
        }

        return total;
    }

    private void noop() {
    }
}
