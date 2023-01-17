package org.kostd.bpms.tasklist.graphql

import org.camunda.bpm.engine.task.Comment

class CommentDto(val who: String, val text: String, val date: String, val taskId: String) {

    // #TODO: эти поля пока нечем заполнить. Потому что модель на фронтенде отличается от модели camunda
    var title: String = "";
    var type: String = "";
    var important: Boolean = false;


    companion object {
        fun fromCamundaComment(comment: Comment): CommentDto {
            return CommentDto(who = comment.userId, text = comment.fullMessage, date = comment.time!!.toString(), taskId = comment.taskId);
        }
    }
}