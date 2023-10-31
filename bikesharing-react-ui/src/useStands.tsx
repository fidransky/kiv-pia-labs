import React from 'react';
import { retrieveStands } from './StandService';
import { Stand } from './types';

export default function useStands() {
	const [ stands, setStands ] = React.useState<Stand[]>([]);

	/**
	 * Loads all Stands currently in the system.
	 */
	React.useEffect(() => {
		// TODO: error handling
		retrieveStands()
			.then(setStands);
	}, []);

	return stands;
}
