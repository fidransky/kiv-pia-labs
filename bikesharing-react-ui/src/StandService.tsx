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
export function retrieveStands() {
	return standsApi.retrieveStands()
		.then((stands) => {
			// map DTOs from API to domain objects
			return stands.map((standDTO) => {
				return new Stand(standDTO);
			});
		});
}
