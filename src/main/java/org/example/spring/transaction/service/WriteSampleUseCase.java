package org.example.spring.transaction.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.transaction.entity.SampleEntity;
import org.example.spring.transaction.repository.SampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WriteSampleUseCase {

	private final SampleRepository repository;

	public SampleEntity execute() {
		SampleEntity entity = SampleEntity.builder().name("name").build();

		return repository.save(entity);
	}
}
