import { LocationDTO, StandDTO } from 'bikesharing-rest-typescript-client';

export class Stand {
	public id?: string;
	public name: string;
	public location: Location;

	constructor(stand: StandDTO) {
		this.id = stand.id;
		this.name = stand.name;
		this.location = new Location(stand.location);
	}
}

export class Location {
	public longitude: number;
	public latitude: number;

	constructor(location: LocationDTO) {
		this.longitude = location.longitude;
		this.latitude = location.latitude;
	}
}
