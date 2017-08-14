package org.mudpot

import java.util.concurrent.CyclicBarrier

import scala.collection.mutable
import scala.io.StdIn
import scalaz.stream.Process.Emit
import scalaz.{\/-, \/, Catchable}
import scalaz.concurrent.Task
import scalaz.stream._
import scalaz.Scalaz._


object GuessByStream {
  def puts(s: String): Task[Unit] = Task.delay {
    println(s)
  }

  lazy val gets: Task[String] = Task {
    StdIn.readLine()
  }

  def main(args: Array[String]) {
    implicit val listCatchable: Catchable[List] = new Catchable[List] {
      override def fail[A](err: Throwable): List[A] = List()

      override def attempt[A](f: List[A]): List[Throwable \/ A] = List(\/-(f.head))
    }

    val p1: Process[List, Int] = Process.range(0, 7)
    val p = p1.filter(_>3).flatMap(s => Process.emitAll(List(s,s-1))).zip(Process.emit("!").repeat).scan("!"){
      (x,y) => x + y._1 + y._2
    }

    //val p: scalaz.stream.Process[Nothing,Int] = Emit(mutable.WrappedArray(1, 2, 3, 4, 5))
    //val p: Process[Task, Int] = Process.eval(Task[Int] {throw new UnsupportedOperationException})
    println(p.run)
    println(p.runLast)
    println(p.runLog)


    val l = Process.emitAll(List(1,2,3,4,5))
    val r = Process.emitAll(List("a", "b", "c", "d", "e"))

    val w = wye.dynamic((x:Int) => if(x >= 3) wye.Request.R else wye.Request.L, (y:String) => wye.Request.R)

    println(l.wye(r)(w).runLog.run)

    val x = scalaz.stream.io.stdOutLines




    /*val rand = Random.nextInt(10)
    println(rand)
    val lines = Process repeatEval gets

    val guess = lines flatMap { line =>
      if(line == rand.toString){
        println("eq")
        Process.halt
      } else {
        Process eval puts(line)
      }
    }
*/
  }

  def signal():Unit = {
    val signal = async.signalUnset[Boolean]
    val signalChanges: Process[Task, Boolean] = signal.discrete

    val gate = new CyclicBarrier(3)
    val t1 = new Thread(new Runnable {
      override def run(): Unit = {
        gate.await()
        signal.set(true).run
        signal.set(true).run
        signal.set(false).run
      }
    })

    val t2 = new Thread(new Runnable {
      override def run(): Unit = {
        gate.await()
        signalChanges.map(x => {
          println("" + x + " -> " + System.currentTimeMillis)
        }).run.run
      }
    })

    t1.start()
    t2.start()

    gate.await()
    println("start all")
  }

}
