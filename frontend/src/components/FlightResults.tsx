import React from 'react';
import {Flight, Itinerary} from "../customTypes";


const FlightResults = ({ flights, onReturnToSearch, onShowDetails, onSort}: any) => {

    const formatDuration = (duration: string): string => {
        const hoursMatch = duration.match(/(\d+)H/);
        const minutesMatch = duration.match(/(\d+)M/);
        const hours = hoursMatch ? hoursMatch[1].padStart(2, '0') : '00';
        const minutes = minutesMatch ? minutesMatch[1].padStart(2, '0') : '00';
        return `${hours}h ${minutes}m`;
    };



    const handleChange = (e:  React.ChangeEvent<HTMLInputElement>) => {
        const checked = e.target.checked;
        const checkedName = e.target.name;
        onSort(checkedName, checked)
    };

    return (
        <div>
            <div className="pagination-container">
                <button onClick={onReturnToSearch}> Return to Search</button>
                <p><input
                    type="checkbox"
                    name="price"
                    onChange={(e) => handleChange(e)}
                    className="checkbox"
                /> Sort by price</p>
                <p><input
                    type="checkbox"
                    name="duration"
                    onChange={(e) => handleChange(e)}
                    className="checkbox"
                /> Sort by duration</p>
            </div>
            {flights ? flights.map((flight: Flight) => (
                <div key={flight.id} className="flight-card">
                    <div className="left-container">
                        {flight.itineraries.map((itinerary: Itinerary, index: Number) => (
                            <div key={index.toString()} className="itinerary">
                                <div className="itinerary-info">
                                    <p>
                                        {new Date(itinerary.departureTime).toLocaleTimeString([], {
                                            hour: '2-digit',
                                            minute: '2-digit'
                                        })} {' '}
                                        - {new Date(itinerary.arrivalTime).toLocaleTimeString([], {
                                        hour: '2-digit',
                                        minute: '2-digit'
                                    })}
                                    </p>
                                    <p>
                                        {`${itinerary.departureAirportCity} ${itinerary.departureAirport} - ${itinerary.arrivalAirportCity} ${itinerary.arrivalAirport}`}
                                    </p>
                                    <p>
                                        {`${itinerary.segments[0].carrierName} (${itinerary.segments[0].carrierCode})
                                        ${(itinerary.segments[0].carrierCodeOperating != '') ? "operating:" : ""}
                                        ${itinerary.segments[0].carrierNameOperating}`}
                                    </p>
                                </div>
                                <div className="itinerary-info">
                                    <p>
                                        {formatDuration(itinerary.duration)}
                                        {itinerary.segments.length > 1 && ` (${itinerary.segments.length - 1} stop${itinerary.segments.length > 2 ? 's' : ''})`}
                                    </p>
                                    <p>
                                        {itinerary.segments.length > 1 && `${formatDuration(itinerary.stopTime)} in ${itinerary.segments[0].arrivalAirport} (${itinerary.segments[0].arrivalAirportCity})`}
                                    </p>
                                </div>

                            </div>
                        ))}
                    </div>
                    <div className="right-container">
                        <div className="price-breakdown">
                            <p>{flight.price.currency} ${flight.price.total.toFixed(2)} total</p>
                            <p>{flight.price.currency} ${(flight.price.total / flight.travelers).toFixed(2)} </p>
                            <p>Per traveler</p>
                        </div>
                        <button onClick={() => onShowDetails(flight)}> Expand Details</button>
                    </div>
                </div>
            )) : ""}
        </div>
    );
};

export default FlightResults;
