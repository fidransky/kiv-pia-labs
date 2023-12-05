import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:8080/');

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Bikesharing/);

  await page.getByRole('link', { name: 'Hello World' }).click();
  await page.getByPlaceholder('Username').fill('pavel.fidransky@yoso.fi');
  await page.getByPlaceholder('Password').fill('password');
  await page.getByRole('button', { name: 'Sign in' }).click();
  await page.getByText('pavel.fidransky@yoso.fi', { exact: true }).click();
  await page.getByRole('link', { name: 'Log out' }).click();
  await page.getByRole('button', { name: 'Log Out' }).click();
});