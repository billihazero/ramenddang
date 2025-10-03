/** @type {import('tailwindcss').Config} */
export default {
  important: true,
  content: ['./index.html', './src/layout/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        ramen: '#FBCE56', // 라멘땅 노란색
      },
    },
  },
  plugins: [],
};
