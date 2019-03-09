package com.example

object AsyncEcsServiceDiscovery extends App {
  EcsService.main(args)
  /*private[this] lazy val ecsClient = {
    val conf = ClientOverrideConfiguration.builder().retryPolicy(RetryPolicy.none()).build()
    EcsAsyncClient.builder().overrideConfiguration(conf).build()
  }

  private[this] def listTaskArns(ecsClient: EcsAsyncClient, cluster: String, serviceName: String, pageTaken: Option[String] = None, accumulator: Seq[String] = Seq.empty)
                                (implicit ec: ExecutionContext): Future[Seq[String]] =
  for {
    listTasksResponse <- toScala(
      ecsClient.listTasks(
        ListTasksRequest
          .builder()
          .cluster(cluster)
          .serviceName(serviceName)
          .nextToken(pageTaken.orNull)
          .desiredStatus(DesiredStatus.RUNNING)
          .build()
      )
    )
    accumulatedTasksArns = accumulator ++ listTasksResponse.taskArns().asScala

    taskArns <- listTasksResponse.nextToken() match {
      case null => Future.successful(accumulatedTasksArns)
      case nextPageToken => listTaskArns(ecsClient, cluster, serviceName, Some(nextPageToken), accumulatedTasksArns)
    }
  } yield taskArns*/
}
