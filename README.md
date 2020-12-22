# Worderize
This is the private repository for development and implementation of the Team-14's Senior Project. It is for back-end part of the project which will be developed bu using Scala language and Akka Toolbox.

### Coding Standard & Style Guideline: https://docs.scala-lang.org/style/

## Team-14 Group Members: 
- Seyit Bahadır Çetin
- Ufuk Çağatay Doğan
- Berk Önder
- Şevki Armağan Oğuz

## What is Worderize
Worderize is an on-demand location work order distribution system that is reachable on mobile platforms (iOS and Android) and also web platform. A work order is a task or job that can be scheduled and assigned to someone by a customer. With Worderize we are aiming to achieve that by creating work orders asynchronously, manage work orders easily, and tracking the work orders precisely. Users can create work orders for their needs in various fields and they can track their work order process. Workers can set availability status whether they are assigned to a work order or not. Supplier simultaneously updates their supply status. According to the job description, with the matching algorithm, related and the closest worker will be assigned to the job to increase the time efficiency. Communication between the services and platforms will be provided via RESTful API.

## Architecture Design and Development
### Robust Back-end is Main Purpose
In the development of the back-end, we are going to use Akka actor-based model with Docker containers which will provide us to failover on our system. We will try to build the most consistent, concurrent, and rapid back-end as much as we can by using Scala language and Akka toolbox. 

### Context Diagram
![worderize-diagram](https://user-images.githubusercontent.com/34353055/102684895-77ece300-41ed-11eb-9082-e087ed09bac8.png)

### High-Level Design Constraints

#### Scala and Akka
We need to stick with these concepts in order to achieve the main advantage of our project, which is an actor-based and event-driven structure with the use of Akka toolbox, we can implement this intention. Also, resilient elastic distributed real-time transaction processing which Akka offers makes a huge difference in the process of creating distributed, concurrent, fault-tolerant, and scalable application. In this process, Scala and Akka are highly integrated and working efficiently together. Therefore, we can also keep Scala as a constraint in the implementation of the project.    

#### Docker Containers
To build distributed architecture, we need to take Docker containers as a constraint in the project. This implementation will ease our work in terms of time and effort.

### Development
In the first part of the project implementation, we will focus on the User Experience and well-designed User Interfaces regarding extensibility, easy to use, quick, and responsive front-end development. Afterwards, building a powerful and highly capable back-end which is fully integrated with UI will take a place. Considering our deadline at the end of the semester, creating a beautiful looking UI for Android devices and the web page will be presented. iOS implementation will take a part later on the schedule.
Note: iOS and Android application development learning process take lots of time regarding our experiences and our education life.
