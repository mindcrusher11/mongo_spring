package com.saltside.bootrest.bird;

import org.springframework.data.repository.Repository;

import com.saltside.bootrest.model.Birds;

import java.util.List;
import java.util.Optional;

/**
 * This repository provides CRUD operations for {@link com.saltside.bootrest.model.Birds.bootrest.todo.Todo}
 * objects.
 */
public interface BirdsRepository extends Repository<Birds, String> {

    /**
     * Deletes a birds entry from the database.
     * @param deleted   The deleted birds entry.
     */
    void delete(Birds deleted);

    /**
     * Finds all birds entries from the database.
     * @return  The information of all birds entries that are found from the database.
     */
    List<Birds> findAll();

    /**
     * Finds the information of a single birds entry.
     * @param id    The id of the requested birds entry.
     * @return      The information of the found birds entry. If no birds entry
     *              is found, this method returns an empty {@link java.util.Optional} object.
     */
    Optional<Birds> findOne(String id);

    /**
     * Saves a new birds entry to the database.
     * @param saved The information of the saved birds entry.
     * @return      The information of the saved birds entry.
     */
    Birds save(Birds saved);
}
