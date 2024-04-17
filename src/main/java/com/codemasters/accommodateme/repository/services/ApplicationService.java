package com.codemasters.accommodateme.repository.services;

import com.codemasters.accommodateme.entity.Application;

import java.util.Collection;


public interface ApplicationService <T extends Application>{



    /**
     * Creating  Application
     */

    public T create(T data);

    /**
     * Get a List of Application
     */

    public Collection<T> getAll();

    /**
     * Get Application by ID
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    public T getById(Integer id);


    /**
     * Get Application by ID
     * @param id must not be {@literal null}.
     */
    public void delete(Integer id);

    /**
     * Get Application by ID
     * @param id   must not be {@literal null}.
     * @param data   must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */

    public T  update(Integer id, T data);
}
