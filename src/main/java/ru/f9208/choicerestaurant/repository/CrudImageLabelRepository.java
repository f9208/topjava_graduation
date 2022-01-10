package ru.f9208.choicerestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.f9208.choicerestaurant.model.entities.ImageLabel;

@Transactional(readOnly = true)
public interface CrudImageLabelRepository extends JpaRepository<ImageLabel, Integer> {
}
