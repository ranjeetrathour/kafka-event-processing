package com.myapp.repository;

import com.myapp.domain.ComplexEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexEventRepository extends JpaRepository<ComplexEvent, String> {
}

