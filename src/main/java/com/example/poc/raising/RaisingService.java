package com.example.poc.raising;

import com.example.poc.stockEtablissement.StockEtablissementService;
import com.example.poc.stockEtablissement.StockEtablissementStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class RaisingService {

    private static final Logger logger = LoggerFactory.getLogger(RaisingService.class);

    private final StockEtablissementService stockEtablissementService;

    private final StockEtablissementStreamService stockEtablissementStreamService;

    public RaisingService(StockEtablissementService stockEtablissementService, StockEtablissementStreamService stockEtablissementStreamService) {
        this.stockEtablissementService = stockEtablissementService;
        this.stockEtablissementStreamService = stockEtablissementStreamService;
    }

    public void raiseDataFromUrl(RaisingRequest raisingRequest) throws IOException {
        logger.info("Service : Start the raising of data.");

        try (InputStream inputStream = new URL(raisingRequest.getUrl()).openStream();
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                logger.info("Service : File found at URL : " + zipEntry.getName());

                if ("STREAM".equals(raisingRequest.getMode())) {
                    stockEtablissementStreamService.processFile(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8),
                            raisingRequest.getFields(),
                            raisingRequest.getRowLimit());
                } else {
                    stockEtablissementService.processFile(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8),
                            raisingRequest.getFields(),
                            raisingRequest.getRowLimit());
                }

                break; //only one file to manage and stream will be closed with bufferedReader

            }

        }

        logger.info("Service : End the raising of data.");
    }


}
