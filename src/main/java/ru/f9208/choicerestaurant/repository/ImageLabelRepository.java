package ru.f9208.choicerestaurant.repository;

import org.springframework.stereotype.Repository;
import ru.f9208.choicerestaurant.model.entities.ImageLabel;

@Repository
public class ImageLabelRepository {
    final CrudImageLabelRepository crudImageLabelRepository;

    public ImageLabelRepository(CrudImageLabelRepository crudImageLabelRepository) {
        this.crudImageLabelRepository = crudImageLabelRepository;
    }

    public ImageLabel saveImage(ImageLabel imageLabel) {
        return crudImageLabelRepository.save(imageLabel);
    }

    public ImageLabel getOne(Integer id) {
        return crudImageLabelRepository.findById(id).orElse(new ImageLabel());
    }
}
