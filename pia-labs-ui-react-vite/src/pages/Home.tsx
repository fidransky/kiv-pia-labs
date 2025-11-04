import type { CreateProjectRequest } from 'pia-labs-typescript-client';
import { useState } from 'react';
import { Alert } from 'react-bootstrap';
import TranslationProjectForm from '../components/TranslationProjectForm';
import { translationProjectService } from '../services/TranslationProjectService';

export default function Home() {
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  async function handleCreateProject(projectData: CreateProjectRequest) {
    setIsLoading(true);
    setSuccessMessage('');
    setErrorMessage('');

    try {
      const result = await translationProjectService.createProject(projectData);
      setSuccessMessage(`Project created successfully with ID: ${result.id}`);
      console.log('Project created:', result);

    } catch (error) {
      setErrorMessage(error instanceof Error ? error.message : 'Failed to create project');

    } finally {
      setIsLoading(false);
    }
  }

  return <>
    <title>Projects - KIV/PIA Labs</title>

    <h1 className="mb-4">Translation Projects</h1>

    {successMessage && (
      <Alert variant="success" className="mb-4">
        {successMessage}
      </Alert>
    )}

    {errorMessage && (
      <Alert variant="danger" className="mb-4">
        {errorMessage}
      </Alert>
    )}

    <TranslationProjectForm 
      onSubmit={handleCreateProject}
      isLoading={isLoading}
    />
  </>;
}
