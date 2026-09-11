package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.FrequencyModel;
import org.kaorun.financetracker.service.FrequencyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frequencies")
@Tag(name = "Частоты повторений", description = "Справочник периодичности для регулярных платежей (ежедневно, еженедельно, ежемесячно)")
public class FrequencyApiController extends AbstractApiController<FrequencyModel, Long> {

    public FrequencyApiController(FrequencyService frequencyService) {
        super(frequencyService, "Частота успешно удалена");
    }
}