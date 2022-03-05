/**
 * @file TypeHelper.java
 * @author Rastislav Budinsky
 * @brief This file contains setup and tearDown functions for testing
 */
package test.diagramObjectTest.helpers;

import java.util.Arrays;
import java.util.TreeSet;

import backend.diagramObject.Type;

public class TypeHelper {

    public static void setup() {
        TreeSet<String> set = new TreeSet<String> ();
        set.addAll(Arrays.asList("int", "boolean", "float", "string", "long", "byte", "short", "char", "void"));
        Type.initTypes(set);
    }

    public static void tearDown() {
        Type.clearTypes();
    }
}
