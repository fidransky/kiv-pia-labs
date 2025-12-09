import { test, expect } from '@playwright/test';

test('Create a new translation project', async ({ page }) => {
  await logIn(page);

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/KIV\/PIA Labs/);

  await page.getByLabel('Target Language').selectOption('de');
  await page.getByRole('button', { name: 'Source File' }).click();
  await page.getByRole('button', { name: 'Source File' }).setInputFiles('test.pdf');
  await page.getByRole('button', { name: ' Create Project' }).click();

  // Expect a success alert with given text to be present
  await expect(page.getByRole('alert')).toBeVisible();
  await expect(page.getByRole('alert')).toContainClass('alert-success');
  await expect(page.getByRole('alert')).toContainText('Project created successfully');
});

async function logIn(page) {
  await page.goto('/login');
  await page.getByRole('textbox', { name: 'Username' }).click();
  await page.getByRole('textbox', { name: 'Username' }).fill('john.doe@example.com');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('password');
  await page.getByRole('button', { name: 'Sign in' }).click();
}
