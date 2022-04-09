package backend.diagram;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import backend.diagramObject.Type;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;
import javafx.util.Pair;

public class ClassDiagram extends Diagram{
    List<UMLClass> classes = new ArrayList<>();
    List<UMLInterface> interfaces = new ArrayList<>();
    List<SeqDiagram> seqDiagrams = new ArrayList<>();

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<List<Pair<Integer, Relation>>> undo_relation = new ArrayDeque<>();
    private Deque<Pair<Integer, UMLClass>> undo_class = new ArrayDeque<>();
    private Deque<Pair<Integer, UMLInterface>> undo_interface = new ArrayDeque<>();
    private Deque<Pair<Integer, SeqDiagram>> undo_diagram = new ArrayDeque<>();

    private enum UndoType {
        addRelation,
        addClass,
        removeClass,
        addInterface,
        removeInterface,
        addDiagram,
        removeDiagram,
        others
    }

    /**
     * @param name
     */
    public ClassDiagram(String name) {
        super(name);
    }

    @Override
    public boolean setName(String newName) {
        undo_stack.addFirst(UndoType.others);
        return super.setName(newName);
    }

    @Override
    public boolean addRelation(Relation relation) {

        if (!(relation instanceof ClassRelation))
            return false;

        for (Relation relation2 : this.getRelations())
            if (((ClassRelation)relation).equals(relation2))
                return false;
        
        undo_stack.addFirst(UndoType.addRelation);

        this.relations.add(relation);

        return true;
    }

    @Override
    public void removeRelation(int index) {
        undo_stack.addFirst(UndoType.others);
        super.removeRelation(index);
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

        undo_stack.addFirst(UndoType.addClass);

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

        List<Pair<Integer, Relation>> toDelete = new ArrayList<>();

        for (int i = 0; i < relations.size(); i++) {
            Relation relation = relations.get(i);

            if (toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()))
                toDelete.add(new Pair<Integer,Relation>(i, relation));
        }

        this.relations.removeIf(relation -> toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()));

        undo_stack.addFirst(UndoType.removeClass);
        undo_relation.addFirst(toDelete);
        undo_class.addFirst(new Pair<Integer,UMLClass>(index, classes.get(index)));

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

        undo_stack.addFirst(UndoType.addInterface);

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

        List<Pair<Integer, Relation>> toDelete = new ArrayList<>();

        for (int i = 0; i < relations.size(); i++) {
            Relation relation = relations.get(i);

            if (toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()))
                toDelete.add(new Pair<Integer,Relation>(i, relation));
        }

        this.relations.removeIf(relation -> toRemove.equals(relation.getFirst().getKey()) || toRemove.equals(relation.getSecond().getKey()));

        undo_stack.addFirst(UndoType.removeInterface);
        undo_relation.addFirst(toDelete);
        undo_interface.addFirst(new Pair<Integer, UMLInterface>(index, interfaces.get(index)));

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

        undo_stack.addFirst(UndoType.addDiagram);

        this.seqDiagrams.add(seqDiagram);    
        return true;
    }

    /**
     * @param index
     */
    public void removeDiagram(int index) {
        if (index < 0 || index >= this.getDiagrams().size())
            return;

        undo_stack.addFirst(UndoType.removeDiagram);
        undo_diagram.addFirst(new Pair<Integer,SeqDiagram>(index, seqDiagrams.get(index)));

        this.seqDiagrams.remove(index);
    }

    /**
     * @return
     */
    public List<SeqDiagram> getDiagrams() {
        return Collections.unmodifiableList(this.seqDiagrams);
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.addRelation) {
            relations.remove(relations.size() - 1);
        } else if (type == UndoType.addClass) {
            classes.remove(classes.size() - 1);
        } else if (type == UndoType.removeClass) {
            List<Pair<Integer, Relation>> top = undo_relation.pop();

            for (Pair<Integer, Relation> pair : top)
                relations.add(pair.getKey(), pair.getValue());

            Pair<Integer, UMLClass> top_class = undo_class.pop();
            classes.add(top_class.getKey(), top_class.getValue());
        } else if (type == UndoType.addInterface) {
            interfaces.remove(interfaces.size() - 1);
        } else if (type == UndoType.removeInterface) {
            List<Pair<Integer, Relation>> top = undo_relation.pop();

            for (Pair<Integer, Relation> pair : top)
                relations.add(pair.getKey(), pair.getValue());

            Pair<Integer, UMLInterface> top_interface = undo_interface.pop();
            interfaces.add(top_interface.getKey(), top_interface.getValue());
        } else if (type == UndoType.addDiagram) {
            seqDiagrams.remove(seqDiagrams.size() - 1);
        } else if (type == UndoType.removeDiagram) {
            Pair<Integer, SeqDiagram> top = undo_diagram.pop();
            seqDiagrams.add(top.getKey(), top.getValue());
        } else if (type == UndoType.others) {
            super.undo();
        }
    }

    @Override
    public JSONObject getJSON() {
        JSONObject json = super.getJSON();

        JSONArray classJSON = new JSONArray();
        JSONArray interfaceJSON = new JSONArray();
        JSONArray diagramJSON = new JSONArray();

        for (UMLClass class1 : getClasses()) {
            classJSON.put(class1.getJSON());
        }

        for (UMLInterface interface1 : getInterfaces()) {
            interfaceJSON.put(interface1.getJSON());
        }

        for (SeqDiagram seqDiagram : getDiagrams()) {
            diagramJSON.put(seqDiagram.getJSON());
        }

        json.put("classes", classJSON);
        json.put("interfaces", interfaceJSON);
        json.put("seqDiagrams", diagramJSON);
        json.put("types", Type.getJSONTypes());

        return json;
    }

    @Override
    public boolean setJSON(JSONObject json) {

        if (!json.has("relations") || !json.has("classes") || !json.has("interfaces") || !json.has("seqDiagrams") || !json.has("types"))
            return false;

        JSONArray classJSON = json.getJSONArray("classes");
        JSONArray interfaceJSON = json.getJSONArray("interfaces");
        JSONArray diagramJSON = json.getJSONArray("seqDiagrams");
        JSONArray relationJSON = json.getJSONArray("relations");
        Type.setJSONTypes(json.getJSONArray("types"));

        for (int i = 0; i < classJSON.length(); i++) {
            UMLClass class1 = new UMLClass("", this);
            if (!class1.setJSON(classJSON.getJSONObject(i)))
                return false;

            classes.add(class1);
        }

        for (int i = 0; i < interfaceJSON.length(); i++) {
            UMLInterface interface1 = new UMLInterface("", this);
            if (!interface1.setJSON(interfaceJSON.getJSONObject(i)))
                return false;

            interfaces.add(interface1);
        }

        for (int i = 0; i < relationJSON.length(); i++) {
            ClassRelation relation = new ClassRelation("", this);
            if (!relation.setJSON(relationJSON.getJSONObject(i)))
                return false;

            relations.add(relation);
        }

        for (int i = 0; i < diagramJSON.length(); i++) {
            SeqDiagram seqDiagram = new SeqDiagram("", this);
            if (!seqDiagram.setJSON(diagramJSON.getJSONObject(i)))
                return false;

            seqDiagrams.add(seqDiagram);
        }

        return super.setJSON(json);
    }
}
