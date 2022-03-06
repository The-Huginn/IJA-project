package backend.diagram;

import java.util.ArrayList;

import backend.diagram.Diagram;
import backend.diagramObject.UMLObject;

public class ClassDiagram extends Diagram{
    ArrayList<UMLObject> objects;
    ArrayList<SeqDiagram> seqDiagrams;

    /**
     * @param name
     */
    public ClassDiagram(String name) {

    }

    @Override
    public boolean addRelation(Relation relation) {
        return false;
    }

    @Override
    public boolean checkCorrect() {
        return false;
    }

    /**
     * @param object
     * @return
     */
    public boolean addObject(UMLObject object) {
        return false;
    }

    /**
     * @param index
     */
    public void removeObject(int index) {

    }

    /**
     * @return
     */
    public ArrayList<UMLObject> getObjects() {
        return null;
    }

    /**
     * @param diagram
     * @return
     */
    public boolean addDiagram(SeqDiagram diagram) {
        return false;
    }

    /**
     * @param index
     */
    public void removeDiagram(int index) {

    }

    /**
     * @return
     */
    public ArrayList<SeqDiagram> getDiagrams() {
        return null;
    }
}
