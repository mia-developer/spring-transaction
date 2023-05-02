package org.example.spring.transaction.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.transaction.entity.SampleEntity;
import org.example.spring.transaction.repository.SampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SampleService {

	private final SampleRepository repository;

	public SampleEntity add() {

		SampleEntity entity = SampleEntity.builder().name("name").build();

		return repository.save(entity);
	}
}
