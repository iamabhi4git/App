package org.bdiplus.v1.taskManager.services;

import org.bdiplus.v1.taskManager.entities.Task;
import org.bdiplus.v1.taskManager.entities.User;

import java.util.List;
import java.util.Optional;
public interface TaskService  {


    List<Task> getAllTasksByUser(User user);

    Optional<Task> getTaskById(Long taskId);

    Task createTask(Task task);

    Task updateTask(Long taskId, Task task);

    void deleteTask(Long taskId);

}
