import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';
import { BrowserRouter } from 'react-router-dom';
import { ConfigProvider, Input } from 'antd';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <ConfigProvider
        theme={{
          components: {
            Input: {
              hoverBorderColor: '#FCD34D',
              activeBorderColor: '#FCD34D',
              activeShadow: '0 0 0 2px rgba(252,211,77,.28)',
            },
            Button: {
              colorPrimary: '#FCD34D',
              colorPrimaryActive: '#FCD34D',
              colorPrimaryHover: '#FCD34D',
            },
          },
        }}
      >
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </ConfigProvider>
    </QueryClientProvider>
  </StrictMode>,
);
