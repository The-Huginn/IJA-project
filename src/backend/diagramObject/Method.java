package backend.diagramObject;

import java.util.ArrayList;

import backend.diagramObject.Attribute;
import backend.diagramObject.Type;

public class Method extends Attribute{
    private ArrayList<Type> parameters;

    public Method(String name, UMLObject parent) {

    }

    public Method(String name, UMLObject parent, Type type, Visibility visibility) {

    }

    public Method(String name, UMLObject parent, Type type, Visibility visibility, boolean isVisibilityChangable) {

    }

    public Method(String name, UMLObject parent, Type type, Visibility visibility, String[] parameters) {

    }

    public Method(String name, UMLObject parent, Type type, Visibility visibility, boolean isVisibilityChangable, String[] parameters) {

    }

    public ArrayList<Type> getParameters() {
        return null;
    }

    public boolean setParameters(String[] newParameters) {
        return false;
    }
}
