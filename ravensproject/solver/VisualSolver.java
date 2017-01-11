package ravensproject.solver;

import ravensproject.RavensFigure;
import ravensproject.RavensProblem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VisualSolver {

    private String name;
    private String problemType;
    private boolean hasVerbal = false;
    private boolean hasVisual = false;

    private Map<String, RavensFigure> ravensFigures;

    public VisualSolver(RavensProblem problem) {

        name = problem.getName();

        hasVerbal = problem.hasVerbal();
        hasVisual = problem.hasVisual();
        problemType = problem.getProblemType();

        ravensFigures = problem.getFigures();
    }

    public int Solve() {

        if(hasVisual && problemType.equals("2x2")) {
            return SolveVisual2x2();
        } else if(hasVisual && problemType.equals("3x3") && !name.contains("Challenge") && !name.contains("Ravens") && name.contains("D-04")) {
            long startTime = System.nanoTime();
            int result = SolveVisual3x3();
            long duration = System.nanoTime() - startTime;
            System.out.println(String.format("%s - %f", name, duration / 1000000000.0));
            return result;
        }

        return -1;
    }

    private int SolveVisual2x2() {
        return -1;
    }

    private int SolveVisual3x3() {

        Map<String, VisualFigure> answerFigures = ravensFigures.entrySet().stream()
                .filter(s -> s.getKey().matches("^\\d+$"))
                .map(s -> new VisualFigure(s.getValue()))
                .collect(Collectors.toMap(s -> s.getName(), s -> s));

        Map<String, VisualFigure> matrixFigures = ravensFigures.entrySet().stream()
                .filter(s -> s.getKey().matches("^[A-H]$"))
                .map(s -> new VisualFigure(s.getValue()))
                .collect(Collectors.toMap(s -> s.getName(), s -> s));

        //Model3x3 model = new Model3x3(name, a, b, c, d, e, f, g, h, answerFigures);
        AdvancedSolver advancedSolver = new AdvancedSolver(name, matrixFigures, answerFigures);

        return advancedSolver.Solve();
    }
}
