import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// For local dev (npm run dev) the gateway on :80 must be running so /api and /auth resolve.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': 'http://localhost',
      '/auth': 'http://localhost',
    },
  },
});
