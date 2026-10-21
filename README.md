### Project Name

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

Threads (ListenerThread and TrasmitterThread Thread Classes)
- an object in it of itself 
- create ones for different components 
- Master has listening to clients, listening to slaves, trasmitting jobs to slaves, trasmitting jobs to clients
- Slaves have processing job threads - part of the job process is to get it from the master and send it back 
- Clients receive user input, trasmit jobs to master, listen from master, trasmit to user 




### Screenshots

Put screenshots here

#### Links

- [name](http://url)