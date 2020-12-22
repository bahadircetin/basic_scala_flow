package com.worderize

import akka.NotUsed

/**
 * FlowShape
 * Outlet[List, WorkOrders]               FlowShape[String, WorkOrder]           FlowShape[ProductID, Supplier]
 *╔══════════════════════╗                ╔══════════════════════╗                ╔══════════════════════╗
 *║                      ║                ║                      ║                ║                      ║
 *║ Windesk API          ║                ║                      ║                ║                      ║
 *║                      ╠──┐          ┌──╣                      ╠──┐          ┌──╣ Check for Supplies   ╠──┐
 *║  Source              │  │─────────▶│  │  WorkOrder Creation │  │─────────▶│  │ if it is a product   │  │──┐
 *║     .fromIterator()  ╠──┘          └──╣                      ╠──┘          └──╣  order               ╠──┘  │
 *║                      ║                ║   Details of Order   ║                ║ Not implemented yet  ║     │
 *║                      ║                ║                      ║                ║                      ║     │
 *║                      ║                ║                      ║                ║                      ║     │
 *╚══════════════════════╝                ╚══════════════════════╝                ╚══════════════════════╝     │
                                                                                                              *│
                                                                                                              *│
                                                                                                              *│
                                                 *Inlet[Any]                   FlowShape[OrderType, Worker]    │
                                         *╔══════════════════════╗                ╔══════════════════════╗     │
                                         *║                      ║                ║                      ║     │
                                         *║Not implemented yet   ║                ║                      ║     │
                                         *║                     ┌╩─┐           ┌──╣    FilterAndMatch    ╠──┐  │
                                         *║      Sink.ignore    │  │◀──────────│  │      Algorithm      │  │◀─┘
                                         *║ If there are no     └╦─┘           └──╣                      ╠──┘
                                         *║ available workers    ║                ║ Not implemented yet  ║
                                         *║ Notification to      ║                ║                      ║
                                         *║ Windesk              ║                ║                      ║
                                         *╚══════════════════════╝                ╚═════════╦──╦═════════╝
                                                                                           *│  │
                                                                                           *└──┘
                                                       *FlowShape                             │
      *Inlet[Any]             [WorkOrder, (Worker, Status, Time)]                             │
*╔══════════════════════╗                ╔══════════════════════╗                             │
*║                      ║                ║                      ║                             │
*║                      ║                ║                      ║                             │
*║All process will be   ╠──┐          ┌──╣                      ╠──┐                          │
*║ sended to Windesk    │  │◀─────────│  │  WorkOrderProcess   │  │◀─────────────────────────┘
*║                      ╠──┘          └──╣                      ╠──┘
*║   Sink.              ║                ║                      ║
*║   foreach(_)         ║                ║Not implemented yet   ║
*║Not implemented yet   ║                ║                      ║
*╚══════════════════════╝                ╚══════════════════════╝
 */

object CreateWorkOrderFlow {
  import akka.actor.ActorSystem
  import akka.stream._
  import akka.stream.scaladsl._
  import scala.concurrent.Future
  import scala.util.Try
  import scala.concurrent.ExecutionContext.Implicits._

  // Implicit actor system
  implicit val system: ActorSystem = ActorSystem("Sys")

  // Implicit actor materializer
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import scala.io.StdIn._

  def main(args: Array[String]): Unit = {

    val workOrderFlow = GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val windeskSource: Outlet[String] = builder.add(Source.fromIterator(() => workOrders)).out
      val flowFileToWorkOrder: FlowShape[String, WorkOrder] = builder.add(csvToWorkOrderEvent)
      // val closeWorkOrder: FlowShape[WorkOrder, (String, Int, Int)] = builder.add(closeWorkOrder)
      val sink = Sink.foreach[(WorkOrder)] (println)

      /* For Logging Operations
      val loggedSourceWindesk = windeskSource.map { elem =>
        println(elem); elem
      }
      val loggedWorkOrder = flowFileToWorkOrder.map { elem =>
        println(elem); elem
      }*/

      // Flow of the Stream
      windeskSource ~> flowFileToWorkOrder ~> sink

      ClosedShape
    }
    RunnableGraph.fromGraph(workOrderFlow).run()
  }

  // Get the file and Iterate through items.
  val workOrders: Iterator[String] = io.Source.fromFile("src/main/scala/com/worderize/work_orders.txt", "utf-8")
    .getLines()

  // Flow for reading csv and mapping to the creation of WorkOrder objects
  val csvToWorkOrderEvent: Flow[String, WorkOrder, NotUsed] = Flow[String]
    .map(_.split(",").map(_.trim)) // Getting columns split by ","
    .map(stringArrayToWorkOrderEvent) // Convert an array of columns to a WorkOrderEvent

  /*val closeWorkOrder =
    println("Enter the closed Work Order ID : ")
    val orderId = readInt()
    Flow[WorkOrder]
      .filter(r => Try(r.orderId.toInt).getOrElse(r.orderId) == orderId)
      .drop(1)*/

  // String array to WorkOrderEvent meaning: creating the WorkOrder objects.
  def stringArrayToWorkOrderEvent(cols: Array[String]) = new WorkOrder(cols(0), cols(1), cols(2), cols(3), cols(4))

  // Class definition of WorkOrder
  case class WorkOrder(
                        orderId: String,
                        orderDescription: String,
                        contactInfo: String,
                        location: String,
                        customerInfo: String) {
    override def toString = s"${orderId}\n${orderDescription}\n${contactInfo}\n${location}\n${customerInfo}\n\n"
  }
}
