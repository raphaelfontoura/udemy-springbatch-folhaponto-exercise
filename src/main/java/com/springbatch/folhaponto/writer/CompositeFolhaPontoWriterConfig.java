package com.springbatch.folhaponto.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.folhaponto.dominio.FolhaPonto;

@Configuration
public class CompositeFolhaPontoWriterConfig {

  @Bean
  public CompositeItemWriter<FolhaPonto> compositeFolhaPontoWriter(
    FlatFileItemWriter<FolhaPonto> flatFileFolhaPonto,
    ItemWriter<FolhaPonto> imprimeFolhaPontoWriter
  ) {
    return new CompositeItemWriterBuilder<FolhaPonto>()
      .delegates(flatFileFolhaPonto, imprimeFolhaPontoWriter)
      .build();
  }

}
