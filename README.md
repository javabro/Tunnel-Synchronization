# Tunnel-Synchronization
A one-way tunnel shared by vehicles traveling in both directions.

# Description
There is a long windy tunnel through a remote mountain that needs to be repaired, necessitating the closure of all but a single lane, which must now be shared by vehicles traveling in both directions. If two vehicles going in opposite directions meet inside the tunnel, they will crash causing the whole tunnel to collapse and the end of the world as we know it. Furthermore, given the lack of good ventilation, the tunnel is safe to use as long as there are no more than N vehicles traveling inside the tunnel.  
__Design a synchronization protocol with the following properties__  
Once a vehicle enters the tunnel, it is guaranteed to get to the other side without crashing into a vehicle going the other way.  
There are never more than N=4 vehicles in the tunnel.  
A continuing stream of vehicles traveling in one direction should not starve vehicles going in the other direction.  
__Implementation__  
Use two types of Java threads representing vehicles going in the two directions, respectively. Explain the purpose from any semaphore/variables in your solution and its initialization.  
Show that your code works by having a set of threads (say 10 or 20) repeat the following forever:  
(1) pick a direction for travel  
(2) wait until it is safe to enter tunnel  
(3) travel inside tunnel for some amount of time  
(4) get out of the tunnel on the other end
