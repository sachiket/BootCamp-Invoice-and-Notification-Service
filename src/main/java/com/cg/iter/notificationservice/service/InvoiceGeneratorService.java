package com.cg.iter.notificationservice.service;

import com.cg.iter.notificationservice.entity.InvoiceResponse;


public interface InvoiceGeneratorService {


	public String generatePdfInvoice(InvoiceResponse invoiceResponse);
}
