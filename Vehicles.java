/*
 * Answer 1
 */
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Vehicles extends Thread {
	
	private int id;
	private boolean direction; // if true direction is Right, if false direction is Left
	
	// counters:
	static volatile int numRight = 0;
	static volatile int numLeft = 0;

	// Semaphores:
	// max number of how many more vehicles that have entered the tunnel in one direction than the other at any time
	private static Semaphore limitRight = new Semaphore(5);
	private static Semaphore limitLeft = new Semaphore(5);
	// capacity of the tunnel, at any time there are at most 4 vehicles in the tunnel
	private static Semaphore capacity = new Semaphore(4);
	// at any time only vehicles traveling in the same direction could enter; the first of its direction needs to get the lock, last return
	private static Semaphore lock = new Semaphore(1);
	// mutex that protects counters
	private static Semaphore mutexRight = new Semaphore(1);
	private static Semaphore mutexLeft = new Semaphore(1);
	
	public Vehicles (int i) {
		id = i;
		Random rnd = new Random();
		direction = rnd.nextBoolean();		
	}
	
	public void travel() {
		try {
			sleep((int) Math.random() * 100);
		} catch (InterruptedException e1) {}
	}
	
	public void run() {
		if (direction) { // right
			
			// enter
			try {
				limitRight.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mutexRight.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Right: car" + id + " arrives");
			numRight++;
			if (numRight == 1)
				try {
					lock.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			mutexRight.release();
			
			// travel
			try {
				capacity.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Right: car " + id + " is traveling through the tunnel");
			travel();
			capacity.release();
			
			// leave
			try {
				mutexRight.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Right: car" + id + " leaves");
			numRight--;
			if (numRight == 0) lock.release();
			mutexRight.release();
			limitLeft.release();
		}
		
		else { // left
			
			// enter
			try {
				limitLeft.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mutexLeft.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Left: car" + id + " arrives");
			numLeft++;
			if (numLeft == 1)
				try {
					lock.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			mutexLeft.release();
			
			// travel
			try {
				capacity.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Left: car " + id + " is traveling through the tunnel");
			travel();
			capacity.release();
			
			// leave
			try {
				mutexLeft.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Left: car" + id + " leaves");
			numLeft--;
			if (numLeft == 0) lock.release();
			mutexLeft.release();
			limitRight.release();			
		}
	}
	
	public static void main(String[] args) {
		final int N = 20;
		Vehicles[] p = new Vehicles[N];
		for (int i = 0; i < N; i++) {
			p[i] = new Vehicles(i);
			p[i].start();
		}
	}
}
