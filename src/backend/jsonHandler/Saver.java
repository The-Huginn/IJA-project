/**
 * @file Saved.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains Saver for loading and saving diagram
 */
package backend.jsonHandler;

import backend.diagram.ClassDiagram;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

import Application.App;

public class Saver {
    
    // most likely even GUI will be passed
    public static void save(ClassDiagram diagram, String path) {

        if (diagram == null)
            return;

        JSONObject json = diagram.getJSON();

        String correct_path = (Paths.get(path).isAbsolute() ? path : "./" + path);
        
        try {
            File file = new File(correct_path);
            file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        try (FileWriter file = new FileWriter(correct_path)) {
            file.write(json.toString(4));
            App.save();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

        if (diagram.setJSON(json))
            return diagram;
        
        return null;
    }
}