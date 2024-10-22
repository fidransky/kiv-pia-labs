import { Configuration, DamageApi, DamageDTO } from 'pia-labs-typescript-client';
import React from 'react';

const basePath = 'http://localhost:8080'
const configuration = new Configuration({ basePath })
const damageApi = new DamageApi(configuration)

function Home() {
	const [ damages, setDamages ] = React.useState<DamageDTO[]>([])

	// ...

	React.useEffect(() => {
		damageApi.retrieveDamage()
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
