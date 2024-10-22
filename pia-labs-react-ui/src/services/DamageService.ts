import { Configuration, DamageApi } from 'pia-labs-typescript-client';
import { Damage } from '../types';

const basePath = 'http://localhost:8080'
const configuration = new Configuration({ basePath })
const damageApi = new DamageApi(configuration)

export async function retrieveDamage(): Promise<Damage[]> {
	const damages = await damageApi.retrieveDamage();

	return damages.map((damage) => {
		return {
			id: damage.id!,
			description: damage.description,
		}
	})
}
