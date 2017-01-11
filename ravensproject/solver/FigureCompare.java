package ravensproject.solver;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FigureCompare {

    public static double CalculateDarkPixelRatio(VisualFigure figure1, VisualFigure figure2) {
        return CalculateDarkPixelRatio(figure1.getFullPixelSet(), figure2.getFullPixelSet());
    }

    public static double CalculateDarkPixelRatio(Set<Pixel> pixelSet1, Set<Pixel> pixelSet2) {
        int dark1 = pixelSet1.size();
        int dark2 = pixelSet2.size();

        double darkPercentage1 = ((double) dark1 / (dark1 + dark2)) * 100;
        double darkPercentage2 = ((double) dark2 / (dark1 + dark2)) * 100;

        return darkPercentage2 - darkPercentage1;
    }

    public static double CalculateIntersectionPixelRatio(VisualFigure figure1, VisualFigure figure2) {
        return CalculateIntersectionPixelRatio(figure1.getFullPixelSet(), figure2.getFullPixelSet());
    }

    public static double CalculateIntersectionPixelRatio(Set<Pixel> pixelSet1, Set<Pixel> pixelSet2) {
        int dark1 = pixelSet1.size();
        int dark2 = pixelSet2.size();

        Set<Pixel> darkIntersection = Intersection(pixelSet1, pixelSet2);
        double darkIntersectionSize = darkIntersection.size();

        if (dark1 + dark2 == 0) {
            return 0;
        }
        return (darkIntersectionSize / (dark1 + dark2)) * 100;
    }

    public static <T> Set<T> Intersection(Set<T> set1, Set<T> set2) {
        Set<T> set1Copy = new HashSet<T>(set1);
        set1Copy.retainAll(set2);
        return set1Copy;
    }

    public static <T> Set<T> Difference(Set<T> set1, Set<T> set2) {
        Set<T> set1Copy = new HashSet<T>(set1);
        set1Copy.removeAll(set2);
        return set1Copy;
    }

    public static <T> Set<T> Union(Set<T> set1, Set<T> set2) {
        Set<T> set1Copy = new HashSet<T>(set1);
        set1Copy.addAll(set2);
        return set1Copy;
    }

    public static <T> Set<T> XOR(Set<T> set1, Set<T> set2) {
        Set<T> xor = new HashSet<>();
        Set<T> union = Union(set1, set2);

        xor.addAll(
            union.stream()
            .filter(item -> (set1.contains(item) && !set2.contains(item)) || (!set1.contains(item) && set2.contains(item)))
            .collect(Collectors.toList()));

        return xor;
    }
}
