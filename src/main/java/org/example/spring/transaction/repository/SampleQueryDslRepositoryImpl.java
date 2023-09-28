package org.example.spring.transaction.repository;

import java.util.List;
import org.example.spring.transaction.entity.QSampleEntity;
import org.example.spring.transaction.entity.SampleEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class SampleQueryDslRepositoryImpl extends QuerydslRepositorySupport implements SampleQueryDslRepository {

	private final QSampleEntity sample = QSampleEntity.sampleEntity;

	public SampleQueryDslRepositoryImpl() {
		super(SampleEntity.class);
	}

	@Override
	public List<SampleEntity> findAllBy(final String name) {
		return from(sample).where(sample.name.eq(name)).fetch();
	}
}
