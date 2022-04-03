package backend.diagram;

import java.util.List;

import backend.diagram.ClassRelation.ClassRelEnum;
import backend.diagramObject.Method;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;
import backend.diagramObject.UMLObject;
import backend.diagramObject.Attribute.Visibility;
import javafx.util.Pair;

public class SeqRelation extends Relation{
    private SeqRelEnum type = SeqRelEnum.SYNCHROUNOUS;
    private String methodName = "Missing method name";
    private String methodParams = "Missing method params";
    private String note = "Missing note";

    public enum SeqRelEnum {
        SYNCHROUNOUS,
        ASYNCHRONOUS,
        RETURN,
        CREATION,
        DESTRUCTION
    }

    /**
     * @param name
     * @param parent Under which parent this SeqRelation lives
     */
    public SeqRelation(String name, Diagram parent) {
        super(name, parent);
    }

    /**
     * @param name
     * @param parent Under which parent this SeqRelation lives
     * @param firstInstance SeqRelation starts from this UMLObject
     * @param firstInstanceNumber instance number of the first UMLObject instance
     * @param secondInstance SeqRelation ends in this UMLObject
     * @param secondInstanceNumber instance number of the second UMLObject instance
     */
    public SeqRelation(String name, Diagram parent, UMLClass firstInstance, int firstInstanceNumber, UMLClass secondInstance, int secondInstanceNumber) {
        super(name, parent, firstInstance, firstInstanceNumber, secondInstance, secondInstanceNumber);
    }

