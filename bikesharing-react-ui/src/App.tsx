import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Configuration, StandsApi, StandDTO } from 'bikesharing-rest-typescript-client';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';

const basePath = 'http://localhost:8080';
const configuration = new Configuration({ basePath });
const standsApi = new StandsApi(configuration);

// this component is not used anymore
function App() {
  const [ stands, setStands ] = React.useState<StandDTO[]>([]);

  React.useEffect(() => {
    standsApi.retrieveStands()
        .then((stands) => {
            setStands(stands);
        });
  }, []);

  return (
    <MapContainer center={[49.7269708, 13.3516872]} zoom={13}>
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

export default App;
