import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';
import { BrowserRouter } from 'react-router-dom';
import { ConfigProvider, Input } from 'antd';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ConfigProvider
      theme={{
        components: {
          Input: {
            hoverBorderColor: '#FCD34D',
            activeBorderColor: '#FCD34D',
            activeShadow: '0 0 0 2px rgba(252,211,77,.28)',
          },
        },
      }}
    >
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </ConfigProvider>
  </StrictMode>,
);
