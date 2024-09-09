import React, {useEffect} from 'react'
import './App.css';
import FlightSearch from './components/FlightSearch';
import { useState } from 'react';
import {Flight} from './customTypes'
import FlightResults from "./components/FlightResults";
import FlightDetails from "./components/FlightDetails";
import Pagination from "./components/Pagination";
import {fetchPaginatedFlights, fetchSortedPaginatedFlights} from "./api";

function App() {

    const [flights, setFlights] = useState<Flight[]>([]);
    const [view, setView] = useState<'search' | 'results' | 'details'>('search');
    const [flight, setFlight] = useState<Flight>()
    const [totalItems, setTotalItems] = useState<number>(0)
    const pageSize = 10;
    const [pageNumber, setPageNumber] = useState(1);

    useEffect(() =>{
        setPageNumber(pageNumber)
    }, [pageNumber]);

    useEffect(() =>{
        setFlights(flights)
    }, [flights]);

    const fetchFlights = async (page: number) => {
        try {
            let flights = await fetchPaginatedFlights(page);
            setFlights(flights)
        } catch (error) {
            console.error(error)
        }

    }

    const sort = async (value: String, checked: boolean) => {
        try {
            let flights = await fetchSortedPaginatedFlights(pageNumber, value, checked);
            setFlights(flights)
            console.log(flights)
        } catch (error) {
            console.error(error)
        }
    };

    const handleSearch = (flights: Flight[], totalItems1: number): void => {
        setFlights(flights)
        setTotalItems(totalItems1)
        setView('results')
    };

    const handleSetPage = (page: number): void => {
        setPageNumber(page)
        fetchFlights(page)
    };

    const handleReturnToSearch = (): void => {
        setView('search')
    };

    const handleReturnToResults = (): void => {
        setView('results')
    };

    const handleShowDetails = (flightSelected: Flight): void => {
        setFlight(flightSelected)
        setView('details')
    };

    return (

        <div className="App">
            {view === 'search' && (<FlightSearch
                onSearch={handleSearch}
            ></FlightSearch>)}

            {view === 'results' && (
                <div>
                    <Pagination
                        setPage={handleSetPage}
                        actualPage={pageNumber}
                        totalPages={Math.ceil(totalItems/pageSize)}></Pagination>
                    <FlightResults
                      flights={flights}
                      onReturnToSearch={handleReturnToSearch}
                      onShowDetails={handleShowDetails}
                      onSort={sort}
                    ></FlightResults>
                </div>)}

            {view === 'details' && (<FlightDetails
                flight={flight}
                onReturnToSearch={handleReturnToSearch}
                onReturnToResults={handleReturnToResults}
            ></FlightDetails>)}

        </div>
    );
}

export default App;
