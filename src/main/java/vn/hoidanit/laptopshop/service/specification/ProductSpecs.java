package vn.hoidanit.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Products;
import vn.hoidanit.laptopshop.domain.Products_;

public class ProductSpecs {
    public static Specification<Products> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Products_.NAME), "%" + name + "%");
    }

    // case 1:
    public static Specification<Products> minPrice(double min) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Products_.PRICE), min);
    }

    // case 2:
    public static Specification<Products> maxPrice(double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Products_.PRICE), max);
    }

    // case 3:
    public static Specification<Products> matchFactory(String factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Products_.FACTORY), factory);
    }

    public static Specification<Products> matchListFactory(List<String> factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Products_.FACTORY)).value(factory);
    }

    // case 4:
    public static Specification<Products> matchListTarget(List<String> target) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Products_.TARGET)).value(target);
    }

    // case 5:
    public static Specification<Products> matchMultiplePrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Products_.PRICE), min, max);
    }

    // case 6:
    public static Specification<Products> matchPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.gt(root.get(Products_.PRICE), min),
                criteriaBuilder.le(root.get(Products_.PRICE), max));
    }
}
