package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.TypeModel;
import org.kaorun.financetracker.service.TypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/types")
@Tag(name = "Типы операций", description = "Справочник типов категорий: Доход и Расход")
public class TypeApiController extends AbstractApiController<TypeModel, Long> {

    public TypeApiController(TypeService typeService) {
        super(typeService, "Тип операции успешно удален");
    }
}