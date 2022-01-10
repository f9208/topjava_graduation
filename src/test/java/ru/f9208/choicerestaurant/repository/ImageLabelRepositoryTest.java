package ru.f9208.choicerestaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.f9208.choicerestaurant.model.entities.ImageLabel;

import static ru.f9208.choicerestaurant.repository.testData.ImageLabelData.*;

public class ImageLabelRepositoryTest extends AbstractStarterTest {
    @Autowired
    ImageLabelRepository imageLabelRepository;

    @Test
    void saveImage() {
        ImageLabel original = new ImageLabel(justOpened);
        ImageLabel saved = imageLabelRepository.saveImage(justOpened);
        original.setId(saved.getId());
        MATCHER_IMAGE.assertMatch(saved, original);
    }

    @Test
    void getOne() {
        ImageLabel af = imageLabelRepository.getOne(1);
        MATCHER_IMAGE.assertMatch(af, bearGrizzlyLabel);
    }
}
