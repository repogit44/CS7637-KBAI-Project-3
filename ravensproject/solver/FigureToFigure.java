package ravensproject.solver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FigureToFigure {

    private VisualFigure figure1;
    private VisualFigure figure2;

    private double intersectionRatio;
    private double darkRatio;

    private Map<Tuple<String, String>, PixelRatio> pixelRatioMap;

    public FigureToFigure(VisualFigure figure1, VisualFigure figure2) {

        if(figure1.getName().matches("\\d+$")) {
            this.figure2 = figure1;
            this.figure1 = figure2;
        } else if(figure2.getName().matches("\\d+$")) {
            this.figure1 = figure1;
            this.figure2 = figure2;
        } else {
            int compareTo = figure1.getName().compareTo(figure2.getName());
            if (compareTo < 0) {
                this.figure1 = figure1;
                this.figure2 = figure2;
            } else if (compareTo > 0) {
                this.figure1 = figure2;
                this.figure2 = figure1;
            } else {
                throw new IllegalArgumentException("Figures are equal");
            }
        }

        this.intersectionRatio = FigureCompare.CalculateIntersectionPixelRatio(figure1, figure2);
        this.darkRatio = FigureCompare.CalculateDarkPixelRatio(figure1, figure2);
    }

    public double getIntersectionRatio() {
        return intersectionRatio;
    }

    public double getDarkRatio() {
        return darkRatio;
    }

    public double getEditedDarkRatio() {
        return FigureCompare.CalculateDarkPixelRatio(figure1.getFullEditedPixelSet(), figure2.getFullEditedPixelSet());
    }

    public double getEditedIntersectionRatio() {
        return FigureCompare.CalculateIntersectionPixelRatio(figure1.getFullEditedPixelSet(), figure2.getFullEditedPixelSet());
    }

    @Override
    public String toString() {
        return figure1.getName() + "to" + figure2.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof FigureToFigure)) return false;

        FigureToFigure figure = (FigureToFigure) obj;
        return this.toString().equals(figure.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public Map<Tuple<String, String>, PixelRatio> getSetRatios() {

        if(pixelRatioMap == null) {
            Map<String, Set<Pixel>> figure1PixelSetMap = figure1.getPixelSetMap();
            Map<String, Set<Pixel>> figure2PixelSetMap = figure2.getPixelSetMap();

            pixelRatioMap = new HashMap<>();

            for (Map.Entry<String, Set<Pixel>> figure1Set : figure1PixelSetMap.entrySet()) {
                for (Map.Entry<String, Set<Pixel>> figure2Set : figure2PixelSetMap.entrySet()) {
                    double darkPixelRatio = FigureCompare.CalculateDarkPixelRatio(figure1Set.getValue(), figure2Set.getValue());
                    double intersectionPixelRatio = FigureCompare.CalculateIntersectionPixelRatio(figure1Set.getValue(), figure2Set.getValue());
                    pixelRatioMap.put(new Tuple<>(figure1Set.getKey(), figure2Set.getKey()), new PixelRatio(darkPixelRatio, intersectionPixelRatio));
                }
            }
        }

        return pixelRatioMap;
    }

    public VisualFigure getFigure1() { return figure1; }

    public VisualFigure getFigure2() { return figure2; }
}
