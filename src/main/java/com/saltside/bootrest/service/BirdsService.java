package com.saltside.bootrest.service;

import java.util.List;

import com.saltside.bootrest.bird.BirdsDTO;
import com.saltside.bootrest.model.Birds;

/**
 * This interface declares the methods that provides CRUD operations for
 * {@link com.saltside.bootrest.model.Birds} objects
 */
public interface BirdsService {

    /**
     * Creates a new birds entry.
     * @param birds  The information of the created birds entry.
     * @return      The information of the created birds entry.
     */
    BirdsDTO create(BirdsDTO birds);

    /**
     * Deletes a birds entry.
     * @param id    The id of the deleted birds entry.
     * @return      THe information of the deleted birds entry.
     * @throws com.BirdsNotFoundException.bootrest.birds.birdsNotFoundException if no birds entry is found.
     */
    BirdsDTO delete(String id);

    /**
     * Finds all birds entries.
     * @return      The information of all birds entries.
     */
    List<BirdsDTO> findAll();

    /**
     * Finds a single birds entry.
     * @param id    The id of the requested birds entry.
     * @return      The information of the requested birds entry.
     * @throws com.BirdsNotFoundException.bootrest.birds.birdsNotFoundException if no birds entry is found.
     */
    BirdsDTO findById(String id);

    /**
     * Updates the information of a birds entry.
     * @param birds  The information of the updated birds entry.
     * @return      The information of the updated birds entry.
     * @throws com.BirdsNotFoundException.bootrest.birds.birdsNotFoundException if no birds entry is found.
     */
    BirdsDTO update(BirdsDTO birds);
}
