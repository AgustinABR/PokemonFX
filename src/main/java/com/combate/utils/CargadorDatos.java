package com.combate.utils;

import com.combate.model.Criatura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Clase de utilidad para la gestión de datos externos.
 * Proporciona métodos estáticos para la carga y deserialización de objetos
 * desde archivos de configuración en formato JSON.
 * 
 */
public class CargadorDatos {

    /**
     * Lee el archivo 'criaturas.json' ubicado en los recursos del proyecto y 
     * lo convierte en una lista de objetos de tipo {@link Criatura}.
     * 
     * Utiliza la librería Gson para mapear automáticamente los campos del JSON 
     * con los atributos de la clase de modelo.
     * 
     * @return Una {@link List} de {@link Criatura} si la carga es exitosa; 
     *         {@code null} si ocurre un error durante la lectura o el parseo.
     * @see com.combate.model.Criatura
     */
    public static List<Criatura> cargar() {
        try {
            // Obtiene el flujo de entrada del archivo JSON de recursos
            Reader reader = new InputStreamReader(
                CargadorDatos.class.getResourceAsStream("/data/criaturas.json")
            );
            
            Gson gson = new Gson();
            
            // Define el tipo complejo (List de Criaturas) para que Gson sepa cómo deserializar
            Type listType = new TypeToken<List<Criatura>>(){}.getType();
            
            // Retorna la lista de objetos ya convertidos
            return gson.fromJson(reader, listType);
            
        } catch (Exception e) {
            // Imprime la traza del error para facilitar la depuración técnica
            e.printStackTrace();
            return null;
        }
    }
}