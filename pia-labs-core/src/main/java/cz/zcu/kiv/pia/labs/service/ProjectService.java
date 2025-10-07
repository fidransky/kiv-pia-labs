package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;

import java.util.Locale;

public interface ProjectService {
    /**
     * Creates a new project for the current user
     * @param targetLanguage target language for translation
     * @param sourceFile source file to translate
     * @return newly created project
     */
    Project createProject(Locale targetLanguage, byte[] sourceFile);

    // other service methods here
}
