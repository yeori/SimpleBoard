package collections;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCollections {

	@Before
	public void setUp() throws Exception {
		
		ArrayList<String> myList = new ArrayList<String>();
		myList.add("ddd");
		Semaphore sem = new Semaphore(1);
		
		ThreadA ta = new ThreadA(sem, myList); // myList.add(...(l
		ThreadB tb = new ThreadB(sem, myList); //myList.remove(...);
		
		ta.start();
		tb.start();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		List<String> list = new ArrayList<String>();
	}

	
	public static class MyClass {
		private List<String> list ;
		
		public MyClass ( List<String> list ) {
			this.list = list;
		}
	}
	
	public static class ThreadA extends Thread {
		public ThreadA(Semaphore sem, ArrayList<String> myList) {
			// TODO Auto-generated constructor stub
			this.sem = sem;
			this.list = myList;
		}
		private Semaphore sem = null;
		private Lock lock = null;
		
		private List<String> list ;
		@Override
		public void run() {
			try {
				sem.acquire();
				if ( list.size() > 0 ) {
					list.remove(0);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				sem.release();
			}
//			synchronized (list) {
//				if ( list.size() > 0 ) {
//				list.remove(0);
//				}
//			}
			
		}
		
	}
	
	public static class ThreadB extends Thread {
		private List<String> list ;
		// TODO Auto-generated constructor stub
		private Semaphore sem;
					
		public ThreadB(Semaphore sem, List<String> myList) {
			// TODO Auto-generated constructor stub
			this.list = myList;
			this.sem = sem;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				sem.acquire();
				if ( list.size() > 0 ) {
					list.remove(0);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				sem.release();
			}
			
		}
		
	}
}
