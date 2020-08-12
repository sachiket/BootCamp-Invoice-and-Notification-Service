package com.cg.iter.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iter.notificationservice.entity.InvoiceResponse;
import com.cg.iter.notificationservice.entity.NotificationRequest;
import com.cg.iter.notificationservice.service.InvoiceGeneratorService;


@RestController
@RequestMapping("/invoice")
public class InvoiceController {
	
	@Autowired
	private InvoiceGeneratorService service;
	
	@PostMapping("/generateInvoice")
	public String generateInvoice(@RequestBody InvoiceResponse invoiceResponse) {
		return service.generatePdfInvoice(invoiceResponse);
	}

}
