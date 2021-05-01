package com.ssafy.recto.controller;

import com.ssafy.recto.service.PhotoService;
import com.ssafy.recto.service.PresentService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("PresentController")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/present")
public class PresentController {

	private static final Logger logger = LoggerFactory.getLogger(PresentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private PresentService presentService;

}