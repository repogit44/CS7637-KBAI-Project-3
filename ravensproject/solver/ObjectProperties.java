package ravensproject.solver;

import java.util.Set;

public class ObjectProperties {
    public String name;

    public int pixelCount;

    public int minX;
    public int minY;
    public int maxX;
    public int maxY;

    public int unfloodedCount = -1;
    public Set<Pixel> unfloodedPixels;

    public ObjectShape verticalObjectShape = ObjectShape.UNKNOWN;
    public ObjectShape horizontalObjectShape = ObjectShape.UNKNOWN;

    public double getDensity() {
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        double area = width * height;
        double density = (pixelCount / area) * 100.0;

        return density;
    }

    public boolean isHollow() {
        return unfloodedCount > 1;
    }

    public boolean isSolid() {
        return unfloodedCount == 0;
    }

    public boolean surrounds(ObjectProperties objectProperties) {
        return minX < objectProperties.minX
                && minY < objectProperties.minY
                && maxX > objectProperties.maxX
                && maxY > objectProperties.maxY;
    }

    public int getWidth() {
        return maxX - minX + 1;
    }

    public int getHeight() {
        return maxY - minY + 1;
    }
}
