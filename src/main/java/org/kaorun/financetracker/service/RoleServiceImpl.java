package org.kaorun.financetracker.service;

import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.model.RoleModel;
import org.kaorun.financetracker.repository.RoleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repo;

    public List<RoleModel> findAll() {
        return repo.findAll();
    }

    public RoleModel findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<RoleModel> findByRole(String role) {
        return repo.findByRoleContainingIgnoreCase(role);
    }

    public List<RoleModel> findPage(int page, int size) {
        return repo.findAll(PageRequest.of(page, size)).getContent();
    }

    public RoleModel add(RoleModel role) {
        return repo.save(role);
    }

    public RoleModel update(RoleModel role) {
        return repo.save(role);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
