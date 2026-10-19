# Distributed System Simulation

This project simulates a distributed job-processing system using separate applications that communicate over sockets.

The system includes:

- A master that receives jobs from clients and assigns them to slaves based on workload.
- Slave-A and Slave-B, optimized for different job types.
- Clients that submit jobs and receive completion notifications.

The project uses multiple threads, shared memory, and locking so jobs and status messages can be handled concurrently. 
Each step is printed to the console to demonstrate accurate communication throughout the program.