import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import FlightSearch from './FlightSearch';
import { fetchFlights} from '../api';

jest.mock('../api');

describe('FlightSearch Component', () => {
    const mockOnSearch = jest.fn();

    beforeEach(() => {
        jest.clearAllMocks();
    });

    test('renders form inputs correctly', () => {
        render(<FlightSearch onSearch={mockOnSearch} />);

        expect(screen.getByLabelText(/Departure Airport:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Arrival Airport:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Departure Date:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Return Date:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Currency:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Non-stop/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Adults:/i)).toBeInTheDocument();
    });

    test('updates form fields on user input', () => {
        render(<FlightSearch onSearch={mockOnSearch} />);

        const departureInput = screen.getByLabelText(/Departure Airport:/i);
        fireEvent.change(departureInput, { target: { value: 'JFK' } });
        expect(departureInput).toHaveValue('JFK');

        const arrivalInput = screen.getByLabelText(/Arrival Airport:/i);
        fireEvent.change(arrivalInput, { target: { value: 'LAX' } });
        expect(arrivalInput).toHaveValue('LAX');

        const departureDateInput = screen.getByLabelText(/Departure Date:/i);
        fireEvent.change(departureDateInput, { target: { value: '2023-10-10' } });
        expect(departureDateInput).toHaveValue('2023-10-10');

        const adultsInput = screen.getByLabelText(/Adults:/i);
        fireEvent.change(adultsInput, { target: { value: '2' } });
        expect(adultsInput).toHaveValue(2);
    });

});
