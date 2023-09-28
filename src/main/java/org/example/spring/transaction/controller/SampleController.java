package org.example.spring.transaction.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.spring.transaction.entity.SampleEntity;
import org.example.spring.transaction.service.ReadSampleUseCase;
import org.example.spring.transaction.service.ReadAllSampleUseCase;
import org.example.spring.transaction.service.SearchSampleUseCase;
import org.example.spring.transaction.service.WriteSampleUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
public class SampleController {

	private final ReadSampleUseCase readUseCase;
	private final ReadAllSampleUseCase readAllService;
	private final SearchSampleUseCase searchService;
	private final WriteSampleUseCase writeService;

	@GetMapping
	public List<SampleEntity> browse(final @RequestParam String name){
		return searchService.execute(name);
	}

	@GetMapping("/{id}")
	public SampleEntity read(final @PathVariable Long id){
		return readUseCase.execute(id);
	}

	@GetMapping("/all")
	public List<SampleEntity> readAll(){
		return readAllService.execute();
	}

	@PostMapping
	public SampleEntity add(){
		return writeService.execute();
	}
}
