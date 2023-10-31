import { useSearchParams } from 'react-router-dom';

export default function Hello() {
	const [ searchParams ] = useSearchParams();

	return <>
		<h2>Hello World from {searchParams.get('from') ?? 'me'}</h2>

		<p>&hellip;</p>
	</>;
}