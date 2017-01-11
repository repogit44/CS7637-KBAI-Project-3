package ravensproject.solver;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class FigureSeries {

    private final VisualFigure figure1;
    private final VisualFigure figure2;
    private final VisualFigure figure3;

    private FigureToFigure left;
    private FigureToFigure right;
    private FigureToFigure outside;

    public FigureSeries(VisualFigure figure1, VisualFigure figure2, VisualFigure figure3) {

        this.figure1 = figure1;
        this.figure2 = figure2;
        this.figure3 = figure3;

        this.left = new FigureToFigure(figure1, figure2);
        this.right = new FigureToFigure(figure2, figure3);
        this.outside = new FigureToFigure(figure1, figure3);
    }

    public FigureToFigure getLeft() { return left; }

    public FigureToFigure getRight() { return right; }

    public FigureToFigure getOutside() { return outside; }

    public VisualFigure getFigure1() {
        return figure1;
    }

    public int getFigure1ObjectCount() {
        return figure1.getEditedPixelSetMap().size();
    }

    public VisualFigure getFigure2() {
        return figure2;
    }

    public int getFigure2ObjectCount() {
        return figure2.getEditedPixelSetMap().size();
    }

    public VisualFigure getFigure3() {
        return figure3;
    }

    public int getFigure3ObjectCount() {
        return figure3.getEditedPixelSetMap().size();
    }

    public boolean hasEqualDarkRatio() {
        return hasEqualDarkRatio(false);
    }

    public boolean hasEqualDarkRatio(boolean useEdited) {
        if (useEdited) {
            double leftEditedDarkRatio = left.getEditedDarkRatio();
            double rightEditedDarkRatio = right.getEditedDarkRatio();

            return darkRatioCloseTo0(leftEditedDarkRatio) && darkRatioCloseTo0(rightEditedDarkRatio);
        } else {
            //return darkRatioCloseTo0(left.getDarkRatio()) && darkRatioCloseTo0(right.getDarkRatio());
            return left.getDarkRatio() == right.getDarkRatio();
        }
    }

    public boolean hasMatchingFigures(FigureSeries series) {
        return hasMatchingFigures(series, false);
    }

    public boolean hasMatchingFigures(FigureSeries series, boolean useEdited) {

        FigureToFigure f1to2 = new FigureToFigure(figure1, series.figure2);
        FigureToFigure f1to3 = new FigureToFigure(figure1, series.figure3);

        FigureToFigure f2to1 = new FigureToFigure(figure2, series.figure1);
        FigureToFigure f2to3 = new FigureToFigure(figure2, series.figure3);

        FigureToFigure f3to1 = new FigureToFigure(figure3, series.figure1);
        FigureToFigure f3to2 = new FigureToFigure(figure3, series.figure2);

        boolean set1 = darkRatioCloseTo0(f1to2, useEdited) && darkRatioCloseTo0(f2to3, useEdited) && darkRatioCloseTo0(f3to1, useEdited);
        boolean set2 = darkRatioCloseTo0(f1to3, useEdited) && darkRatioCloseTo0(f2to1, useEdited) && darkRatioCloseTo0(f3to2, useEdited);

        return set1 || set2;
    }

    public boolean hasDuplicateFigures(boolean useEdited) {
        FigureToFigure f1to2 = new FigureToFigure(figure1, figure2);
        FigureToFigure f1to3 = new FigureToFigure(figure1, figure3);
        FigureToFigure f2to3 = new FigureToFigure(figure2, figure3);

        return (darkRatioCloseTo0(f1to2, useEdited) && intersectionRatioCloseTo50(f1to2, useEdited))
                || (darkRatioCloseTo0(f1to3, useEdited) && intersectionRatioCloseTo50(f1to3, useEdited))
                || (darkRatioCloseTo0(f2to3, useEdited) && intersectionRatioCloseTo50(f2to3, useEdited));
    }

    public boolean hasAtLeastOneMatchingFigure(FigureSeries series) {
        return hasAtLeastOneMatchingFigure(series, false);
    }

    public boolean hasAtLeastOneMatchingFigure(FigureSeries series, boolean useEdited) {
        FigureToFigure f1to2 = new FigureToFigure(figure1, series.figure2);
        FigureToFigure f1to3 = new FigureToFigure(figure1, series.figure3);

        FigureToFigure f2to1 = new FigureToFigure(figure2, series.figure1);
        FigureToFigure f2to3 = new FigureToFigure(figure2, series.figure3);

        FigureToFigure f3to1 = new FigureToFigure(figure3, series.figure1);
        FigureToFigure f3to2 = new FigureToFigure(figure3, series.figure2);

        boolean set1 = (darkRatioCloseTo0(f1to2, useEdited) && intersectionRatioCloseTo50(f1to2, useEdited))
                || (darkRatioCloseTo0(f2to3, useEdited) && intersectionRatioCloseTo50(f2to3, useEdited))
                || (darkRatioCloseTo0(f3to1, useEdited) && intersectionRatioCloseTo50(f3to1, useEdited));
        boolean set2 = (darkRatioCloseTo0(f1to3, useEdited) && intersectionRatioCloseTo50(f1to3, useEdited))
                || (darkRatioCloseTo0(f2to1, useEdited) && intersectionRatioCloseTo50(f2to1, useEdited))
                || (darkRatioCloseTo0(f3to2, useEdited) && intersectionRatioCloseTo50(f3to2, useEdited));

        return set1 || set2;
    }

    public void removePartialMatchingFigures(FigureSeries center, FigureSeries right) {

        FigureToFigure f1toC2 = new FigureToFigure(figure1, center.figure2);
        FigureToFigure f1toC3 = new FigureToFigure(figure1, center.figure3);

        FigureToFigure f2toC1 = new FigureToFigure(figure2, center.figure1);
        FigureToFigure f2toC3 = new FigureToFigure(figure2, center.figure3);

        FigureToFigure f3toC1 = new FigureToFigure(figure3, center.figure1);
        FigureToFigure f3toC2 = new FigureToFigure(figure3, center.figure2);

        FigureToFigure C1toR2 = new FigureToFigure(center.figure1, right.figure2);
        FigureToFigure C1toR3 = new FigureToFigure(center.figure1, right.figure3);

        FigureToFigure C2toR1 = new FigureToFigure(center.figure2, right.figure1);
        FigureToFigure C2toR3 = new FigureToFigure(center.figure2, right.figure3);

        FigureToFigure C3toR1 = new FigureToFigure(center.figure3, right.figure1);
        FigureToFigure C3toR2 = new FigureToFigure(center.figure3, right.figure2);

        removePartialMatchingFigures(f1toC2, C2toR3, false);
        removePartialMatchingFigures(f2toC3, C3toR1, true);
        removePartialMatchingFigures(f3toC1, C1toR2, true);

        removePartialMatchingFigures(f1toC3, C3toR2, true);
        removePartialMatchingFigures(f2toC1, C1toR3, true);
        removePartialMatchingFigures(f3toC2, C2toR1, true);

        noop();
    }

    public void removePartialMatchingFigures() {
        removePartialMatchingFigures(this.left, this.right, false);
    }

    public void removePartialMatchingFigures(boolean useEdited) {
        removePartialMatchingFigures(this.left, this.right, true);
    }

    public void removePartialMatchingFigures(FigureToFigure f2fLeft, FigureToFigure f2fRight, boolean useEdited) {
        Map<Tuple<String, String>, PixelRatio> leftSetRatios = f2fLeft.getSetRatios();
        Map<Tuple<String, String>, PixelRatio> rightSetRatios = f2fRight.getSetRatios();

        Map<Tuple<String, String>, PixelRatio> leftClose = new HashMap<>();
        for (Map.Entry<Tuple<String, String>, PixelRatio> pixelRatioEntry : leftSetRatios.entrySet()) {
            double darkPixelRatio = pixelRatioEntry.getValue().getDarkPixelRatio();
            double intersectionPixelRatio = pixelRatioEntry.getValue().getIntersectionPixelRatio();
            if (darkRatioCloseTo0(darkPixelRatio) && intersectionRatioCloseTo50(intersectionPixelRatio)) {
                leftClose.put(pixelRatioEntry.getKey(), pixelRatioEntry.getValue());
            }
        }

        Map<Tuple<String, String>, PixelRatio> rightClose = new HashMap<>();
        for (Map.Entry<Tuple<String, String>, PixelRatio> pixelRatioEntry : rightSetRatios.entrySet()) {
            double darkPixelRatio = pixelRatioEntry.getValue().getDarkPixelRatio();
            double intersectionPixelRatio = pixelRatioEntry.getValue().getIntersectionPixelRatio();
            if (darkRatioCloseTo0(darkPixelRatio) && intersectionRatioCloseTo50(intersectionPixelRatio)) {
                rightClose.put(pixelRatioEntry.getKey(), pixelRatioEntry.getValue());
            }
        }


        VisualFigure figure1 = f2fLeft.getFigure1();
        VisualFigure figure2 = f2fLeft.getFigure2();
        VisualFigure figure3 = f2fRight.getFigure2();

        if (!useEdited) {
            figure1.setEditedPixelSetMap(figure1.getPixelSetMap());
            figure2.setEditedPixelSetMap(figure2.getPixelSetMap());
            figure3.setEditedPixelSetMap(figure3.getPixelSetMap());
        }

        for (Map.Entry<Tuple<String, String>, PixelRatio> leftEntry : leftClose.entrySet()) {
            for (Map.Entry<Tuple<String, String>, PixelRatio> rightEntry : rightClose.entrySet()) {
                if (leftEntry.getKey().getRight().equals(rightEntry.getKey().getLeft())) {
                    Map<String, Set<Pixel>> figure1EditedPixelSetMap = figure1.getEditedPixelSetMap();
                    figure1EditedPixelSetMap.remove(leftEntry.getKey().getLeft());
                    figure1.setEditedPixelSetMap(figure1EditedPixelSetMap);

                    Map<String, Set<Pixel>> figure2EditedPixelSetMap = figure2.getEditedPixelSetMap();
                    figure2EditedPixelSetMap.remove(leftEntry.getKey().getRight());
                    figure2.setEditedPixelSetMap(figure2EditedPixelSetMap);

                    Map<String, Set<Pixel>> figure3EditedPixelSetMap = figure3.getEditedPixelSetMap();
                    figure3EditedPixelSetMap.remove(rightEntry.getKey().getRight());
                    figure3.setEditedPixelSetMap(figure3EditedPixelSetMap);

                    noop();
                }
            }
        }
    }

    public void classifyFigures() {
        classifyFigure(figure1);
        classifyFigure(figure2);
        classifyFigure(figure3);
    }

    private void classifyFigure(VisualFigure figure) {

        Map<String, ObjectProperties> objectPropertiesMap = new HashMap<>();
        for (Map.Entry<String, Set<Pixel>> pixelSetEntry : figure.getPixelSetMap().entrySet()) {
            Set<Pixel> pixelSet = pixelSetEntry.getValue();

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (Pixel pixel : pixelSet) {
                if (pixel.getX() < minX) {
                    minX = pixel.getX();
                }
                if (pixel.getX() > maxX) {
                    maxX = pixel.getX();
                }
                if (pixel.getY() < minY) {
                    minY = pixel.getY();
                }
                if (pixel.getY() > maxY) {
                    maxY = pixel.getY();
                }
            }

            ObjectProperties objectProperties = new ObjectProperties();
            objectProperties.name = pixelSetEntry.getKey();
            objectProperties.pixelCount = pixelSet.size();
            objectProperties.minX = minX;
            objectProperties.minY = minY;
            objectProperties.maxX = maxX;
            objectProperties.maxY = maxY;

            floodFill(pixelSet, objectProperties);
            classifyShape(pixelSet, objectProperties);

            objectPropertiesMap.put(pixelSetEntry.getKey(), objectProperties);
        }
        figure.setObjectPropertiesMap(objectPropertiesMap);
    }

    private boolean darkRatioCloseTo0(FigureToFigure figureToFigure, boolean useEdited) {
        if (useEdited) {
            double editedDarkRatio = figureToFigure.getEditedDarkRatio();
            return darkRatioCloseTo0(editedDarkRatio);
        } else {
            double darkRatio = figureToFigure.getDarkRatio();
            return darkRatioCloseTo0(darkRatio);
        }
    }

    private boolean darkRatioCloseTo0(double darkPixelRatio) {
        return Math.abs(darkPixelRatio) < 6;
    }

    private boolean intersectionRatioCloseTo50(FigureToFigure figureToFigure, boolean useEdited) {
        if (useEdited) {
            double editedIntersectionRatio = figureToFigure.getEditedIntersectionRatio();
            return intersectionRatioCloseTo50(editedIntersectionRatio);
        } else {
            double intersectionRatio = figureToFigure.getIntersectionRatio();
            return intersectionRatioCloseTo50(intersectionRatio);
        }
    }

    private boolean intersectionRatioCloseTo50(double intersectionPixelRatio) {
        return Math.abs(intersectionPixelRatio) > 40;
    }

    private Set<Pixel> buildObjectXorSet(VisualFigure leftFigure, VisualFigure rightFigure) {
        Set<Pixel> xorResult = new HashSet<>();
        for (Map.Entry<String, Set<Pixel>> leftSetEntry : leftFigure.getPixelSetMap().entrySet()) {
            Set<Pixel> leftPixelSet = leftSetEntry.getValue();
            boolean foundMatch = false;

            for (Map.Entry<String, Set<Pixel>> rightSetEntry : rightFigure.getPixelSetMap().entrySet()) {
                Set<Pixel> rightPixelSet = rightSetEntry.getValue();
                Set<Pixel> intersect = FigureCompare.Intersection(leftPixelSet, rightPixelSet);
                Tuple<Double, Double> ratios = CalculateRatios(leftPixelSet, intersect);
                if (darkRatioCloseTo0(ratios.getLeft()) && intersectionRatioCloseTo50(ratios.getRight())) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                xorResult.addAll(leftPixelSet);
            }
        }

        for (Map.Entry<String, Set<Pixel>> leftSetEntry : rightFigure.getPixelSetMap().entrySet()) {
            Set<Pixel> leftPixelSet = leftSetEntry.getValue();
            boolean foundMatch = false;

            for (Map.Entry<String, Set<Pixel>> rightSetEntry : leftFigure.getPixelSetMap().entrySet()) {
                Set<Pixel> rightPixelSet = rightSetEntry.getValue();
                Set<Pixel> intersect = FigureCompare.Intersection(leftPixelSet, rightPixelSet);
                Tuple<Double, Double> ratios = CalculateRatios(leftPixelSet, intersect);
                if (darkRatioCloseTo0(ratios.getLeft()) && intersectionRatioCloseTo50(ratios.getRight())) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                xorResult.addAll(leftPixelSet);
            }
        }

        return xorResult;
    }

    public Set<String> hasObjectXORMatch() {
        Set<Pixel> L = figure1.getFullPixelSet();
        Set<Pixel> C = figure2.getFullPixelSet();
        Set<Pixel> R = figure3.getFullPixelSet();

        Set<Pixel> xorLC = buildObjectXorSet(figure1, figure2);
        Set<Pixel> xorLR = buildObjectXorSet(figure1, figure3);
        Set<Pixel> xorCR = buildObjectXorSet(figure2, figure3);

        Tuple<Double, Double> resultLCR = CalculateRatios(xorLC, R);
        Tuple<Double, Double> resultLRC = CalculateRatios(xorLR, C);
        Tuple<Double, Double> resultCRL = CalculateRatios(xorCR, L);

        Set<String> relationshipOrder = new HashSet<>();

        if (darkRatioCloseTo0(resultLCR.getLeft()) && intersectionRatioCloseTo50(resultLCR.getRight())) {
            relationshipOrder.add("L+C=R");
        }
        if (darkRatioCloseTo0(resultLRC.getLeft()) && intersectionRatioCloseTo50(resultLRC.getRight())) {
            relationshipOrder.add("L+R=C");
        }
        if (darkRatioCloseTo0(resultCRL.getLeft()) && intersectionRatioCloseTo50(resultCRL.getRight())) {
            relationshipOrder.add("C+R=L");
        }

        return relationshipOrder;
    }

    public Set<String> hasAndMatch() {
        return hasAndMatch(FigureShift.NONE);
    }

    public Set<String> hasAndMatch(FigureShift shift) {
        Set<Pixel> L = figure1.getFullPixelSet();
        Set<Pixel> C = figure2.getFullPixelSet();
        Set<Pixel> R = figure3.getFullPixelSet();

        if (shift != FigureShift.NONE) {
            L = shiftPixelSet(L, shift);
            C = shiftPixelSet(C, shift);
            R = shiftPixelSet(R, shift);
        }

        Set<Pixel> intersectionLC = FigureCompare.Intersection(L, C);
        Set<Pixel> intersectionLR = FigureCompare.Intersection(L, R);
        Set<Pixel> intersectionCR = FigureCompare.Intersection(C, R);

        if (shift != FigureShift.NONE) {
            intersectionLC = shiftPixelSet(intersectionLC, shift);
            intersectionLR = shiftPixelSet(intersectionLR, shift);
            intersectionCR = shiftPixelSet(intersectionCR, shift);
        }

        Tuple<Double, Double> resultLCR = CalculateRatios(intersectionLC, R);
        Tuple<Double, Double> resultLRC = CalculateRatios(intersectionLR, C);
        Tuple<Double, Double> resultCRL = CalculateRatios(intersectionCR, L);

        Set<String> relationshipOrder = new HashSet<>();

        if (darkRatioCloseTo0(resultLCR.getLeft()) && intersectionRatioCloseTo50(resultLCR.getRight())) {
            relationshipOrder.add("L+C=R");
        }
        if (darkRatioCloseTo0(resultLRC.getLeft()) && intersectionRatioCloseTo50(resultLRC.getRight())) {
            relationshipOrder.add("L+R=C");
        }
        if (darkRatioCloseTo0(resultCRL.getLeft()) && intersectionRatioCloseTo50(resultCRL.getRight())) {
            relationshipOrder.add("C+R=L");
        }

        return relationshipOrder;
    }

    public Set<String> hasOrMatch() {
        return hasOrMatch(FigureShift.NONE);
    }

    public Set<String> hasOrMatch(FigureShift shift) {
        Set<Pixel> L = figure1.getFullPixelSet();
        Set<Pixel> C = figure2.getFullPixelSet();
        Set<Pixel> R = figure3.getFullPixelSet();

        if (shift != FigureShift.NONE) {
            L = shiftPixelSet(L, shift);
            C = shiftPixelSet(C, shift);
            R = shiftPixelSet(R, shift);
        }

        Set<Pixel> unionLC = FigureCompare.Union(L, C);
        Set<Pixel> unionLR = FigureCompare.Union(L, R);
        Set<Pixel> unionCR = FigureCompare.Union(C, R);

        if (shift != FigureShift.NONE) {
            unionLC = shiftPixelSet(unionLC, shift);
            unionLR = shiftPixelSet(unionLR, shift);
            unionCR = shiftPixelSet(unionCR, shift);
        }

        Tuple<Double, Double> resultLCR = CalculateRatios(unionLC, R);
        Tuple<Double, Double> resultLRC = CalculateRatios(unionLR, C);
        Tuple<Double, Double> resultCRL = CalculateRatios(unionCR, L);

        Set<String> relationshipOrder = new HashSet<>();

        if (darkRatioCloseTo0(resultLCR.getLeft()) && intersectionRatioCloseTo50(resultLCR.getRight())) {
            relationshipOrder.add("L+C=R");
        }
        if (darkRatioCloseTo0(resultLRC.getLeft()) && intersectionRatioCloseTo50(resultLRC.getRight())) {
            relationshipOrder.add("L+R=C");
        }
        if (darkRatioCloseTo0(resultCRL.getLeft()) && intersectionRatioCloseTo50(resultCRL.getRight())) {
            relationshipOrder.add("C+R=L");
        }

        return relationshipOrder;
    }

    public Set<String> hasXORMatch() {
        return hasXORMatch(FigureShift.NONE);
    }

    public Set<String> hasXORMatch(FigureShift shift) {
        Set<Pixel> L = figure1.getFullPixelSet();
        Set<Pixel> C = figure2.getFullPixelSet();
        Set<Pixel> R = figure3.getFullPixelSet();

        if (shift != FigureShift.NONE) {
            L = shiftPixelSet(L, shift);
            C = shiftPixelSet(C, shift);
            R = shiftPixelSet(R, shift);
        }

        Set<Pixel> xorLC = FigureCompare.XOR(L, C);
        Set<Pixel> xorLR = FigureCompare.XOR(L, R);
        Set<Pixel> xorCR = FigureCompare.XOR(C, R);

        if (shift != FigureShift.NONE) {
            xorLC = shiftPixelSet(xorLC, shift);
            xorLR = shiftPixelSet(xorLR, shift);
            xorCR = shiftPixelSet(xorCR, shift);
        }

        Tuple<Double, Double> resultLCR = CalculateRatios(xorLC, R);
        Tuple<Double, Double> resultLRC = CalculateRatios(xorLR, C);
        Tuple<Double, Double> resultCRL = CalculateRatios(xorCR, L);

        Set<String> relationshipOrder = new HashSet<>();

        if (darkRatioCloseTo0(resultLCR.getLeft()) && intersectionRatioCloseTo50(resultLCR.getRight())) {
            relationshipOrder.add("L+C=R");
        }
        if (darkRatioCloseTo0(resultLRC.getLeft()) && intersectionRatioCloseTo50(resultLRC.getRight())) {
            relationshipOrder.add("L+R=C");
        }
        if (darkRatioCloseTo0(resultCRL.getLeft()) && intersectionRatioCloseTo50(resultCRL.getRight())) {
            relationshipOrder.add("C+R=L");
        }

        return relationshipOrder;
    }

    public Set<String> hasDifferenceMatch() {
        return hasDifferenceMatch(FigureShift.NONE);
    }

    public Set<String> hasDifferenceMatch(FigureShift shift) {
        Set<Pixel> L = figure1.getFullPixelSet();
        Set<Pixel> C = figure2.getFullPixelSet();
        Set<Pixel> R = figure3.getFullPixelSet();

        if (shift != FigureShift.NONE) {
            L = shiftPixelSet(L, shift);
            C = shiftPixelSet(C, shift);
            R = shiftPixelSet(R, shift);
        }

        Set<Pixel> differenceLC = FigureCompare.Difference(L, C);
        Set<Pixel> differenceLR = FigureCompare.Difference(L, R);
        Set<Pixel> differenceCR = FigureCompare.Difference(C, R);

        if (shift != FigureShift.NONE) {
            differenceLC = shiftPixelSet(differenceLC, shift);
            differenceLR = shiftPixelSet(differenceLR, shift);
            differenceCR = shiftPixelSet(differenceCR, shift);
        }

        Tuple<Double, Double> resultLCR = CalculateRatios(differenceLC, R);
        Tuple<Double, Double> resultLRC = CalculateRatios(differenceLR, C);
        Tuple<Double, Double> resultCRL = CalculateRatios(differenceCR, L);

        Set<String> relationshipOrder = new HashSet<>();

        if (darkRatioCloseTo0(resultLCR.getLeft()) && intersectionRatioCloseTo50(resultLCR.getRight())) {
            relationshipOrder.add("L+C=R");
        }
        if (darkRatioCloseTo0(resultLRC.getLeft()) && intersectionRatioCloseTo50(resultLRC.getRight())) {
            relationshipOrder.add("L+R=C");
        }
        if (darkRatioCloseTo0(resultCRL.getLeft()) && intersectionRatioCloseTo50(resultCRL.getRight())) {
            relationshipOrder.add("C+R=L");
        }

        return relationshipOrder;
    }

    public void shift(FigureShift shiftDirection) {
        shiftFigure(figure1, shiftDirection);
        shiftFigure(figure2, shiftDirection);
        shiftFigure(figure3, shiftDirection);
    }

    public void shiftFigure(VisualFigure figure, FigureShift shiftDirection) {
        calculateFigureProperties(figure);
        Set<Pixel> shiftedPixelSet = shiftPixelSet(figure.getFullPixelSet(), shiftDirection);

        figure.setEditedPixelSet(shiftedPixelSet);
    }

    public Set<Pixel> shiftPixelSet(Set<Pixel> pixelSet, FigureShift shiftDirection) {
        ObjectProperties pixelSetProperties = calculatePixelSetProperties(pixelSet);

        Set<Pixel> editedPixelSet = new HashSet<>();

        int maxSize = 200; // slightly larger than largest known image size of 185
        int rightShift = maxSize - pixelSetProperties.maxX;
        int bottomShift = maxSize - pixelSetProperties.maxY;

        for (Pixel pixel : pixelSet) {
            Pixel shiftedPixel;
            switch (shiftDirection) {
                case LEFT:
                    shiftedPixel = new Pixel(pixel.getX() - pixelSetProperties.minX, pixel.getY());
                    break;
                case RIGHT:
                    shiftedPixel = new Pixel( pixel.getX() + rightShift, pixel.getY());
                    break;
                case TOP:
                    shiftedPixel = new Pixel(pixel.getX(), pixel.getY() - pixelSetProperties.minY);
                    break;
                case BOTTOM:
                    shiftedPixel = new Pixel(pixel.getX(), pixel.getY() + bottomShift);
                    break;
                default:
                    throw new IllegalStateException("Invalid shift direction");
            }

            editedPixelSet.add(shiftedPixel);
        }

        return editedPixelSet;
    }

    public void calculateFigureProperties(VisualFigure figure) {
        ObjectProperties objectProperties = calculatePixelSetProperties(figure.getFullPixelSet());
        figure.setFigureProperties(objectProperties);
    }

    public ObjectProperties calculatePixelSetProperties(Set<Pixel> pixelSet) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Pixel pixel : pixelSet) {
            if (pixel.getX() < minX) {
                minX = pixel.getX();
            }
            if (pixel.getX() > maxX) {
                maxX = pixel.getX();
            }
            if (pixel.getY() < minY) {
                minY = pixel.getY();
            }
            if (pixel.getY() > maxY) {
                maxY = pixel.getY();
            }
        }

        ObjectProperties objectProperties = new ObjectProperties();
        objectProperties.minX = minX;
        objectProperties.minY = minY;
        objectProperties.maxX = maxX;
        objectProperties.maxY = maxY;

        floodFill(pixelSet, objectProperties);

        return objectProperties;
    }

    public boolean testMatchingSeries(FigureSeries series) {
        List<Tuple<Tuple<String, String>, Tuple<String, String>>> matchingSeries = new ArrayList<>();
        matchingSeries.add(new Tuple<>(new Tuple<>("28.09056", "26.5131"), new Tuple<>("14.33068", "-4.67197")));
        matchingSeries.add(new Tuple<>(new Tuple<>("10.16407", "6.31889"), new Tuple<>("14.33068", "-4.67197")));

        matchingSeries.add(new Tuple<>(new Tuple<>("38.44775", "-17.5903"), new Tuple<>("27.525", "20.29913")));
        matchingSeries.add(new Tuple<>(new Tuple<>("31.05844", "-31.70694"), new Tuple<>("27.525", "20.29913")));

        matchingSeries.add(new Tuple<>(new Tuple<>("11.03701", "-31.77726"), new Tuple<>("10.2279", "9.8388")));
        matchingSeries.add(new Tuple<>(new Tuple<>("12.1995", "-41.80122"), new Tuple<>("10.2279", "9.8388")));

        matchingSeries.add(new Tuple<>(new Tuple<>("41.68869", "16.62261"), new Tuple<>("36.74605", "26.5079")));
        matchingSeries.add(new Tuple<>(new Tuple<>("37.60127", "0"), new Tuple<>("36.74605", "26.5079")));

        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        for(Tuple<Tuple<String, String>, Tuple<String, String>> tuple : matchingSeries) {
            if(df.format(outside.getIntersectionRatio()).equals(tuple.getLeft().getLeft())
                    && df.format(outside.getDarkRatio()).equals(tuple.getLeft().getRight())) {

                return df.format(series.outside.getIntersectionRatio()).equals(tuple.getRight().getLeft())
                        && df.format(series.outside.getDarkRatio()).equals(tuple.getRight().getRight());
            }
        }

        return false;
    }

    private void floodFill(Set<Pixel> pixelSet, ObjectProperties objectProperties) {

        Queue<Pixel> pixelQueue = new LinkedList<>();
        Pixel outsidePixel = new Pixel(objectProperties.minX - 1, objectProperties.minY - 1);

        pixelQueue.add(outsidePixel);

        Set<Pixel> floodedPixels = new HashSet<>();

        while (!pixelQueue.isEmpty()) {
            Pixel n = pixelQueue.remove();
            if (!pixelSet.contains(n) && !floodedPixels.contains(n)) {
                floodedPixels.add(n);
                Pixel north = new Pixel(n.getX(), n.getY() - 1);
                Pixel south = new Pixel(n.getX(), n.getY() + 1);
                Pixel east = new Pixel(n.getX() + 1, n.getY());
                Pixel west = new Pixel(n.getX() - 1, n.getY());
                if (pixelIsInBounds(north, objectProperties) && !pixelSet.contains(north) && !floodedPixels.contains(north)) {
                    pixelQueue.add(north);
                }
                if (pixelIsInBounds(south, objectProperties) && !pixelSet.contains(south) && !floodedPixels.contains(south)) {
                    pixelQueue.add(south);
                }
                if (pixelIsInBounds(east, objectProperties) && !pixelSet.contains(east) && !floodedPixels.contains(east)) {
                    pixelQueue.add(east);
                }
                if (pixelIsInBounds(west, objectProperties) && !pixelSet.contains(west) && !floodedPixels.contains(west)) {
                    pixelQueue.add(west);
                }
            }
        }

        Set<Pixel> unionFill = new HashSet<>();
        unionFill.addAll(pixelSet);
        unionFill.addAll(floodedPixels);

        Set<Pixel> remainingEmpty = new HashSet<>();

        for (int y = objectProperties.minY; y <= objectProperties.maxY; y++) {
            for (int x = objectProperties.minX; x <= objectProperties.maxX; x++) {
                Pixel pixel = new Pixel(x, y);
                if (!unionFill.contains(pixel)) {
                    remainingEmpty.add(pixel);
                }
            }
        }

        objectProperties.unfloodedCount = remainingEmpty.size();
        objectProperties.unfloodedPixels = remainingEmpty;
    }

    private void classifyShape(Set<Pixel> pixelSet, ObjectProperties objectProperties) {
        int width = objectProperties.getWidth();
        int height = objectProperties.getHeight();

        // picking to 3 rows, bottom 3 rows
        if (height > 6) {

            int topRowsPixels = 0;
            int bottomRowPixels = 0;

            for (int y = objectProperties.minY; y <= objectProperties.minY + 2; y++) {
                for (int x = objectProperties.minX; x <= objectProperties.maxX; x++) {
                    if (pixelSet.contains(new Pixel(x, y))) {
                        topRowsPixels += 1;
                    }
                }
            }

            for (int y = objectProperties.maxY - 2; y <= objectProperties.maxY; y++) {
                for (int x = objectProperties.minX; x <= objectProperties.maxX; x++) {
                    if (pixelSet.contains(new Pixel(x, y))) {
                        bottomRowPixels += 1;
                    }
                }
            }

            if (topRowsPixels > bottomRowPixels) {
                objectProperties.verticalObjectShape = ObjectShape.TOP_WEIGHTED;
            } else if (topRowsPixels < bottomRowPixels) {
                objectProperties.verticalObjectShape = ObjectShape.BOTTOM_WEIGHTED;
            }
        }

        if (width > 6) {

            int leftRowsPixels = 0;
            int rightRowPixels = 0;

            for (int y = objectProperties.minY; y <= objectProperties.maxY; y++) {
                for (int x = objectProperties.minX; x <= objectProperties.minX + 2; x++) {
                    if (pixelSet.contains(new Pixel(x, y))) {
                        leftRowsPixels += 1;
                    }
                }
            }

            for (int y = objectProperties.minY; y <= objectProperties.maxY; y++) {
                for (int x = objectProperties.maxX - 2; x <= objectProperties.maxX; x++) {
                    if (pixelSet.contains(new Pixel(x, y))) {
                        rightRowPixels += 1;
                    }
                }
            }

            if (leftRowsPixels > rightRowPixels) {
                objectProperties.horizontalObjectShape = ObjectShape.LEFT_WEIGHTED;
            } else if (leftRowsPixels < rightRowPixels) {
                objectProperties.horizontalObjectShape = ObjectShape.RIGHT_WEIGHTED;
            }
        }
    }

    private boolean pixelIsInBounds(Pixel pixel, ObjectProperties objectProperties) {
        return pixelIsInBounds(pixel,
                objectProperties.minX - 1, objectProperties.maxX + 1,
                objectProperties.minY - 1, objectProperties.maxY + 1);
    }

    private boolean pixelIsInBounds(Pixel pixel, int minX, int maxX, int minY, int maxY) {

        return pixel.getX() >= minX && pixel.getX() <= maxX
                && pixel.getY() >= minY && pixel.getY() <= maxY;
    }

    private Tuple<Double, Double> CalculateRatios(Set<Pixel> set1, Set<Pixel> set2) {
        double darkPixelRatio = FigureCompare.CalculateDarkPixelRatio(set1, set2);
        double intersectionPixelRatio = FigureCompare.CalculateIntersectionPixelRatio(set1, set2);

        return new Tuple<>(darkPixelRatio, intersectionPixelRatio);
    }

    public ArrayList<FigureClassification> calculateFigureSeriesClassification() {
        ArrayList<FigureClassification> figureClassifications = new ArrayList<>();
        classifyFigure(getFigure1());
        classifyFigure(figureClassifications, getFigure1());

        classifyFigure(getFigure2());
        classifyFigure(figureClassifications, getFigure2());

        classifyFigure(getFigure3());
        classifyFigure(figureClassifications, getFigure3());

        return figureClassifications;
    }

    private void classifyFigure(ArrayList<FigureClassification> figureClassifications, VisualFigure figure) {
        int objectCount = figure.getObjectPropertiesMap().size();

        if (objectCount == 1) {
            Map<String, ObjectProperties> map = figure.getObjectPropertiesMap();
            ObjectProperties properties = map.values().stream().findFirst().get();
            boolean hollow = properties.isHollow();
            boolean solid = properties.isSolid();

            if (hollow) {
                figureClassifications.add(FigureClassification.HOLLOW);
            } else if (solid) {
                figureClassifications.add(FigureClassification.SOLID);
            } else {
                figureClassifications.add(FigureClassification.UNKNOWN);
            }
        } else if (objectCount == 2) {
            Map<String, ObjectProperties> map = figure.getObjectPropertiesMap();
            boolean surrounds = false;
            for (ObjectProperties outerProp : map.values()) {
                for (ObjectProperties innerProp : map.values()) {
                    if (outerProp.surrounds(innerProp)) {
                        surrounds = true;
                        break;
                    }
                }
                if (surrounds) {
                    break;
                }
            }

            if (surrounds) {
                figureClassifications.add(FigureClassification.SURROUNDS);
            } else {
                figureClassifications.add(FigureClassification.UNKNOWN);
            }
        } else {
            figureClassifications.add(FigureClassification.UNKNOWN);
        }
    }

    private void noop() {
    }
}
