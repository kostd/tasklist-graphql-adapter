package org.kostd.bpms.tasklist.rest

import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.task.Comment
import org.camunda.bpm.engine.task.Task
import org.kostd.bpms.tasklist.graphql.CommentDto
import org.kostd.bpms.tasklist.graphql.TaskDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import javax.inject.Inject


@Component
class CamundaTaskClient {

    @Inject
    @Qualifier("remote")
    private lateinit var taskService: TaskService;

    @Inject
    @Qualifier("remote")
    private lateinit var historyService: HistoryService;


    fun findOpenedByAssignee(assignee: String): List<TaskDto> {
        // taskService возвращает все открытые (not completed) таски
        return taskService.createTaskQuery().taskAssignee(assignee).list().map { TaskDto.fromCamundaTask(it) }
    }

    fun findClosedByAssignee(assignee: String): List<TaskDto> {
        // за completed тасками топаем в historyService
        return historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).list().map { TaskDto.fromCamundaHistoricTask(it) }
    }

    fun complete(taskInstanceId: Long, closeCodeId: Long): TaskDto {
        taskService.complete(taskInstanceId.toString());
        // #TODO: сохранять шифр закрытия в соотв. переменную процесса(задачи? закрываем ведь задачу)
        val task: Task = taskService.createTaskQuery().taskId(taskInstanceId.toString()).singleResult();
        return TaskDto.fromCamundaTask(task);
    }

    fun addComment(taskInstanceId: Long, comment: String): CommentDto{
        val task: Task = taskService.createTaskQuery().taskId(taskInstanceId.toString()).singleResult();
        val comment: Comment = taskService.createComment(taskInstanceId.toString(), task.processInstanceId, comment);
        return CommentDto.fromCamundaComment(comment);
    }


}