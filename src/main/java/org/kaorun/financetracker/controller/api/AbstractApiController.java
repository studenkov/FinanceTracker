package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.Identifiable;
import org.kaorun.financetracker.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public abstract class AbstractApiController<T extends Identifiable<ID>, ID> {

    protected final CrudService<T, ID> service;
    protected final String deleteSuccessMessage;

    protected AbstractApiController(CrudService<T, ID> service, String deleteSuccessMessage) {
        this.service = service;
        this.deleteSuccessMessage = deleteSuccessMessage;
    }

    @GetMapping
    @Operation(summary = "Получить все записи", description = "Возвращает полный список сущностей из базы данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список успешно получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован (требуется Bearer токен)")
    })
    public List<T> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить запись по ID", description = "Возвращает отдельную сущность по ее уникальному первичному ключу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Запись с указанным ID не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "Уникальный идентификатор записи", required = true)
            @PathVariable ID id) {
        T entity = service.findById(id);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    @Operation(summary = "Создать новую запись", description = "Валидирует и сохраняет новую сущность в базе данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Сущность успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации переданных данных"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<T> create(@Valid @RequestBody T entity) {
        T created = service.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующую запись", description = "Обновляет поля существующей сущности по ее ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сущность успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации переданных данных"),
            @ApiResponse(responseCode = "404", description = "Запись с указанным ID не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID обновляемой записи", required = true)
            @PathVariable ID id,
            @Valid @RequestBody T entity) {
        if (service.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        entity.setId(id);
        service.update(entity);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить запись по ID", description = "Удаляет сущность из базы данных по ее идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сущность успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Запись с указанным ID не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID удаляемой записи", required = true)
            @PathVariable ID id) {
        if (service.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", deleteSuccessMessage));
    }
}
