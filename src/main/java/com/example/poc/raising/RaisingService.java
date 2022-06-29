package com.example.poc.raising;

import com.example.poc.stockEtablissement.StockEtablissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class RaisingService {

    private static final Logger logger = LoggerFactory.getLogger(RaisingService.class);

    private final StockEtablissementService stockEtablissementService;

    public RaisingService(StockEtablissementService stockEtablissementService) {
        this.stockEtablissementService = stockEtablissementService;
    }

    public void raiseDataFromUrl(RaisingRequest raisingRequest) throws IOException {
        logger.info("Service : Start the raising of data.");

        try (InputStream inputStream = new URL(raisingRequest.getUrl()).openStream();
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                logger.info("Service : File found at URL : " + zipEntry.getName());

                processFile(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8),
                        raisingRequest.getFields(),
                        raisingRequest.getRowLimit());

                break; //only one file to manage and stream will be closed with bufferedReader
            }

        }

        logger.info("Service : End the raising of data.");
    }

    private void processFile(Reader reader, List<String> fields, Integer rowLimit) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        boolean limitReached;

        while ((line = bufferedReader.readLine()) != null) {
            limitReached = stockEtablissementService.processLine(line, fields, rowLimit);
            if (limitReached) {
                break;
            }
        }
        bufferedReader.close();
    }

}
