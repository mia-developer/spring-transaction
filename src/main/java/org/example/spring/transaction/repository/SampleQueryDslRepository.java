package org.example.spring.transaction.repository;

import java.util.List;
import org.example.spring.transaction.entity.SampleEntity;

interface SampleQueryDslRepository {

	List<SampleEntity> findAllBy(final String name);
}
