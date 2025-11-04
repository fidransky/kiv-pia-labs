import type { CreateProjectRequest } from 'pia-labs-typescript-client';
import React, { useMemo, useState } from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { fileToBase64 } from '../utils/FileUtils';

interface FormData {
  languageCode: string;
  file: File | null;
}

interface FormErrors {
  languageCode?: string;
  file?: string;
}

interface TranslationProjectFormProps {
  onSubmit: (data: CreateProjectRequest) => Promise<void>;
  isLoading?: boolean;
}

export default function TranslationProjectForm({ onSubmit, isLoading = false }: TranslationProjectFormProps) {
  const languageOptions = useMemo(() => {
    const languageCodes = ['en', 'es', 'fr', 'de', 'it', 'pt', 'ru', 'zh', 'ja', 'ko'];
    const displayNames = new Intl.DisplayNames([navigator.language], { type: 'language' });
    
    return languageCodes.map(code => ({
      value: code.toUpperCase(),
      label: displayNames.of(code),
    }));
  }, []);

  const [formData, setFormData] = useState<FormData>({
    languageCode: 'DE',
    file: null,
  });

  const [errors, setErrors] = useState<FormErrors>({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const validateForm = (): boolean => {
    const newErrors: FormErrors = {};

    if (!formData.languageCode || formData.languageCode.length !== 2) {
      newErrors.languageCode = 'Language code must be 2 characters';
    }

    if (!formData.file) {
      newErrors.file = 'Please select a file to upload';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    setIsSubmitting(true);

    try {
      const base64 = await fileToBase64(formData.file!);

      const submitData: CreateProjectRequest = {
        languageCode: formData.languageCode,
        originalFile: base64 as unknown as Blob, // Workaround for issue with Blob in generated client
      };

      await onSubmit(submitData);

      // Reset form on success
      setFormData({
        languageCode: 'DE',
        file: null,
      });
      setErrors({});

      // Reset file input
      const fileInput = document.getElementById('sourceFile') as HTMLInputElement;
      if (fileInput) {
        fileInput.value = '';
      }

    } catch (error) {
      console.error('Failed to create project:', error);

    } finally {
      setIsSubmitting(false);
    }
  }

  function handleFileChange(e: React.ChangeEvent<HTMLInputElement>) {
    const file = e.target.files?.[0] || null;
    setFormData(prev => ({ ...prev, file }));
  }

  return (
    <Card className="mb-4">
      <Card.Header>
        <h5 className="card-title mb-0">
          <i className="bi bi-plus-circle me-2"></i>
          Create New Translation Project
        </h5>
      </Card.Header>

      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <div className="row">
            <div className="col-md-6">
              <Form.Group className="mb-3">
                <Form.Label htmlFor="targetLanguage">Target Language</Form.Label>

                <Form.Select
                  id="targetLanguage"
                  value={formData.languageCode}
                  onChange={(e) => setFormData(prev => ({ ...prev, languageCode: e.target.value }))}
                  disabled={isLoading || isSubmitting}
                  isInvalid={!!errors.languageCode}
                  required
                >
                  <option value="">Select target language&hellip;</option>
                  {languageOptions.map(option => (
                    <option key={option.value} value={option.value}>
                      {option.label}
                    </option>
                  ))}
                </Form.Select>

                <Form.Control.Feedback type="invalid">
                  {errors.languageCode}
                </Form.Control.Feedback>

                <Form.Text className="text-muted">
                  Choose the language you want your document translated to.
                </Form.Text>
              </Form.Group>
            </div>

            <div className="col-md-6">
              <Form.Group className="mb-3">
                <Form.Label htmlFor="sourceFile">Source File</Form.Label>

                <Form.Control
                  id="sourceFile"
                  type="file"
                  onChange={handleFileChange}
                  disabled={isLoading || isSubmitting}
                  isInvalid={!!errors.file}
                  required
                  accept=".txt,.doc,.docx,.pdf"
                />

                <Form.Control.Feedback type="invalid">
                  {errors.file}
                </Form.Control.Feedback>

                <Form.Text className="text-muted">
                  Upload the document you want to translate (max 10MB).
                </Form.Text>
              </Form.Group>
            </div>
          </div>

          <Button 
            type="submit" 
            variant="primary"
            disabled={isLoading || isSubmitting}
          >
            <i className="bi bi-plus-circle me-2"></i>
            {isSubmitting ? 'Creating...' : 'Create Project'}
          </Button>

          <Button 
            type="reset" 
            variant="outline-secondary"
            className="ms-2"
            disabled={isLoading || isSubmitting}
            onClick={() => {
              setFormData({ languageCode: 'DE', file: null });
              setErrors({});
              const fileInput = document.getElementById('sourceFile') as HTMLInputElement;
              if (fileInput) fileInput.value = '';
            }}
          >
            <i className="bi bi-arrow-clockwise me-2"></i>
            Reset
          </Button>
        </Form>
      </Card.Body>
    </Card>
  );
};
