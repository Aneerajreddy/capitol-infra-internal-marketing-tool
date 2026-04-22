import { Navigate, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import DashboardPage from './pages/DashboardPage';
import LoginPage from './pages/LoginPage';
import ListPage from './pages/ListPage';
import ReportsPage from './pages/ReportsPage';

export default function App() {
  const loggedIn = Boolean(localStorage.getItem('accessToken'));
  if (!loggedIn) return <LoginPage onDone={() => window.location.reload()} />;

  return (
    <Layout>
      <Routes>
        <Route path="/" element={<DashboardPage />} />
        <Route path="/users" element={<ListPage resource="users" columns={['_id', 'name', 'email', 'mobile']} />} />
        <Route path="/leads" element={<ListPage resource="leads" columns={['_id', 'name', 'phone', 'status']} />} />
        <Route path="/projects" element={<ListPage resource="projects" columns={['_id', 'name', 'location']} />} />
        <Route path="/inventory" element={<ListPage resource="inventory" columns={['_id', 'unitCode', 'unitType', 'availability']} />} />
        <Route path="/bookings" element={<ListPage resource="bookings" columns={['_id', 'receiptNumber', 'status', 'bookingAmount']} />} />
        <Route path="/payments" element={<ListPage resource="payments" columns={['_id', 'receiptNumber', 'amount', 'mode']} />} />
        <Route path="/reports" element={<ReportsPage />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Layout>
  );
}
