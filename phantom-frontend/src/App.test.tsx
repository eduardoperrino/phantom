import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';
import * as utils from './Utils';


test('renders learn react link', () => {
  const buildEventSourceSpy = jest.spyOn(utils, 'buildEventSource');

  buildEventSourceSpy.mockReturnValue({
      CLOSED: 0,
      CONNECTING: 0,
      OPEN: 0,
      dispatchEvent(event: Event): boolean {
        return false;
      },
      onerror: jest.fn(),
      onmessage: jest.fn(),
      onopen: jest.fn(),
      readyState: 0,
      url: '',
      withCredentials: false,
      addEventListener(
        type: any,
        listener: any,
        options?: boolean | AddEventListenerOptions
      ): void {},
      close(): void {},
      removeEventListener(
        type: any,
        listener: any,
        options?: boolean | EventListenerOptions
      ): void {}
    });

  render(<App />);
  const containerElement = screen.getByText(/Decision Taken List/i);
  expect(containerElement).toBeInTheDocument();
});
