package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface ProjectService {
    /**
     * Creates a new project for the current user
     * @param targetLanguage target language for translation
     * @param sourceFile source file to translate
     * @return newly created project
     */
    Project createProject(Locale targetLanguage, byte[] sourceFile);

    /**
     * Fetches all projects, no matter their state
     *
     * @return all projects
     */
    List<Project> getAllProjects();

    /**
     * Fetches project by its ID.
     *
     * @param id unique ID of the project
     * @return project with the given ID
     * @throws NoSuchElementException if no project with the given ID exists
     */
    Project getProjectById(UUID id);

    /**
     * Marks the project with the given ID as completed.
     *
     * @param id unique ID of the project to be completed
     * @param translatedFile translated file to be stored with the project
     */
    void completeProject(UUID id, byte[] translatedFile);

    // other service methods here
}
