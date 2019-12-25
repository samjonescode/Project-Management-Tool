package com.ppmtool.services;

import com.ppmtool.domain.Backlog;
import com.ppmtool.domain.Project;
import com.ppmtool.domain.User;
import com.ppmtool.exceptions.ProjectIdException;
import com.ppmtool.repositories.BacklogRepository;
import com.ppmtool.repositories.ProjectRepository;
import com.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdateProject(Project project, String username) {
		try {

			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			String projectIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifier);

			if(project.getId()==null){
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(projectIdentifier);
			}

			if(project.getId()!=null){
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
			}
			return projectRepository.save(project);
		} catch (Exception e){
			throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase()+"' already exists.");
		}
	}

	public Project findProjectByIdentifier(String projectId){
		Project project = projectRepository.findByProjectIdentifier(projectId);
		if (project == null) {
			throw new ProjectIdException(("Project does '" + projectId +"'not exist."));
		}
		return projectRepository.findByProjectIdentifier(projectId.toUpperCase());
	}

	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectId){
		Project project = projectRepository.findByProjectIdentifier(projectId);

		if(project == null){
			throw new ProjectIdException(("Cannot delete project with ID'"+projectId +"'. This project does not exist."));
		}

		projectRepository.delete(project);
	}
}
