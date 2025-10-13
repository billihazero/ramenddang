import dotenv from 'dotenv';
import app from './server.js';

dotenv.config();
const PORT = process.env.PORT || 3000;

app.listen(PORT, '0.0.0.0', () => {
  console.log(`${PORT} port 연결 성공`);
});
