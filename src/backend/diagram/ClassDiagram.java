package backend.diagram;

import java.util.ArrayList;

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
        super(name);
        this.classes = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.seqDiagrams = new ArrayList<>();
    }

    @Override
    public boolean addRelation(Relation relation) {

        if (!(relation instanceof ClassRelation))
            return false;

        for (Relation relation2 : this.getRelations())
            if (((ClassRelation)relation).equals(relation2))
                return false;
        
        this.getRelations().add(relation);

        return true;
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

        if (umlClass == null)
            return false;

        for (UMLClass umlClass1 : this.getClasses())
            if (umlClass.equals(umlClass1))
                return false;

        this.classes.add(umlClass);
        return true;
    }

    /**
     * @param index
     */
    public void removeClass(int index) {

        if (index < 0 || index >= this.getClasses().size())
            return;
        
        UMLClass toRemove = this.getClasses().get(index);

        this.getRelations().removeIf(relation -> toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()));

        this.classes.remove(index);
    }

    /**
     * @return
     */
    public ArrayList<UMLClass> getClasses() {
        return this.classes;
    }

    /**
     * @note Every UMLInterface has to have unique name
     * @param umlInterface
     * @return
     */
    public boolean addInterface(UMLInterface umlInterface) {

        if (umlInterface == null)
            return false;

        for (UMLInterface umlInterface1 : this.getInterfaces()) 
            if (umlInterface.equals(umlInterface1))
                return false;

        this.interfaces.add(umlInterface);
        return true;
    }

    /**
     * @param index
     */
    public void removeInterface(int index) {

        if (index < 0 || index >= this.getInterfaces().size())
            return;

        UMLInterface toRemove = this.getInterfaces().get(index);

        this.getRelations().removeIf(relation -> toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()));

        this.interfaces.remove(index);
    }

    /**
     * @return
     */
    public ArrayList<UMLInterface> getInterfaces() {
        return this.interfaces;
    }

    /**
     * @note Every SeqDiagram has to have unique name
     * @param diagram
     * @return
     */
    public boolean addDiagram(SeqDiagram seqDiagram) {

        if (seqDiagram == null)
            return false;
        
        for (SeqDiagram seqDiagram1 : this.getDiagrams())
            if (seqDiagram1.equals(seqDiagram))
                return false;

        this.seqDiagrams.add(seqDiagram);    
        return true;
    }

    /**
     * @param index
     */
    public void removeDiagram(int index) {
        if (index < 0 || index >= this.getDiagrams().size())
            return;

        this.seqDiagrams.remove(index);
    }

    /**
     * @return
     */
    public ArrayList<SeqDiagram> getDiagrams() {
        return this.seqDiagrams;
    }
}
