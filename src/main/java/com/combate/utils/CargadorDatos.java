package com.combate.utils;

import com.combate.model.Criatura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class CargadorDatos {
    public static List<Criatura> cargar() {
        try {
            Reader reader = new InputStreamReader(
                CargadorDatos.class.getResourceAsStream("/data/criaturas.json")
            );
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Criatura>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}