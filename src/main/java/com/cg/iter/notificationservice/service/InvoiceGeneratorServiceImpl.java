package com.cg.iter.notificationservice.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cg.iter.notificationservice.entity.AddressDTO;
import com.cg.iter.notificationservice.entity.InvoiceResponse;
import com.cg.iter.notificationservice.entity.OrderProductMapDTO;
import com.cg.iter.notificationservice.entity.ProductDTO;
import com.cg.iter.notificationservice.entity.UserInvoiceResponse;
import com.itextpdf.html2pdf.HtmlConverter;


@Service
public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService{

	@Autowired
	private RestTemplate restTemplate;

	@Value("${product.url}")
	private String productURL;
	
	@Value("${address.url}")
	private String addressURL;
	
	@Value("${auth.url}")
	private String authURL;
	
	@Autowired
    private JavaMailSender javaMailSender;


	@Override
	public String generatePdfInvoice(InvoiceResponse invoiceResponse) {
		
		UserInvoiceResponse userDetails = restTemplate.getForObject(authURL+"?userId="+invoiceResponse.getUserId(), UserInvoiceResponse.class);
		String pdfPathUrl = "D:\\BootCamp\\Invoices\\Invoice"
				+invoiceResponse.getUserId()+invoiceResponse.getOrderList().get(0).getOrderId()+".pdf";
		String html = getHTMLdoc(invoiceResponse , userDetails);
		
		
		try {
			HtmlConverter.convertToPdf(html, new FileOutputStream(pdfPathUrl));
//			TimeUnit.SECONDS.sleep(1);
			sendInvoiceMail( userDetails ,html,pdfPathUrl);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (MessagingException e) {
			e.printStackTrace();
			
		}
		return "pdf generated!!";
	}




	private void sendInvoiceMail(UserInvoiceResponse userDetails, String invoice, String pdfPathUrl) throws MessagingException {
		
		 MimeMessage msg = javaMailSender.createMimeMessage();
		    

	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	        helper.setTo(userDetails.getEmail());

	        helper.setSubject("Order placed successfully!!"); 

	        // default = text/plain
	        //helper.setText("Check attachment for image!");
	   
	        helper.setText( "<h2>Hi, "+userDetails.getName()+" </h2>"
	        		+ "<h3>Thank you for shopping with CapStore</h3>"
	        		+ "<img src=\"https://i.ibb.co/Jdpt4N6/cgad.jpg\" alt=\"CG\" width=\"100%\" height=\"100%\">"
	        		+ "<hr>" + invoice, true);

			// hard coded a file path
	        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));
	        helper.addAttachment("CapStore.png", 
	        		new ClassPathResource("cgstore.png"));
	        
	        
	        
	    

	        javaMailSender.send(msg);
	        return;
		
	}




	private String getHTMLdoc(InvoiceResponse invoiceResponse ,UserInvoiceResponse userDetails) {
		String head = "<!doctype html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"    <meta charset=\"utf-8\">\r\n" + 
				"    <title>CapStore Invoice</title>\r\n" + 
				"    \r\n" + 
				"    <style>\r\n" + 
				"    .invoice-box {\r\n" + 
				"        max-width: 800px;\r\n" + 
				"        margin: auto;\r\n" + 
				"        padding: 30px;\r\n" + 
				"        border: 1px solid #eee;\r\n" + 
				"        box-shadow: 0 0 10px rgba(0, 0, 0, .15);\r\n" + 
				"        font-size: 16px;\r\n" + 
				"        line-height: 24px;\r\n" + 
				"        font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\r\n" + 
				"        color: #555;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table {\r\n" + 
				"        width: 100%;\r\n" + 
				"        line-height: inherit;\r\n" + 
				"        text-align: left;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table td {\r\n" + 
				"        padding: 5px;\r\n" + 
				"        vertical-align: top;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr td:nth-child(2) {\r\n" + 
				"        text-align: right;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.top table td {\r\n" + 
				"        padding-bottom: 20px;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.top table td.title {\r\n" + 
				"        font-size: 45px;\r\n" + 
				"        line-height: 45px;\r\n" + 
				"        color: #333;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.information table td {\r\n" + 
				"        padding-bottom: 40px;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.heading td {\r\n" + 
				"        background: #eee;\r\n" + 
				"        border-bottom: 1px solid #ddd;\r\n" + 
				"        font-weight: bold;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.details td {\r\n" + 
				"        padding-bottom: 20px;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.item td{\r\n" + 
				"        border-bottom: 1px solid #eee;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.item.last td {\r\n" + 
				"        border-bottom: none;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .invoice-box table tr.total td:nth-child(2) {\r\n" + 
				"        border-top: 2px solid #eee;\r\n" + 
				"        font-weight: bold;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    @media only screen and (max-width: 600px) {\r\n" + 
				"        .invoice-box table tr.top table td {\r\n" + 
				"            width: 100%;\r\n" + 
				"            display: block;\r\n" + 
				"            text-align: center;\r\n" + 
				"        }\r\n" + 
				"        \r\n" + 
				"        .invoice-box table tr.information table td {\r\n" + 
				"            width: 100%;\r\n" + 
				"            display: block;\r\n" + 
				"            text-align: center;\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    /** RTL **/\r\n" + 
				"    .rtl {\r\n" + 
				"        direction: rtl;\r\n" + 
				"        font-family: Tahoma, 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .rtl table {\r\n" + 
				"        text-align: right;\r\n" + 
				"    }\r\n" + 
				"    \r\n" + 
				"    .rtl table tr td:nth-child(2) {\r\n" + 
				"        text-align: left;\r\n" + 
				"    }\r\n" + 
				"    </style>\r\n" + 
				"</head>";
		
		String body = "<body>\r\n" + 
				"    <div class=\"invoice-box\">\r\n" + 
				"        <table cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
				"            <tr class=\"top\">\r\n" + 
				"                <td colspan=\"3\">\r\n" + 
				"                    <table>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td class=\"title\">\r\n" + 
				"                                <img src=\"https://www.ivalua.com/wp-content/uploads//2019/06/Capgemini_Logo_2COL_RGB.jpg\" style=\"width:100%; max-width:300px;\">\r\n" + 
				"                            </td>\r\n" + 
				"                            \r\n" + 
				"                            <td>\r\n" + 
				"                                OrderId #: "+invoiceResponse.getOrderList().get(0).getOrderId()+"<br>\r\n" + 
				"                                Created: "+invoiceResponse.getOrderInitiateTime()+"<br>\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
				"                    </table>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            \r\n" + 
				"            <tr class=\"information\">\r\n" + 
				"                <td colspan=\"3\">\r\n" + 
				"                    <table>\r\n" + 
				"                        <tr>\r\n" + 
				"                            <td>";
		
		
	
		
		AddressDTO address = restTemplate.getForObject(addressURL+"?addressId="+invoiceResponse.getAddressId(), AddressDTO.class);
		
		body += address.getBuildingNo()+" .<br>\r\n"
				+ address.getField()+"  <br>\r\n" + 
				 address.getCity()+"  <br>\r\n" + 
				 address.getState()+"  <br>\r\n" + 
				 address.getZip()+"  <br>\r\n" + 
				"                            </td>\r\n" + 
				"                            \r\n" + 
				"                            <td>\r\n" + 
				userDetails.getName()+"  <br>\r\n" + 
				userDetails.getEmail()+"                 <br>\r\n" + 
				"(+91) "+userDetails.getPhoneno()+"\r\n" + 
				"                            </td>\r\n" + 
				"                        </tr>\r\n" + 
				"                    </table>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            \r\n" + 
				"            <tr class=\"heading\">\r\n" + 
				"                <td>\r\n" + 
				"                    Payment Method\r\n" + 
				"                </td>\r\n" + 
				"                \r\n" + 
				"                <td>\r\n" + 
				"                </td>\r\n" + 
				"                \r\n" + 
				"                <td>\r\n" + 
				"                    Card #\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            \r\n" + 
				"            <tr class=\"details\">\r\n" + 
				"                <td>\r\n" + 
				"                    Debit Card\r\n" + 
				"                </td>\r\n" + 
				"                <td>\r\n" + 
				"                </td>\r\n" + 
				"                \r\n" + 
				"                <td>\r\n" + 
				"                    1000\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            \r\n" + 
				"            <tr class=\"heading\">\r\n" + 
				"                <td>\r\n" + 
				"                    Item\r\n" + 
				"                </td>\r\n" + 
				"                \r\n" + 
				"                <td>\r\n" + 
				"                    Quantity\r\n" + 
				"                </td>\r\n" + 
				"                \r\n" + 
				"                <td>\r\n" + 
				"                    Price\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            ";
		
		List<OrderProductMapDTO> orderProductmap = invoiceResponse.getOrderList();

		Iterator<OrderProductMapDTO> itr = orderProductmap.iterator();
		int index = 0;
		
		while (itr.hasNext()) {
			ProductDTO product = restTemplate.getForObject(productURL+"/getProductById?productId="+orderProductmap.get(index).getProductId(),
					ProductDTO.class);
			body+="  <tr class=\"item\">\r\n" + 
					"                <td>\r\n" + 
					product.getProductName()+" \r\n" + 
					"                </td>\r\n" + 
					"                \r\n" + 
					"                <td>\r\n" + 
					orderProductmap.get(index).getQuantity()+"\r\n" + 
					"                </td>\r\n" + 
					"                \r\n" + 
					"                <td>\r\n" + 
					"                    ₹"+(orderProductmap.get(index).getQuantity() * product.getPrice())+" \r\n" + 
					"                </td>\r\n" + 
					"            </tr>";
			index++;
			itr.next();
		}
		
		body +="<tr class=\"total\">\r\n" + 
				"                <td></td>\r\n" + 
				"                <td></td>\r\n" + 
				"                \r\n" + 
				"                <td>\r\n" + 
				"                   Total: ₹"+invoiceResponse.getTotalcost()+"\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </table>\r\n" + 
				"    </div>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		
		return head+body;
	}
	
}
