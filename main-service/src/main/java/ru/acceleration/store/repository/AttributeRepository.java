package ru.acceleration.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.acceleration.store.model.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

}
