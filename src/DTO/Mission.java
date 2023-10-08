package DTO;

import java.util.List;

import lombok.Data;

public @Data class Mission {
    private String code;
    private String name;
    private String description;
    private List<Affectation> affectations;
}
