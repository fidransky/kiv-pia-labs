import { Configuration, StandDTO, StandsApi } from 'bikesharing-rest-typescript-client';
import React from 'react';
import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet';

const basePath = 'http://localhost:8080';
const configuration = new Configuration({ basePath });
const standsApi = new StandsApi(configuration);

type Props = {
	zoomLevel?: number,
}

export default function Home({ zoomLevel = 13 }: Props) {
	const [ stands, setStands ] = React.useState<StandDTO[]>([]);

	React.useEffect(() => {
		standsApi.retrieveStands()
				.then((stands) => {
						setStands(stands);
				});
	}, []);

	return (
		<MapContainer center={[49.7269708, 13.3516872]} zoom={zoomLevel}>
			<TileLayer
				attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
				url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
			/>
			{stands.map((stand) => {
				return (
					<Marker key={stand.id} position={[
						stand.location.longitude,
						stand.location.latitude,
					]}>
						<Popup>
							{stand.name}
						</Popup>
					</Marker>
				);
			})}
		</MapContainer>
	);
}