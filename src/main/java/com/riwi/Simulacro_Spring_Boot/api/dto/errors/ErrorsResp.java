package com.riwi.Simulacro_Spring_Boot.api.dto.errors;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/*
 *Clase que extiende BaseErrorResp para incluir una lista de errores detallados.
 * Usa @EqualsAndHashCode(callSuper = true) para incluir las propiedades de la clase base en equals y hashCode.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsResp extends BaseErrorResp{
    
    private List<Map<String, String>> errors; // Lista de mapas con los detalles de los errores
}
