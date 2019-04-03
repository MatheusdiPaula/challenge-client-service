package com.microservices.template.controller;

import com.microservices.template.model.dto.TemplateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class TemplateController {

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getTemplate() {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> postTemplate(@RequestBody TemplateDto templateDto) {
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> putTemplate(@RequestAttribute(value = "id") Long id, @RequestBody TemplateDto templateDto) {
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteTemplate(@RequestAttribute(value = "id") Long id) {
		return null;
	}
}
