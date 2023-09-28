package org.example.spring.transaction.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.spring.transaction.entity.SampleEntity;
import org.example.spring.transaction.repository.SampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchSampleUseCase {

	private final SampleRepository repository;

	public List<SampleEntity> execute(final String name) {
		return repository.findAllBy(name);
	}
}
