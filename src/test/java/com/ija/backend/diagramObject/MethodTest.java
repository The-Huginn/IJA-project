/**
 * @file MethodTest.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Method class
 */
package com.ija.backend.diagramObject;

import com.ija.backend.diagramObject.helpers.TypeHelper;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class MethodTest {
    private String params[] = {"int", "int", "boolean", "long", "int", "string"};

    @Before
    public void setup() {
        TypeHelper.setup();
    }
    
    @After
    public void tearDown() {
        TypeHelper.tearDown();
    }

    @Test
    public void getterTest() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        List<Type> parameters = m.getParameters();
        assertTrue(parameters.size() == params.length);
        for (int i = 0; i < parameters.size(); i++)
            assertTrue(parameters.get(i).getName().equals(params[i]));
    }

    @Test
    public void setParametersTest() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        assertTrue(m.setParameters(params));
    }

    @Test
    public void setParametersTest2() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String newParams[] = {"long", "boolean", "string"};
        assertTrue(m.setParameters(newParams));
        List<Type> parameters = m.getParameters();
        assertTrue(parameters.size() == newParams.length);
        for (int i = 0; i < parameters.size(); i++)
            assertTrue(parameters.get(i).getName().equals(newParams[i]));
    }

    @Test
    public void setParametersTest3() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String wrongParams[] = {"long", "boolean", "string", "random"};
        assertFalse(m.setParameters(wrongParams));
        List<Type> parameters = m.getParameters();
        assertTrue(parameters.size() == params.length);
        for (int i = 0; i < parameters.size(); i++)
            assertTrue(parameters.get(i).getName().equals(params[i]));
    }
}
