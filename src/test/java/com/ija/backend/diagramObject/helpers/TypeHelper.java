/**
 * @file TypeHelper.java
 * @author Rastislav Budinsky
 * @brief This file contains setup and tearDown functions for testing
 */
package com.ija.backend.diagramObject.helpers;
import java.util.Arrays;
import java.util.TreeSet;

import com.ija.backend.diagramObject.Type;

public class TypeHelper {

    public static void setup() {
        TreeSet<String> set = new TreeSet<String> ();
        set.addAll(Arrays.asList("int", "boolean", "float", "string", "long", "byte", "short", "char", "void"));
        Type.initTypes(set.toArray(new String[set.size()]), null);
    }

    public static void tearDown() {
        Type.clearTypes();
    }
}
