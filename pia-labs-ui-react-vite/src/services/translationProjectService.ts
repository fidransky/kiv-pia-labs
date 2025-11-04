import type {
  CreateProjectRequest,
  CreateProjectResponse
} from 'pia-labs-typescript-client';
import {
  Configuration,
  ProjectsApi
} from 'pia-labs-typescript-client';

class TranslationProjectService {
  private projectsApi: ProjectsApi;

  constructor() {
    const basePath = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/v1';
    const config = new Configuration({ basePath });
    this.projectsApi = new ProjectsApi(config);
  }

  async createProject(createProjectRequest: CreateProjectRequest): Promise<CreateProjectResponse> {
    try {
      return this.projectsApi.createProject({ createProjectRequest });
    } catch (error) {
      console.error('Failed to create project:', error);

      if (error instanceof Error) {
        throw error;
      }
      throw new Error('Failed to create project. Please try again.');
    }
  }
}

export const translationProjectService = new TranslationProjectService();
