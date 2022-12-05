import { test, expect } from '@playwright/test';

test('homepage has title and links to intro page', async ({ page }) => {
  await page.goto('http://localhost:8080/pia-labs/spring/');
  await expect(page).toHaveTitle('Please sign in');

  // Log in
  await page.getByPlaceholder('Username').fill('john.doe');
  await page.getByPlaceholder('Password').fill('password');
  await page.getByRole('button', { name: 'Sign in' }).click();
  await expect(page).toHaveTitle('KIV/PIA Labs - Chat rooms');

  const chatRoomName = 'test-' + Date.now();

  // Search test chat room
  await page.getByPlaceholder('Search chat room by name').fill(chatRoomName);
  await page.getByPlaceholder('Search chat room by name').press('Enter');
  await expect(page.getByRole('alert')).toBeVisible();
  await expect(page.getByRole('alert')).toContainText('No chat rooms were found for query ' + chatRoomName + '.');

  // Create test chat room
  await page.getByRole('link', { name: 'Create a new chat room' }).click();
  await page.locator('#roomName').fill(chatRoomName);
  await page.getByRole('button', { name: 'Create' }).click();
  await expect(page.locator('.navbar-brand')).toContainText(chatRoomName + ' chat room');

  // Search test chat room again
  await page.locator('.navbar-brand').click();
  await page.getByPlaceholder('Search chat room by name').fill(chatRoomName);
  await page.getByPlaceholder('Search chat room by name').press('Enter');
  await expect(page.getByRole('alert')).not.toBeVisible();
});
