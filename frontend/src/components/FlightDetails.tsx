import React from 'react';
import {Itinerary, Segment, Fee} from "../customTypes";

const FlightDetails = ({ flight, onReturnToSearch, onReturnToResults }: any) => {

    const formatDuration = (duration: string): string => {
        const hoursMatch = duration.match(/(\d+)H/);
        const minutesMatch = duration.match(/(\d+)M/);
        const hours = hoursMatch ? hoursMatch[1].padStart(2, '0') : '00';
        const minutes = minutesMatch ? minutesMatch[1].padStart(2, '0') : '00';
        return `${hours}h ${minutes}m`;
    };

    return (
        <div>
            <button onClick={onReturnToSearch}> Return to Search</button>
            <button onClick={onReturnToResults}> Return to Results</button>
            {flight.itineraries.map((itinerary: Itinerary) => (
                <div key={flight.id} className="flight-card">
                    <div className="left-container">
                        {itinerary.segments.map((segment: Segment, index: number) =>
                            <div key={segment.id.toString()} className="segment">
                                <div className="segment-info">
                                    <h3> {"Segment " + (index + 1)}</h3>
                                    <h4 className='inline-text'>Departure:</h4>
                                    <p className="inline-text"> {segment.departureAirportCity} {segment.departureAirport}</p>
                                    <p>
                                        {new Date(segment.departureTime).toLocaleDateString([], {
                                            year: 'numeric',
                                            month: 'short',
                                            day: 'numeric'
                                        })} {new Date(segment.departureTime).toLocaleTimeString([], {
                                        hour: '2-digit',
                                        minute: '2-digit'
                                    })}
                                    </p>
                                    <h4 className="inline-text"> Arrival: </h4>
                                    <p className="inline-text"> {segment.arrivalAirportCity} {segment.arrivalAirport} </p>
                                    <p>
                                        {new Date(segment.arrivalTime).toLocaleDateString([], {
                                            year: 'numeric',
                                            month: 'short',
                                            day: 'numeric'
                                        })} - {new Date(segment.arrivalTime).toLocaleTimeString([], {
                                        hour: '2-digit',
                                        minute: '2-digit'
                                    })}
                                    </p>
                                    
                                    <h4 className="inline-text">{(segment.carrierCodeOperating !== '') ? "operating:" : ""}</h4>
                                    <p className="inline-text">{(segment.carrierCodeOperating !== '') ? segment.carrierNameOperating : ''}</p>


                                    <p> Aircraft: {segment.aircraftCode} ({segment.aircraftName})</p>
                                </div>
                                <div className="segment-info">
                                    <p>
                                        {formatDuration(segment.duration)}
                                    </p>
                                </div>

                                <div className="fare-details">
                                    <h3>Travelers fareDetails</h3>
                                    <h4 className="inline-text">Cabin: </h4>
                                    <p className="inline-text">{segment.cabin}</p> <p></p>
                                    <h4 className="inline-text">Class: </h4>
                                    <p className="inline-text">{segment.fareClass}</p> <p></p>
                                    <h4 className="inline-text">{segment.brandedFareLabel ? "Fare:" : ""}</h4>
                                    <p className="inline-text">{segment.brandedFareLabel}</p>
                                    <p>{segment.checkedBagsWeight ? `Bags: ${segment.checkedBagsQuantity} bag${segment.checkedBagsQuantity>1 ? "s" : ""}${segment.checkedBagsWeight} ${segment.weightUnit}` : ""}</p>
                                    {segment.amenities.length >0 ? <h4>Amenities:</h4> : ""}
                                    {segment.amenities.map((amenity: string, index: number) => (
                                        <p key={index.toString()} >{amenity}</p>
                                    ))}
                                </div>
                        </div>)}
                    </div>
                    <div className="right-container">
                        <div className="price-breakdown">
                            <h4>Price Breakdown</h4>
                            <p className="inline-text">{flight.price.currency} {flight.price.total.toFixed(2)}</p>
                            <h4 className="inline-text"> total</h4> <p></p>
                            <p className="inline-text">{flight.price.currency} {flight.price.base.toFixed(2)}</p>
                            <h4 className="inline-text"> base</h4>
                            <h4>Fees: </h4>
                            {flight.price.fees.map((fee: Fee) => (
                                <p key={fee.type}>{fee.type} ${fee.amount} </p>
                            ))}
                            <div className="price-breakdown">
                                <p>Per traveler</p>
                                <p>{flight.price.currency} {(flight.price.total / flight.travelers).toFixed(2)}</p>
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default FlightDetails;
