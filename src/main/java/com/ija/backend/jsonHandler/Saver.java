/**
 * @file Saved.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains Saver for loading and saving diagram
 */
package com.ija.backend.jsonHandler;

import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagramObject.Type;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

import com.ija.Application.App;

public class Saver {
    
    // most likely even GUI will be passed
    public static boolean save(ClassDiagram diagram, String path) {

        if (diagram == null)
            return false;

        JSONObject json = diagram.getJSON();

        String correct_path = (Paths.get(path).isAbsolute() ? path : "./" + path);
        
        try {
            File file = new File(correct_path);
            file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
        try (FileWriter file = new FileWriter(correct_path)) {
            file.write(json.toString(4));
            App.save();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @brief This function tries to read json file to import the saved diagram
     * @param path Path to the saved diagram
     * @return ClassDiagram if successful otherwise null
     */
    public static ClassDiagram load(String path) {

        String correct_path = (Paths.get(path).isAbsolute() ? path : "./" + path);

        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(correct_path)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(content);

        ClassDiagram diagram = new ClassDiagram("");
        Type.setParent(diagram);

        if (diagram.setJSON(json))
            return diagram;
        
        return null;
    }
}