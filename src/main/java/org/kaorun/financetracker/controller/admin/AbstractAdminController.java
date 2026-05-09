package org.kaorun.financetracker.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractAdminController {
    protected final int PAGE_SIZE = 10;

    protected <T> Page<T> getPage(
            List<T> sourceList,
            int page
    ) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new PageImpl<>(List.of());
        }
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, sourceList.size());
        if (start > end) {
            return new PageImpl<>(List.of());
        }
        return new PageImpl<>(
                sourceList.subList(start, end),
                PageRequest.of(page, PAGE_SIZE),
                sourceList.size()
        );
    }

    protected <T> Page<T> resolveData(
            String query,
            int page,
            Function<Long, T> idSearcher,
            Function<String, List<T>> textSearcher,
            Supplier<List<T>> pageSupplier,
            Supplier<List<T>> allSupplier
    ) {
        if (query != null && !query.isEmpty()) {
            try {
                T result = idSearcher.apply(Long.parseLong(query));
                return getPage(result != null ? List.of(result) : List.of(), page);
            } catch (NumberFormatException e) {
                return getPage(textSearcher.apply(query), page);
            }
        }
        return new PageImpl<>(
                pageSupplier.get(),
                PageRequest.of(page, PAGE_SIZE),
                allSupplier.get().size()
        );
    }

    protected String redirect(
            String path,
            int page,
            String query
    ) {
        return String.format(
                "redirect:%s?page=%d%s",
                path,
                page,
                query != null && !query.isEmpty() ? "&query=" + query : ""
        );
    }

    protected boolean hasErrors(
            BindingResult result,
            RedirectAttributes redirectAttrs,
            String entityName,
            Object entity
    ) {
        if (result.hasErrors()) {
            String specificErrors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(" | "));
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.entityModel", result);
            redirectAttrs.addFlashAttribute("entityModel", entity);
            redirectAttrs.addFlashAttribute("errorMessage", "Ошибка (" + entityName + "): " + specificErrors);
            return true;
        }
        return false;
    }
}