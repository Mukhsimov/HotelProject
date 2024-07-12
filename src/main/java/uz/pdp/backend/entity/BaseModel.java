package uz.pdp.backend.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class BaseModel {
    private final String ID;

    public BaseModel() {
        this.ID = UUID.randomUUID().toString();
    }
}