    @Override
    public boolean equals(Object anotherObject) {

        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof ClassRelation))
            return false;

        SeqRelation object = (SeqRelation) anotherObject;

        return object.getName().equals(this.getName()) &&
                object.getFirst().getKey().equals(this.getFirst().getKey()) &&
                object.getFirst().getValue().equals(this.getFirst().getValue()) &&
                object.getSecond().getKey().equals(this.getSecond().getKey()) &&
                object.getSecond().getValue().equals(this.getSecond().getValue()) &&
                object.getType() == this.getType() &&
                object.getParent() == this.getParent();
    }

    @Override
    public boolean checkCorrect() {
        return checkValidity((SeqDiagram) this.getParent(),
                            (UMLClass) this.getFirst().getKey(),
                            this.getFirst().getValue(),
                            (UMLClass) this.getSecond().getKey(),
                            this.getSecond().getValue(),
                            methodName,
                            methodParams);
    }

    /**
     * @note Checks, whether instance with it's instanceNumber exists in parent
     */
    @Override
    public boolean setFirst(UMLObject instance, Integer instanceNumber) {
        
        if (instance == null || (instance instanceof UMLInterface))
            return false;

        SeqDiagram parent = (SeqDiagram) this.getParent();

        // Just for test cases
        if (parent == null) {
            this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        for (Pair<UMLClass, Integer> instance1 : parent.getInstances()){
            if (instance1.getKey().equals(instance) && instance1.getValue() == instanceNumber) {
                this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
                return true;
            }
        }
        
        return false;      
    }

    /**
     * @note Checks, whether instance with it's instanceNumber exists in parent
     */
    @Override
    public boolean setSecond(UMLObject instance, Integer instanceNumber) {
        
        if (instance == null || (instance instanceof UMLInterface) || this.getFirst().getKey() == null)
            return false;

        SeqDiagram parent = (SeqDiagram) this.getParent();
        
        // Just for test cases
        if (parent == null) {
            this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        for (Pair<UMLClass, Integer> instance1 : parent.getInstances())
            if (instance1.getKey().equals(instance) && instance1.getValue() == instanceNumber) {
                this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);    
                return true;
            }
        
        return false;
    }

    /**
     * @brief Checks if the combination of first, second and type of the relationship is correct
     * @param instance
     * @param instanceNumber
     * @param newType
     * @return True upon success otherwise false
     */
    public boolean setSecond(UMLObject instance, Integer instanceNumber, SeqRelEnum newType) {

        if (instance == null || (instance instanceof UMLInterface) || this.getFirst().getKey() == null)
            return false;

        SeqDiagram parent = (SeqDiagram) this.getParent();
        
        // Just for test cases
        if (parent == null) {
            this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        boolean found = false;
        for (Pair<UMLClass, Integer> instance1 : parent.getInstances())
            if (instance1.getKey().equals(instance) && instance1.getValue() == instanceNumber) {
                this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
                found = true;
            }
        if (!found)
            return false;

        if (!checkValidity(parent,
                            (UMLClass)this.getFirst().getKey(),
                            this.getFirst().getValue(),
                            (UMLClass)this.getSecond().getKey(),
                            this.getSecond().getValue(),
                            this.methodName,
                            this.methodParams))
            return false;

        this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
        this.type = newType;

        return true;
    }

    /**
     * @return String with method invoking relation
     */
    public String getMethod() {
        return this.methodName  ;
    }

    /**
     * @brief Accepts the method which invokes this relation, must be visible
     * @param methodCall
     * @return True if method was changed
     * @implNote No syntax analysis is made, operator "," cannot be passed into function
     */
    public boolean setMethod(String methodCall) {

        if (methodCall.charAt(methodCall.length() - 1) != ')' || methodCall.indexOf("(") == -1)
            return false;


        String auxMethodName = methodCall.substring(0, methodCall.indexOf("("));
        String auxParams = methodCall.substring(methodCall.indexOf("(") + 1).substring(0, methodCall.length() - auxMethodName.length() - 2);

        if (!checkValidity((SeqDiagram) this.getParent(),
                (UMLClass) this.getFirst().getKey(),
                this.getFirst().getValue(),
                (UMLClass) this.getSecond().getKey(),
                this.getSecond().getValue(),
                auxMethodName,
                auxParams))
            return false;

        this.methodName = auxMethodName;
        this.methodParams = auxParams;

        return true;
    }
    
    /**
     * @return String containing note
     */
    public String getNote() {
        return this.note;
    }

    /**
     * @param newNote
     */
    public void setNote(String newNote) {
        this.note = newNote;
    }

    /**
     * @return
     */
    public SeqRelEnum getType() {
        return this.type;
    }

    /**
     * @param newType
     */
    public boolean setType(SeqRelEnum newType) {
        this.type = newType;
        return true;
    }

    private static boolean checkValidity(   SeqDiagram parent,
                                            UMLClass firstInstance,
                                            Integer firstInstanceNumber,
                                            UMLClass secondInstance,
                                            Integer secondInstanceNumber,
                                            String methodName,
                                            String methodParams) {

        ClassDiagram grandParent = null;

        // Just for test cases
        if (parent != null) {
            grandParent = parent.getParent();

            // We check for instances
            Pair<Integer, Integer> occurences = new Pair<Integer,Integer>(0, 0);
            for (Pair<UMLClass, Integer> instance : parent.getInstances()) {
                
                if (instance.getKey().equals(firstInstance) && instance.getValue() == firstInstanceNumber)
                    occurences = new Pair<Integer,Integer>(occurences.getKey() + 1, occurences.getValue());

                if (instance.getKey().equals(secondInstance) && instance.getValue() == secondInstanceNumber)
                    occurences = new Pair<Integer,Integer>(occurences.getKey(), occurences.getValue() + 1);
            }

            if (occurences.getKey() != 1 || occurences.getValue() != 1)
                return false;
        }

        String parameters[] = methodParams.split(",");
        
        for (String param : parameters) {
            if (param.replaceAll(" ","").equals(""))
                if (parameters.length == 1)
                    parameters = new String[] {""};
                else 
                    return false;
        }

        int paramCount = parameters.length == 1 && parameters[0].length() == 0 ? 0 : parameters.length;
        
        return findMethod(grandParent, firstInstance, methodName, paramCount);
    }

    /**
     * @brief This function checks if methodName with paramCount amount of parameters is inherited from another object
     * @param diagram
     * @param current
     * @param methodName
     * @param paramCount
     * @return True upon success otherwise false
     */
    private static boolean findMethod(ClassDiagram diagram, UMLObject current, String methodName, int paramCount) {

        for (Method method : current.getMethods())
            if (method.getName().equals(methodName) && method.getParameters().size() == paramCount)
                if (method.getVisibility() == Visibility.PRIVATE)
                    return false;
                else
                    return true;

        // Just for test cases
        if (diagram == null)
            return false;

        if (current instanceof UMLClass) {
            List<Relation> relations = diagram.getRelations();

            for (Relation relation : relations) {
                ClassRelation classRelation = (ClassRelation) relation;

                if (classRelation.getFirst().getKey().equals(current) &&
                    (classRelation.getType() == ClassRelEnum.GENERALIZATION || classRelation.getType() == ClassRelEnum.IMPLEMENTS))

                    // We check if it returns true otherwise we search further
                    if (findMethod(diagram, classRelation.getSecond().getKey(), methodName, paramCount))
                        return true;
            }
        }
        else {
            List<Relation> relations = diagram.getRelations();

            for (Relation relation : relations) {
                ClassRelation classRelation = (ClassRelation) relation;

                if (classRelation.getFirst().getKey().equals(current) && classRelation.getType() == ClassRelEnum.GENERALIZATION)
                    return findMethod(diagram, classRelation.getSecond().getKey(), methodName, paramCount);
            }
        }

        return false;
    }
}
