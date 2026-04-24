require('express-async-errors');
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const morgan = require('morgan');
const path = require('path');

const env = require('./config/env');
const connectDb = require('./config/db');
const routes = require('./routes');
const errorHandler = require('./middlewares/errorHandler');

const app = express();

app.use(cors());
app.use(helmet());
app.use(morgan('combined'));
app.use(express.json({ limit: '5mb' }));
app.use('/uploads', express.static(path.join(process.cwd(), 'backend', 'uploads')));

app.get('/health', (req, res) => res.json({ status: 'ok' }));
app.use('/api', routes);
app.use(errorHandler);

connectDb().then(() => {
  app.listen(env.port, () => console.log(`API started on ${env.port}`));
});
