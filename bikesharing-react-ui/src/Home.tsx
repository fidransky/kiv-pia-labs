import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet';
import useStands from './useStands';

type Props = {
	zoomLevel?: number,
}

export default function Home({ zoomLevel = 13 }: Props) {
	const stands = useStands();

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