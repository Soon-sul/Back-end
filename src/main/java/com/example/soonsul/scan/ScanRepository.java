package com.example.soonsul.scan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Long> {
}
