package com.bastien.bankop.dto;

import com.bastien.bankop.dto.base.TableDTO;

import java.util.List;

public record TreeTableDTO(TableNode root) implements GenericDTO {
    public record TableNode(TableDTO tableDTO, List<TableNode> children) {}
}


//    getByKey qui prend KEY dans PARAMECONFIGURATION
//            le type et la valeur multiple vont etre utiles
//    la langue c'est une valeur multiple' donc y'a un séparateur
// si valeur multiple alors on renvoie un array de string avec le "£" en spérateur
//  Faut connaitre le type pour instancier mais à priori on renvoie que des string (à voir...)
//    Le paramConfigDTO renvoie un List<String> d'un élément si y'a qu'une valeur de retour