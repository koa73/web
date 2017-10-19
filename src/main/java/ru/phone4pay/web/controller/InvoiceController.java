package ru.phone4pay.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.phone4pay.web.rest.exception.WebApiException;
import ru.phone4pay.web.rest.model.FormatTemplate;
//import ru.phone4pay.web.service.InvoiceService;

/**
 *
 * Created by Олег on 23.09.2017.
 */

@Controller
@Validated
@RequestMapping(path = "/web", method = RequestMethod.GET)
public class InvoiceController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    //@Autowired
    //InvoiceService invoiceService;

    @Autowired
    FormatTemplate format;

    @RequestMapping(path = "/invoice")
    public String greeting( @RequestParam(value="id", required=true) String operId,
                            @RequestParam(value="t", required=true) String timeStamp, Model model) throws WebApiException {

        //final InvoiceVerify invoice = invoiceService.getInvoiceInfo(operId, timeStamp);

        //format.getAsPhone(invoice.getReceiver());
        //model.addAttribute("receiver", format.getAsPhone(invoice.getReceiver()));
        //model.addAttribute("sum", invoice.getAmount());
        model.addAttribute("sum", 1000);
        model.addAttribute("fee", 0);
        model.addAttribute("amount", 0);
        //model.addAttribute("txtMsg", invoice.getTextMsg());
        //model.addAttribute("token", invoiceService.getToken(operId, timeStamp));
        return "get_invoice";
    }

}
