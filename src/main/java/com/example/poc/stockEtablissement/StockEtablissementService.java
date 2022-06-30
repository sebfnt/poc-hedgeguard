package com.example.poc.stockEtablissement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockEtablissementService {

    private static final int BUFFER_SIZE = 10000;

    private static final Logger logger = LoggerFactory.getLogger(StockEtablissementService.class);
    private final StockEtablissementRepository stockEtablissementRepository;
    private final List<StockEtablissement> buffer;
    private boolean isHeadersLine = true;
    private int rowCount = 0;

    public StockEtablissementService(StockEtablissementRepository stockEtablissementRepository) {
        this.stockEtablissementRepository = stockEtablissementRepository;
        this.buffer = new ArrayList<>();
    }

    public void processFile(Reader reader, List<String> fields, Integer rowLimit) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        boolean limitReached;

        while ((line = bufferedReader.readLine()) != null) {
            limitReached = processLine(line, fields, rowLimit);
            if (limitReached) {
                break;
            }
        }
        bufferedReader.close();
    }

    private boolean processLine(String line, List<String> fields, Integer rowLimit) {

        if (this.isHeadersLine) {
            isHeadersLine = false;
        } else {
            buffer.add(mapLineToStockEtablissement(line, fields));
            rowCount++;
        }

        boolean limitReached = rowLimit != null && rowCount >= rowLimit;

        if (buffer.size() == BUFFER_SIZE || limitReached) {
            logger.info("Service : Persist StockEtablissement elements in batch mode.");
            stockEtablissementRepository.saveAllAndFlush(buffer);
            buffer.clear();
        }
        return limitReached;
    }

    private StockEtablissement mapLineToStockEtablissement(String line, List<String> fields) {
        StockEtablissement stockEtablissement = new StockEtablissement();

        String[] values = line.split(",");

        fields.forEach(f -> {
            Field field;
            try {
                field = StockEtablissement.class.getDeclaredField(f);
                field.setAccessible(true);
                field.set(stockEtablissement, values[StockEtablissementEnum.valueOfHeader(f).atomicIndex]);
                field.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return stockEtablissement;
    }
}
