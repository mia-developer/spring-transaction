package org.example.spring.transaction.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.transaction.entity.SampleEntity;
import org.example.spring.transaction.repository.SampleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadSampleUseCase {

	private final SampleRepository repository;

	public SampleEntity execute(final Long id) {
		return repository.findFirstById(id).orElseThrow(IllegalArgumentException::new);
	}
}
