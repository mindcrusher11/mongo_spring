package com.saltside.bootrest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saltside.bootrest.bird.BirdsDTO;
import com.saltside.bootrest.bird.BirdsNotFoundException;
import com.saltside.bootrest.bird.BirdsRepository;
import com.saltside.bootrest.model.Birds;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * This service class saves {@link com.saltside.bootrest.model.Birds} objects
 * to MongoDB database.
 */
@Service
final class MongoDBBirdsService implements BirdsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBBirdsService.class);

    private final BirdsRepository repository;

    @Autowired
    MongoDBBirdsService(BirdsRepository repository) {
        this.repository = repository;
    }

    @Override
    public BirdsDTO create(BirdsDTO birds) {
        LOGGER.info("Creating a new birds entry with information: {}", birds);
        Birds persisted = new Birds();
        if((birds.getContinents() != null) && (birds.getContinents().size() != 0)){
        persisted = Birds.getBuilder()
                .id(birds.getId())
                .family(birds.getFamily())
                .continents(birds.getContinents())
                .name(birds.getName())
                .added(birds.getAdded())
                .visible(birds.isVisible())
                .build();
        }
        else
        {
            persisted = Birds.getBuilder()
                    .id(birds.getId())
                    .family(birds.getFamily())
                    .name(birds.getName())
                    .added(birds.getAdded())
                    .visible(birds.isVisible())
                    .build();
        }
        persisted = repository.save(persisted);
        LOGGER.info("Created a new birds entry with information: {}", persisted);

        return convertToDTO(persisted);
    }

    @Override
    public BirdsDTO delete(String id) {
        LOGGER.info("Deleting a birds entry with id: {}", id);

        Birds deleted = findbirdsById(id);
        repository.delete(deleted);

        LOGGER.info("Deleted birds entry with informtation: {}", deleted);

        return convertToDTO(deleted);
    }

    @Override
    public List<BirdsDTO> findAll() {
        LOGGER.info("Finding all birds entries.");

        List<Birds> birdsEntries = repository.findAll();

        LOGGER.info("Found {} birds entries", birdsEntries.size());

        return convertToDTOs(birdsEntries);
    }

    private List<BirdsDTO> convertToDTOs(List<Birds> models) {
        return models.stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public BirdsDTO findById(String id) {
        LOGGER.info("Finding birds entry with id: {}", id);

        Birds found = findbirdsById(id);

        LOGGER.info("Found birds entry: {}", found);

        return convertToDTO(found);
    }

    @Override
    public BirdsDTO update(BirdsDTO birds) {
        LOGGER.info("Updating birds entry with information: {}", birds);

        Birds updated = findbirdsById(birds.getId());
        updated.update(birds.getName(), birds.getFamily());
        updated = repository.save(updated);

        LOGGER.info("Updated birds entry with information: {}", updated);

        return convertToDTO(updated);
    }

    private Birds findbirdsById(String id) {
        Optional<Birds> result = repository.findOne(id);
        return result.orElseThrow(() -> new BirdsNotFoundException(id));

    }

    private BirdsDTO convertToDTO(Birds model) {
        BirdsDTO dto = new BirdsDTO();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setFamily(model.getFamily());
        dto.setContinents(model.getContinents());

        return dto;
    }
}
