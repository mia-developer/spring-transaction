package org.example.spring.transaction.repository;

import java.util.Optional;
import org.example.spring.transaction.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, Long>, SampleQueryDslRepository{

	Optional<SampleEntity> findFirstById(final Long id);
}
