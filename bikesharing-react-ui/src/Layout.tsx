import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Link, Outlet } from 'react-router-dom';

export default function Layout() {
	return <>
		<header>
			<Link to='/' style={{ color: 'inherit', textDecoration: 'none' }}>
				<img src={logo} alt="" style={{ float: 'left', height: '3rem', }}/>
				<h1>Bikesharing</h1>
			</Link>
		</header>

		<Outlet/>

		<footer style={{ padding: '0.5rem' }}>
			&copy; KIV/PIA Bikesharing
		</footer>
	</>;
}
