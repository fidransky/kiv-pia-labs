import { useEffect } from 'react';
import { Client, StompSubscription } from '@stomp/stompjs';
import { useSearchParams } from 'react-router-dom';

type Location = Pick<GeolocationCoordinates, 'longitude' | 'latitude'>;

const bikeId = crypto.randomUUID();
const url = `http://localhost:8080/bikes/${bikeId}/location`;

const client = new Client();
client.brokerURL = 'ws://localhost:61614/ws';

export default function Hello() {
	const [ searchParams ] = useSearchParams();

	/**
	 * Watch client position and update bike location accordingly.
	 */
	useEffect(() => {
		// https://developer.mozilla.org/en-US/docs/Web/API/Geolocation_API/Using_the_Geolocation_API
		const watchID = navigator.geolocation.watchPosition((position) => {
			const location: Location = {
				longitude: position.coords.longitude,
				latitude: position.coords.latitude,
			};

			fetch(url, {
				method: 'POST',
				body: JSON.stringify(location),
				headers: {
					'Content-Type': 'application/json',
				}
			});
		});

		return () => {
			navigator.geolocation.clearWatch(watchID);
		};
	}, []);

	/**
	 * Stream bike location updates using SSE (server-sent events).
	 */
	useEffect(() => {
		const eventSource = new EventSource(url);
		eventSource.addEventListener('open', console.log);
		eventSource.addEventListener('bikeLocation', (event) => {
			console.log('server-sent event:', JSON.parse(event.data));
		});
		eventSource.addEventListener('close', console.log);

		return () => {
			eventSource.close();
		};
	}, []);

	/**
	 * Stream bike location updates using StompJS.
	 */
	useEffect(() => {
		let subscription: StompSubscription | undefined;

		client.onConnect = function onConnect(frame) {
			const destination = `/topic/kiv.pia.bikesharing.bikes.${bikeId}.location`;

			subscription = client.subscribe(destination, function onMessageReceived(payload) {
				const message = JSON.parse(payload.body);
				console.log('stomp message:', message);
			});
		};
		client.activate();

		return () => {
			if (subscription === undefined) return;

			subscription.unsubscribe();
		};
	}, []);

	return <>
		<h2>Hello World from {searchParams.get('from') ?? 'me'}</h2>

		<button onClick={() => {
			fetch(url, {
				method: 'POST',
				body: JSON.stringify({
					longitude: 13.3786114,
					latitude: 49.7479433,
				}),
				headers: {
					'Content-Type': 'application/json',
				}
			});
		}}>update bike location</button>
	</>;
}