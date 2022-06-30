package com.example.poc.stockEtablissement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StockEtablissementStreamService {

    private static final Logger logger = LoggerFactory.getLogger(StockEtablissementStreamService.class);

    @Autowired
    private StockEtablissementRepository stockEtablissementRepository;

    public void processFile(Reader reader, List<String> fields,
                            Integer rowLimit) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {

            Stream<String> lines = bufferedReader.lines();

            int chunkSize = 10000;
            AtomicInteger counter = new AtomicInteger();
            final Collection<List<String>> partitionedList =
                    lines.limit(rowLimit)
                            .collect(Collectors.groupingBy(i -> counter.getAndIncrement() / chunkSize))
                            .values();

            partitionedList.parallelStream()
                    .forEach(list -> {

                        List<StockEtablissement> toPersist = list.stream()
                                .map(l -> l.split(","))
                                .map(arr -> buildElement(arr, fields))
                                .collect(Collectors.toList());

                        logger.info("Service : Persist StockEtablissement elements in batch mode. (" + toPersist.size() + ")");

                        stockEtablissementRepository.saveAllAndFlush(toPersist);
                    });

        }


    }

    private StockEtablissement buildElement(String[] arr, List<String> fields) {
        StockEtablissement stockEtablissement = new StockEtablissement();
        fields.forEach(f -> {
            Field field;
            try {
                field = StockEtablissement.class.getDeclaredField(f);
                field.setAccessible(true);
                field.set(stockEtablissement,
                        arr[StockEtablissementEnum.valueOfHeader(f).atomicIndex]);
                field.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return stockEtablissement;
    }


}
