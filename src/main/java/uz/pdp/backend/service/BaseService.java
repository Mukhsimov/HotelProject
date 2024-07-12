package uz.pdp.backend.service;

import java.util.List;

public interface BaseService<T> {
    void create(T t);

    boolean update(T t);

    T get(String ID);

    boolean delete(String ID);

    List<T> getList();
}
