package com.ppmtool.services;

import com.ppmtool.domain.Backlog;
import com.ppmtool.domain.ProjectTask;
import com.ppmtool.repositories.BacklogRepository;
import com.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        //PT to be added to a specific project, so project cant be null
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        // need a backlog object to refer to
        projectTask.setBacklog(backlog);
        // have to set the bl to the pt
        Integer BacklogSequence = backlog.getPTSequence();
        // update the backlog sequence
//        Integer backlogSeq = ; //increment the sequence
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);

        //add sequence to task
        projectTask.setProjectSequence(projectIdentifier);
        projectTask.setProjectIdentifier(projectIdentifier);
        // set an initial priority & status when priority/status is null

        if(projectTask.getStatus()=="" || projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }
        if(projectTask.getPriority()==null){
            projectTask.setPriority(3); //low priority as default
        }

        if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBackLogById(String backlog_id) {

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}
