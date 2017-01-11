package ravensproject.solver;

public class PixelRatio {
    private double darkPixelRatio;
    private double intersectionPixelRatio;

    public PixelRatio(double darkPixelRatio, double intersectionPixelRatio) {
        this.darkPixelRatio = darkPixelRatio;
        this.intersectionPixelRatio = intersectionPixelRatio;
    }

    public double getDarkPixelRatio() {
        return darkPixelRatio;
    }

    public double getIntersectionPixelRatio() {
        return intersectionPixelRatio;
    }
}
