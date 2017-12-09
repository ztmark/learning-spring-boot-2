package io.github.ztmark.learningspringboot2.dao;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.github.ztmark.learningspringboot2.domain.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Author: Mark
 * Date  : 2017/12/8
 */
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long>, ReactiveQueryByExampleExecutor<Employee> {

    Flux<Employee> findByFirstName(Mono<String> firstName);

}
