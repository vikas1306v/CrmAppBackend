package com.crm.repository;

import com.crm.entity.CommunicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationLogRepository extends JpaRepository<CommunicationLog,Long> {
}
