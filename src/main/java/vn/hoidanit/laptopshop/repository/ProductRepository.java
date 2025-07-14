package vn.hoidanit.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long>, JpaSpecificationExecutor<Products> {
    Page<Products> findAll(Pageable page);

    Page<Products> findAll(Specification<Products> spec, Pageable pageable);
}
