import { Link } from 'react-router-dom';

export default function Layout({ children }) {
  return (
    <div className="layout">
      <aside className="sidebar">
        <h2>CRM Admin</h2>
        <Link to="/">Dashboard</Link>
        <Link to="/users">Users</Link>
        <Link to="/leads">Leads</Link>
        <Link to="/projects">Projects</Link>
        <Link to="/inventory">Inventory</Link>
        <Link to="/bookings">Bookings</Link>
        <Link to="/payments">Payments</Link>
        <Link to="/reports">Reports</Link>
      </aside>
      <main className="main">{children}</main>
    </div>
  );
}
