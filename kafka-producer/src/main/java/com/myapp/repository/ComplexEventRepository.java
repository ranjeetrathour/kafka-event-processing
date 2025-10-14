package com.myapp.repository;


import com.myapp.domain.ComplexEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplexEventRepository extends JpaRepository<ComplexEvent, String> {}

