package cn.synchronizedDemo;

//: concurrency/PipedIO.java
// Using pipes for inter-task I/O
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static net.mindview.util.Print.*;

class Sender implements Runnable {
  private Random rand = new Random(47);
 // private PipedWriter out = new PipedWriter();
  //public PipedWriter getPipedWriter() { return out; }
  private LinkedBlockingQueue queue = new LinkedBlockingQueue();
  public LinkedBlockingQueue getLinkedBlockingQueue(){return queue; }
  
  public void run() {
    try {
      while(true)
        for(char c = 'A'; c <= 'z'; c++) {
        	queue.put(c);
          TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
        }
    } catch(Exception e) {
      print(e + " Sender write exception");
    }
  }
}

class Receiver implements Runnable {
  //private PipedReader in;
	private LinkedBlockingQueue queue;
  public Receiver(Sender sender) throws IOException {
    //in = new PipedReader(sender.getPipedWriter());
	  queue = sender.getLinkedBlockingQueue();
  }
  public void run() {
    try {
      while(true) {
        // Blocks until characters are there:
        printnb("Read: " + (char)queue.take() + ", ");
      }
    } catch(Exception e) {
      print(e + " Receiver read exception");
    }
  }
}

public class PipedIO {
  public static void main(String[] args) throws Exception {
    Sender sender = new Sender();
    Receiver receiver = new Receiver(sender);
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(sender);
    exec.execute(receiver);
    TimeUnit.SECONDS.sleep(4);
    exec.shutdownNow();
  }
} /* Output: (65% match)
Read: A, Read: B, Read: C, Read: D, Read: E, Read: F, Read: G, Read: H, Read: I, Read: J, Read: K, Read: L, Read: M, java.lang.InterruptedException: sleep interrupted Sender sleep interrupted
java.io.InterruptedIOException Receiver read exception
*///:~
