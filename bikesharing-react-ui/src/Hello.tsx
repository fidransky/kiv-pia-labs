import { useSearchParams } from 'react-router-dom';

export default function Hello() {
	const [ searchParams ] = useSearchParams();

	const url = `http://localhost:8080/bikes/${crypto.randomUUID()}/location`;
	const sse = new EventSource(url);
	sse.addEventListener('open', console.log);
	sse.addEventListener('bikeLocation', (event) => {
		console.log(JSON.parse(event.data));
	});
	sse.addEventListener('close', console.log);

	function onClick() {
		fetch(url, {
			method: 'POST',
			body: JSON.stringify({
				longitude: 49.7479433,
				latitude: 13.3786114,
			}),
			headers: {
				'Content-Type': 'application/json',
			}
		});
	}

	return <>
		<h2>Hello World from {searchParams.get('from') ?? 'me'}</h2>

		<button onClick={onClick}>update bike location</button>
	</>;
}