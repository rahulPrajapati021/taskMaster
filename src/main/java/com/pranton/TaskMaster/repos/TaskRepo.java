package com.pranton.TaskMaster.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pranton.TaskMaster.models.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
}
