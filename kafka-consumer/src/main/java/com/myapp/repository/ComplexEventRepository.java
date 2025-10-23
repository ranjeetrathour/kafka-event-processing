package com.myapp.repository;

import com.myapp.domain.ComplexEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplexEventRepository extends JpaRepository<ComplexEvent, String> {

    @Query(nativeQuery = true, value = "select * from complex_event order by created_at desc")
    List<ComplexEvent> getAllData();
}

