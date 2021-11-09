package ru.f9208.choiserestaurant.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.f9208.choiserestaurant.model.entities.ImageLabel;

@Repository
public class ImageLabelRepository {
    final CrudImageLabelRepository crudImageLabelRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ImageLabelRepository(CrudImageLabelRepository crudImageLabelRepository) {
        this.crudImageLabelRepository = crudImageLabelRepository;
    }

    public ImageLabel saveImage(ImageLabel imageLabel) {
        return crudImageLabelRepository.save(imageLabel);
    }

    public ImageLabel getOne(Integer id) {
        return crudImageLabelRepository.getOne(id);
    }
}
