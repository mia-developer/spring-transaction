package org.example.spring.transaction.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.spring.transaction.entity.SampleEntity;
import org.example.spring.transaction.repository.SampleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAllSampleUseCase {

	private final SampleRepository repository;

	public List<SampleEntity> execute() {
		return repository.findAll();
	}
}
