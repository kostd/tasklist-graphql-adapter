package org.kostd.bpms.tasklist.graphql

import com.google.common.base.Preconditions
import org.kostd.bpms.tasklist.rest.CamundaTaskClient
import org.kostd.bpms.tasklistgraphqladapter.auth.CurrentPrincipal
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import javax.inject.Inject


@Controller
class TaskListController {
    @Inject
    lateinit var camundaTaskClient: CamundaTaskClient;

    @Inject
    lateinit var currentPrincipal: CurrentPrincipal;


    @QueryMapping
    fun myOpenedTasks(): List<TaskDto> {
        val userPrincipal: UserDetails = currentPrincipal.get();
        Preconditions.checkState(userPrincipal.username != null);
        // #TODO: paging (listPage)
        return camundaTaskClient.findOpenedByAssignee(userPrincipal.username);
    }

    @QueryMapping
    fun myClosedTasks(day: String): List<TaskDto> {
        val userPrincipal: UserDetails = currentPrincipal.get();
        Preconditions.checkState(userPrincipal.username != null);
        // #TODO: paging (listPage)
        return camundaTaskClient.findClosedByAssignee(userPrincipal.username);
    }

    @QueryMapping
    fun myNotifications(): List<Any> {
        // #TODO:
        throw NotImplementedError();
        return listOf();

    }

    @QueryMapping
    fun addTaskComment(taskInstanceId: Long, text: String, important: Boolean): CommentDto {
        return camundaTaskClient.addComment(taskInstanceId, text);
    }

    @QueryMapping
    fun completeTask(taskInstanceId: Long, closeCodeId: Long): TaskDto {
        return camundaTaskClient.complete(taskInstanceId, closeCodeId);
    }

    @MutationMapping
    fun readNotification(notificationId: Long): String {
        // #TODO:
        throw NotImplementedError();
        return "";
    }
}

