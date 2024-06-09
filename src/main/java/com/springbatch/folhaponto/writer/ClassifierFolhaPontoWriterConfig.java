package com.springbatch.folhaponto.writer;


import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.folhaponto.dominio.FolhaPonto;

@Configuration
public class ClassifierFolhaPontoWriterConfig {

  @Bean
  public ClassifierCompositeItemWriter<FolhaPonto> classifierFolhaPonto(
    CompositeItemWriter<FolhaPonto> compositeFolhaPontoWriter,
    FlatFileItemWriter<FolhaPonto> funcionariosSemPontoFileWriter
  ) {
    return new ClassifierCompositeItemWriterBuilder<FolhaPonto>()
      .classifier(classifierImpl(compositeFolhaPontoWriter, funcionariosSemPontoFileWriter))
      .build();
  }

  private Classifier<FolhaPonto, ItemWriter<? super FolhaPonto>> classifierImpl(
    CompositeItemWriter<FolhaPonto> compositeFolhaPontoWriter,
    FlatFileItemWriter<FolhaPonto> funcionariosSemPontoFileWriter
  ) {
    return new Classifier<FolhaPonto,ItemWriter<? super FolhaPonto>>() {

      @Override
      public ItemWriter<? super FolhaPonto> classify(FolhaPonto classifiable) {
        if (classifiable.getRegistrosPontos().isEmpty()) {
          return funcionariosSemPontoFileWriter;
        } else {
          return compositeFolhaPontoWriter;
        }
      }
      
    };
  }

}
