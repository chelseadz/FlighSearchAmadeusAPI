import axios from 'axios';

const API_URL = 'http://localhost:8080/';

export const fetchAirports = async (keyword: String, maxAirports: String) => {
    const params = { keyword: keyword, maxAirports: maxAirports};
    const { data } = await axios.get(API_URL+"airports",{
        params:params,
    });
    return data;
}

export const fetchFlights = async (
    departureAirport: String,
    arrivalAirport: String,
    departureDate: String,
    returnDate: String,
    currency: String,
    nonStop: boolean,
    adults: number
) => {
    const params = {
        departureAirport: departureAirport,
        arrivalAirport: arrivalAirport,
        departureDate: departureDate,
        returnDate: returnDate,
        currency: currency,
        nonStop: nonStop,
        adults: adults,
    };
    console.log(params)
    const { data } = await axios.post(API_URL+"flight-offers",{params});
    return data;
}