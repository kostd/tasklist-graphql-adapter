package org.kostd.bpms.tasklist.rest

import com.google.common.base.Preconditions
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.task.Comment
import org.camunda.bpm.engine.task.Task
import org.camunda.bpm.engine.task.TaskQuery
import org.kostd.bpms.tasklist.graphql.CommentDto
import org.kostd.bpms.tasklist.graphql.TaskDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.lang.RuntimeException
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
        // #TODO: неожиданно оказалось, что HistoricTaskInstanceQuery не реализованы для remote в camunda-platfrom7-rest-client
        // Если хочешь это, сделать не сложно, но надо форкать или делать PR. На этапе showcase преждевременно, пока попробуем
        // обойтись без закрытых
        return historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).list().map { TaskDto.fromCamundaHistoricTask(it) }
    }

    private fun findTask(taskInstanceId: String): Task{
        var taskQuery: TaskQuery = taskService.createTaskQuery().taskId(taskInstanceId);
        if (taskQuery.count() == 0L){
            throw RuntimeException("Задача с  id = $taskInstanceId не найдена");
        }
        return taskQuery.singleResult();
    }

    fun complete(taskInstanceId: String, closeCodeId: Long): TaskDto {
        // сначала поищем таск, чтобы убедиться, что он существует. Иначе некрасиво (непонятно) упадем в #complete
        val task: Task = findTask(taskInstanceId);
        taskService.complete(taskInstanceId);
        // #TODO: сохранять шифр закрытия в соотв. переменную процесса(задачи? закрываем ведь задачу)
        return TaskDto.fromCamundaTask(task);
    }

    fun addComment(taskInstanceId: String, comment: String): CommentDto{
        val task: Task = findTask(taskInstanceId);
        // #TODO: и тут not implemented, чтож такое то :/ Надо было посмотреть исходники, прежде чем брать этот rest-client
        val result: Comment = taskService.createComment(taskInstanceId, task.processInstanceId, comment);
        return CommentDto.fromCamundaComment(result);
    }


}