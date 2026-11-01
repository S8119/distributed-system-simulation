# Distributed System Simulation
This project simulates a distributed job-processing system using separate applications that communicate over sockets.


The system includes:

- A master that receives jobs from clients and assigns them to slaves based on workload.
- Slave-A and Slave-B, optimized for different job types.
- Clients that submit jobs and receive completion notifications.

The project uses multiple threads, shared memory, and locking so jobs and status messages can be handled concurrently.
Each step is printed to the console to demonstrate accurate communication throughout the program.

Notes:
1 Master Program - needs threads for constantly checking for new jobs and sending them to the slaves
Slave program - 2 instances
- waits at better job for 2 seconds and other for 10 seconds 
Job types A and B (coordinated better at specific jobs Slave A and Slave B respectivley )
- Jobs are not a class just a string with either A or B and name (Ex: "Job 75: A")

Clients 
- gets the jobs from user and assigns IDs 
- ability to send jobs to server 
- get back messages from the server that says done
- informs the user the job is done

Threads (ListenerThread and TransmitterThread Thread Classes)
- an object in it of itself 
- create ones for different components 
- Master has listening to clients, listening to slaves, transmitting jobs to slaves, transmitting jobs to clients
- Slaves have processing job threads - part of the job process is to get it from the master and send it back 
- Clients receive user input, transmit jobs to master, listen from master, transmit to user 
