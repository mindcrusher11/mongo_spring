package com.saltside.bootrest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.saltside.bootrest.bird.BirdsDTO;
import com.saltside.bootrest.bird.BirdsNotFoundException;
import com.saltside.bootrest.service.BirdsService;

import javax.validation.Valid;

import java.util.List;

/**
 * This controller provides the public API that is used to manage the information
 * of birds entries.
 */
@RestController
@RequestMapping("/api/birds")
public
final class BirdsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BirdsController.class);

    private final BirdsService service;

    @Autowired
	public
    BirdsController(BirdsService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    BirdsDTO create(@RequestBody @Valid BirdsDTO birdsEntry) {
        LOGGER.info("Creating a new birds entry with information: {}", birdsEntry);
        
        BirdsDTO created = service.create(birdsEntry);
        LOGGER.info("Created a new birds entry with information: {}", created);

        return created;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    BirdsDTO delete(@PathVariable("id") String id) {
        LOGGER.info("Deleting birds entry with id: {}", id);

        BirdsDTO deleted = service.delete(id);
        LOGGER.info("Deleted birds entry with information: {}", deleted);

        return deleted;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<BirdsDTO> findAll() {
        LOGGER.info("Finding all birds entries");

        List<BirdsDTO> birdsEntries = service.findAll();
        LOGGER.info("Found {} birds entries", birdsEntries.size());
        if(birdsEntries.size() == 0)
        {
        	throw new BirdsNotFoundException("0");
        }
        return birdsEntries;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    BirdsDTO findById(@PathVariable("id") String id) {
        LOGGER.info("Finding birds entry with id: {}", id);

        BirdsDTO birdsEntry = service.findById(id);
        LOGGER.info("Found birds entry with information: {}", birdsEntry);
        if(birdsEntry.getId().isEmpty())
        {
        	throw new BirdsNotFoundException(id);
        }

        return birdsEntry;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    BirdsDTO update(@RequestBody @Valid BirdsDTO birdsEntry) {
        LOGGER.info("Updating birdsEntry entry with information: {}", birdsEntry);

        BirdsDTO updated = service.update(birdsEntry);
        LOGGER.info("Updated birdsEntry entry with information: {}", birdsEntry);

        return updated;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleBirdsFound(BirdsNotFoundException ex) {
        LOGGER.error("Handling error with message: {}", ex.getMessage());
    }
}
