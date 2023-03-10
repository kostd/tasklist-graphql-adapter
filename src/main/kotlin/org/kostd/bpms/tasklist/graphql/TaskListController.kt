package org.kostd.bpms.tasklist.graphql

import com.google.common.base.Preconditions
import org.kostd.bpms.tasklist.rest.CamundaTaskClient
import org.kostd.bpms.tasklistgraphqladapter.auth.CurrentPrincipal
import org.springframework.graphql.data.method.annotation.Argument
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
    fun myClosedTasks(@Argument day: String): List<TaskDto> {
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

    @MutationMapping
    fun addTaskComment(@Argument taskInstanceId: String, @Argument text: String, @Argument important: Boolean): CommentDto {
        return camundaTaskClient.addComment(taskInstanceId, text);
    }

    @MutationMapping
    fun completeTask(@Argument taskInstanceId: String, @Argument closeCodeId: Long): TaskDto {
        return camundaTaskClient.complete(taskInstanceId, closeCodeId);
    }

    @MutationMapping
    fun readNotification(notificationId: Long): String {
        // #TODO:
        throw NotImplementedError();
        return "";
    }
}

