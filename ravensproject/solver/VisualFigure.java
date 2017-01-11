package ravensproject.solver;

import ravensproject.RavensFigure;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class VisualFigure {

    private final String name;
    private final Map<String, Set<Pixel>> pixelSetMap;
    private final Set<Pixel> fullPixelSet;


    private Map<String, ObjectProperties> objectPropertiesMap;
    private ObjectProperties figureProperties;

    private Map<String, Set<Pixel>> editedPixelSetMap;
    private Set<Pixel> editedPixelSet;

    public VisualFigure(RavensFigure ravensFigure) {

        List<Set<Pixel>> setList = BuildPixelSetList(ravensFigure);

        this.pixelSetMap = new HashMap<>();
        this.fullPixelSet = new HashSet<>();

        Integer i = 1;
        for(Set<Pixel> pixelSet : setList) {
            pixelSetMap.put(i.toString(), pixelSet);
            fullPixelSet.addAll(pixelSet);
            i += 1;
        }

        this.name = ravensFigure.getName();
    }

    public String getName() {
        return name;
    }

    public Set<Pixel> getFullPixelSet() { return this.fullPixelSet; }

    public Map<String, Set<Pixel>> getPixelSetMap() {
        return pixelSetMap;
    }

    private List<Set<Pixel>> BuildPixelSetList(RavensFigure figure) {

        BufferedImage figureImage = GetFigureImage(figure);
        int width = figureImage.getWidth();
        int height = figureImage.getHeight();

        List<Set<Pixel>> pixelSetList = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (Pixel.IsDark(figureImage.getRGB(x, y))) {
                    Pixel pixel = new Pixel(x, y);

                    if(pixelSetList.isEmpty()){
                        Set<Pixel> pixelSet = new HashSet<>();
                        pixelSet.add(pixel);
                        pixelSetList.add(pixelSet);
                    } else {
                        boolean connected = false;
                        List<Set<Pixel>> notConnectedList = new ArrayList<>();
                        List<Set<Pixel>> connectedList = new ArrayList<>();

                        for (Set<Pixel> pixelSet: pixelSetList) {
                            if(ConnectedToSet(pixelSet, pixel)) {
                                pixelSet.add(pixel);
                                connected = true;
                                connectedList.add(pixelSet);
                            } else {
                                notConnectedList.add(pixelSet);
                            }
                        }

                        if(!connected){
                            Set<Pixel> pixelSet = new HashSet<>();
                            pixelSet.add(pixel);
                            pixelSetList.add(pixelSet);
                        } else {
                            Set<Pixel> unionSet = new HashSet<>();
                            for(Set<Pixel> pixelSet: connectedList) {
                                unionSet.addAll(pixelSet);
                            }

                            if(!notConnectedList.isEmpty()) {
                                if(!unionSet.isEmpty()) {
                                    notConnectedList.add(unionSet);
                                }
                                pixelSetList = notConnectedList;
                            } else if(!unionSet.isEmpty()){
                                pixelSetList = new ArrayList<>();
                                pixelSetList.add(unionSet);
                            } else {
                                throw new IllegalStateException("Set operations did something really wrong");
                            }
                        }
                    }
                }
            }
        }

        return pixelSetList;
    }

    private BufferedImage GetFigureImage(RavensFigure figure) {
        try {
            return ImageIO.read(new File(figure.getVisual()));
        } catch (Exception ex) {
        }

        return null;
    }

    private boolean ConnectedToSet(Set<Pixel> pixelSet, Pixel pixel) {

        int x = pixel.getX();
        int y = pixel.getY();

        return pixelSet.contains(new Pixel(x + 1, y)) ||
                pixelSet.contains(new Pixel(x - 1, y)) ||
                pixelSet.contains(new Pixel(x, y + 1)) ||
                pixelSet.contains(new Pixel(x, y - 1)) ||
                pixelSet.contains(new Pixel(x + 1, y + 1)) ||
                pixelSet.contains(new Pixel(x - 1, y - 1)) ||
                pixelSet.contains(new Pixel(x + 1, y - 1)) ||
                pixelSet.contains(new Pixel(x - 1, y + 1));
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof VisualFigure)) return false;

        VisualFigure figure = (VisualFigure) obj;
        return name.equals(figure.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Map<String, Set<Pixel>> getEditedPixelSetMap() {
        return editedPixelSetMap;
    }

    public Set<Pixel> getFullEditedPixelSet() {
        if(editedPixelSetMap == null) {
            return new HashSet<>();
        }

        HashSet<Pixel> fullEditedPixelSet = new HashSet<>();

        for(Map.Entry<String, Set<Pixel>> pixelSet : editedPixelSetMap.entrySet()) {
            fullEditedPixelSet.addAll(pixelSet.getValue());
        }

        return fullEditedPixelSet;
    }

    public void setEditedPixelSetMap(Map<String, Set<Pixel>> editedPixelSetMap) {
        this.editedPixelSetMap = new HashMap<>();
        this.editedPixelSetMap.putAll(editedPixelSetMap);
    }

    public Map<String, ObjectProperties> getObjectPropertiesMap() {
        return objectPropertiesMap;
    }

    public void setObjectPropertiesMap(Map<String, ObjectProperties> objectPropertiesMap) {
        this.objectPropertiesMap = objectPropertiesMap;
    }

    public ObjectProperties getFigureProperties() {
        return figureProperties;
    }

    public void setFigureProperties(ObjectProperties figureProperties) {
        this.figureProperties = figureProperties;
    }

    public Set<Pixel> getEditedPixelSet() {
        return editedPixelSet;
    }

    public void setEditedPixelSet(Set<Pixel> editedPixelSet) {
        this.editedPixelSet = editedPixelSet;
    }

    public Map<String, Set<Pixel>> getPixelSetMapForClassification(FigureClassification classification) {

        Map<String, Set<Pixel>> classifiedMap = new HashMap<>();

        for(Map.Entry<String, Set<Pixel>> pixelSetMapEntry : pixelSetMap.entrySet()) {
            for(Map.Entry<String, ObjectProperties> propertiesEntry : objectPropertiesMap.entrySet()) {
                if(pixelSetMapEntry.getKey().equals(propertiesEntry.getKey())) {

                    switch(classification) {
                        case HOLLOW:
                            if(propertiesEntry.getValue().isHollow()) {
                                classifiedMap.put(pixelSetMapEntry.getKey(), pixelSetMapEntry.getValue());
                            }
                            break;
                        case SOLID:
                            if(propertiesEntry.getValue().isSolid()) {
                                classifiedMap.put(pixelSetMapEntry.getKey(), pixelSetMapEntry.getValue());
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        return classifiedMap;
    }
}
