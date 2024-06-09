package com.springbatch.folhaponto.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import com.springbatch.folhaponto.dominio.Funcionario;
import com.springbatch.folhaponto.reader.FuncionarioReader;

@Configuration
public class FolhaPontoStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step folhaPontoStep(
			JdbcCursorItemReader<Funcionario> funcionarioReaderJdbc,
			ItemProcessor<Funcionario, FolhaPonto> folhaPontoProcessor,
			ClassifierCompositeItemWriter<FolhaPonto> classifierFolhaPonto,
      @Qualifier("flatFileFolhaPonto") FlatFileItemWriter<FolhaPonto> flatFileFolhaPonto,
      @Qualifier("funcionariosSemPontoFileWriter") FlatFileItemWriter<FolhaPonto> funcionariosSemPontoFileWriter
  ) {
		return stepBuilderFactory
				.get("folhaPontoStep")
				.<Funcionario,FolhaPonto>chunk(100)
				.reader(new FuncionarioReader(funcionarioReaderJdbc))
				.processor(folhaPontoProcessor)
				.writer(classifierFolhaPonto)
        .stream(flatFileFolhaPonto)
        .stream(funcionariosSemPontoFileWriter)
				.build();
	}
}
