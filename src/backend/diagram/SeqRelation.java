package backend.diagram;

import backend.diagram.Relation;

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
     */
    public SeqRelation(String name) {

    }

    @Override
    public boolean checkCorrect() {
        return false;
    }

    /**
     * @return String with method invoking relation
     */
    public String getMethod() {
        return null;
    }

    /**
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
}
