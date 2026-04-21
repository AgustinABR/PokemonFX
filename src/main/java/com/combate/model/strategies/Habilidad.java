package com.combate.model.strategies;
import com.combate.model.Criatura;

public interface Habilidad {
    String ejecutar(Criatura usuario, Criatura objetivo);
}