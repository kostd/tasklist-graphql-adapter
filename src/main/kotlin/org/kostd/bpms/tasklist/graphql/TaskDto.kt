package org.kostd.bpms.tasklist.graphql

import org.camunda.bpm.engine.history.HistoricTaskInstance
import org.camunda.bpm.engine.task.Task

/**
 * нужен, только чтобы отдать graphql-фреймворку состояние тасков camunda`ы
 * <p>
 * graphql-фреймворк считает полученный объект простым pojo и рефлексией достает нужные ему поля,
 * значения которых уже возвращает на клиент
 */
data class TaskDto(val id: String, val name: String, val desc: String?) {


    lateinit var processTypeName: String;
    var taskType: String? = null;
    var dueDate: String? = null;
    var address: String? = null;
    var addressComment: String? = null;
    lateinit var createDate: String;
    var closeDate: String? = null;
    var isClosed: Boolean = false;
    var latitude: String? = null;
    var longitude: String? = null;
    var comment: String = "";
    var scheduledDate: String? = null;
    var assignee: MutableList<String> = mutableListOf();

    class AttributeDto(val key: String, val value: String);
    var attrs: List<AttributeDto> = mutableListOf();


    companion object {
        fun fromCamundaTask(task: Task): TaskDto {
            var result: TaskDto = TaskDto(task.id, task.name, task.description);
            // #TODO: taskType, address, addressComment, latitude, longitude, пока нечем заполнять
            // #TODO: гибкие атрибуты положить в attrs, если они будут
            // #TODO: для получения каментов по таску надо вызывать отдельный метод TaskService`а
            result.processTypeName = task.processDefinitionId;
            result.dueDate = task.dueDate?.toString();
            // по идее, createTime, возвращаемый camunda`ой, не должен быть null
            result.createDate = task.createTime!!.toString();
            result.scheduledDate = task.followUpDate?.toString();
            if (task.assignee != null) {
                result.assignee.add(task.assignee);
            }
            return result;
        }

        fun fromCamundaHistoricTask(task: HistoricTaskInstance): TaskDto{
            var result: TaskDto = TaskDto(task.id, task.name, task.description);
            result.processTypeName = task.processDefinitionId;
            result.dueDate = task.dueDate?.toString();
            // #TODO: startTime это не то же, что createTime. Непонятно, почему для исторических тасков
            // не хранится время создания.
            result.createDate = task.startTime!!.toString();
            result.closeDate = task.endTime!!.toString();
            result.isClosed = true;
            result.scheduledDate = task.followUpDate?.toString();
            if (task.assignee != null) {
                result.assignee.add(task.assignee);
            }
            return result;
        }
    }

}