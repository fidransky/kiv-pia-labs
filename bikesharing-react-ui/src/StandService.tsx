import { Configuration, StandsApi } from 'bikesharing-rest-typescript-client';
import { Stand } from './types';

const basePath = 'http://localhost:8080';
const configuration = new Configuration({ basePath });
const standsApi = new StandsApi(configuration);

/**
 * Retrieves all Stands currently in the system.
 * 
 * @returns All stands
 */
export async function retrieveStands() {
	const stands = await standsApi.retrieveStands();

	// map DTOs from API to domain objects
	return stands.map((standDTO) => new Stand(standDTO));
}
