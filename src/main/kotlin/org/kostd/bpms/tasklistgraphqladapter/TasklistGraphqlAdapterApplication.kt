package org.kostd.bpms.tasklistgraphqladapter

import org.camunda.community.rest.EnableCamundaRestClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableCamundaRestClient
class TasklistGraphqlAdapterApplication

fun main(args: Array<String>) {
	runApplication<TasklistGraphqlAdapterApplication>(*args)
}
