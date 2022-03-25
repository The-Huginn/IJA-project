package backend.diagram;

import java.util.ArrayList;

import backend.diagram.Diagram;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;

public class ClassDiagram extends Diagram{
    ArrayList<UMLClass> classes;
    ArrayList<UMLInterface> interfaces;
    ArrayList<SeqDiagram> seqDiagrams;

    /**
     * @param name
     */
    public ClassDiagram(String name) {
        super();
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
     * @note Every UMLClass has to have unique name
     * @param umlClass
     * @return
     */
    public boolean addClass(UMLClass umlClass) {
        return false;
    }

    /**
     * @param index
     */
    public void removeClass(int index) {

    }

    /**
     * @return
     */
    public ArrayList<UMLClass> getClasses() {
        return null;
    }

    /**
     * @note Every UMLInterface has to have unique name
     * @param umlInterface
     * @return
     */
    public boolean addInterface(UMLInterface umlInterface) {
        return false;
    }

    /**
     * @param index
     */
    public void removeInterface(int index) {

    }

    /**
     * @return
     */
    public ArrayList<UMLInterface> getInterfaces() {
        return null;
    }

    /**
     * @note Every SeqDiagram has to have unique name
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
