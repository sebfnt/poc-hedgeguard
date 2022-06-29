package com.example.poc.raising;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class RaisingController {

    private static Logger logger = LoggerFactory.getLogger(RaisingController.class);

    private final RaisingService raisingService;

    public RaisingController(RaisingService raisingService) {
        this.raisingService = raisingService;
    }

    @PostMapping(value = "/raising-request")
    public ResponseEntity<RaisingResponse> raiseDataFromUrl(@RequestBody RaisingRequest raisingRequest) {
        logger.info("Controller : Start the raising of data.");

        try {
            raisingService.raiseDataFromUrl(raisingRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new RaisingResponse("ERROR with data raised and stored in DB."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Controller : End the raising of data.");
        return new ResponseEntity<>(new RaisingResponse("All data raised and stored in DB."), HttpStatus.OK);
    }

}
