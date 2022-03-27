package backend.diagram;

import java.util.Arrays;

import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;
import backend.diagramObject.UMLObject;
import javafx.util.Pair;

public class ClassRelation extends Relation{
    private ClassRelEnum type;

    public enum ClassRelEnum {
        ASSOCIATION,
        AGGREGATION,
        COMPOSITION,
        GENERALIZATION,
        IMPLEMENTS
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     */
    public ClassRelation(String name, Diagram parent) {
        super(name, parent);
        this.type = ClassRelEnum.ASSOCIATION;
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     * @param firstInstance ClassRelation starts from this UMLObject
     * @param firstInstanceNumber instance number of the first UMLObject instance
     * @param secondInstance ClassRelation ends in this UMLObject
     * @param secondInstanceNumber instance number of the second UMLObject instance
     */
    public ClassRelation(String name, Diagram parent, UMLObject firstClass, int firstInstance, UMLObject secondClass, int secondInstance) {
        super(name, parent, firstClass, firstInstance, secondClass, secondInstance);
        this.type = ClassRelEnum.ASSOCIATION;

        if ((firstClass instanceof UMLInterface) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.GENERALIZATION;

        if ((firstClass instanceof UMLClass) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.IMPLEMENTS;
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     * @param firstInstance ClassRelation starts from this UMLObject
     * @param firstInstanceNumber instance number of the first UMLObject instance
     * @param secondInstance ClassRelation ends in this UMLObject
     * @param secondInstanceNumber instance number of the second UMLObject instance
     * @param type Type of relations
     */
    public ClassRelation(String name, Diagram parent, UMLObject firstClass, int firstInstance, UMLObject secondClass, int secondInstance, ClassRelEnum type) {
        super(name, parent, firstClass, firstInstance, secondClass, secondInstance);
        this.type = type;

        // Overrides to correct type
        if ((firstClass instanceof UMLInterface) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.GENERALIZATION;

        // Overrides to correct type
        if ((firstClass instanceof UMLClass) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.IMPLEMENTS;
    }

    @Override
    public boolean equals(Object anotherObject) {

        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof ClassRelation))
            return false;

        ClassRelation object = (ClassRelation) anotherObject;

        return object.getName().equals(this.getName()) &&
                object.getFirst().getKey().equals(this.getFirst().getKey()) &&
                object.getSecond().getKey().equals(this.getSecond().getKey()) &&
                object.getType() == this.getType() &&
                object.getParent() == this.getParent();
    }

    @Override
    public boolean checkCorrect() {
        return checkValidity(this.getParent(), this.getFirst().getKey(), this.getSecond().getKey(), this.getType());
    }

    /**
     * @note Checks, whether instance exists in parent and if it's correct combination
     */
    @Override
    public boolean setFirst(UMLObject instance, Integer instanceNumber) {

        if (this.getSecond().getKey() == null) {
            this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        if (instance == null)
            return false;

        if (checkValidity(this.getParent(), instance, this.getSecond().getKey(), this.getType())){
            this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        return false;
    }

    /**
     * @note Checks, whether instance exists in parent and if it's correct combination
     */
    @Override
    public boolean setSecond(UMLObject instance, Integer instanceNumber) {

        if (this.getFirst().getKey() == null || instance == null)
            return false;

        if (checkValidity(this.getParent(), this.getFirst().getKey(), instance, this.getType())){
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
    public boolean setSecond(UMLObject instance, Integer instanceNumber, ClassRelEnum newType) {
        if (this.getFirst().getKey() == null || instance == null)
            return false;

        if (!checkValidity(this.getParent(), this.getFirst().getKey(), instance, newType))
            return false;

        this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
        this.type = newType;

        return true;
    }

    /**
     * @return
     */
    public ClassRelEnum getType() {
        return this.type;
    }

    /**
     * @param newType
     * @return
     */
    public boolean setType(ClassRelEnum newType) {

        if (this.getFirst().getKey() == null || this.getSecond().getKey() == null) {
            this.type = newType;
            return true;
        }

        if (checkValidity(this.getParent(), this.getFirst().getKey(), this.getSecond().getKey(), newType)){
            this.type = newType;
            return true;
        }

        return false;
    }

    /**
     * @brief This function checks for validity of the combination of arguments
     * @note For test cases we allow currentParent to be null
     * @param currentParent
     * @param firstInstance
     * @param secondInstance
     * @param type
     * @return
     */
    private static boolean checkValidity(   Diagram currentParent,
                                            UMLObject firstInstance,
                                            UMLObject secondInstance,
                                            ClassRelEnum type) {

        if (currentParent != null)
            if (!(currentParent instanceof ClassDiagram))
                return false;

        ClassDiagram parent = (ClassDiagram) currentParent;

        // Class to Class relationship
        if ((firstInstance instanceof UMLClass) && (secondInstance instanceof UMLClass)) {
            
            if (parent != null)
                if (!parent.getClasses().contains(firstInstance) || !parent.getClasses().contains(secondInstance))
                    return false;

            if (! Arrays.asList(ClassRelEnum.AGGREGATION, ClassRelEnum.ASSOCIATION, ClassRelEnum.COMPOSITION, ClassRelEnum.GENERALIZATION).contains(type))
                return false;
        }
        // Class to Interface relationship
        else if ((firstInstance instanceof UMLClass) && (secondInstance instanceof UMLInterface)) {
            if (parent != null)
                if (!parent.getClasses().contains(firstInstance) || !parent.getInterfaces().contains(secondInstance))
                    return false;

            if (! Arrays.asList(ClassRelEnum.IMPLEMENTS).contains(type))
                return false;
        }
        // Interface to Interface relationship
        else if ((firstInstance instanceof UMLInterface) && (secondInstance instanceof UMLInterface)) {
            if (parent != null)
                if (!parent.getInterfaces().contains(firstInstance) || !parent.getInterfaces().contains(secondInstance))
                    return false;

            if (! Arrays.asList(ClassRelEnum.GENERALIZATION).contains(type))
                return false;
        }
        // Interface to Class relationship
        else
            return false;

        return true;
    }
}
