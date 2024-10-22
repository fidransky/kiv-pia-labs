import React from 'react';
import { retrieveDamage } from './services/DamageService';
import { Damage } from './types';

function Home() {
	const [ damages, setDamages ] = React.useState<Damage[]>([])

	// ...

	React.useEffect(() => {
		retrieveDamage()
			.then((damages) => {
					setDamages(damages)
			})
	}, [])

	return <ul>
		{damages.map((damage) => {
			return (
				<li key={damage.id}>
					{damage.description}
				</li>
			)
		})}
	</ul>
}

export default Home
