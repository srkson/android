package com.example.test3;

import android.content.pm.PackageInstaller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class Recipe {

    private String name, imgpath;

    public Recipe() {}

    public Recipe(String name, String imgpath) {
        this.name = name;
        this.imgpath = imgpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public static Recipe parseJSONObject (JSONObject object) {
        Recipe recipe = new Recipe();



        try {
            object = (JSONObject) object.get("recipe");

            if (object.has("label")) {
                recipe.setName(object.getString("label"));
            }
            if (object.has("image")) {
                recipe.setImgpath(object.getString("image"));
            }
        } catch (Exception e) {e.printStackTrace();}

        return recipe;
    }

    public static LinkedList<Recipe> parseJSONArray (JSONArray array) {
        LinkedList<Recipe> lista = new LinkedList<Recipe>();

        try {
            for (int i = 0; i < array.length(); i++) {
                Recipe recipe = parseJSONObject(array.getJSONObject(i));
                lista.add(recipe);
            }


        } catch (Exception e) {e.printStackTrace();}

        return lista;
    }
}
