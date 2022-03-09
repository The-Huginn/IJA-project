package backend.diagram;

import backend.diagram.Relation;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLObject;

public class SeqRelation extends Relation{
    private SeqRelEnum type;
    private String methodName;
    private String methodParams;
    private int methodParamsCount;
    private String note;

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

    }

    @Override
    public boolean checkCorrect() {
        return false;
    }

    /**
     * @note Checks, whether instance with it's instanceNumber exists in parent
     */
    @Override
    public boolean setFirst(UMLObject instance, Integer instanceNumber) {
        return false;
    }

    /**
     * @note Checks, whether instance with it's instanceNumber exists in parent
     */
    @Override
    public boolean setSecond(UMLObject instance, Integer instanceNumber) {
        return false;
    }

    /**
     * @return String with method invoking relation
     */
    public String getMethod() {
        return null;
    }

    /**
     * @brief Accepts the method which invokes this relation, must be visible
     * @param methodCall
     * @return True if method was changed
     */
    public boolean setMethod(String methodCall) {
        return false;
    }

    /**
     * @return String containing note
     */
    public String getNote() {
        return null;
    }

    /**
     * @param newNote
     */
    public void setNote(String newNote) {
        
    }

    /**
     * @return
     */
    public SeqRelEnum getType() {
        return null;
    }

    /**
     * @param newType
     */
    public void setType(SeqRelEnum newType) {

    }
}
