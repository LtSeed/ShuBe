package org.example.shubackend.service.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class GenericCrudService<E, ID> {
    protected final JpaRepository<E, ID> repo;

    protected GenericCrudService(JpaRepository<E, ID> repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<E> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<E> find(ID id) {
        return repo.findById(id);
    }

    @Transactional
    public E create(E e) {
        return repo.save(e);
    }

    @Transactional
    public E update(ID id, E e) {
        return repo.save(e);
    }

    @Transactional
    public void delete(ID id) {
        repo.deleteById(id);
    }
}