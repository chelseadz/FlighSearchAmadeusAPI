
export type Money = "MXN" | "USD" | "EUR";

export type Airport = {
    iata: string;
    name: string;
};

export type Segment = {
    aircraftName: string;
    amenities: string[];
    weightUnit: string;
    checkedBagsQuantity: number;
    checkedBagsWeight: string;
    carrierName: string;
    carrierNameOperating: string;
    brandedFareLabel?: string;
    arrivalAirportCity: string;
    id: string;
    departureAirport: string;
    departureAirportCity: string;
    departureTime: string;
    arrivalAirport: string;
    arrivalTime: string;
    terminal: string;
    carrierCode: string;
    carrierCodeOperating: string;
    aircraftCode: string;
    duration: string;
    segmentId: string;
    cabin: string;
    fareBasis: string;
    fareClass: string;
}

export type Itinerary = {
    arrivalAirportCity: string;
    departureAirportCity: string;
    stopTime: string;
    departureAirport: string;
    arrivalAirport: string;
    departureTime: string;
    arrivalTime: string;
    duration: string;
    segments: Segment[];
}

export type Fee = { amount: number; type: string }

export type Price = {
    currency: string;
    total: number;
    base: number;
    fees: Fee[];
}

export type Flight = {
    id: string;
    oneWay: boolean;
    numberOfBookableSeats: number;
    travelers: number;
    itineraries: Itinerary[];
    price: Price;
}

