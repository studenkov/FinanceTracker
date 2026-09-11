package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.CurrencyModel;
import org.kaorun.financetracker.service.CurrencyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencies")
@Tag(name = "Валюты", description = "Справочник поддерживаемых мировых валют")
public class CurrencyApiController extends AbstractApiController<CurrencyModel, Long> {

    public CurrencyApiController(CurrencyService currencyService) {
        super(currencyService, "Валюта успешно удалена");
    }
}