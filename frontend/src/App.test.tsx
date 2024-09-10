import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders search component initially', () => {
  render(<App />);
  const searchElement = screen.getByText(/Search/i);
  expect(searchElement).toBeInTheDocument();
});

