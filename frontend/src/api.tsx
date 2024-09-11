const axios = require('axios');

const API_URL = 'http://localhost:8080/';

export const fetchSortedPaginatedFlights = async (page: number, value: String, checked: boolean) => {
    const params = { page: page, value: value, checked: checked};
    const {data} = await axios.get(API_URL+"sorted-pagination",{
        params:params,
    });
    return data;
}

export const fetchPaginatedFlights = async (page: number) => {
    const params = { page: page};
    const {data} = await axios.get(API_URL+"pagination",{
        params:params,
    });
    return data;
}

export const fetchAirports = async (keyword: String) => {
    const params = { keyword: keyword};
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