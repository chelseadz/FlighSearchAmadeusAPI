import React, {useState, FormEvent, useEffect, useRef} from 'react';
import {Money, Flight} from "../customTypes"
import {fetchAirports, fetchFlights} from "../api";
import LoadingSpinner from "./LoadingSpinner";

const FlightSearch = ({ onSearch}: { onSearch: (flights: Flight[], totalItems: number) => void }) => {
    const [departureAirport, setDepartureAirport] = useState<string>('');
    const [arrivalAirport, setArrivalAirport] = useState<string>('');
    const [departureDate, setDepartureDate] = useState<string>('');
    const [returnDate, setReturnDate] = useState<string>('');
    const [currency, setCurrency] = useState<Money>('MXN');
    const [nonStop, setNonStop] = useState<boolean>(false);
    const [adults, setAdults] = useState<number>(1);

    const [loading, setLoading] = useState(false);

    const searchFlights = async () => {
        try {
            setLoading(true);
            let response = await fetchFlights(
                departureAirport,
                arrivalAirport,
                departureDate,
                returnDate,
                currency,
                nonStop,
                adults
            );
            setLoading(false);
            console.log(response);
            onSearch(response.flights, response.totalItems);
        } catch (error) {
            console.error(error)
        }
    };

    const handleSubmit = (event: FormEvent) => {
        event.preventDefault();
        searchFlights()
    }

    const [filteredDepartureAirports, setFilteredDepartureAirports] = useState([]);
    const [filteredArrivalAirports, setFilteredArrivalAirports] = useState([]);


    useEffect(() =>{
        const fetchAndSetDeparture = async () => {
            if(departureAirport && requestCount<3 && departureAirport.length<30) {
                incrementRequestCounter();
                try {
                    const airports: [] = await fetchAirports(departureAirport);
                    setFilteredDepartureAirports(airports);
                    console.log(airports)
                } catch (error) {
                    console.error(error)
                }
            }
        };
        fetchAndSetDeparture()
    }, [departureAirport]);

    useEffect(() =>{
        const fetchAndSetArrival = async () => {
            if (arrivalAirport && requestCount<3 && arrivalAirport.length<30) {
                incrementRequestCounter();
                try {
                    const airports = await fetchAirports(arrivalAirport);
                    setFilteredArrivalAirports(airports);
                    console.log(airports)
                } catch (error) {
                    console.error(error)
                }
            }
        };
        fetchAndSetArrival()
    }, [arrivalAirport]);

    const [requestCount, setRequestCount] = useState<number>(0);
    const requestCountRef = useRef(0);

    const incrementRequestCounter = () => {
        requestCountRef.current += 1;
        setRequestCount(requestCountRef.current);
    };

    useEffect(() => {
        const interval = setInterval(() => {
            setRequestCount(requestCountRef.current);
            requestCountRef.current = 0;
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    const getTodayDate = (): string => {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };


    return (
        <div>
        <form className="search-form" onSubmit={handleSubmit}>
            <div className="form-section">
                    <div className="form-group">
                        <label htmlFor="departureAirport">Departure Airport:</label>
                        <input
                            type="text"
                            id="departureAirport"
                            name="departureAirport"
                            placeholder="Enter departure airport"
                            value={departureAirport}
                            onChange={(e) => setDepartureAirport(e.target.value)}
                            list="departure-airports-list"
                            required
                        />
                        <datalist id="departure-airports-list">
                            {filteredDepartureAirports.map((airport) => (
                                <option key={airport['iata']} value={airport['iata']}>
                                    {airport['iata'] + ': ' + airport['name']}
                                </option>
                            ))}
                        </datalist>
                    </div>

                <div className="form-group">
                        <label htmlFor="arrivalAirport">Arrival Airport:</label>
                        <input
                            type=""
                            id="arrivalAirport"
                            name="arrivalAirport"
                            placeholder="Enter arrival airport"
                            value={arrivalAirport}
                            onChange={(e) => setArrivalAirport(e.target.value)}
                            list="arrival-airports-list"
                            required
                        />
                        <datalist id="arrival-airports-list">
                            {filteredArrivalAirports.map((airport) => (
                                <option key={airport['iata']} value={airport['iata']}>
                                    {airport['iata'] + ': ' + airport['name']}
                                </option>
                            ))}
                        </datalist>
                    </div>
            </div>
            <div className="form-section">
                <div className="form-group">
                    <label htmlFor="departureDate">Departure Date:</label>
                    <input
                        type="date"
                        id="departureDate"
                        name="departureDate"
                        min={getTodayDate()}
                        value={departureDate}
                        onChange={(e) => setDepartureDate(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="returnDate">Return Date:</label>
                    <input
                        type="date"
                        id="returnDate"
                        name="returnDate"
                        min={departureDate}
                        value={returnDate}
                        onChange={(e) => setReturnDate(e.target.value)}
                    />
                </div>
            </div>

            <div className="form-group">
                <label htmlFor="currency">Currency:</label>
                <select
                    id="currency"
                    name="currency"
                    value={currency}
                    onChange={(e) => setCurrency(e.target.value as Money)}
                >
                    <option value="MXN">MXN</option>
                    <option value="USD">USD</option>
                    <option value="EUR">EUR</option>
                </select>
                <div className="checkbox-row">
                    <input type="checkbox" name="nonStop"
                           onChange={(e) => setNonStop(Boolean(e.target.value))}/>
                    <label>Non-stop</label>
                </div>
                <div>
                    <label>Adults:</label>
                    <input type="number" min="1" placeholder="Enter number of adults"
                           onChange={(e) => setAdults(Number(e.target.value))}/>
                </div>
            </div>

            <div>
                {loading ? <LoadingSpinner/> : <button type="submit" className="submitSearch">Search</button>}
            </div>
        </form>
        </div>
    );
};

export default FlightSearch