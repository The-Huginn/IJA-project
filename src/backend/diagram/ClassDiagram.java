package backend.diagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;

public class ClassDiagram extends Diagram{
    List<UMLClass> classes = new ArrayList<>();
    List<UMLInterface> interfaces = new ArrayList<>();
    List<SeqDiagram> seqDiagrams = new ArrayList<>();

    /**
     * @param name
     */
    public ClassDiagram(String name) {
        super(name);
    }

    @Override
    public boolean addRelation(Relation relation) {

        if (!(relation instanceof ClassRelation))
            return false;

        for (Relation relation2 : this.getRelations())
            if (((ClassRelation)relation).equals(relation2))
                return false;
        
        this.relations.add(relation);

        return true;
    }

    @Override
    public boolean checkCorrect() {

        for (SeqDiagram diagram : this.getDiagrams())
            if (!diagram.checkCorrect())
                return false;

        for (UMLClass class1 : this.getClasses())
            if (!class1.checkCorrect())
                return false;

        for (UMLInterface interface1 : this.getInterfaces())
            if (!interface1.checkCorrect())
                return false;

        for (Relation relation : this.getRelations())
            if (!((ClassRelation)relation).checkCorrect())
                return false;

        return true;
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

        this.relations.removeIf(relation -> toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()));

        this.classes.remove(index);
    }

    /**
     * @return
     */
    public List<UMLClass> getClasses() {
        return Collections.unmodifiableList(this.classes);
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

        this.relations.removeIf(relation -> toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()));

        this.interfaces.remove(index);
    }

    /**
     * @return
     */
    public List<UMLInterface> getInterfaces() {
        return Collections.unmodifiableList(this.interfaces);
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
    public List<SeqDiagram> getDiagrams() {
        return Collections.unmodifiableList(this.seqDiagrams);
    }
}
